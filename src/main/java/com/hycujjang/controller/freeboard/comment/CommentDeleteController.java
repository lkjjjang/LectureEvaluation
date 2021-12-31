package com.hycujjang.controller.freeboard.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.comment.CommentDAO;
import com.hycujjang.enumCollection.ResultCode;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.reply.ReplyDAO;

@WebServlet("/commentDeleteController")
public class CommentDeleteController extends HttpServlet{
	private CommentDAO commentDAO = new CommentDAO();
	private ReplyDAO replyDAO = new ReplyDAO();
	private int bbsID;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		HashMap<String, String> requestMap = new HashMap<String, String>();
		requestMap = jsonParse(requestData);
		
		// commentID로 패스워드 받아와 비교후 작업
		// CommentDAO commentDAO = new CommentDAO();
		String inputPassword = requestMap.get("password");
		String commentID = requestMap.get("id");
		String bbsPassword = this.commentDAO.getPassword(commentID);
		this.bbsID = this.commentDAO.getBbsID(Integer.parseInt(commentID));
		
		ResultCode resultCode = null;		
		if (bbsPassword.equals(inputPassword)) {
			resultCode = deleteAll(commentID);
			if (resultCode == ResultCode.ERROR) {
				String resultJson = parseJson(resultCode.toString());
				response.getWriter().write(resultJson);
				return;
			}
		} else {
			resultCode = ResultCode.WRONG_PASS;
			String resultJson = parseJson(resultCode.toString());
			response.getWriter().write(resultJson);
			return;
		}
		
		
		int totalCommentCount = getCommentCount();
		// 게시글 commentCount 수정
		setBbsCommentCount(totalCommentCount);
		String resultJson = parseJson(resultCode.toString(), totalCommentCount);
		response.getWriter().write(resultJson);
	}
	
	private void setBbsCommentCount(int count) {
		BbsDAO bbsDAO = new BbsDAO();
		bbsDAO.setCommentCountUpdate(count, this.bbsID);
	}
	
	private int getCommentCount() {
		int commentCount = this.commentDAO.getCommentCount(this.bbsID);
		int replyCount = this.replyDAO.getReplyCount(this.bbsID);
		
		return commentCount + replyCount;
	}
	
	private ResultCode deleteAll(String commentID) {
		int comment = this.commentDAO.delete(commentID);
		int reply = this.replyDAO.deletebyComment(commentID);
		
		if (comment == -1 || reply == -1) {
			return ResultCode.ERROR;
		}
		
		return ResultCode.OK;
	}
	
	private String parseJson(String resultCode, int commentCount) {		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"},");
		sb.append("{\"commentCount\":\"");
		sb.append(commentCount);
		sb.append("\"}]");
		return sb.toString();
	}

	private String parseJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// 앞에서 넘겨준 데이터 형식 {id: commnetID, password: password}		
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			result.put(sp[0], sp[1]);
		}

		return result;
	}

}
