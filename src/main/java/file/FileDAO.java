package file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class FileDAO {
	public int upload(FileDTO fileDTO) {
		String SQL = "INSERT INTO (bbsID, userID, fileRealName, fileNewName) VALUES (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, fileDTO.getBbsID());
			pstmt.setString(1, fileDTO.getUserID());
			pstmt.setString(1, fileDTO.getFileRealName());
			pstmt.setString(1, fileDTO.getFileNewName());
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -1; // 데이터베이스 오류
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
