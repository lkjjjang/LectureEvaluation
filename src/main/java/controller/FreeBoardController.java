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
	
	public static int freeBBSListPrintCount = 10;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		if (request.getParameter("pageNumber") == null) {
			pageBack(response, "�߸��� ���� �Դϴ�.");
		}
		
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		if (userID == null) {
			// �α��� �ȵ�
			pageBack(response, "�α����� ��� �����մϴ�.");
		} else {
			// �α��� ��
			// �Խñ� ����
			ArrayList<BbsDTO> bbsList = new ArrayList<BbsDTO>();
			BbsDAO bbsDAO = new BbsDAO();
			bbsList = bbsDAO.getList(pageNumber);
			regDateModify(bbsList);
			
			// ����¡ó���� ���� �Խñ� ���� �ľ�
			int listCount = getListCount(request);			
						
			request.setAttribute("freeBBS", bbsList);
			request.setAttribute("listCount", listCount);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("freeBBSListPrintCount", FreeBoardController.freeBBSListPrintCount);
			request.getRequestDispatcher("freeBoard.jsp").forward(request, response);
		}
	}
	
	private void regDateModify(ArrayList<BbsDTO> list) {
		// �ۼ����� '����'�� �ٸ��� ��,��,�� ��� ������ ��,�� ���
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
				System.out.println("�˻� ������ ��ȣ ����");
			}
		}
		
		ArrayList<BbsDTO> list = new ArrayList<BbsDTO>();
		BbsDAO bbsDAO = new BbsDAO();
				
		try {
			list = bbsDAO.getList(pageNumber);
			if (list == null) {
				pageBack(response, "�����ͺ��̽� ���� �Դϴ�.");
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