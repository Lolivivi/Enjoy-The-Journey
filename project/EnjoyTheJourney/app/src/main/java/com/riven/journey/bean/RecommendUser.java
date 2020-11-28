package com.riven.journey.bean;

/**
 * @author 张硕
 */
public class RecommendUser {
    private String imgUrl;
    private String title;
    private String userName;
    private String likeCount;
    private String userImg;
    private String artId;
    private String artIsLike;

    public RecommendUser(){}

    public RecommendUser(String imgUrl, String title, String userName, String likeCount, String userImg, String artId, String artIsLike) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.userName = userName;
        this.likeCount = likeCount;
        this.userImg = userImg;
        this.artId = artId;
        this.artIsLike = artIsLike;
    }

    public String getArtIsLike() {
        return artIsLike;
    }

    public void setArtIsLike(String artIsLike) {
        this.artIsLike = artIsLike;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }


    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public String toString() {
        return "RecommendUser{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", userImg='" + userImg + '\'' +
                ", artId='" + artId + '\'' +
                ", artIsLike='" + artIsLike + '\'' +
                '}';
    }
}
