package com.hycujjang.objectPack.likey;

public class LikeyDTO {
	private String userID;
	private int evaluationID;
	
	public LikeyDTO() {
		
	}
	
	public LikeyDTO(String userID, int evaluationID) {
		super();
		this.userID = userID;
		this.evaluationID = evaluationID;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getEvaluationID() {
		return evaluationID;
	}
	public void setEvaluationID(int evaluationID) {
		this.evaluationID = evaluationID;
	}

	@Override
	public String toString() {
		return "LikeyDTO [userID=" + userID + ", evaluationID=" + evaluationID + "]";
	}

	
	
}
