package com.pengjunlee.hive.util;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetConnection {

	public static Connection getConnection(String url,String driver){
		Connection con = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (con == null) {
			try {
				con = DriverManager.getConnection(url);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public static Connection getPhoenixConn() {
		String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
		String url = "jdbc:phoenix:192.168.15.195:2181/hbase";
		Connection con = GetConnection.getConnection(url, driver);
		return con;
	}

	public static Connection getHiveConn() {
		String driver = "org.apache.hive.jdbc.HiveDriver";
		String url = "jdbc:hive2://i-851rnvp8:10000/default?useUnicode=true&characterEncoding=UTF-8";  // i-851rnvp8:default,i-kz8o5fp0:dataware
		String username = "hdfs";
		String password = "666666";
		Connection con = GetConnection.getConnection(url, driver);
		return con;
	}

	public static Connection getConn_mysql() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/mysql";
		String username = "root";
		String password = "rootROOT.1";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();
		Connection hiveConn = GetConnection.getHiveConn();
//		Connection mysqlConn = GetConnection.getConn_mysql();
		PreparedStatement stmt = null;
		List<Map> list = new ArrayList();
		try {
//			stmt = mysqlConn.prepareStatement("select * from user limit 2;");
			/*String sql = "INSERT into table adlogs(ip,website) VALUES('laoluo','laoluo')";
			PreparedStatement prest = hiveConn.prepareStatement(sql);
//			prest.executeUpdate();
			prest.execute();
			hiveConn.commit();
			hiveConn.close();*/

//			String sql = "select * from dataware.cms_sales limit 10";
//			String sql = "show tables";
			String sql = "SELECT count(distinct(userid)) from dataplatform";
			stmt = hiveConn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();  // 获取键名
			int columnCount = md.getColumnCount();  // 获取行的数量
			while (rs.next()) {
				Map rowData = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));  // 获取键名及值
				}
				list.add(rowData);
			}
			for (Map<String, Object> map : list){
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				sb.append("Key = " + entry.getKey() + ":" + entry.getValue() + ", ");
			}
			System.out.println(sb.toString().substring(0, sb.length() - 2));
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		System.out.println("over...");
		long endtime = System.currentTimeMillis();
		System.out.println(DateUtil.nowString() + " 耗时为： " + (endtime - starttime) + "毫秒");
	}

}
