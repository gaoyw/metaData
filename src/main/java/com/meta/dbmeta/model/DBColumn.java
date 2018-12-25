package com.meta.dbmeta.model;

/**
 * 检索指定目录中可用的表列的描述
 * 
 * @ClassName: DBColumn
 * @author gaoy
 * @date 2018年12月10日 下午2:21:27
 */
public class DBColumn {

	private String type_name;// String =>数据源相关类型名称，对于UDT，类型名称是完全限定的
	// 字符串>ISO规则用于确定列的可空性 (1.YES-如果列可以包含NULL 2.否-如果列不能包含NULL 3.空字符串-如果列的可空性是未知的)
	private String is_nullable;
	// 字符串>指示此列是否自动递增(1.YES---如果列自动递增 2.否---如果列不自动递增 3.空字符串---如果无法确定列是否自动递增)
	private String is_autoincrement;
	// int>允许NULL (1.columnNoNulls-可能不允许NULL值 2.columnNullable-绝对允许NULL值 3.columnNullableUnknown-可空性未知)
	private String nullable;
	private String num_prec_radix;// int =>基数（通常为10或2）
	private String decimal_digits; // int =>小数位数。 对于DECIMAL_DIGITS不适用的数据类型，返回空值
	private String sql_data_type; // 未使用SQL_DATA_TYPE int =>
	private String column_name;// 字符串=>列名
	private String table_name;// String =>表名
	private String ordinal_position; // int =>表中的列索引（从1开始）
	// 字符串>指示这是否是生成的列(1.是-如果这是一个生成的列 2.否-如果这不是一个生成的列 3.空字符串-如果无法确定这是否是生成的列)
	private String is_generatedcolumn;
	private String column_size;// int =>列大小
	private String data_type;// SQL类型
	private String buffer_length;// 不使用BUFFER_LENGTH
	private String sql_datetime_sub;// int =>未使用
	private String table_cat;// 字符串=>表目录（可能为null）
	private String remarks; // 备注字符串=>注释描述列（可能为null ）
	private String column_def;	//字符串=>列的默认值，当值以单引号括起时，应将其解释为字符串（可能为null ）
	private String char_octet_length; // int =>用于char类型列中最大字节数
	private String table_schem;	//字符串=>表格式（可能是null ）
	
	// SCOPE_CATALOG字符串=>目录表的，它是引用属性的范围（ null如果DATA_TYPE不是REF）
	// SCOPE_SCHEMA字符串=>作为参考属性范围的表格（ null如果DATA_TYPE不是REF）
	// SCOPE_TABLE字符串=>表名称，该引用属性的（范围null如果DATA_TYPE不是REF）
	// SOURCE_DATA_TYPE短=>源类型来自java.sql.Types的独特的类型或用户生成Ref类型，SQL型的（null如果DATA_TYPE不是DISTINCT或用户生成的REF）

}
