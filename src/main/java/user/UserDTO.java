package user;

public class UserDTO {
	private String userID;
	private String usgerPassword;
	private String userEmail;
	private String userEmailHash;
	private boolean userEmailChecked;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String userID, String usgerPassword, String userEmail, String userEmailHash,
			boolean userEmailChecked) {
		this.userID = userID;
		this.usgerPassword = usgerPassword;
		this.userEmail = userEmail;
		this.userEmailHash = userEmailHash;
		this.userEmailChecked = userEmailChecked;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUsgerPassword() {
		return usgerPassword;
	}
	public void setUsgerPassword(String usgerPassword) {
		this.usgerPassword = usgerPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserEmailHash() {
		return userEmailHash;
	}
	public void setUserEmailHash(String userEmailHash) {
		this.userEmailHash = userEmailHash;
	}
	public boolean isUserEmailChecked() {
		return userEmailChecked;
	}
	public void setUserEmailChecked(boolean userEmailChecked) {
		this.userEmailChecked = userEmailChecked;
	}
	
	
	
	
}
