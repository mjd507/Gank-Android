package common.db.entity;

import java.lang.reflect.Field;

import common.db.annotation.Column;

import static java.lang.Enum.valueOf;

/**
 * 描述: 表实体中的字段元数据
 * Created by mjd on 2017/1/7.
 */

public class ColumnEntity {

    private String name;

    private Class<?> type;

    private Field field;

    public ColumnEntity(Field field) {
        this.field = field;
        if (field.isAnnotationPresent(Column.class)) {
            this.name = field.getAnnotation(Column.class).name();
        } else {
            this.name = field.getName();
        }

        type = field.getType();
    }

    /**
     * 获取字段类型
     */
    public String getSqlType() {
        if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).primaryKey()) {
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
        } else if (type.equals(Enum.class)) {
            return "TEXT";
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public Field getField() {
        return field;
    }

    public Class<?> getType() {
        return type;
    }

    public void setValue(ColumnEntity entity, Object value) {
        try {
            if (Enum.class.isAssignableFrom(type)) {
                value =  valueOf((Class<Enum>) type, value.toString());
            } else if (type.equals(boolean.class)) {
                value =  ((Integer) value).intValue() == 0 ? false : true;
            } else {
                value = value;
            }
            field.set(entity, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object getValue(ColumnEntity entity) {
        try {
            if (Enum.class.isAssignableFrom(type)) {
                return field.get(entity).toString();
            } else {
                return field.get(entity);
            }
        } catch (Exception ex) {
            return null;
        }
    }

}
