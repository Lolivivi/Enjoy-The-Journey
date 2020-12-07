package com.example.mybaidupanorma.bean;

import android.graphics.Bitmap;

/**
 * @author 田春雨
 */
public class MyWorks {
    private Bitmap photo;
    private String title;
    private String id;
    public MyWorks(){

    }

    public MyWorks(Bitmap photo, String title, String id) {
        this.photo = photo;
        this.title = title;
        this.id = id;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyWorks{" +
                "photo=" + photo +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
