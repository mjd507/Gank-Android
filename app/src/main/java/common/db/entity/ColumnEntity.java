package common.db.entity;

import java.lang.reflect.Field;

import common.db.annotation.Column;

/**
 * 描述: 表实体中的字段元数据
 * Created by mjd on 2017/1/7.
 */
public class ColumnEntity {

    private String name;

    private Class<?> type;

    private boolean primaryKey;

    private Field field;

    public ColumnEntity(Field field) {
        this.field = field;
        field.setAccessible(true);//设置访问权限
        if (field.isAnnotationPresent(Column.class)) {
            this.name = field.getAnnotation(Column.class).name();
            this.primaryKey = field.getAnnotation(Column.class).primaryKey();
        } else {
            this.name = field.getName();
            this.primaryKey = false;
        }

        this.type = field.getType();
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public Field getField() {
        return field;
    }

    public void setValue(Object entity, Object value) {
        try {
            field.set(entity, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object getValue(Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSqlType() {
        if (primaryKey) {
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
}
