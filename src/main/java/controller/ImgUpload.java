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

import file.FileDAO;
import file.FileDTO;
import util.FileUtils;

@MultipartConfig(
		//location = "/tmp", 경로 문제 때문에 설정 안하는게 일반적
		fileSizeThreshold = 1024*1024,
		maxFileSize = 1024*1024*50, // 파일 1개의 최대 사이즈 5mb
		maxRequestSize = 1024*1024*50*5 // 전체 사이즈 25mb로 제한 		
)
@WebServlet("/uploadSummernoteImageFile")
public class ImgUpload extends HttpServlet{
	private final long maxSize = 1024 * 1024 * 10;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// multiPartRequest 안됨 하지마!!!!
		Part part = request.getPart("file");
		long inputFileSize = part.getSize();
		if (inputFileSize > this.maxSize) {
			writeResponse(response);
			return;
		}
		
		String userID = request.getParameter("userID");		
		String orifileName = part.getSubmittedFileName();	
		
		// 앞단에서 넘어온 파일을 받아옴
		// summernote 자체에서 이미지외 파일은 걸러줌
		InputStream fis = part.getInputStream();
		
		// 경로 설정 물리적인 경로 얻는 방법 예)"c:/desktop/upload/.... 등등"
		// 기존 tempImg 폴더 하위에 userID 폴더를 만들어 이미지 저장 
		// 만들어진 폴더는 글 생성시 삭제되고 파일들은 upload/일자 폴더로 이동
		String directory = this.getServletContext().getRealPath("/tempImg/") + userID;
		FileUtils fileUtils = new FileUtils(directory, orifileName);		
		
		File folder = new File(directory);		
		if (!folder.exists()) {
			folder.mkdir();
		} else {
			long uploadedFileSize = fileUtils.getFolderSize();
			if (inputFileSize + uploadedFileSize > this.maxSize) {
				writeResponse(response);
				return;
			}
		}

		if (fileUtils.containsFileName()) {
			fileUtils.changeFileName();
		}

		directory = fileUtils.getDirectory();
		String newfileName = fileUtils.getFileName();
		// 경로설정시 경로구분자 사용, 최종 경로임
		String filePath = directory + File.separator + newfileName;
		// 출력스트림을 이용 파일을 원하는 경로에 붙여넣음
		FileOutputStream fos = new FileOutputStream(filePath);
		try {
			byte[] buf = new byte[1024];
			int size = 0;
			while((size = fis.read(buf)) != -1 ) {
				fos.write(buf, 0, size);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// 이미지정보 db저장
		FileDAO fileDAO = new FileDAO();
		FileDTO fileDTO = new FileDTO(userID, orifileName, newfileName);
		fileDAO.uploadTempFileName(fileDTO);
	
		// url을 json으로 보내면 ajax에서 아예 먹통됨 오류로 인식못함 아무런 응답이 없음
		// 이미지링크를 ajax에 보내고 싶을땐 서버단에서 ContentType 지정하지 않고 기본 type으로 url만 보내줌 (이렇게 해야 img태크를 자동으로 붙여줌)
		// 제작자가 원하는 성공이 아닌 다른 대체 응답을 보내고 싶을땐 ContentType 을 json으로 지정후 상황에 맞춰 보내줌
		// 컨텍스트명을 / 로 변경했기때문에 hycu(컨텍스트명)는 url에서 제거함
		String url = "/tempImg/" + userID + File.separator + newfileName;
		response.getWriter().write(url);
	}
	
	private void writeResponse(HttpServletResponse response) throws IOException {
		String result = "[{\"resultCode\":\"capacityFull\"}, {\"msg\":\"데이터는 10mb 까지 사용 가능합니다.\"}]";
		response.setContentType("application/json charset=utf-8");
		response.getWriter().write(result);
	}
}
