package com.youngsterblues.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
	public Connection getConnection() {
		String url = "jdbc:mysql://54.65.20.191:3306/youngster?useUnicode=true&characterEncoding=utf8";
		String id = "youngster";
		String pw = "qkrtjdgh1";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, id, pw);
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean executeQuery(String sql, ArrayList<Object> parameters) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof String) {
					pstmt.setString(i + 1, (String) parameters.get(i));
				} else if (parameters.get(i) instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) parameters.get(i));
				}
			}
			pstmt.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}
		return false;
	}

	public ArrayList<String> selectQuery(String sql,
			ArrayList<Object> parameters, int resultSetLength) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof String) {
					pstmt.setString(i + 1, (String) parameters.get(i));
				} else if (parameters.get(i) instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) parameters.get(i));
				}
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < resultSetLength; i++) {
					result.add(rs.getString(i + 1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
			if (rs != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}
		return result;
	}
}
