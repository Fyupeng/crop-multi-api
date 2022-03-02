package com.crop.service.impl;

import com.crop.mapper.UserMapper;
import com.crop.pojo.User;
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
public class UserServiceImpl<queryUserInfo> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        User user = new User();
        user.setUsername(username);
        User result = userMapper.selectOne(user);

        return result == null ? false : true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveuser(User user) {

        String userId = sid.nextShort();

        user.setId(userId);

        userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserForLogin(String username, String password){
        Example userExample = new Example(User.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        User result = userMapper.selectOneByExample(userExample);

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(User user){

        Example userExample = new Example(User.class);

        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        userMapper.updateByExampleSelective(user, userExample);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserInfo(String userId) {
        Example userExample = new Example(User.class);

        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        User user = userMapper.selectOneByExample(userExample);
        return user;
    }







}
