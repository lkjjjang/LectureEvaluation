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
			pageBack(response, "�α����� ��� �����մϴ�.");
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
		
		// �� ������ html�±װ� �Բ� �Ѿ�� null�� �ɼ� ����
		if (isWhiteSpace(nickName) || isWhiteSpace(password) || isWhiteSpace(title)) {
			pageBack(response, "�Է� �� �� ������ �ֽ��ϴ�.");
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
		// �������� ���� ���� ����Ʈ
		ArrayList<String> usedFileList = fileUtils.getUsedFileList(contentSplit);	
		// �����̵��� �̵��� ���� ����Ʈ
		ArrayList<String> reNameList = fileUtils.moveFile(directory, tempDir, usedFileList);	
		// �ӽ������� �Ǿ� �ִ� �̹��� ��θ� �� ��η� �ٲ���
		content = fileUtils.getModifyedContent(contentSplit, usedFileList, reNameList);
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO(0, nickName, password, "NOW()", title, content, 0, 0, 0);
		int result = bbsDAO.write(bbsDTO);
		if (result == -1) {
			pageBack(response, "�ۼ��� �� ��Ͽ� ���� �߽��ϴ�.");
		} else {
			//���������� ��� �Ǿ����ϴ�.
			pageBack(response, "���������� ��� �Ǿ����ϴ�.", backURL);
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
	/*
	ArrayList<FileInputStream> inputStreams = new ArrayList<FileInputStream>();
	for (String flist: usedFileList) {
		String path = tempDir + File.separator + flist;
		FileInputStream file = new FileInputStream(path);
		inputStreams.add(file);
	}
	
	ArrayList<FileOutputStream> outputStreams = new ArrayList<FileOutputStream>();
	for (String flist: usedFileList) {
		String path = directory + File.separator + flist;
		FileOutputStream file = new FileOutputStream(path);
		outputStreams.add(file);
	}
	
	int index = 0;
	for (FileInputStream fis: inputStreams) {
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fis.read(buf)) != -1 ) {
			outputStreams.get(index).write(buf, 0, size);
		}
		outputStreams.get(index).flush();
		outputStreams.get(index).close();
		fis.close();
		index++;
	}
	*/
}
