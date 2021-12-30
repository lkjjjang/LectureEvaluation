package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import com.google.gson.Gson;

import comment.CommentDAO;
import file.FileDAO;
import freeBBS.BbsDAO;
import reply.ReplyDAO;
import vote.VoteDAO;
@WebServlet("/freeBoardDeleteController")
public class FreeBoardDeleteController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");

		if (userID == null) {
			pageBack(response, "로그인후 사용 가능합니다.");
		} else if (userID.equals("admin")) {
			deleteAll(request.getParameterValues("delCheck_id"));
			response.sendRedirect("/hycu/freeBoardController?pageNumber=1");
		} else {
			BufferedReader br = request.getReader();
			String requestData = br.readLine();
			HashMap<String, String> requestMap = new HashMap<String, String>();
			requestMap = jsonParse(requestData);
			
			// bbsID로 패스워드 받아와 비교후 작업
			BbsDAO BbsDAO = new BbsDAO();
			String inputPassword = requestMap.get("password");
			String bbsID = requestMap.get("id");
			String bbsPassword = BbsDAO.getContentPassword(bbsID);	

			String resultCode = null;
			if (bbsPassword.equals(inputPassword)) {
				// 게시글, 댓글, 대댓글, 추천 삭제
				if (isDeleteAll(bbsID)) {
					resultCode = "OK";
				} else {
					resultCode = "ERROR";
				}
			} else {
				resultCode = "WRONG_PASS";
			}
				
			String resultJson = getJson(resultCode);
			System.out.println("aaaaaaaaa : " + resultJson);
			response.getWriter().write(resultJson);
		}
	}
	
	private void deleteAll(String[] delIDs) {
		BbsDAO bbsDAO = new BbsDAO();
		CommentDAO commentDAO = new CommentDAO();
		ReplyDAO replyDAO = new ReplyDAO();
		FileDAO fileDAO = new FileDAO();
		VoteDAO voteDAO = new VoteDAO();
		
		bbsDAO.deleteAll(delIDs);
		commentDAO.deleteAll(delIDs);
		replyDAO.deleteAll(delIDs);
		fileDAO.deleteAll(delIDs);
		voteDAO.deleteAll(delIDs);
	}
	
	private void pageBack(HttpServletResponse response, String alertMsg) throws IOException {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + alertMsg + "');");
		script.println("location.href = 'index2.jsp'");
		script.println("</script>");
		script.close();
	}
	
	private boolean isDeleteAll(String bbsID) {
		BbsDAO BbsDAO = new BbsDAO();
		CommentDAO commmentDAO = new CommentDAO();
		ReplyDAO replyDAO = new ReplyDAO();
		VoteDAO voteDAO = new VoteDAO();
		
		int bbs = BbsDAO.delete(bbsID);
		int comment = commmentDAO.deleteAll(bbsID);
		int reply = replyDAO.deleteAll(bbsID);
		int vote = voteDAO.delete(bbsID);
		
		if (bbs == -1 || comment == -1 || reply == -1 || vote == -1) {
			return false;
		}
		return true;
	}

	private String getJson(String resultCode) {		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"resultCode\":\"");
		sb.append(resultCode);
		sb.append("\"}]");
		
		return sb.toString();
	}
	
	private HashMap<String, String> jsonParse(String json) {
		// 앞에서 넘겨준 데이터 형식 {id: bbsID, password: password}		
		HashMap<String, String> result = new HashMap<String, String>();
		String markRemove = json.replace("{", "").replace("}", "").replace("\"", "");
		String[] split = markRemove.split(",");
		
		for (String cha: split) {
			String[] sp = cha.split(":");
			result.put(sp[0], sp[1]);
		}

		return result;
	}

}
