package com.android.common.db.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.ContentValues;

import com.fastandroid.annotation.Transient;
import com.fastandroid.exception.TADBAnnotationException;
import com.fastandroid.exception.TADBException;
import com.fastandroid.lib.db.EntityRepository;
import com.fastandroid.lib.db.annotation.DatabaseColumn;
import com.fastandroid.lib.db.annotation.DatabaseForeignKey;
import com.fastandroid.lib.db.annotation.DatabasePrimaryKey;
import com.fastandroid.lib.db.annotation.DatabaseTable;

/**
 * @description 表实体的元数据
 * @author 许友爻
 * @date 2014年6月18日 下午12:40:46
 * @version 1.0
 */
public class EntityMetadata {
	/**
	 * 类型
	 */
	private Class<?> type;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 数据库实体的字段信息
	 */
	private ArrayList<FieldMetadata> fields;

	/**
	 * 构造
	 * 
	 * @param type类型
	 */
	public EntityMetadata(Class<? extends BaseDB> type) {
		this.type = type;
		if (type.isAnnotationPresent(DatabaseTable.class)) {
			tableName = type.getAnnotation(DatabaseTable.class).name();
		} else {
			tableName = type.getSimpleName();
		}

		fields = new ArrayList<FieldMetadata>();
		for (Field field : type.getFields()) {
			// 不是表字段
			if (field.getAnnotation(Transient.class) == null) {
				fields.add(new FieldMetadata(field));
			}
		}
	}

	/**
	 * 获取删除表sql语句
	 * 
	 * @return sql语句
	 */
	public String getDropTableStatement() {
		return "DROP TABLE IF EXISTS " + tableName;
	}

	/**
	 * 获取创建数据库语句
	 * 
	 * @return 返回sql语句
	 */
	public String getCreateTableStatement() {
		StringBuffer b = new StringBuffer();
		b.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
		int index = 0;

		for (FieldMetadata field : fields) {
			b.append(field.getName()).append(" ");
			b.append(field.getSqlType()).append(" ");
			DatabasePrimaryKey primaryKey = field.getField().getAnnotation(
					DatabasePrimaryKey.class);
			DatabaseForeignKey foreignKey = field.getField().getAnnotation(
					DatabaseForeignKey.class);
			DatabaseColumn column = field.getField().getAnnotation(
					DatabaseColumn.class);

			// 解析普通列注解
			if (column != null && primaryKey == null) {
				if (column.isNullable()) {
					b.append("null").append(" ");
				} else {
					b.append("not null").append(" ");
				}
				if (column.isUnique()) {
					b.append("unique").append(" ");
				}
			}
			// 解析外键注解
			if (foreignKey != null) {
				String tempStr = "REFERENCES %s(%s) ON DELETE CASCADE ON UPDATE CASCADE";
				String tableName = EntityRepository.getInstance()
						.find(foreignKey.table()).getTableName();
				String refSql = String.format(tempStr, tableName,
						foreignKey.tableFieldName());
				b.append(refSql).append(" ");
			}
			b.append(index < fields.size() - 1 ? "," : ")");
			index++;
		}
		return b.toString();
	}

	public ContentValues createContentValues(BaseDB entity) {

		ContentValues contentValues = new ContentValues();

		for (FieldMetadata field : fields) {
			if (field.getField().isAnnotationPresent(DatabasePrimaryKey.class))
				continue;
			Object value = field.getValue(entity);
			if (value == null)
				continue;
			if (value instanceof String) {
				contentValues.put(field.getName(), (String) value);
			} else if (value instanceof Short) {
				contentValues.put(field.getName(), (Short) value);
			} else if (value instanceof Integer) {
				contentValues.put(field.getName(), (Integer) value);
			} else if (value instanceof Long) {
				contentValues.put(field.getName(), (Long) value);
			} else if (value instanceof Float) {
				contentValues.put(field.getName(), (Float) value);
			} else if (value instanceof Double) {
				contentValues.put(field.getName(), (Double) value);
			} else if (value instanceof Boolean) {
				contentValues.put(field.getName(), (Boolean) value);
			} else {
				contentValues.put(field.getName(), value.toString());
			}
		}

		return contentValues;
	}

	public Class<?> getType() {
		return type;
	}

	public String getTableName() {
		return tableName;
	}

	public ArrayList<FieldMetadata> getFields() {
		return fields;
	}

	/**
	 * 通过属性名称获取属性实体对象
	 * 
	 * @name 属性名
	 * @return 返回实体对象，没有则返回null
	 * */
	public FieldMetadata getField(String name) {
		for (FieldMetadata field : fields) {
			if (field.getName().equals(name))
				return field;
		}
		return null;
	}
}
