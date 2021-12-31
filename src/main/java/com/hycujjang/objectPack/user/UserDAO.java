package com.hycujjang.objectPack.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hycujjang.util.DatabaseUtil;

public class UserDAO {
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM LECTURE_USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString(1).equals(userPassword)) {
					return 1; // �α��μ���
				} else {
					return 0; // �����ȣ Ʋ��
				}
			}
			
			return -1; // ���̵� ����
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		
		return -2; // �����ͺ��̽� ����
	}
	
	public int join(UserDTO user) {
		String SQL = "INSERT INTO LECTURE_USER VALUES (?, ?, ?, ?, false)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUsgerPassword());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserEmailHash());
			// executeUpdate ���༺���� ������Ʈ�� ������ ��ȯ
			// �̻�Ȳ���� 1���� �߰� �ϱ� ������ 1�� ��ȯ
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return -1; // ȸ������ ����
	}
	
	public boolean getUserEmailChecked(String userID) {
		String SQL = "SELECT userEmailChecked FROM LECTURE_USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return false; //�����ͺ��̽� ����
	}
	
	public boolean setUserEmailChecked(String userID) {
		String SQL = "UPDATE LECTURE_USER SET userEmailChecked = true WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return false; //�����ͺ��̽� ����
	}
	
	public String getUserEmail(String userID) {
		String SQL = "SELECT userEmail FROM LECTURE_USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(1));
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			instanseClose(conn, pstmt, rs);
		}
		return null; //�����ͺ��̽� ����
	}
	
	private void instanseClose(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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