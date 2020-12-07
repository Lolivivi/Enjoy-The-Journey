package com.example.mybaidupanorma.bean;

/**
 * @author 张硕
 */
public class ChildComments {
    private String comUser;
    private String comment;
    private String comTime;
    private String likeCount;
    private String isLike;
    private String comUserHeader;

    public ChildComments(){}
    public ChildComments(String comUser, String comment, String comTime, String likeCount, String isLike, String comUserHeader) {
        this.comUser = comUser;
        this.comment = comment;
        this.comTime = comTime;
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.comUserHeader = comUserHeader;
    }

    public String getComUser() {
        return comUser;
    }

    public void setComUser(String comUser) {
        this.comUser = comUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getComUserHeader() {
        return comUserHeader;
    }

    public void setComUserHeader(String comUserHeader) {
        this.comUserHeader = comUserHeader;
    }

    @Override
    public String toString() {
        return "ChildComments{" +
                "comUser='" + comUser + '\'' +
                ", comment='" + comment + '\'' +
                ", comTime='" + comTime + '\'' +
                ", likeCount='" + likeCount + '\'' +
                ", isLike='" + isLike + '\'' +
                ", comUserHeader='" + comUserHeader + '\'' +
                '}';
    }
}
