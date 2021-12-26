package vote;

public class VoteDTO {
	String userID;
	String bbsID;
	
	public VoteDTO(String userID, String bbsID) {
		super();
		this.userID = userID;
		this.bbsID = bbsID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBbsID() {
		return bbsID;
	}

	public void setBbsID(String bbsID) {
		this.bbsID = bbsID;
	}

	@Override
	public String toString() {
		return "VoteDTO [userID=" + userID + ", bbsID=" + bbsID + "]";
	}
}
