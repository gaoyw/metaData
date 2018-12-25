package com.meta.dbmeta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建JDBC
 * 
 * @ClassName: DbBuilder
 * @author gaoy
 * @date 2018年12月4日 上午10:41:06
 */
public class DbBuilder {

	private static Logger log = LoggerFactory.getLogger(DbBuilder.class);

	/**
	 * 获取JDBC连接
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @return Connection
	 */
	public static Connection getConnection(String url, String userName, String password) {
		Connection conn = null;
		System.err.println("数据库连接地址：" + url);
		try {
			// 不需要加载Driver. Servlet 2.4规范开始容器会自动载入
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			log.info("loginName:{} --- 开始尝试连接数据库", userName);
			Properties info = new Properties();
			info.put("user", userName);
			info.put("password", password);
			// !!! Oracle 如果想要获取元数据 REMARKS 信息，需要加此参数
			info.put("remarksReporting", "true");
			// !!! MySQL 标志位，获取TABLE元数据 REMARKS 信息
			info.put("useInformationSchema", "true");
			// 不知道SQLServer需不需要设置...
			// 获取连接
			conn = DriverManager.getConnection(url, info);
			log.info("loginName:{} --- 获取数据库连接" + conn, userName);
			// } catch (ClassNotFoundException e) {
			// log.info("loginName:{} --- 未找到相关数据库驱动!" + userName);
			// e.printStackTrace();
		} catch (SQLException e) {
			log.info("loginName:{} --- 获取连接失败!", userName, password);
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭Connection连接
	 * 
	 * @Title: close
	 * @param conn
	 * @return void
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Statement
	 * 
	 * @Title: close
	 * @param stmt
	 * @return void
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @Title: close
	 * @param rs
	 * @return void
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
