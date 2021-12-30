<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%!
	public void deleteFolder(String path) {
		File folder = new File(path);
	    try {
			if (!folder.exists()) {
				return;
			}
			
			File[] folder_list = folder.listFiles(); 			
			for (int i = 0; i < folder_list.length; i++) {
			    if (folder_list[i].isFile()) {
			    	folder_list[i].delete();
			    	folder_list[i].getName();
			    } else {
			    	deleteFolder(folder_list[i].getPath());
			    }
			}
			folder.delete();
	    } catch (Exception e) {
	    	e.getStackTrace();
	    }
	}
%>
<%
	String userID = (String) request.getSession().getAttribute("userID");
	String directory = this.getServletContext().getRealPath("/tempImg/") + userID;
	deleteFolder(directory);
	session.invalidate();
%>
<script>
	location.href = 'index.jsp';
</script>