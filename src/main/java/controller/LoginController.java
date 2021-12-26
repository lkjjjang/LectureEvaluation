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

@WebServlet("/loginController")
public class LoginController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = null;
		String userPassword = null;
		UserDAO userDAO = new UserDAO();
		
		if (request.getParameter("userID") != null) {
			userID = request.getParameter("userID");
		}
		
		if (request.getParameter("userPassword") != null) {
			userPassword = request.getParameter("userPassword");
		}
		
		if (userID == null || userPassword == null 
				|| userID.equals("") || userID.equals("")) {
			pageBack(request, response, "입력 안 된 사항이 있습니다.");
		}
		
		if (!userDAO.getUserEmailChecked(userID)) {
			pageBack(request, response, "이메일 인증후 사용 가능합니다.");
		}
		
		// 실제 로그인구현부분 
		// db에 아이디, 비번이 일치하면 세션에 userID를 저장시켜 
		// 모든페이지에서 세션값이 있으면 로그인된 상태로 간주
		int result = userDAO.login(userID, userPassword);
		HttpSession session = request.getSession();
		
		if (result == 1) {
			session.setAttribute("userID", userID);
			response.sendRedirect("lectureBoardController");
		} else if (result == 0){
			pageBack(request, response, "비밀번호가 틀렸습니다.");
		} else if (result == -1){
			pageBack(request, response, "존재하지 않는 아이디 입니다.");
		} else if (result == -2){
			pageBack(request, response, "데이터 베이스 오류 입니다.");
		}
	}
	
	private void pageBack(HttpServletRequest request, HttpServletResponse response, String arlertMsg) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + arlertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
}
