package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;
import util.SHA256;

@WebServlet("/emailCheckAction")
public class EmailCheckController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		UserDAO userDAO = new UserDAO();	
		String userID = null;
		String code = null;
		
		if (request.getParameter("code") != null) {
			code = request.getParameter("code");
		}
		
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		String userEmail = userDAO.getUserEmail(userID);
		boolean isRight = false;
		if (SHA256.getSHA256(userEmail).equals(code)) {
			isRight = true;
		}
		
		if (isRight == true) {
			userDAO.setUserEmailChecked(userID);
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('인증에 성공했습니다.');");
			script.println("location.href = 'index.jsp'");
			script.println("</script>");
			script.close();
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 코드 입니다.');");
			script.println("location.href = 'index.jsp'");
			script.println("</script>");
			script.close();	
		}
	}
}
