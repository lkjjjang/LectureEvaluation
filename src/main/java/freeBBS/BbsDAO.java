package freeBBS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import controller.FreeBoardController;
import controller.LectureBoardController;
import evaluation.EvaluationDTO;
import util.DatabaseUtil;

public class BbsDAO {
	
	public BbsDTO getFreeBoardDetail(int bbsID) {		
		BbsDTO result = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {		
			SQL = "SELECT * FROM FREE_BBS WHERE bbsID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				result = new BbsDTO(
						rs.getInt(1),
						rs.getString(2).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(3).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(4).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(5).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getString(6).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br>", "\r\n"),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9)
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public int hitUpdate(int bbsID) {
		String SQL = "UPDATE FREE_BBS SET bbsHit = bbsHit + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // �����ͺ��̽� ����
	}
	
	
	public int update(int bbsID) {
		String SQL = "UPDATE FREE_BBS SET bbsUpvote = bbsUpvote + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // �����ͺ��̽� ����
	}
	
	
	public int write(BbsDTO bbsDTO) {
		String SQL = "INSERT INTO FREE_BBS VALUES (NULL, ?, ?, NOW(), ?, ?, 0, 0, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsDTO.getNickName().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(2, bbsDTO.getPassword().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(3, bbsDTO.getBbsTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));
			pstmt.setString(4, bbsDTO.getBbsContente().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br>"));

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // �����ͺ��̽� ����
	}
	
	public ArrayList<BbsDTO> getList(int pageNumber) {		
		ArrayList<BbsDTO> list = new ArrayList<>();
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int minColNum = (pageNumber - 1) * FreeBoardController.freeBBSListPrintCount;
		int colCount = FreeBoardController.freeBBSListPrintCount;
		
		try {		
			SQL = "SELECT * FROM FREE_BBS ORDER BY bbsID DESC LIMIT " + minColNum + ", " + colCount;
			// SELECT ((SELECT COUNT(*) FROM FREE_BBS_COMMENT) + (SELECT COUNT(*) FROM FREE_BBS_REPLY));
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BbsDTO bbs = new BbsDTO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9)
						);
				list.add(bbs);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null;
	}
	
	public String getContentPassword(String bbsID) {
		String SQL = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			SQL = "SELECT password FROM FREE_BBS WHERE bbsID = ?";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return result; //-1�� ��� �����ͺ��̽� ����
	}
	
	public int getListCount() {
		String SQL = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			SQL = "SELECT COUNT(*) FROM FREE_BBS";
			
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return Integer.parseInt(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int voteCheck(int bbsID) {
		String SQL = "SELECT * FROM FREE_BBS_VOTE ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int upvote(String bbsID) {
		String SQL = "UPDATE FREE_BBS SET bbsUpvote = bbsUpvote + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int downvote(String bbsID) {
		String SQL = "UPDATE FREE_BBS SET downvote = downvote + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int delete(String bbsID) {
		String SQL = "DELETE FROM FREE_BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(bbsID));
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public String getUserID(String bbsID) {
		String SQL = "SELECT userID FROM FREE_BBS WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(bbsID));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return null; // �������� �ʴ� ���̵�
	}
	
	public void setCommentCountUpdate(int count, int bbsID) {
		String SQL = "UPDATE FREE_BBS SET commentCount = ? WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, count);
			pstmt.setInt(2, bbsID);
				
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
	}
	
	public static void bbsCommentCountUp(int bbsID) {
		String SQL = "UPDATE FREE_BBS SET commentCount = commentCount + 1 WHERE bbsID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
	}
	
	private static void instanseClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}