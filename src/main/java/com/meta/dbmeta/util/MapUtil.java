package com.meta.dbmeta.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map转换工具
 * 
 * @ClassName: MapUtil
 * @author gaoy
 * @date 2018年12月3日 下午2:48:28
 */
public class MapUtil {

	/**
	 * 将List中的Key转换为小写
	 * 
	 * @param list
	 *            返回新对象
	 */
	public static List<Map<String, Object>> convertKeyList2LowerCase(List<Map<String, Object>> list) {
		if (null == list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			Map<String, Object> result = convertKey2LowerCase(map);
			if (null != result) {
				resultList.add(result);
			}
		}
		return resultList;
	}

	/**
	 * 转换单个map,将key转换为小写.
	 * 
	 * @param map
	 *            返回新对象
	 */
	public static Map<String, Object> convertKey2LowerCase(Map<String, Object> map) {
		if (null == map) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Set<String> keys = map.keySet();
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = map.get(key);
			if (null == key) {
				continue;
			}
			String keyL = key.toLowerCase();
			result.put(keyL, value);
		}
		return result;
	}

	/**
	 * 将一个未处理的ResultSet解析为Map列表.
	 * 
	 * @param rs
	 */
	public static List<Map<String, Object>> parseResultSetToMapList(ResultSet rs) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (null == rs) {
			return null;
		}
		try {
			while (rs.next()) {
				Map<String, Object> map = parseResultSetToMap(rs);
				if (null != map) {
					result.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解析ResultSet的单条记录,不进行 ResultSet 的next移动处理
	 * 
	 * @param rs
	 * @return
	 */
	private static Map<String, Object> parseResultSetToMap(ResultSet rs) {
		if (null == rs) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int colNum = meta.getColumnCount();
			for (int i = 1; i <= colNum; i++) {
				// 列名
				String name = meta.getColumnLabel(i); // i+1
				Object value = rs.getObject(i);
				// 加入属性
				map.put(name, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
