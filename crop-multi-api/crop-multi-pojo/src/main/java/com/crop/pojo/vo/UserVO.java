package com.crop.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;

public class UserVO {
    /**
     * 主键，注意：可能逆向表生成对象与mysql.user冲突
     */
    private String id;

    private String userToken;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户权限，1：表示游客，2：表示普通用户，3：管理员
     */
    private Integer permission;

    /**
     * 获取主键，注意：可能逆向表生成对象与mysql.user冲突
     *
     * @return id - 主键，注意：可能逆向表生成对象与mysql.user冲突
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键，注意：可能逆向表生成对象与mysql.user冲突
     *
     * @param id 主键，注意：可能逆向表生成对象与mysql.user冲突
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */



    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户权限，1：表示游客，2：表示普通用户，3：管理员
     *
     * @return permission - 用户权限，1：表示游客，2：表示普通用户，3：管理员
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     * 设置用户权限，1：表示游客，2：表示普通用户，3：管理员
     *
     * @param permission 用户权限，1：表示游客，2：表示普通用户，3：管理员
     */
    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}