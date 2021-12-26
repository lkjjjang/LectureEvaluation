package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;
import user.UserDTO;
import util.SHA256;

@WebServlet("/userRegisterController")
public class UserRegisterController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String userPassword = null;
		String userEmail = null;
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		if (request.getParameter("userPassword") != null) {
			userPassword = request.getParameter("userPassword");
		}
		
		if (request.getParameter("userEmail") != null) {
			userEmail = request.getParameter("userEmail");
		}
		
		if (userID == "" || userPassword == "" || userEmail == "" 
				|| userID == null || userPassword == null || userEmail == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
		
		// 학교 이메일 검사
		String[] mailCheck = userEmail.split("@");
		if (!mailCheck[1].equals("hycu.ac.kr")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('한사대 이메일만 이용 가능 합니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		
		UserDAO user = new UserDAO();
		UserDTO userDTO = new UserDTO(userID, userPassword, userEmail, SHA256.getSHA256(userEmail), false);
		int result = user.join(userDTO);
		PrintWriter script = response.getWriter();
		if (result == -1) {
			script.println("<script>");
			script.println("alert('이미 존재하는 아이디 입니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		} else {		
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);
			request.setAttribute("userID", userID);
			RequestDispatcher dispatcher = request.getRequestDispatcher("emailSendAction");
			dispatcher.forward(request, response);	
		}
	}
}
