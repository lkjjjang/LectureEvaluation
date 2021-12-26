package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.CommentDAO;
import comment.CommentDTO;
import freeBBS.BbsDAO;
import freeBBS.BbsDTO;
import reply.ReplyDAO;
import reply.ReplyDTO;

@WebServlet("/freeBoardDetailController")
public class FreeBoardDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bbsID = 0;		
		if (request.getParameter("id") != null) {
			try {
				bbsID = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e){
				System.out.print("게시판 글 번호 오류");
			}			
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		BbsDTO bbsDTO = new BbsDTO();
		
		// 조회수 증가
		bbsDAO.hitUpdate(bbsID);
		
		// 글 내용 생성		
		bbsDTO = bbsDAO.getFreeBoardDetail(bbsID);

		// 댓글목록 생성
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = getCommentList(bbsID);
		
		// 대댓글목록 생성
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = getReplyList(bbsID);
		
		// 댓글목록에 대댓글 삽입
		insertReplyInComment(commentList, replyList);

		int commentCount = commentList.size() + replyList.size();
		request.setAttribute("freeBoardDetail", bbsDTO);
		request.setAttribute("comments", commentList);
		request.setAttribute("commentCount", commentCount);
		request.getRequestDispatcher("view.jsp").forward(request, response);
	}
	
	private ArrayList<ReplyDTO> getReplyList(int bbsID) {
		ReplyDAO replyDAO = new ReplyDAO();
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		replyList = replyDAO.getList(bbsID);		
		replyRegDateModify(replyList);
		
		return replyList;
	}
	
	private ArrayList<CommentDTO> getCommentList(int bbsID) {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<CommentDTO> commentList = new ArrayList<CommentDTO>();
		commentList = commentDAO.getList(bbsID);
		commentRegDateModify(commentList);
		
		return commentList;
	}
	
	private void insertReplyInComment(ArrayList<CommentDTO> commentList, ArrayList<ReplyDTO> replyList) {
		for (CommentDTO comment: commentList) {
			for (ReplyDTO reply: replyList) {
				if (comment.getCommentID() == reply.getCommentID()) {					
					comment.addReplyList(reply);
				}
			}
		}
	}
	
	private void commentRegDateModify(ArrayList<CommentDTO> comments) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (CommentDTO dto: comments) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
	
	private void replyRegDateModify(ArrayList<ReplyDTO> list) {
		// 작성일자 '일자'가 다르면 년,월,일 출력 같으면 시,분 출력
		Date nowTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (ReplyDTO dto: list) {
			String[] dtoDate = dto.getRegDate().split(" ");
			
			if (dtoDate[0].equals(sf.format(nowTime))) {
				dto.setRegDate(dtoDate[1].substring(0, 5));
			} else {
				dto.setRegDate(dtoDate[0]);
			}
		}
	}
}
