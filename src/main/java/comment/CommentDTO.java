package comment;

import java.util.ArrayList;

import reply.ReplyDTO;

public class CommentDTO {
	private int commentID;
	private int bbsID;
	private String writeID;
	private String password;
	private String comment;
	private String regDate;
	private String ip;
	private ArrayList<ReplyDTO> replyList;
	
	public CommentDTO() {}
	
	public CommentDTO(int commentID) {
		this.commentID = commentID;
	}
	
	public CommentDTO(int commentID, String password) {
		this.commentID = commentID;
		this.password = password;
	}

	public CommentDTO(int bbsID, String writeID, String password, String comment) {
		super();
		this.bbsID = bbsID;
		this.writeID = writeID;
		this.password = password;
		this.comment = comment;
	}
	
	public CommentDTO(int commentID, int bbsID, String writeID, String password, String comment, String regDate, String ip) {
		super();
		this.commentID = commentID;
		this.bbsID = bbsID;
		this.writeID = writeID;
		this.password = password;
		this.comment = comment;
		this.regDate = regDate;
		this.ip = ip;
		this.replyList = new ArrayList<ReplyDTO>();
	}
	
	
	public ArrayList<ReplyDTO> getReplyList() {
		return replyList;
	}

	public void setReplyList(ArrayList<ReplyDTO> replyList) {
		this.replyList = replyList;
	}
	
	public void addReplyList(ReplyDTO replyList) {
		this.replyList.add(replyList);
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getWriteID() {
		return writeID;
	}

	public void setWriteID(String writeID) {
		this.writeID = writeID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String coment) {
		this.comment = coment;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "CommentDTO [commentID=" + commentID + ", bbsID=" + bbsID + ", writeID=" + writeID + ", password="
				+ password + ", comment=" + comment + ", regDate=" + regDate + ", ip=" + ip + ", replyList=" + replyList
				+ "]";
	}

	

	

}
