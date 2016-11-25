package com.android.common.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fastandroid.lib.db.entity.BaseDB;

/**
 * @description 数据库外键
 * @author 许友爻
 * @date 2014年7月14日下午1:49:21
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseForeignKey {
	Class<? extends BaseDB> table();

	String tableFieldName() default "";
}
