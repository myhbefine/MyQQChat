package qqchat;

/*
 * 
 * 搭建数据库参考尚硅谷javaweb P163开始的书城数据库搭建
 * 
 */

import java.sql.*;
import java.util.*;

public class JDBC {
	ResourceBundle bundle = ResourceBundle.getBundle("JDBC");
	String driver = bundle.getString("driver");
	String url = bundle.getString("url");
	String user = bundle.getString("user");
	String password = bundle.getString("password");
	
	Statement stmt = null;
	PreparedStatement ps = null; ///预编译，防止SQL注入
	Connection conn = null;
	ResultSet rs = null;//结果集
	
	private boolean suclog = false;
	private String idname;
	private String photo;
	private Boolean rem;
	private String numpassword;
	private String thenumber;
	
	private int count = 0; //注册账号时判断是否注册成功
	
	JDBC() {
		try {
			//1.注册驱动
			Class.forName(driver);
			
			//2.获取连接
			conn = DriverManager.getConnection(url, user, password);
			//System.out.println("数据库连接对象2 = " + conn);

			conn.setAutoCommit(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void searchNum(String num) {
		try {
			
			//3.获取数据库操作对象（Statement专门执行sql语句的）
			String sql = "select * from users where Number = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			
			//4.执行SQL
			rs = ps.executeQuery( ); //专门执行DQL语句的方法

			
			if(rs.next()) {
				thenumber = num;
				idname = rs.getString("ID_name");
				photo = rs.getString("Photo");
				rem = rs.getBoolean("Rem");
				suclog = true;
				if(rem) {
					numpassword = rs.getString("Password");
				}
			}
			else {
				suclog = false;
			}
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void searchNumAndPassword(String num, String thepassword) {	
		try {
			
			//3.获取数据库操作对象（Statement专门执行sql语句的）
			String sql = "select * from users where Number = ? and Password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			ps.setString(2, thepassword);
			
			//4.执行SQL
			rs = ps.executeQuery();

			//5.处理查询结果集
			if(rs.next()) {
				thenumber = num;
				idname = rs.getString("ID_name");
				photo = rs.getString("Photo");
				rem = rs.getBoolean("Rem");
				suclog = true;
				numpassword = thepassword;
			}
			else {
				suclog = false;
			}
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void changeRem(boolean i) {	
		try {
			
			//3.获取数据库操作对象（Statement专门执行sql语句的）
			stmt = conn.createStatement();
			//4.执行SQL
			String	sql = "update users set Rem = " + i + " where Number = " + thenumber;///更新
			//System.out.println(count);
			int count = stmt.executeUpdate(sql);
			//System.out.print(count);
			
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void insertNewAccount(String num, String thepassword) {
		try {
			
			//3.获取数据库操作对象（Statement专门执行sql语句的）
			String sql = "insert into users(Number, Password) values( ?, ? )";
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			ps.setString(2, thepassword);
			
			//4.执行SQL
			count = ps.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void close() {
		if(rs != null) {
			try {
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.print("关闭成功！");
	}
	
	public boolean isCount() {
		if(count > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isLoginSuc() {
		return suclog;
	}
	public String getID() {
		return idname;
	}
	public String getPhoto() {
		return photo;
	}
	public String getPassword() {
		return numpassword;
	}
	public boolean isRemPassword() {
		return rem;
	}
}
