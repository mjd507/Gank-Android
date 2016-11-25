package com.android.common.db;

import com.fastandroid.lib.db.TASQLiteDatabase.TADBUpdateListener;
import com.fastandroid.lib.db.entity.EntityMetadata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description 管理数据库的创建和版本更新
 * @author 许友爻
 * @date 2014年6月17日 下午4:53:59
 * @version 1.0
 */
public class DBHelper extends SQLiteOpenHelper
{
	/**
	 * 数据库更新监听器
	 */
	private TADBUpdateListener mTadbUpdateListener;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库名字
	 * @param factory
	 *            可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标
	 * @param version
	 *            数据库版本
	 */
	public DBHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库名字
	 * @param factory
	 *            可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标
	 * @param version
	 *            数据库版本
	 * @param tadbUpdateListener
	 *            数据库更新监听器
	 */
	public DBHelper(Context context, String name, CursorFactory factory, int version, TADBUpdateListener tadbUpdateListener)
	{
		super(context, name, factory, version);
		this.mTadbUpdateListener = tadbUpdateListener;
	}

	/**
	 * 设置数据库更新监听器
	 * 
	 * @param mTadbUpdateListener
	 *            数据库更新监听器
	 */
	public void setOndbUpdateListener(TADBUpdateListener tadbUpdateListener)
	{
		this.mTadbUpdateListener = tadbUpdateListener;
	}

	/**
	 * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
	 * */
	public void onCreate(SQLiteDatabase db)
	{

	}

	/**
	 * 当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
	 * */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (mTadbUpdateListener != null)
		{
			mTadbUpdateListener.onUpgrade(db, oldVersion, newVersion);
		}
	}
}
