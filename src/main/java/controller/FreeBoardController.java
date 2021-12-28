package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;
import evaluation.EvaluationDTO;
import freeBBS.BbsDAO;
import freeBBS.BbsDTO;
import user.UserDAO;

@WebServlet("/freeBoardController")
public class FreeBoardController extends HttpServlet {
	
	public static int freeBBSListPrintCount = 20;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (request.getParameter("pageNumber") == null) {
			pageBack(response, "잘못된 접근 입니다.");
		}
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		if (userID == null) {
			// 로그인 안됨
			pageBack(response, "로그인후 사용 가능합니다.");
		} else {
			// 로그인 됨
			// 게시글 생성
			ArrayList<BbsDTO> bbsList = new ArrayList<BbsDTO>();
			BbsDAO bbsDAO = new BbsDAO();
			bbsList = bbsDAO.getList(pageNumber);
			regDateModify(bbsList);
			
			// 페이징처리를 위해 게시글 갯수 파악
			int listCount = getListCount(request);			
						
			request.setAttribute("freeBBS", bbsList);
			request.setAttribute("listCount", listCount);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("freeBBSListPrintCount", FreeBoardController.freeBBSListPrintCount);
			request.getRequestDispatcher("freeBoard.jsp").forward(request, response);
		}
	}
	
	private void regDateModify(ArrayList<BbsDTO> list) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (BbsDTO dto: list) {
			String[] dtoDate = dto.getBbsDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setBbsDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setBbsDate(dtoDate[0]);
			}
		}
	}
	
	private int getListCount(HttpServletRequest request) {
		BbsDAO bbsDAO = new BbsDAO();
		int listCount = bbsDAO.getListCount();
		int result = listCount / FreeBoardController.freeBBSListPrintCount;
		
		if (listCount % FreeBoardController.freeBBSListPrintCount > 0) {
			result += 1;
		}
		
		return result;
	}
	
	private ArrayList<BbsDTO> getBbsList(HttpServletRequest request, HttpServletResponse response) {
		int pageNumber = 1;
		
		if (request.getParameter("pageNumber") != null) {
			try {
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			} catch (Exception e) {
				System.out.println("검색 페이지 번호 오류");
			}
		}
		
		ArrayList<BbsDTO> list = new ArrayList<BbsDTO>();
		BbsDAO bbsDAO = new BbsDAO();
				
		try {
			list = bbsDAO.getList(pageNumber);
			if (list == null) {
				pageBack(response, "데이터베이스 오류 입니다.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
