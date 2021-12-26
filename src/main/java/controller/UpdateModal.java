package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateModal")
public class UpdateModal extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lectureName = request.getParameter("lectureName").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String professorName = request.getParameter("professorName").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String totalScore = request.getParameter("totalScore").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String evaluationTitle = request.getParameter("evaluationTitle").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String lectureYear = request.getParameter("lectureYear").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String semesterDivide = request.getParameter("semesterDivide").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String evaluationContent = request.getParameter("evaluationContent").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String evaluationID = request.getParameter("evaluationID");
		String creditScore = request.getParameter("creditScore").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String comfortableScore = request.getParameter("comfortableScore").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String lectureScore = request.getParameter("lectureScore").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");
		String lectureDivide = request.getParameter("lectureDivide").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n");

		request.setAttribute("lectureDivide", lectureDivide);
		request.setAttribute("lectureName", lectureName);
		request.setAttribute("professorName", professorName);
		request.setAttribute("totalScore", totalScore);
		request.setAttribute("evaluationTitle", evaluationTitle);
		request.setAttribute("lectureYear", lectureYear);
		request.setAttribute("semesterDivide", semesterDivide);
		request.setAttribute("evaluationContent", evaluationContent);
		request.setAttribute("evaluationID", evaluationID);
		request.setAttribute("creditScore", creditScore);
		request.setAttribute("comfortableScore", comfortableScore);
		request.setAttribute("lectureScore", lectureScore);
		request.getRequestDispatcher("updateModal.jsp").forward(request, response);
	}
}
