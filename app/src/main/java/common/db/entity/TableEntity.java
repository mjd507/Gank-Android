package common.db.entity;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

import common.db.annotation.Column;
import common.db.annotation.Table;

/**
 * 描述:
 * Created by mjd on 2017/1/7.
 */

public class TableEntity {
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
    private ArrayList<ColumnEntity> fields;

    /**
     * 构造
     */
    public TableEntity(Class<?> type) {
        this.type = type;
        if (type.isAnnotationPresent(Table.class)) {
            tableName = type.getAnnotation(Table.class).name();
        } else {
            tableName = type.getSimpleName();
        }

        fields = new ArrayList<>();
        for (Field field : type.getFields()) {
            fields.add(new ColumnEntity(field));
        }
    }

    /**
     * 获取删除表sql语句
     */
    public String getDropTableStatement() {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    /**
     * 获取创建数据库语句
     */
    public String getCreateTableStatement() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
        int index = 0;

        for (ColumnEntity field : fields) {
            sb.append(field.getName()).append(" ");
            sb.append(field.getSqlType()).append(" ");
            sb.append(index < fields.size() - 1 ? "," : ")");
            index++;
        }
        return sb.toString();
    }

    public ContentValues createContentValues(ColumnEntity entity) {

        ContentValues contentValues = new ContentValues();

        for (ColumnEntity field : fields) {
            if (field.getField().getAnnotation(Column.class).primaryKey())
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

    public ArrayList<ColumnEntity> getFields() {
        return fields;
    }

    /**
     * 通过属性名称获取属性实体对象
     */
    public ColumnEntity getField(String name) {
        for (ColumnEntity field : fields) {
            if (field.getName().equals(name))
                return field;
        }
        return null;
    }
}
