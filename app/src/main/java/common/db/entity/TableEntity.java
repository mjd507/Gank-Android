package common.db.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import common.db.annotation.Table;

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

}
