package com.crop.service;

import com.crop.pojo.User;
import com.crop.pojo.UserInfo;

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
    public void saveUser(User user);

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
    public void updateUser(User user);

    /**
     * @Description: 用户修改详细信息
     * @param user
     */
    public void updateUserInfo(UserInfo user);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public UserInfo queryUserInfo(String userId);

    boolean queryUserIdIsExist(String userId);
}
