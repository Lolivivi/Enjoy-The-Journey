package com.example.mybaidupanorma.bean;

/**
 * 线上旅行类
 * @author 姚沅令
 * @date 2020-11-29
 */
public class Journey {
    //标题
    private String title;
    //大图
    private String image;
    //首地点介绍
    private String introduce;
    //首地点地址
    private String address;
    //子景点1图
    private String imag_one;
    //子景点2图
    private String imag_two;
    //子景点3图
    private String imag_there;
    private String address_one;
    private String address_two;
    private String address_there;
    private String introduce_one;
    private String introduce_two;
    private String introduce_there;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImag_one() {
        return imag_one;
    }

    public void setImag_one(String imag_one) {
        this.imag_one = imag_one;
    }

    public String getImag_two() {
        return imag_two;
    }

    public void setImag_two(String imag_two) {
        this.imag_two = imag_two;
    }

    public String getImag_there() {
        return imag_there;
    }

    public void setImag_there(String imag_there) {
        this.imag_there = imag_there;
    }

    public String getAddress_one() {
        return address_one;
    }

    public void setAddress_one(String address_one) {
        this.address_one = address_one;
    }

    public String getAddress_two() {
        return address_two;
    }

    public void setAddress_two(String address_two) {
        this.address_two = address_two;
    }

    public String getAddress_there() {
        return address_there;
    }

    public void setAddress_there(String address_there) {
        this.address_there = address_there;
    }

    public String getIntroduce_one() {
        return introduce_one;
    }

    public void setIntroduce_one(String introduce_one) {
        this.introduce_one = introduce_one;
    }

    public String getIntroduce_two() {
        return introduce_two;
    }

    public void setIntroduce_two(String introduce_two) {
        this.introduce_two = introduce_two;
    }

    public String getIntroduce_there() {
        return introduce_there;
    }

    public void setIntroduce_there(String introduce_there) {
        this.introduce_there = introduce_there;
    }

    @Override
    public String toString() {
        return "JourneyOnline{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", introduce='" + introduce + '\'' +
                ", address='" + address + '\'' +
                ", imag_one='" + imag_one + '\'' +
                ", imag_two='" + imag_two + '\'' +
                ", imag_there='" + imag_there + '\'' +
                ", address_one='" + address_one + '\'' +
                ", address_two='" + address_two + '\'' +
                ", address_there='" + address_there + '\'' +
                ", introduce_one='" + introduce_one + '\'' +
                ", introduce_two='" + introduce_two + '\'' +
                ", introduce_there='" + introduce_there + '\'' +
                '}';
    }
}
