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
		//location = "/tmp", ��� ���� ������ ���� ���ϴ°� �Ϲ���
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, // ���� 1���� �ִ� ������ 5mb
		maxRequestSize = 1024*1024*50*5 // ��ü ������ 25mb�� ���� 		
)
@WebServlet("/uploadSummernoteImageFile")
public class ImgUpload extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("11111111");
		
		Part part = request.getPart("file");
		String fileName = part.getSubmittedFileName();
		// �մܿ��� �Ѿ�� ������ �޾ƿ�
		InputStream fis = part.getInputStream();
		
		// ��� ���� ���� �������� ��� ��� ��� "c:/desktop/upload/.... ���"
		String directory = this.getServletContext().getRealPath("/upload");
		
		// ��μ����� ��α����� ���
		String filePath = directory + File.separator + fileName;
		// ��½�Ʈ�� ������ ���ϴ� ��ο� �ٿ��ִ� ��Ʈ��
		FileOutputStream fos = new FileOutputStream(filePath);
		
		// int b = fis.read();
		byte[] buf = new byte[1024];
		int size = 0;
		while((size = fis.read(buf)) != -1 ) {
			fos.write(buf, 0, size);
		}
		fos.close();
		fis.close();
		// ��� ��Ʈ���� �̹����� ���� �Ǿ� �ִ� �� ���� ��ȯ
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
