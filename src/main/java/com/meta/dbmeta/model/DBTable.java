package com.meta.dbmeta.model;

/**
 * 检索给定目录中可用表的描述
 * 
 * @ClassName: DBTable
 * @author gaoy
 * @date 2018年12月10日 下午2:18:19
 */
public class DBTable {

	private String TABLE_CAT; // 字符串=>表目录（可能为null）
	private String TABLE_NAME;// 字符串=>表名
	private String REMARKS;// 备注（可能为null）
	private String TABLE_TYPE;// 字符串=>表类型

	public String getTABLE_CAT() {
		return TABLE_CAT;
	}

	public void setTABLE_CAT(String tABLE_CAT) {
		TABLE_CAT = tABLE_CAT;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}

	public String getTABLE_TYPE() {
		return TABLE_TYPE;
	}

	public void setTABLE_TYPE(String tABLE_TYPE) {
		TABLE_TYPE = tABLE_TYPE;
	}
}
