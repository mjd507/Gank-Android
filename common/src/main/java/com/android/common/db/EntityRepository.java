package com.android.common.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.fastandroid.exception.TADBNotOpenException;
import com.fastandroid.lib.db.entity.BaseDB;
import com.fastandroid.lib.db.entity.EntityMetadata;
import com.fastandroid.lib.log.TALogger;

import android.database.sqlite.SQLiteDatabase;

/**
 * @description 数据库表实体仓库
 * @author 许友爻
 * @date 2014年6月17日 下午4:53:59
 * @version 1.0
 */
public class EntityRepository {
	private static EntityRepository entityRepository;
	/**
	 * 注册的表实体，键对值关系存储，键：class对象 值：实体数据
	 */
	private HashMap<Class<?>, EntityMetadata> entities = new HashMap<Class<?>, EntityMetadata>();
	/**
	 * 表实体数据集合
	 */
	private ArrayList<EntityMetadata> entityList = new ArrayList<EntityMetadata>();

	/**
	 * 获取表实体数据
	 * 
	 * @return 实体数据数组
	 */
	public EntityMetadata[] getEntities() {
		EntityMetadata[] metadatas = new EntityMetadata[entityList.size()];
		entityList.toArray(metadatas);
		
		return metadatas;
	}

	/**
	 * 注册数据库表实体
	 * 
	 * @param types
	 *        实体类型
	 */
	public void register(Class<? extends BaseDB>... types) {
		for (Class<? extends BaseDB> type : types) {
			EntityMetadata m = new EntityMetadata(type);
			entities.put(type, m);
			entityList.add(m);
		}
	}

	/**
	 * 获得表实体
	 * 
	 * @param type
	 *            实体类型
	 * @return
	 */
	public EntityMetadata find(Class<? extends BaseDB> type) {
		return entities.get(type);
	}

	/**
	 * 获取表容器单例对象
	 * */
	public static EntityRepository getInstance() {
		if (entityRepository == null) {
			entityRepository = new EntityRepository();
		}
		return entityRepository;
	}

	/**
	 * 销毁单例对象，释放占用内存
	 * */
	public void destroyInstance() {
		entities.clear();
		entityList.clear();
		entities = null;
		entityList = null;
		entityRepository = null;
	}

	/**
	 * 创建容器中的所有表
	 * 
	 * @param dropTablesFirst
	 *            判断的标签。如果存在已存在此表，true删除并重新创建，false不删除不重新创建
	 * @param database
	 *            数据库操作对象
	 */
	public void createTables(boolean dropTablesFirst, TASQLiteDatabase database) {
		try {
			for (EntityMetadata m : entityList) {
				if (dropTablesFirst) {
					database.execute(m.getDropTableStatement(), null);
				}
				database.execute(m.getCreateTableStatement(), null);
			}
		} catch (TADBNotOpenException ex) {
			TALogger.e(EntityRepository.this, ex.getMessage());
		}
	}
}
