package com.example.mybaidupanorma.bean;

public class Note {
    private String id;
    private String tel;
    private String index;
    private int like;
    private int collection;
    private int commit;
    private String title;
    private String content;
    private String time;
    private String place;
    private String type;
    private String model;
    private String tag;

    public Note() {
    }

    public Note(String id, String tel, String index, int like, int collection, int commit, String title, String content, String time, String place, String type, String model, String tag) {
        this.id = id;
        this.tel = tel;
        this.index = index;
        this.like = like;
        this.collection = collection;
        this.commit = commit;
        this.title = title;
        this.content = content;
        this.time = time;
        this.place = place;
        this.type = type;
        this.model = model;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public int getCommit() {
        return commit;
    }

    public void setCommit(int commit) {
        this.commit = commit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", tel='" + tel + '\'' +
                ", index='" + index + '\'' +
                ", like=" + like +
                ", collection=" + collection +
                ", commit=" + commit +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
