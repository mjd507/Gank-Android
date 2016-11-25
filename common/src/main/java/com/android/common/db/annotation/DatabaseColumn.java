package com.android.common.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @description 数据库表列名
 * @author 许友爻
 * @date 2014年7月14日下午1:39:21
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseColumn {
	/**表字段名*/
	String name();
	/**true字段值可以为null，false字段值不能null。不指定默认true*/
	boolean isNullable() default true;
	/**true字段值唯一，false字段值可以重复。不指定默认false*/
	boolean isUnique() default false;
	/**表字段默认值,不指定默认空字符串*/
	public String defaultValue() default "";
}
//	/**true字段为外键，false字段不是外键。不指定默认为false*/
//	boolean isForeign() default false;
