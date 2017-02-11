package com.cleaner.gank.tag.model;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class TagInfoBeen {

    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public Object who;
    public List<String> images;

    @Override
    public String toString() {
        return "TagInfoBeen{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who=" + who +
                ", images=" + images +
                '}';
    }
}
