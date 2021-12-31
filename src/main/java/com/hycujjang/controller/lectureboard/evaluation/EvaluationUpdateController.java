package com.hycujjang.controller.lectureboard.evaluation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hycujjang.objectPack.evaluaion.EvaluationDAO;
import com.hycujjang.objectPack.evaluaion.EvaluationDTO;

@WebServlet("/evaluationUpdateController")
public class EvaluationUpdateController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String lectureName = null;
		String professorName = null;
		int lectureYear = 0;
		String semesterDivide = null;
	    String lectureDivide = null;
		String evaluationTitle = null;
		String evaluationContent = null;
		String totalScore = null;
		String creditScore = null;
		String comfortableScore = null;
		String lectureScore = null;
		int evaluationID = 0;
		
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		if (request.getParameter("lectureName") != null) {
			lectureName = request.getParameter("lectureName");
		}
		
		if (request.getParameter("professorName") != null) {
			professorName = request.getParameter("professorName");
		}
		
		if (request.getParameter("lectureYear") != null) {
			try {
				lectureYear = Integer.parseInt(request.getParameter("lectureYear"));
			} catch (Exception e) {
				System.out.print("강의년도 데이터 오류");
			}
		}
		
		if (request.getParameter("evaluationID") != null) {
			try {
				evaluationID = Integer.parseInt(request.getParameter("evaluationID"));
			} catch (Exception e) {
				System.out.print("평가글 번호 오류");
			}
		}
		
		if (request.getParameter("semesterDivide") != null) {
			semesterDivide = request.getParameter("semesterDivide");
		}
		
		if (request.getParameter("lectureDivide") != null) {
			lectureDivide = request.getParameter("lectureDivide");
		}
		
		if (request.getParameter("evaluationTitle") != null) {
			evaluationTitle = request.getParameter("evaluationTitle");
		}
		
		if (request.getParameter("evaluationContent") != null) {
			evaluationContent = request.getParameter("evaluationContent");
		}
		
		if (request.getParameter("totalScore") != null) {
			totalScore = request.getParameter("totalScore");
		}
		
		if (request.getParameter("creditScore") != null) {
			creditScore = request.getParameter("creditScore");
		}
		
		if (request.getParameter("comfortableScore") != null) {
			comfortableScore = request.getParameter("comfortableScore");
		}
		
		if (request.getParameter("lectureScore") != null) {
			lectureScore = request.getParameter("lectureScore");
		}
		
		if (lectureName == null || professorName == null || lectureYear == 0 
				|| semesterDivide == null || lectureDivide == null || evaluationTitle == null 
				|| evaluationContent == null || totalScore == null || creditScore == null 
				|| comfortableScore == null || lectureScore == null
				|| evaluationTitle.equals("") || evaluationContent.equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		EvaluationDTO evaluationDTO = new EvaluationDTO(evaluationID, userID, lectureName, professorName, lectureYear, 
				semesterDivide, lectureDivide, evaluationTitle, evaluationContent, 
				totalScore, creditScore, comfortableScore, lectureScore, 0);
		
		int result = evaluationDAO.update(evaluationDTO);
		PrintWriter script = response.getWriter();
		if (result == -1) {
			script.println("<script>");
			script.println("alert('강의평가 등록에 실패 했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		} else {
			script.println("<script>");
			script.println("alert('정상적으로 등록 되었습니다.');");
			script.println("location.href='lectureBoardController'");
			script.println("</script>");
			script.close();
		}
	}
}
