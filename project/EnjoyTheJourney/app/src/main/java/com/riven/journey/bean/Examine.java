package com.riven.journey.bean;

/**
 * @author 田春雨
 * 我的收藏
 */
public class Examine {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Examine{" +
                "title='" + title + '\'' +
                '}';
    }
}
