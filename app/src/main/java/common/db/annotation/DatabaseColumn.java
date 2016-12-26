package common.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 数据库列名
 * Created by mjd on 2016/11/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseColumn {
    /**
     * 表字段名
     */
    String name();

    /**
     * true字段值可以为null，false字段值不能null。不指定默认true
     */
    boolean isNullable() default true;

    /**
     * true字段值唯一，false字段值可以重复。不指定默认false
     */
    boolean isUnique() default false;

    /**
     * 表字段默认值,不指定默认空字符串
     */
    String defaultValue() default "";
}
