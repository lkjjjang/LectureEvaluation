package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;
import util.Gmail;
import util.SHA256;

@WebServlet("/emailSendAction")
public class EmailSendController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();	
		String userID = request.getParameter("userID");
		
		boolean emailChecked = userDAO.getUserEmailChecked(userID);
		PrintWriter script = response.getWriter();
		if (emailChecked == true) {
			script.println("<script>");
			script.println("alert('이미 인증 된 회원입니다.');");
			script.println("location.href = 'index.jsp'");
			script.println("</script>");
			script.close();
		}
		
		String host = "http://localhost:8080/hycu/";
		String from = "lkjjjang1985@gmail.com";
		String to = userDAO.getUserEmail(userID);
		String subject = "강의평가를 위한 이메일 인증 메일입니다.";
		String content = "다음 링크에 접속하여 이메일 인증을 진행하세요." +
			"<a href='" + host + "emailCheckAction?code=" + SHA256.getSHA256(to) + "'>이메일 인증하기</a>";
	
		Properties p = new Properties();
		
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.startssl.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		try {
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html;charset=UTF8");		
					
			Transport.send(msg);
			
		} catch (Exception e) {
			script.println("<script>");
			script.println("alert('오류가 발생했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		
		script.println("<script>");
		script.println("location.href = 'emailSendAction.jsp'");
		script.println("</script>");
		script.close();
	}
}
