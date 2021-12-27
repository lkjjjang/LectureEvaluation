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
		System.out.println("11111111");
		
		Part part = request.getPart("file");
		String fileName = part.getSubmittedFileName();
		// 앞단에서 넘어온 파일을 받아옴
		InputStream fis = part.getInputStream();
		
		// 경로 설정 예로 물리적인 경로 얻는 방법 "c:/desktop/upload/.... 등등"
		String directory = this.getServletContext().getRealPath("/upload");
		
		// 경로설정시 경로구분자 사용
		String filePath = directory + File.separator + fileName;
		// 출력스트림 파일을 원하는 경로에 붙여넣는 스트림
		FileOutputStream fos = new FileOutputStream(filePath);
		
		// int b = fis.read();
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fis.read(buf)) != -1 ) {
			fos.write(buf, 0, size);
		}
		fos.close();
		fis.close();
		// 경로 루트부터 이미지가 저장 되어 있는 곳 까지 반환
		response.getWriter().write("/hycu/upload/" + fileName);
	}
	
	private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
