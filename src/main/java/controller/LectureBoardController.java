package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;
import evaluation.EvaluationDTO;
import user.UserDAO;

@WebServlet("/lectureBoardController")
public class LectureBoardController extends HttpServlet{
		
	public static int lectureListPrintCount = 10;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (userID == null) {
			// 로그인 안됨
			pageBack(response, "로그인후 사용 가능합니다.");
		} else {
			// 로그인 됨
			ArrayList<EvaluationDTO> list = new ArrayList<EvaluationDTO>();
			// 게시글 생성
			list = creatEvaluationList(request);
			// 페이징처리를 위해 게시글 카운트
			int listCount = getListCount(request);
			
			request.setAttribute("userID", userID);
			request.setAttribute("evaluationList", list);
			request.setAttribute("listCount", listCount);
			request.setAttribute("lectureListPrintCount", LectureBoardController.lectureListPrintCount);
			request.getRequestDispatcher("lectureBoard.jsp").forward(request, response);
		}
	}
	
	
	private int getListCount(HttpServletRequest request) {
		String lectureDivide = "전체";
		String search = "";
		
		if (request.getParameter("lectureDivide") != null) {
			lectureDivide = request.getParameter("lectureDivide");
		}
		
		if (request.getParameter("search") != null) {
			search = request.getParameter("search");
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		int listCount = evaluationDAO.getListCount(lectureDivide, search);
		int result = listCount / LectureBoardController.lectureListPrintCount;
		
		if (listCount % LectureBoardController.lectureListPrintCount > 0) {
			result += 1;
		}
		
		return result;
	}
	
	private ArrayList<EvaluationDTO> creatEvaluationList(HttpServletRequest request) {
		String lectureDivide = "전체";
		String searchType = "최신순";
		String search = "";
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
		
		if (request.getParameter("pageNumber") != null) {
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				System.out.println("검색 페이지 번호 오류");
			}
		}
		
		ArrayList<EvaluationDTO> evaluationList = new ArrayList<EvaluationDTO>();
		evaluationList = new EvaluationDAO().getList(lectureDivide, searchType, search, pageNumber);
	
		return evaluationList;
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
}
