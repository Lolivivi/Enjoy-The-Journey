package com.example.mybaidupanorma.util;

/**
 * @author 梁钰钊
 * 喜欢我的列表的实体类属性
 * 给我点赞的用户的用户名，头像，被点赞的作品名字，作品封面
 */
public class LikeData {
    private String username;
    private String name;
    private String userHeadsrc;
    private String coverimg;

    public String getCoverimg() {
        return coverimg;
    }

    public void setCoverimg(String coverimg) {
        this.coverimg = coverimg;
    }

    public LikeData(String username, String name, String userHeadsrc,String coverimg) {
        this.username = username;
        this.name = name;
        this.userHeadsrc = userHeadsrc;
        this.coverimg = coverimg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserHeadsrc() {
        return userHeadsrc;
    }

    public void setUserHeadsrc(String userHeadsrc) {
        this.userHeadsrc = userHeadsrc;
    }

    @Override
    public String toString() {
        return "LikeData{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", userHeadsrc='" + userHeadsrc + '\'' +
                '}';
    }
}
