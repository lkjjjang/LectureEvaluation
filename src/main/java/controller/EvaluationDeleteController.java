package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;

@WebServlet("/evaluationDeleteController")
public class EvaluationDeleteController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter script = response.getWriter();
		String userID = (String) request.getSession().getAttribute("userID");
		String evaluationID = null;
		
		if (request.getParameter("evaluationID") != null) {
			evaluationID = request.getParameter("evaluationID");
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		String url = getPrevURL(request, evaluationID);
		System.out.print(url);
		
		if (userID.equals(evaluationDAO.getUserID(evaluationID))) {
			int result = new EvaluationDAO().delete(evaluationID);
			if (result == 1) {
				script.println("<script>");
				script.println("alert('삭제가 완료되었습니다.');");
				script.println("location.href = '" + url + "'");
				script.println("</script>");
				script.close();
			} else {
				script.println("<script>");
				script.println("alert('데이터베이스 오류가 발생했습니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
			}
		} else {
			script.println("<script>");
			script.println("alert('자신이 쓴 글만 삭제 가능합니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
	}
	
	private String getPrevURL(HttpServletRequest request, String evaluationID) {
		String lectureDivide = null;
		String searchType = null;
		String search = null;
		String pageNumber = null;
		
		if (request.getParameter("lectureDivide") != null) {
			lectureDivide = request.getParameter("lectureDivide");
		}
		
		if (request.getParameter("searchType") != null) {
			searchType = request.getParameter("searchType");
		}
		
		if (request.getParameter("search") != null) {
			search = request.getParameter("search");
		}
		
		if (request.getParameter("pageNumber") != null) {
			pageNumber = request.getParameter("pageNumber");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("lectureBoardController");
		sb.append("?evaluationID=" + evaluationID);
		sb.append("&lectureDivide" + lectureDivide);
		sb.append("&searchType=" + searchType);
		sb.append("&search=" + search);
		sb.append("&pageNumber=" + pageNumber);
		
		return sb.toString();
	}
	
}
