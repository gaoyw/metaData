package com.meta.dbmeta.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 获取数据库元数据工具
 * 
 * @ClassName: DBMSMetaUtil
 * @author gaoy
 * @date 2018年12月4日 下午2:01:12
 */
public class DBMSMetaUtil {

	/**
	 * 数据库类型, 枚举
	 * 
	 */
	public static enum DATABASETYPE {
		ORACLE, MYSQL, SQLSERVER, SQLSERVER2005, SQLSERVER2017, DB2, INFORMIX, SYBASE, OTHER, EMPTY
	}

	/**
	 * 列出数据库的所有表 可以参考: http://www.cnblogs.com/chinafine/articles/1847205.html
	 */
	public static List<Map<String, Object>> listTables(String databasetype, String ip, String port, String dbname,
			String username, String password, String schemaPattern) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		List<Map<String, Object>> result = null;
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		// Statement stmt = null;
		ResultSet rs = null;
		try {
			// 这句话我也不懂是什么意思... 好像没什么用
			conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// 获取Meta信息对象
			DatabaseMetaData meta = conn.getMetaData();
			// 数据库
			String catalog = null;
			// 数据库的用户// meta.getUserName();
			// 表名
			String tableNamePattern = null;//
			// types指的是table、view
			String[] types = { "TABLE" };
			// Oracle
			if (DATABASETYPE.ORACLE.equals(dbtype)) {
				if (null != schemaPattern) {
					schemaPattern = schemaPattern.toUpperCase();// 转成大写
				}
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.MYSQL.equals(dbtype)) {
				// MySQL查询
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.SQLSERVER.equals(dbtype) || DATABASETYPE.SQLSERVER2005.equals(dbtype)
					|| DATABASETYPE.SQLSERVER2017.equals(dbtype)) {
				// SQL Server
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.DB2.equals(dbtype)) {
				if (null != schemaPattern) {
					schemaPattern = schemaPattern.toUpperCase();// 转成大写
				}
				// DB2查询
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.INFORMIX.equals(dbtype)) {
				// INFORMIX
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.SYBASE.equals(dbtype)) {
				// SYBASE
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else {
				throw new RuntimeException("不认识的数据库类型!");
			}
			// 将一个未处理的ResultSet解析为Map列表
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbBuilder.close(rs);
			DbBuilder.close(conn);
		}
		return result;
	}

