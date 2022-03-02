package com.crop.service;

import com.crop.pojo.User;

public interface UserService {
    /**
     * @Description: 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * @Description: 保存用户（注册用户）
     * @param user
     */
    public void saveuser(User user);

    /**
     * @Description: 用户登录，根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    public User queryUserForLogin(String username, String password);

    /**
     * @Description: 用户修改信息
     * @param user
     */
    public void updateUserInfo(User user);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public User queryUserInfo(String userId);

}
