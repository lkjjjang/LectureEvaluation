package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import comment.CommentDAO;
import comment.CommentDTO;
import enumCollection.PostRequestDivide;
import freeBBS.BbsDAO;
import reply.ReplyDAO;
import reply.ReplyDTO;

@WebServlet("/commentRegisterController")
public class CommentRegisterController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		Gson gson = new Gson();

		// json으로 넘어온 request를 gson이 분해하고 commentDTO를 생성
		CommentDTO commentDTO = gson.fromJson(requestData, CommentDTO.class);
		
		// 입력값 검사
		if (commentDTO.getWriteID().equals("") || commentDTO.getPassword().equals("") || commentDTO.getComment().equals("")) {
			response.getWriter().write("error");
			return;
		}

		// 아이피 생성
		commentDTO.setIp(getClientIP(request));

		// 댓글테이블에 저장
		CommentDAO commentDAO = new CommentDAO();
		int result = commentDAO.write(commentDTO);
		if (result != 1) {
			response.getWriter().write("error");
			return;
		}
		BbsDAO.bbsCommentCountUp(commentDTO.getBbsID());
		
		// 댓글목록 가져옴
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = commentDAO.getList(commentDTO.getBbsID());
		commentRegDateModify(commentList);
		
		// 대댓글목록 생성
		ReplyDAO replyDAO = new ReplyDAO();
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = replyDAO.getList(commentDTO.getBbsID());		
		replyRegDateModify(replyList);	
		
		// 댓글목록에 대댓글 삽입
		for (CommentDTO comment: commentList) {
			for (ReplyDTO reply: replyList) {
				if (comment.getCommentID() == reply.getCommentID()) {					
					comment.addReplyList(reply);
				}
			}
		}
		
		// dto개체를 json으로 변환
		String jsonResult = gson.toJson(commentList);
		// 요청에 응답해줌
		response.getWriter().write(jsonResult);
	}
	
	private void commentRegDateModify(ArrayList<CommentDTO> comments) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (CommentDTO dto: comments) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
	
	private void replyRegDateModify(ArrayList<ReplyDTO> list) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (ReplyDTO dto: list) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
	
	private String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("Procy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
