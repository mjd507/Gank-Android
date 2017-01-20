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

    ColumnEntity(Field field) {
        this.field = field;
        field.setAccessible(true);//设置访问权限
        this.name = field.getName();
        this.primaryKey = field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).primaryKey();
        this.type = field.getType();
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setValue(Object entity, Object value) {
        try {
            if (type.equals(boolean.class)) {
                value = ((Integer) value) == 1; //true 1; false 2
            }
            field.set(entity, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Object getValue(Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
