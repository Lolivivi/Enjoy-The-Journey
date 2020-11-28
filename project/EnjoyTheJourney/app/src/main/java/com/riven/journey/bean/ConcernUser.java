package com.riven.journey.bean;

import java.util.List;

/**
 * @author 张硕
 */

public class ConcernUser {
    private int userId;
    private String userName;
    private String relTime;
    private String userPhoto;
    private String firstImg;
    private List<String> imgUrls;
    private String artTitle;
    private String artLike;
    private String artColl;
    private List<Comments> artCom;
    private String artComCount;
    private String isLike;
    private String isColl;
    private String artId;

    public ConcernUser(){}

    public ConcernUser(int userId, String userName, String relTime, String userPhoto, String firstImg, List<String> imgUrls, String artTitle, String artLike, String artColl, List<Comments> artCom) {
        this.userId = userId;
        this.userName = userName;
        this.relTime = relTime;
        this.userPhoto = userPhoto;
        this.firstImg = firstImg;
        this.imgUrls = imgUrls;
        this.artTitle = artTitle;
        this.artLike = artLike;
        this.artColl = artColl;
        this.artCom = artCom;
    }

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsColl() {
        return isColl;
    }

    public void setIsColl(String isColl) {
        this.isColl = isColl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRelTime() {
        return relTime;
    }

    public void setRelTime(String relTime) {
        this.relTime = relTime;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }


    public String getArtLike() {
        return artLike;
    }

    public void setArtLike(String artLike) {
        this.artLike = artLike;
    }

    public String getArtColl() {
        return artColl;
    }

    public void setArtColl(String artColl) {
        this.artColl = artColl;
    }

    public List<Comments> getArtCom() {
        return artCom;
    }

    public void setArtCom(List<Comments> artCom) {
        this.artCom = artCom;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    public String getArtComCount() {
        return artComCount;
    }

    public void setArtComCount(String artComCount) {
        this.artComCount = artComCount;
    }

    @Override
    public String toString() {
        return "ConcernUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", relTime='" + relTime + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", imgUrls=" + imgUrls +
                ", artTitle='" + artTitle + '\'' +
                ", artLike=" + artLike +
                ", artColl=" + artColl +
                ", artCom=" + artCom +
                '}';
    }
}
