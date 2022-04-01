package com.crop.pojo;

import java.util.Date;

public class Picture {
    private String id;

    private String userId;

    private String picturePath;

    private String pictureDesc;

    private Double pictureWidth;

    private Double pictureHeight;

    private Date uploadTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getPictureDesc() {
        return pictureDesc;
    }

    public void setPictureDesc(String pictureDesc) {
        this.pictureDesc = pictureDesc == null ? null : pictureDesc.trim();
    }

    public Double getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(Double pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public Double getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(Double pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}