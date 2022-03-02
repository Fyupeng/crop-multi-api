package com.crop.pojo;

public class Picture {
    private Integer pictureid;

    private String picturename;

    private String pictureroute;

    private Integer userid;

    public Integer getPictureid() {
        return pictureid;
    }

    public void setPictureid(Integer pictureid) {
        this.pictureid = pictureid;
    }

    public String getPicturename() {
        return picturename;
    }

    public void setPicturename(String picturename) {
        this.picturename = picturename == null ? null : picturename.trim();
    }

    public String getPictureroute() {
        return pictureroute;
    }

    public void setPictureroute(String pictureroute) {
        this.pictureroute = pictureroute == null ? null : pictureroute.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}