	/**
	 * 列出表的所有字段
	 */
	public static List<Map<String, Object>> listColumns(String databasetype, String ip, String port, String dbname,
			String username, String password, String tableName, String schemaPattern) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		tableName = StringUtil.trim(tableName);
		// 根据字符串, 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		List<Map<String, Object>> result = null;
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		// Statement stmt = null;
		ResultSet rs = null;
		try {
			// 获取Meta信息对象
			DatabaseMetaData meta = conn.getMetaData();
			// 数据库
			String catalog = null;
			// 数据库的用户
			// meta.getUserName();
			// 表名
			String tableNamePattern = tableName;//
			// 转换为大写
			if (null != tableNamePattern) {
				if (DATABASETYPE.ORACLE.equals(dbtype) || DATABASETYPE.MYSQL.equals(dbtype)
						|| DATABASETYPE.SQLSERVER.equals(dbtype) || DATABASETYPE.SQLSERVER2005.equals(dbtype)
						|| DATABASETYPE.SQLSERVER2017.equals(dbtype)) {
					tableNamePattern = tableNamePattern.toUpperCase();
				}
			}
			String columnNamePattern = null;
			// 转大写
			if (DATABASETYPE.ORACLE.equals(dbtype) || DATABASETYPE.DB2.equals(dbtype)) {
				if (null != schemaPattern && schemaPattern != "") {
					schemaPattern = schemaPattern.toUpperCase();
				}
			}
			rs = meta.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
			// 将一个未处理的ResultSet解析为Map列表
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			DbBuilder.close(rs);
			DbBuilder.close(conn);
		}
		return result;
	}

	/**
	 * 判断元数据对象是否为空
	 * 
	 * @Title: TryLink
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean TryLink(String databasetype, String ip, String port, String dbname, String username,
			String password) {
		// 根据字符串, 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		Connection conn = null;
		try {
			// 获取JDBC连接
			conn = DbBuilder.getConnection(url, username, password);
			if (null == conn) {
				return false;
			}
			// 获取元数据对象
			DatabaseMetaData meta = conn.getMetaData();
			if (null == meta) {
				return false;
			} else {
				// 只有这里返回true
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbBuilder.close(conn);
		}
		return false;
	}

	/**
	 * 获取数据库版本基本信息
	 * 
	 * @Title: demoDatabaseMetaData
	 * @return void
	 */
	public static void databaseMetaData(String databasetype, String ip, String port, String dbname, String username,
			String password) {
		try {
			databasetype = StringUtil.trim(databasetype);
			ip = StringUtil.trim(ip);
			port = StringUtil.trim(port);
			dbname = StringUtil.trim(dbname);
			username = StringUtil.trim(username);
			password = StringUtil.trim(password);
			// 判断数据库类型
			DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
			// 根据IP,端口,以及数据库名字,拼接连接字符串
			String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
			// 获取JDBC连接
			Connection conn = DbBuilder.getConnection(url, username, password);
			// 获取元数据对象
			DatabaseMetaData dmd = conn.getMetaData();
			System.err.println("当前数据库是：" + dmd.getDatabaseProductName());
			System.err.println("当前数据库版本：" + dmd.getDatabaseProductVersion());
			System.err.println("当前数据库驱动：" + dmd.getDriverVersion());
			System.err.println("当前数据库URL：" + dmd.getURL());
			System.err.println("当前数据库是否是只读模式？：" + dmd.isReadOnly());
			System.err.println("当前数据库是否支持批量更新？：" + dmd.supportsBatchUpdates());
			System.err.println("当前数据库是否支持结果集的双向移动（数据库数据变动不在ResultSet体现）？："
					+ dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
			System.err.println("当前数据库是否支持结果集的双向移动（数据库数据变动会影响到ResultSet的内容）？："
					+ dmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
			System.err.println("========================================");
			System.err.println("========================================");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("数据库操作出现异常");
		}
	}

	// ResultSetMetaData 使用示例
	// 此方法参考 http://blog.csdn.net/yirentianran/article/details/2950321
	public static void demoResultSetMetaData(ResultSetMetaData data) throws SQLException {
		for (int i = 1; i <= data.getColumnCount(); i++) {
			// 获得所有列的数目及实际列数
			int columnCount = data.getColumnCount();
			// 获得指定列的列名
			String columnName = data.getColumnName(i);
			// 获得指定列的列值
			// String columnValue = rs.getString(i);
			// 获得指定列的数据类型
			int columnType = data.getColumnType(i);
			// 获得指定列的数据类型名
			String columnTypeName = data.getColumnTypeName(i);
			// 所在的Catalog名字
			String catalogName = data.getCatalogName(i);
			// 对应数据类型的类
			String columnClassName = data.getColumnClassName(i);
			// 在数据库中类型的最大字符个数
			int columnDisplaySize = data.getColumnDisplaySize(i);
			// 默认的列的标题
			String columnLabel = data.getColumnLabel(i);
			// 获得列的模式
			String schemaName = data.getSchemaName(i);
			// 某列类型的精确度(类型的长度)
			int precision = data.getPrecision(i);
			// 小数点后的位数
			int scale = data.getScale(i);
			// 获取某列对应的表名
			String tableName = data.getTableName(i);
			// 是否自动递增
			boolean isAutoInctement = data.isAutoIncrement(i);
			// 在数据库中是否为货币型
			boolean isCurrency = data.isCurrency(i);
			// 是否为空
			int isNullable = data.isNullable(i);
			// 是否为只读
			boolean isReadOnly = data.isReadOnly(i);
			// 能否出现在where中
			boolean isSearchable = data.isSearchable(i);
			System.err.println(columnCount);
			System.err.println("获得列" + i + "的字段名称:" + columnName);
			// System.err.println("获得列" + i + "的字段值:" + columnValue);
			System.err.println("获得列" + i + "的类型,返回SqlType中的编号:" + columnType);
			System.err.println("获得列" + i + "的数据类型名:" + columnTypeName);
			System.err.println("获得列" + i + "所在的Catalog名字:" + catalogName);
			System.err.println("获得列" + i + "对应数据类型的类:" + columnClassName);
			System.err.println("获得列" + i + "在数据库中类型的最大字符个数:" + columnDisplaySize);
			System.err.println("获得列" + i + "的默认的列的标题:" + columnLabel);
			System.err.println("获得列" + i + "的模式:" + schemaName);
			System.err.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
			System.err.println("获得列" + i + "小数点后的位数:" + scale);
			System.err.println("获得列" + i + "对应的表名:" + tableName);
			System.err.println("获得列" + i + "是否自动递增:" + isAutoInctement);
			System.err.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
			System.err.println("获得列" + i + "是否为空:" + isNullable);
			System.err.println("获得列" + i + "是否为只读:" + isReadOnly);
			System.err.println("获得列" + i + "能否出现在where中:" + isSearchable);
		}
	}

	/**
	 * 获取所有视图
	 * 
	 * @Title: listViews
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> listViews(String databasetype, String ip, String port, String dbname,
			String username, String password, String schemaPattern) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		List<Map<String, Object>> result = null;
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		// Statement stmt = null;
		ResultSet rs = null;
		try {
			// 这句话我也不懂是什么意思... 好像没什么用
			conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// 获取Meta信息对象
			DatabaseMetaData meta = conn.getMetaData();
			// 数据库
			String catalog = null;
			// 数据库的用户// meta.getUserName();
			// 表名
			String tableNamePattern = null;//
			// types指的是table、view
			String[] types = { "VIEW" };
			// Oracle
			if (DATABASETYPE.ORACLE.equals(dbtype)) {
				if (null != schemaPattern) {
					schemaPattern = schemaPattern.toUpperCase();// 转成大写
				}
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.MYSQL.equals(dbtype)) {
				// MySQL查询
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.SQLSERVER.equals(dbtype) || DATABASETYPE.SQLSERVER2005.equals(dbtype)
					|| DATABASETYPE.SQLSERVER2017.equals(dbtype)) {
				// SQL Server
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.DB2.equals(dbtype)) {
				if (null != schemaPattern) {
					schemaPattern = schemaPattern.toUpperCase();// 转成大写
				}
				// DB2查询
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.INFORMIX.equals(dbtype)) {
				// INFORMIX
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else if (DATABASETYPE.SYBASE.equals(dbtype)) {
				// SYBASE
				tableNamePattern = "%";
				rs = meta.getTables(catalog, schemaPattern, tableNamePattern, types);
			} else {
				throw new RuntimeException("不认识的数据库类型!");
			}
			// 将一个未处理的ResultSet解析为Map列表
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbBuilder.close(rs);
			DbBuilder.close(conn);
		}
		return result;
	}

	/**
	 * 获得数据库中所有方案名称
	 * 
	 * @Title: getAllSchemas
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getAllSchemas(String databasetype, String ip, String port, String dbname,
			String username, String password) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		DatabaseMetaData meta;
		String catalog = null;
		try {
			meta = conn.getMetaData();
			rs = meta.getSchemas();
			result = MapUtil.parseResultSetToMapList(rs);
			// while (rs.next()) {
			// String tableSchem = rs.getString("TABLE_SCHEM");
			// System.out.println(tableSchem);
			// }
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取表中主键信息
	 * 
	 * @Title: getAllPrimaryKeys
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getAllPrimaryKeys(String databasetype, String ip, String port,
			String dbname, String username, String password, String tableName, String schema) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getPrimaryKeys(catalog, schema, tableName);
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取主表中主键所被哪些表引用作为外键
	 * 
	 * @Title: getAllExportedKeys
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 */
	public static List<Map<String, Object>> getAllExportedKeys(String databasetype, String ip, String port,
			String dbname, String username, String password, String tableName, String schema) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getExportedKeys(catalog, schema, tableName);
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取表中所引用的外键
	 * 
	 * @Title: getImportedKeys
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getImportedKeys(String databasetype, String ip, String port, String dbname,
			String username, String password, String tableName, String schema) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getImportedKeys(catalog, schema, tableName);
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取表中索引信息
	 * 
	 * @Title: getIndexInfo
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getIndexInfo(String databasetype, String ip, String port, String dbname,
			String username, String password, String tableName, String schema) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getIndexInfo(catalog, schema, tableName, false, true);
			result = MapUtil.parseResultSetToMapList(rs);
			/*
			 * while (rs.next()) { // 非唯一索引(Can index values be non-unique.
			 * false when TYPE is // tableIndexStatistic) boolean nonUnique =
			 * rs.getBoolean("NON_UNIQUE"); String indexQualifier =
			 * rs.getString("INDEX_QUALIFIER");// 索引目录（可能为空） String indexName =
			 * rs.getString("INDEX_NAME");// 索引的名称 short type =
			 * rs.getShort("TYPE");// 索引类型 short ordinalPosition =
			 * rs.getShort("ORDINAL_POSITION");// 在索引列顺序号 String columnName =
			 * rs.getString("COLUMN_NAME");// 列名 String ascOrDesc =
			 * rs.getString("ASC_OR_DESC");// 列排序顺序:升序还是降序 int cardinality =
			 * rs.getInt("CARDINALITY"); // 基数 System.err.println("非唯一索引：" +
			 * nonUnique + "-索引目录：" + indexQualifier + "-索引的名称 ：" + indexName +
			 * "-索引类型:" + type + "-在索引列顺序号:" + ordinalPosition + "-列名 ：" +
			 * columnName + "-列排序顺序,升序还是降序：" + ascOrDesc + "-基数：" +
			 * cardinality);
			 * 
			 * }
			 */
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 检索数据库中可用的目录名
	 * 
	 * @Title: getCatalogs
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getCatalogs(String databasetype, String ip, String port, String dbname,
			String username, String password) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getCatalogs();
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 返回当前目录名
	 * 
	 * @Title: getCatalog
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @return String
	 */
	public static String getCatalog(String databasetype, String ip, String port, String dbname, String username,
			String password) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		String result = null;
		try {
			result = conn.getCatalog();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取所有存储过程
	 * 
	 * @Title: getProcedures
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getProcedures(String databasetype, String ip, String port, String dbname,
			String username, String password, String schema) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getProcedures(null, schema, null);
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取所有存储过程参数结果列表
	 * 
	 * @Title: getProcedureColumns
	 * @param databasetype
	 * @param ip
	 * @param port
	 * @param dbname
	 * @param username
	 * @param password
	 * @param tableName
	 * @param schema
	 * @param procedureName
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> getProcedureColumns(String databasetype, String ip, String port,
			String dbname, String username, String password, String schema, String procedureName,
			String columnNamePattern) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getProcedureColumns(catalog, schema, procedureName, columnNamePattern);
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, Object>> getTablePrivileges(String databasetype, String ip, String port,
			String dbname, String username, String password, String schema, String tableName) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			rs = meta.getColumnPrivileges(catalog, schema, tableName, "MAJOR_ID");
			result = MapUtil.parseResultSetToMapList(rs);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, Object>> getTest(String databasetype, String ip, String port, String dbname,
			String username, String password, String tableName, String schema, String procedureName) {
		// 去除首尾空格
		databasetype = StringUtil.trim(databasetype);
		ip = StringUtil.trim(ip);
		port = StringUtil.trim(port);
		dbname = StringUtil.trim(dbname);
		username = StringUtil.trim(username);
		password = StringUtil.trim(password);
		// 判断数据库类型
		DATABASETYPE dbtype = DBUtil.parseDATABASETYPE(databasetype);
		// 根据IP,端口,以及数据库名字,拼接连接字符串
		String url = DBUtil.concatDBURL(dbtype, ip, port, dbname);
		// 获取JDBC连接
		Connection conn = DbBuilder.getConnection(url, username, password);
		List<Map<String, Object>> result = null;
		ResultSet rs = null;
		String catalog = null;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			String ss = meta.getUserName();
			// rs = meta.getFunctions(catalog, schema, "F_ISNUMBER");
			System.err.println("==============================");
			// result = MapUtil.parseResultSetToMapList(rs);

			// MapUtil.convertKeyList2LowerCase(result);
			// String jsonT = JSONArray.toJSONString(result, true);
			// System.err.println(jsonT);
			// System.err.println("getTest：" +
			// JSON.toJSONString(conn.getSchema()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return result;
	}
}
