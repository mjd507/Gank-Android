package com.android.common.db.entity;

import com.android.common.db.annotation.DatabaseColumn;
import com.android.common.db.annotation.DatabasePrimaryKey;

/**
 * 描述: 数据库表的实体基类
 * Created by mjd on 2016/11/26.
 */

public class BaseDB {
    @DatabasePrimaryKey
    @DatabaseColumn(name = "_id")
    public long id;
}
