package com.android.common.db.annotation;

import com.android.common.db.entity.BaseDB;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: 数据库外键
 * Created by mjd on 2016/11/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DatabaseForeignKey {
    Class<? extends BaseDB> table();

    String tableFieldName() default "";
}
