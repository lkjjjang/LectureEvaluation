package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import util.FileUtils;

@MultipartConfig(
		//location = "/tmp", ��� ���� ������ ���� ���ϴ°� �Ϲ���
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, // ���� 1���� �ִ� ������ 5mb
		maxRequestSize = 1024*1024*50*5 // ��ü ������ 25mb�� ���� 		
)
@WebServlet("/uploadSummernoteImageFile")
public class ImgUpload extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// multiPartRequest �ȵ� ������!!!!
		
		Part part = request.getPart("file");
		String userID = request.getParameter("userID");		
		String fileName = part.getSubmittedFileName();
		
		// �մܿ��� �Ѿ�� ������ �޾ƿ�
		InputStream fis = part.getInputStream();
		
		// ��� ���� �������� ��� ��� ��� ��)"c:/desktop/upload/.... ���"
		// ���� tempImg ���� ������ userID ������ ����� �̹��� ���� 
		// ������� ������ �� ������ �����ǰ� ���ϵ��� upload/���� ������ �̵�
		String directory = this.getServletContext().getRealPath("/tempImg/") + userID;
		
		File folder = new File(directory);		
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		FileUtils fileUtils = new FileUtils(directory, fileName);
		if (fileUtils.containsFileName()) {
			fileUtils.changeFileName();
		}
		
		directory = fileUtils.getDirectory();
		fileName = fileUtils.getFileName();
		
		// ��μ����� ��α����� ���, ���� �����
		String filePath = directory + File.separator + fileName;
		// ��½�Ʈ�� ������ ���ϴ� ��ο� �ٿ��ִ� ��Ʈ��
		FileOutputStream fos = new FileOutputStream(filePath);
		
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fis.read(buf)) != -1 ) {
			fos.write(buf, 0, size);
		}
		fos.flush();
		fos.close();
		fis.close();
		
		response.getWriter().write("/hycu/tempImg/" + userID + File.separator + fileName);
	}
}
