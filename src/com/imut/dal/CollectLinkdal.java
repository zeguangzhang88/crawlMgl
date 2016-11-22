package com.imut.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.imut.model.CollectLink;

/// <summary>
/// 锟斤拷莘锟斤拷锟斤拷锟�CollectLinkdal
/// </summary>
public class CollectLinkdal {
	public CollectLinkdal() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String DBDRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String DBURL = "jdbc:mysql://localhost:3306/crawlmgl?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";

	public static boolean save(CollectLink collectLink) {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		try {
			
			con = DriverManager.getConnection(DBURL, "root", "root");
			//System.out.println("锟斤拷菘锟斤拷锟斤拷映晒锟斤拷锟�);
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch锟斤拷锟斤拷锟�

		try {
			String date = collectLink.getDate();
			String host = collectLink.getHost();
			String link = collectLink.getLink();
			String text = collectLink.getText();
			String weight = collectLink.getWeight();

			
			String sql = "{ call saveCollection(?,?,?,?,?) }";
			cs =con.prepareCall(sql);
			cs.setString(1, link);
			cs.setString(2, host);
			cs.setString(3, text);
			cs.setString(4, weight);
			cs.setString(5, date);			
			cs.executeUpdate();
			return true;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			return false;
		} finally {

			if (rs != null) { // 锟截憋拷锟斤拷菁锟�
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (cs != null) { // 锟截憋拷锟斤拷锟�
				try {
					cs.close();
				} catch (SQLException e) {
				}

			}

			if (con != null) { // 锟截憋拷锟斤拷锟斤拷
				try {
					con.close();
				} catch (SQLException e) {
				}

			}
		}// try/catch/finally锟斤拷锟斤拷锟�

	}// main锟斤拷锟斤拷锟斤拷锟斤拷
}
