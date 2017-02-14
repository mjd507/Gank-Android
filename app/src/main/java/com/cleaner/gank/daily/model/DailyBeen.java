package com.cleaner.gank.daily.model;

import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/7.
 */

public class DailyBeen {

    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
    public List<String> images;

    public boolean isRead;
}
