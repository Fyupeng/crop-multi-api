package com.crop.admin.controller;

import com.crop.pojo.User;
import com.crop.pojo.vo.UserVO;
import com.crop.service.UserService;
import com.crop.user.controller.BasicController;
import com.crop.utils.CropJSONResult;
import com.crop.utils.MD5Utils;
import com.crop.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/admin")
@CrossOrigin
@Api(value = "管理员注册登录的接口", tags = {"管理员注册和登录的controller"})
public class AdminRegistLoginController extends BasicController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "管理员注册", notes = "用管理员注册的接口")
    @ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "User", paramType = "body")
    @PostMapping(value = "/regist")
    public CropJSONResult regist(@RequestBody User user) throws Exception{

        //1. 判断用户名和密码必须不为空
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return CropJSONResult.errorMsg("用户名和密码不能为空");
        }

        //2. 判断用户是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        //3. 保存用户，注册信息
        if(!usernameIsExist){
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            // 防止 被 注入
            user.setPermission(3);
            userService.saveUser(user);
        }else {
            return CropJSONResult.errorMsg("用户名已存在");
        }
        user.setPassword("");
        //String uniqueToken = UUID.randomUUID().toString();
        //redis.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 * 30);
        //
        //UsersVO usersVO = new UsersVO();
        //BeanUtils.copyProperties(user, usersVO);
        //usersVO.setUserToken(uniqueToken);

        UserVO userVO = setUserRedisSessionToken(user);

        return CropJSONResult.ok(userVO);
    }

    public UserVO setUserRedisSessionToken(User userModel){
        String uniqueToken = UUID.randomUUID().toString();

        String userRedisSession = RedisUtils.getUserRedisSession(userModel.getId());

        redis.set(userRedisSession, uniqueToken,  60 * 30);

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(userModel, usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }

    @ApiOperation(value = "管理员登录", notes = "管理员登录的接口")
    @ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "User", paramType = "body")
    @PostMapping(value = "/login")
    public CropJSONResult login(@RequestBody User user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();


        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return CropJSONResult.ok("用户名或密码不能为空....");
        }

        User userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(user.getPassword()));

        if(userResult == null || userResult.getPermission() != 3){
            return CropJSONResult.errorMsg("用户名密码不正确,或为非管理员登录");
        }else {
            userResult.setPassword("");
            UserVO usersVO = setUserRedisSessionToken(userResult);
            return CropJSONResult.ok(usersVO);

        }
    }

    @ApiOperation(value = "管理员注销", notes = "管理员注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/logout")
    public CropJSONResult logout(String userId) throws Exception {

        String userRedisSession = RedisUtils.getUserRedisSession(userId);
        redis.del(userRedisSession);

        return CropJSONResult.ok("注销成功");

    }

}
