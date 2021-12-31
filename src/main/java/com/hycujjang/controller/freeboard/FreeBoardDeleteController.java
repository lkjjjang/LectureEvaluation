package com.hycujjang.controller.freeboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.comment.CommentDAO;
import com.hycujjang.objectPack.file.FileDAO;
import com.hycujjang.objectPack.freeBBS.BbsDAO;
import com.hycujjang.objectPack.reply.ReplyDAO;
import com.hycujjang.objectPack.vote.VoteDAO;

@WebServlet("/freeBoardDeleteController")
public class FreeBoardDeleteController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			pageBack(response, "�α����� ��� �����մϴ�.");
		} 
		
		if (userID.equals("admin")) {
			BufferedReader br = request.getReader();
			String requestData = br.readLine();
			
			deleteAll(requestData);
			
			String resultJson = getJson("ok");
			response.getWriter().write(resultJson);
		} else {
			BufferedReader br = request.getReader();
			String requestData = br.readLine();
			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap = jsonParse(requestData);
			
			// bbsID�� �н����� �޾ƿ� ���� �۾�
			BbsDAO BbsDAO = new BbsDAO();
			String inputPassword = requestMap.get("password");
			String bbsID = requestMap.get("id");
			String bbsPassword = BbsDAO.getContentPassword(bbsID);	

			String resultCode = null;
			if (bbsPassword.equals(inputPassword)) {
				// �Խñ�, ���, ����, ��õ, ���� ����
				if (isDeleteAll(bbsID)) {
					resultCode = "OK";
				} else {
					resultCode = "ERROR";
				}
			} else {
				resultCode = "WRONG_PASS";
			}
				
			String resultJson = getJson(resultCode);
			response.getWriter().write(resultJson);
		}
	}
	
	private void deleteAll(String jsonStr) {
		System.out.println(jsonStr);
		String[] idList = jsonStr.replace(":", "").replace("i", "").replace("d", "").replace("\"", "").replace("{", "").replace("}", "").replace("[", "").replace("]", "").split(",");

		BbsDAO bbsDAO = new BbsDAO();
		CommentDAO commentDAO = new CommentDAO();
		ReplyDAO replyDAO = new ReplyDAO();
		FileDAO fileDAO = new FileDAO();
		VoteDAO voteDAO = new VoteDAO();
		
		bbsDAO.deleteAll(idList);
		commentDAO.deleteAll(idList);
		replyDAO.deleteAll(idList);
		fileDAO.deleteAll(idList);
		voteDAO.deleteAll(idList);
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
	
	private boolean isDeleteAll(String bbsID) {
		BbsDAO BbsDAO = new BbsDAO();
		CommentDAO commmentDAO = new CommentDAO();
		ReplyDAO replyDAO = new ReplyDAO();
		VoteDAO voteDAO = new VoteDAO();
		FileDAO fileDAO = new FileDAO();
		
		int bbs = BbsDAO.delete(bbsID);
		int comment = commmentDAO.deleteAll(bbsID);
		int reply = replyDAO.deleteAll(bbsID);
		int vote = voteDAO.delete(bbsID);
		int file = fileDAO.delete(bbsID);
		if (bbs == -1 || comment == -1 || reply == -1 || vote == -1 || file == -1) {
			return false;
		}
		return true;
	}

	private String getJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// �տ��� �Ѱ��� ������ ���� {id: bbsID, password: password}		
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