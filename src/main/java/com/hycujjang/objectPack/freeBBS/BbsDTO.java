package com.hycujjang.objectPack.freeBBS;

public class BbsDTO {

	private int bbsID;
	private String nickName;
	private String password;
	private String bbsDate;
	private String bbsTitle;
	private String bbsContente;
	private int bbsHit;
	private int bbsUpvote;
	private int commentCount;
	private int useImage;
	
	public BbsDTO() {}
	
	public BbsDTO(int bbsID, String nickName, String password, String bbsDate, String bbsTitle, String bbsContente,
			int bbsHit, int bbsUpvote, int commentCount, int useImage) {
		super();
		this.bbsID = bbsID;
		this.nickName = nickName;
		this.password = password;
		this.bbsDate = bbsDate;
		this.bbsTitle = bbsTitle;
		this.bbsContente = bbsContente;
		this.bbsHit = bbsHit;
		this.bbsUpvote = bbsUpvote;
		this.commentCount = commentCount;
		this.useImage = useImage;
	}
	
	public int getUseImage() {
		return useImage;
	}
	public void setUseImage(int useImage) {
		this.useImage = useImage;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBbsDate() {
		return bbsDate;
	}
	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}
	public String getBbsTitle() {
		return bbsTitle;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	public String getBbsContente() {
		return bbsContente;
	}
	public void setBbsContente(String bbsContente) {
		this.bbsContente = bbsContente;
	}
	public int getBbsHit() {
		return bbsHit;
	}
	public void setBbsHit(int bbsHit) {
		this.bbsHit = bbsHit;
	}
	public int getBbsUpvote() {
		return bbsUpvote;
	}
	public void setBbsUpvote(int bbsUpvote) {
		this.bbsUpvote = bbsUpvote;
	}

	@Override
	public String toString() {
		return "BbsDTO [bbsID=" + bbsID + ", nickName=" + nickName + ", password=" + password + ", bbsDate=" + bbsDate
				+ ", bbsTitle=" + bbsTitle + ", bbsContente=" + bbsContente + ", bbsHit=" + bbsHit + ", bbsUpvote="
				+ bbsUpvote + ", commentCount=" + commentCount + "]";
	}
	
	
	
}
