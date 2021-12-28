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
		//location = "/tmp", 경로 문제 때문에 설정 안하는게 일반적
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, // 파일 1개의 최대 사이즈 5mb
		maxRequestSize = 1024*1024*50*5 // 전체 사이즈 25mb로 제한 		
)
@WebServlet("/uploadSummernoteImageFile")
public class ImgUpload extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// multiPartRequest 안됨 하지마!!!!
		
		Part part = request.getPart("file");
		String userID = request.getParameter("userID");		
		String fileName = part.getSubmittedFileName();
		
		// 앞단에서 넘어온 파일을 받아옴
		InputStream fis = part.getInputStream();
		
		// 경로 설정 물리적인 경로 얻는 방법 예)"c:/desktop/upload/.... 등등"
		// 기존 tempImg 폴더 하위에 userID 폴더를 만들어 이미지 저장 
		// 만들어진 폴더는 글 생성시 삭제되고 파일들은 upload/일자 폴더로 이동
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
		
		// 경로설정시 경로구분자 사용, 최종 경로임
		String filePath = directory + File.separator + fileName;
		// 출력스트림 파일을 원하는 경로에 붙여넣는 스트림
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
