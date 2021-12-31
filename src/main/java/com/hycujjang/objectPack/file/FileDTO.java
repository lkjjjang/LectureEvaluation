package com.hycujjang.objectPack.file;

public class FileDTO {
	private int fileID;
	private int bbsID;
	private String userID;
	private String fileRealName;
	private String fileTempName;
	private String fileLastName;
	
	public FileDTO(String userID, String fileRealName, String fileTempName) {
		this.userID = userID;
		this.fileRealName = fileRealName;
		this.fileTempName = fileTempName;
	}

	public FileDTO(int fileID, int bbsID, String fileTempName, String fileLastName) {
		this.fileID = fileID;
		this.bbsID = bbsID;
		this.fileTempName = fileTempName;
		this.fileLastName = fileLastName;
	}

	public FileDTO(int fileID, int bbsID, String userID, String fileRealName, String fileTempName) {
		super();
		this.fileID = fileID;
		this.bbsID = bbsID;
		this.userID = userID;
		this.fileRealName = fileRealName;
		this.fileTempName = fileTempName;
	}
	
	public String getFileLastName() {
		return fileLastName;
	}
	public void setFileLastName(String fileLastName) {
		this.fileLastName = fileLastName;
	}
	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFileTempName() {
		return fileTempName;
	}
	public void setFileTempName(String fileTempName) {
		this.fileTempName = fileTempName;
	}
	@Override
	public String toString() {
		return "FileDTO [fileID=" + fileID + ", bbsID=" + bbsID + ", userID=" + userID + ", fileRealName="
				+ fileRealName + ", fileTempName=" + fileTempName + "]";
	}
	
}
