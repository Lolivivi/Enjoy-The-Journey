package com.example.mybaidupanorma.bean;

public class VideoUser {
    private String nodeId;
    private String isLike;
    private String numLike;
    private String isColl;
    private String numColl;
    private String numComments;
    public VideoUser(){}
    public VideoUser(String nodeId, String isLike, String numLike, String isColl, String numColl, String numComments) {
        this.nodeId = nodeId;
        this.isLike = isLike;
        this.numLike = numLike;
        this.isColl = isColl;
        this.numColl = numColl;
        this.numComments = numComments;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getNumLike() {
        return numLike;
    }

    public void setNumLike(String numLike) {
        this.numLike = numLike;
    }

    public String getIsColl() {
        return isColl;
    }

    public void setIsColl(String isColl) {
        this.isColl = isColl;
    }

    public String getNumColl() {
        return numColl;
    }

    public void setNumColl(String numColl) {
        this.numColl = numColl;
    }

    public String getNumComments() {
        return numComments;
    }

    public void setNumComments(String numComments) {
        this.numComments = numComments;
    }
}
