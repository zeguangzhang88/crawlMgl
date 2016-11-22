package com.imut.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.imut.model.QueueDisk;

;

/// <summary>
/// 锟斤拷莘锟斤拷锟斤拷锟�Queuedal
/// </summary>
public class Queuedal {
	public Queuedal() {
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String DBDRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String DBURL = "jdbc:mysql://localhost:3306/crawlmgl?useUnicode=true&characterEncoding=utf-8";


	public  static boolean save(QueueDisk queue) {
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
			String date = queue.getDate();
			String host = queue.getHost();
			String link = queue.getLink();
			String weight = queue.getWeight();

			String sql = "{ call saveQueue(?,?,?,?) }";
			
			cs = con.prepareCall(sql);	
			cs.setString(1, link);
			cs.setString(2, host);
			cs.setString(3, weight);
			cs.setString(4, date);
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

	}// save锟斤拷锟斤拷锟斤拷锟斤拷


	public static Queue<String> getUrl() {
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String url = null;

		try {
		
			con = DriverManager.getConnection(DBURL, "root", "root");
			//System.out.println("锟斤拷菘锟斤拷锟斤拷映晒锟斤拷锟�);
		}catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch锟斤拷锟斤拷锟�

		try {
//
			String sql = "{ call getUrl() }";
			cs =  con.prepareCall(sql);    
			rs =cs.executeQuery();
			displayQueue(rs);
			return queueMem;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			return queueMem;

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

	}// geturl锟斤拷锟斤拷锟斤拷锟斤拷
	
	private static Queue<String> queueMem = new LinkedList<String>(); 

	private static Queue<String> displayQueue(ResultSet rs) throws SQLException {

		String str = "defaut";
		queueMem.clear();
		while(rs.next()){
			str = rs.getString(2);
			int id = rs.getInt(1);
			delete(id);			
			queueMem.offer(str);
		}
	
		return queueMem;
	}
	
	
	private static String displayResult(ResultSet rs) throws SQLException {

		String str = "defaut";
		rs.last();
		int hanghao = rs.getRow();
		if (hanghao > 0) {
			rs.first();
			str = rs.getString(2);
			int id = rs.getInt(1);
			delete(id);
		} else {
			System.exit(1);
		}
		return str;
	}

	private static void delete(int id) {

		Connection con = null;
		CallableStatement cs = null;
		
	

		try {
		
			con = DriverManager.getConnection(DBURL, "root", "root");

		}catch (SQLException e) {

			System.err.println("SQLException:" + e.getMessage());
			System.exit(1);
		}// try/catch锟斤拷锟斤拷锟�

		try {

			
			String sql = "{ call deleteQueue(?) }";
			cs = con.prepareCall(sql);	
			cs.setInt(1, id);
			if (cs.executeUpdate() != 1) {

				System.out.println("锟斤拷锟斤拷删锟斤拷失锟斤拷");
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());

		} finally {
			

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

	}
}
