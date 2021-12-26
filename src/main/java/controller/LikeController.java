package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;
import likey.LikeyDAO;

@WebServlet("/likecontroller")
public class LikeController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String evaluationID = null;
		
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		if (request.getParameter("evaluationID") != null) {
			evaluationID = request.getParameter("evaluationID");
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		LikeyDAO likeyDAO = new LikeyDAO();
		
		// true:추천 누른글, false:안누름
		boolean likeCheck = likeyDAO.likeCheck(userID, evaluationID);
		String url = getPrevURL(request, evaluationID);		
		
		if (!likeCheck) {
			// 추천테이블에 추천 생성
			int likeResult = likeyDAO.like(userID, evaluationID);
			// 강의테이블에 추천 증가
			int evaluationResult = evaluationDAO.like(evaluationID);
			
			if (likeResult == 1 && evaluationResult == 1) {
				scriptExcute(response, "추천 하였습니다.", url);
			} else {
				scriptExcute(response, "데이터베이스 오류 입니다.", url);
			}
		} else {
			scriptExcute(response, "추천을 누른 글 입니다.", url);
		}	
	}
	
	private void scriptExcute(HttpServletResponse response, String alertMsg, String url) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = '" + url + "'");
		script.println("</script>");
	}
	
	private String getPrevURL(HttpServletRequest request, String evaluationID) {
		String lectureDivide = null;
		String searchType = null;
		String search = null;
		int pageNumber = 1;
		
		if (request.getParameter("lectureDivide") != null) {
			lectureDivide = request.getParameter("lectureDivide");
		}
		
		if (request.getParameter("searchType") != null) {
			searchType = request.getParameter("searchType");
		}
		
		if (request.getParameter("search") != null) {
			search = request.getParameter("search");
		}
		
		if (request.getParameter("pageNumber") != null && !request.getParameter("pageNumber").equals("")) {
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				System.out.println("검색 페이지 번호 오류");
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("lectureBoardController");
		sb.append("?lectureDivide=" + lectureDivide);
		sb.append("&searchType=" + searchType);
		sb.append("&search=" + search);
		sb.append("&pageNumber=" + pageNumber);
		
		return sb.toString();
	}
	
	// 사용안함
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
