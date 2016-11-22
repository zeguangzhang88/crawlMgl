package com.imut.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.imut.model.CrawledLink;


/// <summary>
/// 锟斤拷莘锟斤拷锟斤拷锟�CrawledLinkdal
/// </summary>
public class CrawledLinkdal {
	public CrawledLinkdal() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String DBDRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String DBURL = "jdbc:mysql://localhost:3306/crawlmgl?useUnicode=true&characterEncoding=utf-8";

	public static boolean save(CrawledLink crawledLink) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			con = DriverManager.getConnection(DBURL, "root", "root");
			//System.out.println("锟斤拷菘锟斤拷锟斤拷映晒锟斤拷锟�);
		} catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch锟斤拷锟斤拷锟�

		try {

			String link = crawledLink.getLink();
			String sql = "{ call saveCrawledLink(?) }";
			CallableStatement proc =  con.prepareCall(sql);    
			proc.setString(1,link ); 
			proc.executeUpdate();
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

			if (ps != null) { // 锟截憋拷锟斤拷锟�
				try {
					ps.close();
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
