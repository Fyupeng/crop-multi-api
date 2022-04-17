package com.crop.service.impl;

import com.crop.mapper.UserInfoMapper;
import com.crop.mapper.UserMapper;
import com.crop.pojo.User;
import com.crop.pojo.UserInfo;
import com.crop.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@SuppressWarnings("all")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUserIdIsExist(String userId) {

        User user = new User();
        user.setId(userId);
        User result = userMapper.selectOne(user);

        return result == null ? false : true;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {

        User user = new User();
        user.setUsername(username);
        User result = userMapper.selectOne(user);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUser(String userId) {
        Example userExample = new Example(User.class);

        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        User user = userMapper.selectOneByExample(userExample);

        return user;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserInfo queryUserInfo(String userId) {
        Example userInfoExample = new Example(UserInfo.class);

        Criteria criteria = userInfoExample.createCriteria();
        criteria.andEqualTo("userId", userId);
        UserInfo userInfo = userInfoMapper.selectOneByExample(userInfoExample);
        return userInfo;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserForLogin(String username, String password){
        Example userExample = new Example(User.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        User result = userMapper.selectOneByExample(userExample);

        return result;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {

        String userId = sid.nextShort();
        String userInfoId = sid.nextShort();
        UserInfo userInfo = new UserInfo();

        user.setId(userId);

        userInfo.setId(userInfoId);
        userInfo.setUserId(userId);

        userMapper.insert(user);
        userInfoMapper.insert(userInfo);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){

        Example userExample = new Example(User.class);

        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        userMapper.updateByExampleSelective(user, userExample);

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserInfo(UserInfo userInfo){

        Example userInfoExample = new Example(UserInfo.class);

        Criteria criteria = userInfoExample.createCriteria();
        criteria.andEqualTo("id", userInfo.getId());
        criteria.andEqualTo("userId", userInfo.getUserId());

        userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
    }



}
