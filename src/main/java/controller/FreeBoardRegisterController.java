package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation.EvaluationDAO;
import evaluation.EvaluationDTO;
import file.FileDAO;
import file.FileDTO;
import freeBBS.BbsDAO;
import freeBBS.BbsDTO;
import reply.ReplyDTO;
import util.FileUtils;

@WebServlet("/freeBoardRegisterController")
public class FreeBoardRegisterController extends HttpServlet{
	private String backURL = "freeBoardController?pageNumber=1";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickName = null;
		String password = null;
		String title = null;
		String content = null;
		String userID = null;
		
		if (request.getSession().getAttribute("userID") != null) {
			userID = (String) request.getSession().getAttribute("userID");
		}
		
		if (userID == null) {
			pageBack(response, "로그인후 사용 가능합니다.");
		}
		
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
		
		String[] contentSplit = content.split(" ");			
		String directory = this.getServletContext().getRealPath("/upload/") + getTodayDate();
		String tempDir = this.getServletContext().getRealPath("/tempImg/") + userID;
		File folder = new File(directory);				
		
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		FileUtils fileUtils = new FileUtils();
		// 본문에서 사용된 파일 리스트
		ArrayList<String> usedFileList = fileUtils.getUsedFileList(contentSplit, userID);	
		// 파일이동후 이동한 파일 리스트
		ArrayList<String> reNameList = fileUtils.moveFile(directory, tempDir, usedFileList);	
		// 임시폴더로 되어 있는 이미지 경로를 정식 경로로 바꿔줌
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			content = fileUtils.getModifyedContent(contentSplit, usedFileList, reNameList, userID);
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO(0, nickName, password, "NOW()", title, content, 0, 0, 0, 0);
		int result = bbsDAO.write(bbsDTO);
		int imageUpdateResult = 0;
		int useImageUpdateResult = 0;
		
		// 이미지정보 추가
		FileDAO fileDAO = new FileDAO();	
		if (usedFileList.size() != 0 && reNameList.size() != 0) {
			int bbsID = bbsDAO.getBbsID(bbsDTO);
			useImageUpdateResult = bbsDAO.updateUseImage(bbsID);
			
			for (int i = 0; i < usedFileList.size(); i++) {
				int fileID = fileDAO.getFileID(userID, usedFileList.get(i));
				FileDTO fileDTO = new FileDTO(fileID, bbsID, usedFileList.get(i), reNameList.get(i));
				imageUpdateResult = fileDAO.updateFileName(fileDTO);
			}
		}
		
		if (result == -1 || imageUpdateResult == -1 || useImageUpdateResult == -1) {
			bbsDAO.delete(bbsDTO.getBbsID() + "");			
			pageBack(response, "작성한 글 등록에 실패 했습니다.");
		} else {
			//정상적으로 등록 되었습니다.
			pageBack(response, "정상적으로 등록 되었습니다.", backURL);
		}
	}
	
	private String getTodayDate() {
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(nowTime).toString();
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
