package com.meta.metadata;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.meta.dbmeta.util.DBMSMetaUtil;
import com.meta.dbmeta.util.MapUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBMetaDataTests {

	/**
	 * 获取数据库版本基本信息
	 */
	@Test
	public void databaseMetaData() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		DBMSMetaUtil.databaseMetaData(databasetype, ip, port, dbname, username, password);
	}

	/**
	 * 获取数据库下所有表
	 */
	@Test
	public void getaTables() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "exam";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String schemaPattern = null;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String schemaPattern = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String schemaPattern = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String schemaPattern = "DB2ADMIN";

		// 列出数据库的所有表
		List<Map<String, Object>> tables = DBMSMetaUtil.listTables(databasetype, ip, port, dbname, username, password,
				schemaPattern);
		// 将List中的Key转换为小写
		tables = MapUtil.convertKeyList2LowerCase(tables);
		String jsonT = JSONArray.toJSONString(tables, true);
		System.err.println(jsonT);
		System.err.println("tables.size()= " + tables.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取表中所有列
	 */
	@Test
	public void metaColumns() {
		// // MySQL
		String ip = "127.0.0.1";
		String port = "3306";
		String dbname = "boot";
		String username = "root";
		String password = "123456";
		String databasetype = "mysql";
		String tableName = "user";
		String schemaPattern = null;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "am_model";
		// String schemaPattern = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String tableName = "user";
		// String schemaPattern = "dbo";

		// BD2
		// String ip = "127.0.0.1";
		// String port = "50000";
		// String dbname = "db2Test";
		// String username = "db2admin";
		// String password = "123456";
		// String databasetype = "DB2";
		// String tableName = "user";
		// String schemaPattern = "DB2ADMIN";

		List<Map<String, Object>> columns = DBMSMetaUtil.listColumns(databasetype, ip, port, dbname, username, password,
				tableName, schemaPattern);
		// 将List中的Key转换为小写
		columns = MapUtil.convertKeyList2LowerCase(columns);
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		String jsonC = JSONArray.toJSONString(columns, true);
		System.err.println(jsonC);
		System.err.println("columns.size()= " + columns.size());
	}

	/**
	 * 获取数据库下所有视图
	 */
	@Test
	public void metaViews() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "exam";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String schemaPattern = null;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String schemaPattern = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String schemaPattern = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String schemaPattern = "DB2ADMIN";

		List<Map<String, Object>> views = DBMSMetaUtil.listViews(databasetype, ip, port, dbname, username, password,
				schemaPattern);
		// 将List中的Key转换为小写
		views = MapUtil.convertKeyList2LowerCase(views);
		String jsonT = JSONArray.toJSONString(views, true);
		System.err.println(jsonT);
		System.err.println("views.size()= " + views.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取表中索引
	 */
	@Test
	public void getIndexInfo() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String tableName = "user";
		// String schema = dbname;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "AM_MODEL";
		// String schema = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String tableName = "user";
		// String schema = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String tableName = "DT_DEPT";
		String schema = "DB2ADMIN";
		List<Map<String, Object>> indexInfos = DBMSMetaUtil.getIndexInfo(databasetype, ip, port, dbname, username,
				password, tableName, schema);
		indexInfos = MapUtil.convertKeyList2LowerCase(indexInfos);
		String jsonT = JSONArray.toJSONString(indexInfos, true);
		System.err.println(jsonT);
		System.err.println("indexInfos.size()= " + indexInfos.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取表中主键
	 */
	@Test
	public void getAllPrimaryKeys() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String tableName = "user";
		// String schema = dbname;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "SYS_USER";
		// String schema = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String tableName = "dept";
		// String schema = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String tableName = "DT_DEPT";
		String schema = "DB2ADMIN";

		List<Map<String, Object>> pKeys = DBMSMetaUtil.getAllPrimaryKeys(databasetype, ip, port, dbname, username,
				password, tableName, schema);
		pKeys = MapUtil.convertKeyList2LowerCase(pKeys);
		String jsonT = JSONArray.toJSONString(pKeys, true);
		System.err.println(jsonT);
		System.err.println("pKeys.size()= " + pKeys.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取主表的中主键，被哪些表所引用作为外键
	 */
	@Test
	public void getAllExportedKeys() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String tableName = "dept";
		// String schema = dbname;

		// Oracles
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "SYS_UGROUP";
		// String schema = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String tableName = "dept";
		// String schema = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String tableName = "DT_DEPT";
		String schema = "DB2ADMIN";

		List<Map<String, Object>> fKeys = DBMSMetaUtil.getAllExportedKeys(databasetype, ip, port, dbname, username,
				password, tableName, schema);
		fKeys = MapUtil.convertKeyList2LowerCase(fKeys);
		String jsonT = JSONArray.toJSONString(fKeys, true);
		System.err.println(jsonT);
		System.err.println("fKeys.size()= " + fKeys.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取表中所引用的外键
	 */
	@Test
	public void getImportedKeys() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String tableName = "user";
		// String schema = dbname;

		// Oracles
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "SYS_UGROUP_USER";
		// String schema = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String tableName = "user";
		// String schema = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String tableName = "DT_USER";
		String schema = "DB2ADMIN";

		List<Map<String, Object>> fKeys = DBMSMetaUtil.getImportedKeys(databasetype, ip, port, dbname, username,
				password, tableName, schema);
		fKeys = MapUtil.convertKeyList2LowerCase(fKeys);
		String jsonT = JSONArray.toJSONString(fKeys, true);
		System.err.println(jsonT);
		System.err.println("fKeys.size()= " + fKeys.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 检索数据库中可用的目录名(Oracle、DB2没有目录名)
	 */
	@Test
	public void getCatalogs() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";

		List<Map<String, Object>> catalogs = DBMSMetaUtil.getCatalogs(databasetype, ip, port, dbname, username,
				password);
		// 将List中的Key转换为小写
		catalogs = MapUtil.convertKeyList2LowerCase(catalogs);
		String jsonT = JSONArray.toJSONString(catalogs, true);
		System.err.println(jsonT);
		System.err.println("catalogs.size()= " + catalogs.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 返回当前目录名(Oracle、DB2没有目录名)
	 */
	@Test
	public void getCatalog() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";

		String catalog = DBMSMetaUtil.getCatalog(databasetype, ip, port, dbname, username, password);
		System.err.println("Catalog：" + catalog);
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取数据库下所有Schema（MySQL没有Schema）
	 */
	@Test
	public void metaSchemas() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";

		List<Map<String, Object>> schemas = DBMSMetaUtil.getAllSchemas(databasetype, ip, port, dbname, username,
				password);
		schemas = MapUtil.convertKeyList2LowerCase(schemas);
		String jsonT = JSONArray.toJSONString(schemas, true);
		System.err.println(jsonT);
		System.err.println("schemas.size()= " + schemas.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取所有存储过程和函数(type： 1.存储过程 2.函数)
	 */
	@Test
	public void getProcedures() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "erm";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String schema = dbname;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String schema = username;

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String schema = "dbo";

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String schema = "DB2ADMIN";

		List<Map<String, Object>> procedures = DBMSMetaUtil.getProcedures(databasetype, ip, port, dbname, username,
				password, schema);
		procedures = MapUtil.convertKeyList2LowerCase(procedures);
		String jsonT = JSONArray.toJSONString(procedures, true);
		System.err.println(jsonT);
		System.err.println("procedures.size()= " + procedures.size());
		int gc = 0;
		int hs = 0;
		Iterator<Map<String, Object>> iteratorL = procedures.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			Object value = map.get("procedure_type");
			if (value.toString().equals("1")) {
				gc += 1;
			}
			if (value.toString().equals("2")) {
				hs += 1;
			}
		}
		System.err.println("存储过程：" + gc);
		System.err.println("函数：" + hs);
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 获取所有存储过程参数结果列表
	 */
	@Test
	public void getProcedureColumns() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "erm";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String schema = dbname;
		// String procedureName = "concatp";
		// String columnNamePattern = null;

		// Oracle
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String schema = username;
		// String procedureName = "GEN_SEQ_GET_VAL";
		// String columnNamePattern = "V_SEQ";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String schema = "dbo";
		// String procedureName = "GetSum";
		// String columnNamePattern = null;

		// BD2
		String ip = "127.0.0.1";
		String port = "50000";
		String dbname = "db2Test";
		String username = "db2admin";
		String password = "123456";
		String databasetype = "DB2";
		String schema = "DB2ADMIN";
		String procedureName = "USERPROC";
		String columnNamePattern = null;

		List<Map<String, Object>> procedureColumns = DBMSMetaUtil.getProcedureColumns(databasetype, ip, port, dbname,
				username, password, schema, procedureName, columnNamePattern);
		procedureColumns = MapUtil.convertKeyList2LowerCase(procedureColumns);
		String jsonT = JSONArray.toJSONString(procedureColumns, true);
		System.err.println(jsonT);
		System.err.println("procedureColumns.size()= " + procedureColumns.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	/**
	 * 检索目录中可用的每个表的访问权限的描述(获取不到)
	 */
	@Test
	public void getTablePrivileges() {
		// MySQL
		String ip = "127.0.0.1";
		String port = "3306";
		String dbname = "erm";
		String username = "root";
		String password = "123456";
		String databasetype = "mysql";
		String schema = dbname;
		String tableName = "MAJOR";

		// Oracle(盈科)
		// String ip = "10.238.225.34";
		// String port = "1521";
		// String dbname = "orcl";
		// String username = "DMDEV";
		// String password = "dmdev";
		// String databasetype = "oracle";
		// String schema = username;
		// String tableName = "TARGET";

		// Oracle(外运)
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String schema = username;
		// String procedureName = "GEN_SEQ_GET_VAL";
		// String columnNamePattern = "V_SEQ";

		// SQL Server
		// String ip = "127.0.0.1";
		// String port = "1433";
		// String dbname = "metadataTest";
		// String username = "sa";
		// String password = "123456";
		// String databasetype = "SQLSERVER2017";
		// String schema = "dbo";
		// String tableName = "DT_USER";

		// BD2
		// String ip = "127.0.0.1";
		// String port = "50000";
		// String dbname = "db2Test";
		// String username = "db2admin";
		// String password = "123456";
		// String databasetype = "DB2";
		// String schema = "DB2ADMIN";
		// String procedureName = "USERPROC";
		// String columnNamePattern = null;

		List<Map<String, Object>> procedureColumns = DBMSMetaUtil.getTablePrivileges(databasetype, ip, port, dbname,
				username, password, schema, tableName);
		procedureColumns = MapUtil.convertKeyList2LowerCase(procedureColumns);
		String jsonT = JSONArray.toJSONString(procedureColumns, true);
		System.err.println(jsonT);
		System.err.println("procedureColumns.size()= " + procedureColumns.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}

	@Test
	public void getTest() {
		// MySQL
		// String ip = "127.0.0.1";
		// String port = "3306";
		// String dbname = "boot";
		// String username = "root";
		// String password = "123456";
		// String databasetype = "mysql";
		// String tableName = "user";
		// String schema = dbname;

		// Oracle(盈科)
		String ip = "10.238.225.34";
		String port = "1521";
		String dbname = "orcl";
		String username = "DMDEV";
		String password = "dmdev";
		String databasetype = "oracle";
		String schema = username;
		String tableName = "TARGET";

		// Oracle(外运)
		// String ip = "172.30.254.7";
		// String port = "1521";
		// String dbname = "mdtstdb";
		// String username = "SINOTRANSMDM";
		// String password = "sinotransmdm";
		// String databasetype = "oracle";
		// String tableName = "AM_MODEL";
		// String schema = username;

		// BD2
		// String ip = "127.0.0.1";
		// String port = "50000";
		// String dbname = "db2Test";
		// String username = "db2admin";
		// String password = "123456";
		// String databasetype = "DB2";
		// String tableName = "AM_MODEL";
		// String schema = username;

		List<Map<String, Object>> procedureColumns = DBMSMetaUtil.getTest(databasetype, ip, port, dbname, username,
				password, tableName, schema, null);

		// procedureColumns =
		// MapUtil.convertKeyList2LowerCase(procedureColumns);
		// String jsonT = JSONArray.toJSONString(procedureColumns, true);
		// System.err.println(jsonT);
		// System.err.println("procedureColumns.size()= " +
		// procedureColumns.size());
		System.err.println("-----------------------------------------" + "-----------------------------------------");
		System.err.println("-----------------------------------------" + "-----------------------------------------");
	}
}
