package com.hycujjang.objectPack.reply;

public class ReplyDTO {
	private int replyID;
	private int commentID;
	private int bbsID;
	private String nickName;
	private String password;
	private String replyComment;
	private String regDate;
	
	public ReplyDTO() {
		
	}
	
	public ReplyDTO(int commentID, int bbsID, String nickName, String password, String replyComment) {
		super();
		this.commentID = commentID;
		this.bbsID = bbsID;
		this.nickName = nickName;
		this.password = password;
		this.replyComment = replyComment;
	}

	public ReplyDTO(int replyID, int commentID, int bbsID, String nickName, String password,
			String replyComment, String regDate) {
		super();
		this.replyID = replyID;
		this.commentID = commentID;
		this.bbsID = bbsID;
		this.nickName = nickName;
		this.password = password;
		this.replyComment = replyComment;
		this.regDate = regDate;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getReplyComment() {
		return replyComment;
	}

	public void setReplyComment(String replyComment) {
		this.replyComment = replyComment;
	}

	public int getReplyID() {
		return replyID;
	}

	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "ReplyDTO [replyID=" + replyID + ", commentID=" + commentID + ", bbsID=" + bbsID + ", nickName="
				+ nickName + ", password=" + password + ", replyComment=" + replyComment + ", regDate=" + regDate + "]";
	}
}
