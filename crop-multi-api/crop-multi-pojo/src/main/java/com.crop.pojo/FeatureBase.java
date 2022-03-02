package com.crop.pojo;

public class FeatureBase {
    private String id;

    private String botanyId;

    private String pestId;

    private String botanyName;

    private String pestName;

    private String picPath;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBotanyId() {
        return botanyId;
    }

    public void setBotanyId(String botanyId) {
        this.botanyId = botanyId == null ? null : botanyId.trim();
    }

    public String getPestId() {
        return pestId;
    }

    public void setPestId(String pestId) {
        this.pestId = pestId == null ? null : pestId.trim();
    }

    public String getBotanyName() {
        return botanyName;
    }

    public void setBotanyName(String botanyName) {
        this.botanyName = botanyName == null ? null : botanyName.trim();
    }

    public String getPestName() {
        return pestName;
    }

    public void setPestName(String pestName) {
        this.pestName = pestName == null ? null : pestName.trim();
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}