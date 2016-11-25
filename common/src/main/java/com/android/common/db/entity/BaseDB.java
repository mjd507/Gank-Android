package com.android.common.db.entity;


import com.android.common.db.annotation.DatabaseColumn;
import com.android.common.db.annotation.DatabasePrimaryKey;

/**
 * @description 数据库表实体基类
 * @author 许友爻
 * @date 2014年6月17日 下午12:46:46
 * @version 1.0
 */
public class BaseDB{
	@DatabasePrimaryKey
	@DatabaseColumn(name="_id")
	public long id;
}
