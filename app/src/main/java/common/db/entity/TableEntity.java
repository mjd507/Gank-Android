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

    private Class<?> type;

    private ArrayList<ColumnEntity> fields;

    public TableEntity(Class<?> type) {
        this.type = type;

        tableName = type.getSimpleName();

        fields = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            fields.add(new ColumnEntity(field));
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Class<?> getType() {
        return type;
    }

    public ArrayList<ColumnEntity> getFields() {
        return fields;
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
                contentValues.put(columnEntity.getName(), (Boolean) value);
            } else {
                contentValues.put(columnEntity.getName(), value.toString());
            }
        }

        return contentValues;
    }
}
