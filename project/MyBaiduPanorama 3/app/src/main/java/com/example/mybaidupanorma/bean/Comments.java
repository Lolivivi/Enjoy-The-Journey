package com.example.mybaidupanorma.bean;

import java.util.List;

/**
 * @author 张硕
 */

public class Comments {
    private String comUser;
    private String comment;
    private String comTime;
    private List<Comments> childComments;
    private String likeCount;
    private String isLike;
    private String comUserHeader;

    public Comments(){}
    public Comments(String comUser, String comment, String comTime) {
        this.comUser = comUser;
        this.comment = comment;
        this.comTime = comTime;
    }

    public String getComUserHeader() {
        return comUserHeader;
    }

    public void setComUserHeader(String comUserHeader) {
        this.comUserHeader = comUserHeader;
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

    public List<Comments> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comments> childComments) {
        this.childComments = childComments;
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

    @Override
    public String toString() {
        return "Comments{" +
                "comUser='" + comUser + '\'' +
                ", comment='" + comment + '\'' +
                ", comTime='" + comTime + '\'' +
                '}';
    }
}
