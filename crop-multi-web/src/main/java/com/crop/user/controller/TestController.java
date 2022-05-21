package com.crop.user.controller;

import com.crop.mapper.UserMapper;
import com.crop.pojo.User;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/5/10
 * @Description:
 * @Package: com.crop.user.controller
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "/test")
@Api(value = "测试相关业务的接口", tags = {"测试相关业务的controller"})
public class TestController extends BasicController {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping(value = "/testL1Cache")
    @ApiOperation(value = "测试mybatis一级缓存", notes = "测试mybatis一级缓存的接口")
    public CropJSONResult testL1Cache() {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper1 = sqlSession.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("test");

        List<User> select1 = mapper1.select(user);
        System.out.println(select1.get(0).hashCode());

        List<User> select2 = mapper2.select(user);
        System.out.println(select2.get(0).hashCode());

        sqlSession.close();
        return CropJSONResult.ok(select1);
    }

    @GetMapping(value = "/testL2Cache")
    @ApiOperation(value = "测试mybatis二级级缓存", notes = "测试mybatis二级缓存的接口")
    public CropJSONResult testL2Cache() {


        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);

        User user = new User();
        user.setUsername("test");

        List<User> select1 = mapper1.select(user);
        System.out.println(select1.get(0).hashCode());
        System.out.println(select1.get(0).getUsername());
        select1.get(0).setUsername("abababa");

        sqlSession1.close();

        User user1 = new User();
        user1.setId("123456");
        user1.setUsername("test");
        user1.setPassword("aaaaa");
        user1.setPermission(1);
        mapper3.updateByPrimaryKey(user1);
        sqlSession3.close();


        List<User> select2 = mapper2.select(user);
        System.out.println(select2.get(0).hashCode());
        System.out.println(select2.get(0).getUsername());
        System.out.println(select2.get(0).getPassword());

        sqlSession2.close();

        return CropJSONResult.ok(select1);
    }

}
