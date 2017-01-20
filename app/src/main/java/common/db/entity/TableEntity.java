package common.db.entity;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 描述: 表实体的元数据
 * Created by mjd on 2017/1/7.
 */

public class TableEntity {

    private String tableName;

    private ArrayList<ColumnEntity> fields;

    public TableEntity(Class<?> type) {
        tableName = type.getSimpleName();
        fields = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            fields.add(new ColumnEntity(field));
        }
    }

    public String getTableName() {
        return tableName;
    }

    public ColumnEntity getField(String name) {
        for (ColumnEntity field : fields) {
            if (field.getName().equals(name))
                return field;
        }
        return null;
    }

    public String getDropTableStatement() {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public String getCreateTableStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
        int index = 0;

        for (ColumnEntity field : fields) {
            sb.append(field.getName()).append(" ");
            sb.append(getSqlType(field)).append(" ");
            sb.append(index < fields.size() - 1 ? "," : ")");
            index++;
        }
        return sb.toString();
    }

    private String getSqlType(ColumnEntity field) {
        Class type = field.getType();
        if (field.isPrimaryKey()) {
            return "INTEGER PRIMARY KEY AUTOINCREMENT";
        } else if (type.equals(String.class)) {
            return "TEXT";
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return "INT";
        } else if (type.equals(long.class) || type.equals(Long.class)) {
            return "INT";
        } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return "INT";
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return "FLOAT";
        }
        return null;
    }

    public ContentValues createContentValues(Object entity) {

        ContentValues contentValues = new ContentValues();

        for (ColumnEntity columnEntity : fields) {
            if (columnEntity.isPrimaryKey())
                continue;

            Object value = columnEntity.getValue(entity);
            if (value == null)
                continue;
            if (value instanceof String) {
                contentValues.put(columnEntity.getName(), (String) value);
            } else if (value instanceof Short) {
                contentValues.put(columnEntity.getName(), (Short) value);
            } else if (value instanceof Integer) {
                contentValues.put(columnEntity.getName(), (Integer) value);
            } else if (value instanceof Long) {
                contentValues.put(columnEntity.getName(), (Long) value);
            } else if (value instanceof Float) {
                contentValues.put(columnEntity.getName(), (Float) value);
            } else if (value instanceof Double) {
                contentValues.put(columnEntity.getName(), (Double) value);
            } else if (value instanceof Boolean) {
                contentValues.put(columnEntity.getName(), (Boolean) value ? 1 : 2);//true 1; false 2
            } else {
                contentValues.put(columnEntity.getName(), value.toString());
            }
        }

        return contentValues;
    }
}
