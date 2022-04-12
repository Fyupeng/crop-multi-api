package com.crop.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "user_info")
@ApiModel(description = "操作用户上传头像和其他详细操作的model")
public class UserInfo {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户标识符[id]", required = true)
    private String userId;

    /**
     * 我的头像，如果没有就默认给一张
     */
    @ApiModelProperty(hidden = true)
    private String avatar;

    /**
     * 名称
     */
    @ApiModelProperty(value = "别名")
    private String nickname;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话号码")
    private Integer tel;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    public UserInfo() {
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取我的头像，如果没有就默认给一张
     *
     * @return avatar - 我的头像，如果没有就默认给一张
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置我的头像，如果没有就默认给一张
     *
     * @param avatar 我的头像，如果没有就默认给一张
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取名称
     *
     * @return nickname - 名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置名称
     *
     * @param nickname 名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取电话
     *
     * @return tel - 电话
     */
    public Integer getTel() {
        return tel;
    }

    /**
     * 设置电话
     *
     * @param tel 电话
     */
    public void setTel(Integer tel) {
        this.tel = tel;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

}