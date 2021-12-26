package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;
import evaluation.EvaluationDTO;
import freeBBS.BbsDAO;
import freeBBS.BbsDTO;

@WebServlet("/freeBoardRegisterController")
public class FreeBoardRegisterController extends HttpServlet{
	private String backURL = "freeBoardController?pageNumber=1";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickName = null;
		String password = null;
		String title = null;
		String content = null;
		
		if (request.getParameter("nickName") != null) {
			nickName = request.getParameter("nickName");
		}
		
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		}
		
		if (request.getParameter("title") != null) {
			title = request.getParameter("title");
		}
		
		if (request.getParameter("content") != null) {
			content = request.getParameter("content");
		}
		
		// 글 내용은 html태그가 함께 넘어와 null이 될수 없음
		if (isWhiteSpace(nickName) || isWhiteSpace(password) || isWhiteSpace(title)) {
			pageBack(response, "입력 안 된 사항이 있습니다.");
			return;
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO(0, nickName, password, "NOW()", title, content, 0, 0, 0);
		int result = bbsDAO.write(bbsDTO);
		if (result == -1) {
			pageBack(response, "작성한 글 등록에 실패 했습니다.");
		} else {
			//정상적으로 등록 되었습니다.
			pageBack(response, "정상적으로 등록 되었습니다.", backURL);
		}
	}
	
	private boolean isWhiteSpace(String param) {
		if (param.equals("") || param == null) {
			return true;
		}		
		
		if (param.trim().length() == 0) {
			return true;
		}
		
		return false;
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg, String url) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = '" + url + "'");
		script.println("</script>");
		script.close();
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("history.back()");
		script.println("</script>");
		script.close();
	}
}
