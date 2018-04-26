package com.kaku.colorfulnews.mvp.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Email:1479714932@qq.com
 *
 * @author:xsl Date:2018/4/25,Time:16:46
 * Description:
 */

public class CollectBean extends RealmObject {
    private String title;
    private String time;
    private String link;
    private String img;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
