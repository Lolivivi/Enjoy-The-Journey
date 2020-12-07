package com.example.mybaidupanorma.bean;

import android.graphics.Bitmap;

/**
 * 景点类
 * @author 姚沅令
 * @date 2020-11-29
 */
public class Spot {
    private String address;
    private Bitmap image;
    private String info;
    private double lat;
    private double longt;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Spot{" +
                "address='" + address + '\'' +
                ", image=" + image +
                ", info='" + info + '\'' +
                ", lat=" + lat +
                ", longt=" + longt +
                '}';
    }
}