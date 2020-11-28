package com.riven.journey.bean;

import android.graphics.Bitmap;

/**
 * @author 田春雨
 */
public class MyWorks {
    private Bitmap photo;
    private String title;

    public MyWorks(Bitmap photo, String title) {
        this.photo = photo;
        this.title = title;
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

    @Override
    public String toString() {
        return "MyWorks{" +
                "photo=" + photo +
                ", title='" + title + '\'' +
                '}';
    }
}
