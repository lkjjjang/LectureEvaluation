package util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {
	private String directory;
	private String fileName;
	private final String rootPath = "src=\"/hycu/tempImg/1111";
	
	public FileUtils() {		
	}
	public FileUtils(String directory, String fileName) {
		this.directory = directory;
		this.fileName = fileName;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Set<String> getOriFiles(String[] content) {
		Set<String> result = new HashSet<String>();
		
		for (int i = 0; i < content.length; i++) {
			if (content[i].length() < 20) {
				continue;
			}
			
			int separator = content[i].lastIndexOf(File.separator);			
			String rootName = content[i].substring(0, separator);
			
			if (!rootName.equals(this.rootPath)) {
				continue;
			}
			
			int root = 1;
			String[] contentSplit = content[i].split("=");
			result.add(contentSplit[root].replace("\"", ""));
		}
		return result;
	}

	public ArrayList<String> getUsedFileList(String[] content) {
		ArrayList<String> result = new ArrayList<String>();		
		String fileName = "";
		
		for (int i = 0; i < content.length; i++) {
			if (content[i].length() < 20) {
				continue;
			}
			
			int separator = content[i].lastIndexOf(File.separator);			
			String rootName = content[i].substring(0, separator);
			
			if (!rootName.equals(this.rootPath)) {
				continue;
			}
						
			fileName = content[i].substring(separator + 1, content[i].length() - 1);
			result.add(fileName);
		}
		return result;
	}
	
	public String getChangeFileName(String directory, String name) {
		String result = "";
		int front = name.lastIndexOf("(");
		int back = name.lastIndexOf(")");
		int dot = name.lastIndexOf(".");
		// 중복이 있을경우에만 진입함
		// 본 프로그램에서 지정한 중복정책이 아닐경우 (1) 을 붙여 종료
		if (dot - back != 1 || front == -1 || back == -1) {
			String[] sp = name.split("\\.");
			result = sp[0] + "(1)." + sp[1];
			return getChangeFileName(directory, result);
		}
		
		while (containsFileName(directory, name)) {
			int num = Integer.parseInt(name.substring(front + 1, back));
			int nameInx = 0;
			int extInx = 1;
			
			String[] sp = name.split("\\.");
			int bracketFront = sp[nameInx].lastIndexOf("(");
			String reName = sp[nameInx].substring(0, bracketFront);
			String reNum = "(" + (num + 1) + ").";
			
			name = reName + reNum + sp[extInx];
		}
		return name;
	}
	
	public boolean containsFileName(String directory, String name) {
		File dir = new File(directory);
		File files[] = dir.listFiles();
		
		for (File list: files) {
			String listStr = list.toString();
			int subStrStart = listStr.lastIndexOf(File.separator) + 1;
			String fileName = listStr.substring(subStrStart, listStr.length()); 
			if (fileName.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void changeFileName() {
		int front = this.fileName.lastIndexOf("(");
		int back = this.fileName.lastIndexOf(")");
		int dot = this.fileName.lastIndexOf(".");
		// 중복이 있을경우에만 진입함
		// 본 프로그램에서 지정한 중복정책이 아닐경우 (1) 을 붙여 종료
		if (dot - back != 1 || front == -1 || back == -1) {
			String[] sp = this.fileName.split("\\.");
			setFileName(sp[0] + "(1)." + sp[1]);
			changeFileName();
		}
		
		while (containsFileName()) {
			int num = Integer.parseInt(this.fileName.substring(front + 1, back));
			int name = 0;
			int ext = 1;
			
			String[] sp = this.fileName.split("\\.");
			int bracketFront = sp[name].lastIndexOf("(");
			String reName = sp[name].substring(0, bracketFront);
			String reNum = "(" + (num + 1) + ").";
			
			setFileName(reName + reNum + sp[ext]);
		}
	}
	
	public boolean containsFileName() {
		File dir = new File(this.directory);
		File files[] = dir.listFiles();
		
		for (File list: files) {
			String listStr = list.toString();
			int subStrStart = listStr.lastIndexOf(File.separator) + 1;
			String fileName = listStr.substring(subStrStart, listStr.length()); 
			if (fileName.equals(this.fileName)) {
				return true;
			}
		}
		return false;
	}
	
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
}
