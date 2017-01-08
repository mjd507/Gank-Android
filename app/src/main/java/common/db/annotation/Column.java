package common.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 数据库列名
 * Created by mjd on 2017/1/7.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 表字段名
     */
    String name();

    /**
     * 表字段属性,不指定默认空字符串
     */
    String property() default "";

    /**
     * 是否为主键 默认不是主键
     */
    boolean primaryKey() default false;

}
