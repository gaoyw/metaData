package com.meta.dbmeta.util;

import com.meta.dbmeta.util.DBMSMetaUtil.DATABASETYPE;

/**
 * 连接数据库判断工具
 * 
 * @ClassName: DBUtil
 * @author gaoy
 * @date 2018年12月4日 下午1:49:41
 */
public class DBUtil {

	/**
	 * 根据字符串, 判断数据库类型
	 * 
	 * @param databasetype
	 */
	public static DATABASETYPE parseDATABASETYPE(String databasetype) {
		// 空类型
		if (null == databasetype || databasetype.trim().length() < 1) {
			return DATABASETYPE.EMPTY;
		}
		// 截断首尾空格,转换为大写
		databasetype = databasetype.trim().toUpperCase();
		// Oracle数据库
		if (databasetype.contains("ORACLE")) {
			return DATABASETYPE.ORACLE;
		}
		// MYSQL 数据库
		if (databasetype.contains("MYSQL")) {
			return DATABASETYPE.MYSQL;
		}
		// SQL SERVER 数据库
		if (databasetype.contains("SQL") && databasetype.contains("SERVER")) {
			if (databasetype.contains("2005") || databasetype.contains("2008") || databasetype.contains("2012")
					|| databasetype.contains("2017")) {
				try {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return DATABASETYPE.SQLSERVER2005;
			} else {
				try {
					// 注册 JTDS
					Class.forName("net.sourceforge.jtds.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return DATABASETYPE.SQLSERVER;
			}
		}
		// 下面的这几个没有经过实践测试, 判断可能不准确
		// DB2 数据库
		if (databasetype.contains("DB2")) {
			return DATABASETYPE.DB2;
		}
		// INFORMIX 数据库
		if (databasetype.contains("INFORMIX")) {
			return DATABASETYPE.INFORMIX;
		}
		// SYBASE 数据库
		if (databasetype.contains("SYBASE")) {
			return DATABASETYPE.SYBASE;
		}
		// 默认,返回其他
		return DATABASETYPE.OTHER;
	}

	/**
	 * 根据IP,端口,以及数据库名字,拼接连接字符串
	 * 
	 * @param ip
	 * @param port
	 * @param dbname
	 */
	public static String concatDBURL(DATABASETYPE dbtype, String ip, String port, String dbname) {
		String url = "";
		if (DATABASETYPE.ORACLE.equals(dbtype)) { // Oracle数据库
			url += "jdbc:oracle:thin:@";
			url += ip.trim();
			url += ":" + port.trim();
			url += ":" + dbname;
			// 如果需要采用 hotbackup
			String url2 = "";
			url2 = url2 + "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = "
					+ ip.trim() + ")(PORT =" + port.trim() + ")))(CONNECT_DATA = (SERVICE_NAME =" + dbname
					+ ")(FAILOVER_MODE = (TYPE = SELECT)(METHOD = BASIC)(RETRIES = 180)(DELAY = 5))))";
		} else if (DATABASETYPE.MYSQL.equals(dbtype)) { // MySQL数据库
			url += "jdbc:mysql://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "/" + dbname;
		} else if (DATABASETYPE.SQLSERVER.equals(dbtype)) { // SQLServer数据库
			url += "jdbc:jtds:sqlserver://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "/" + dbname;
			url += ";tds=8.0;lastupdatecount=true";
		} else if (DATABASETYPE.SQLSERVER2005.equals(dbtype)) { // SQLServer2005数据库
			url += "jdbc:sqlserver://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "; DatabaseName=" + dbname;
		} else if (DATABASETYPE.SQLSERVER2017.equals(dbtype)) { // SQLServer2017数据库
			url += "jdbc:sqlserver://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "; DatabaseName=" + dbname;
		} else if (DATABASETYPE.DB2.equals(dbtype)) { // DB2数据库
			url += "jdbc:db2://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "/" + dbname;
		} else if (DATABASETYPE.INFORMIX.equals(dbtype)) {
			// Infox mix 可能有BUG
			url += "jdbc:informix-sqli://";
			url += ip.trim();
			url += ":" + port.trim();
			url += "/" + dbname;
			// +":INFORMIXSERVER=myserver;user="+bean.getDatabaseuser()+";password="+bean.getDatabasepassword()
		} else if (DATABASETYPE.SYBASE.equals(dbtype)) {
			url += "jdbc:sybase:Tds:";
			url += ip.trim();
			url += ":" + port.trim();
			url += "/" + dbname;
		} else {
			throw new RuntimeException("不认识的数据库类型!");
		}
		return url;
	}
}
