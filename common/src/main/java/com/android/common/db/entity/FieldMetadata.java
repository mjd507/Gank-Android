package com.android.common.db.entity;

import com.android.common.db.annotation.DatabaseColumn;
import com.android.common.db.annotation.DatabasePrimaryKey;

import java.lang.reflect.Field;

/**
 * 描述:
 * Created by mjd on 2016/11/26.
 */

public class FieldMetadata {
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private Class<?> type;
    /**
     * 属性
     */
    private Field field;

    /**
     * 构造
     * @param field 属性
     */
    public FieldMetadata(Field field) {
        this.field = field;
        if(field.isAnnotationPresent(DatabaseColumn.class)){
            this.name=field.getAnnotation(DatabaseColumn.class).name();
        }else{
            this.name = field.getName();
        }

        type = field.getType();
    }

    /**
     * 获取字段类型
     * @return
     */
    public String getSqlType() {
        if (field.isAnnotationPresent(DatabasePrimaryKey.class)) {
            return "INTEGER PRIMARY KEY AUTOINCREMENT";
        }
        else if (type.equals(String.class)) {
            return "TEXT";
        }
        else if (type.equals(int.class)||type.equals(Integer.class)) {
            return "INT";
        }
        else if (type.equals(long.class)||type.equals(Long.class)) {
            return "INT";
        }
        else if (type.equals(boolean.class)||type.equals(Boolean.class)) {
            return "INT";
        }
        else if (type.equals(double.class)||type.equals(Double.class)) {
            return "FLOAT";
        }
        else if (type.equals(Enum.class)) {
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

    /**
     * 设置值
     * @param entity 实体
     * @param value 值
     */
    public void setValue(BaseDB entity, Object value) {
        try {
            field.set(entity, dbToValue(value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 获取值
     * @param entity 实体
     * @return 值
     */
    public Object getValue(BaseDB entity) {
        try {
            Object value=valueToDB(field.get(entity));
            return value;
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 实体属性到数据库的属性映射
     */
    private Object valueToDB(Object value) {

        if (Enum.class.isAssignableFrom(type)) {
            return value.toString();
        }
        else {
            return value;
        }
    }
    /**
     * 数据库属性到实体的映射
     * @param value
     * @return
     */
    private Object dbToValue(Object value) {

        if (Enum.class.isAssignableFrom(type)) {
            return Enum.valueOf((Class<Enum>)type, value.toString());
        }
        else if (type.equals(boolean.class)) {
            return ((Integer)value).intValue() == 0 ? false : true;
        }
        else {
            return value;
        }
    }
}
