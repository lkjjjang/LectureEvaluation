package file;

public class FileDTO {
	private int fileID;
	private int bbsID;
	private String userID;
	private String fileRealName;
	private String fileNewName;

	public FileDTO(int fileID, int bbsID, String userID, String fileRealName, String fileNewName) {
		super();
		this.fileID = fileID;
		this.bbsID = bbsID;
		this.userID = userID;
		this.fileRealName = fileRealName;
		this.fileNewName = fileNewName;
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
	public String getFileNewName() {
		return fileNewName;
	}
	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}
	@Override
	public String toString() {
		return "FileDTO [fileID=" + fileID + ", bbsID=" + bbsID + ", userID=" + userID + ", fileRealName="
				+ fileRealName + ", fileNewName=" + fileNewName + "]";
	}
	
}
