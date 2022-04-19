package com.crop.user.controller;

import com.crop.pojo.UserInfo;
import com.crop.pojo.vo.UserInfoVO;
import com.crop.service.UserService;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/user")
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
public class UserController extends BasicController{

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/query")
    public CropJSONResult query(String userId) {

        if(StringUtils.isBlank(userId)){
            return CropJSONResult.errorMsg("用户id不能为空");
        }

        UserInfo userInfo = userService.queryUserInfo(userId);
        UserInfoVO userInfoVO = new UserInfoVO();

        if (userInfo != null)
            BeanUtils.copyProperties(userInfo,userInfoVO);

        return CropJSONResult.ok(userInfoVO);
    }



    @ApiOperation(value = "完善个人信息 - id字段请忽略", notes = "完善个人信息的接口")
    @ApiImplicitParam(name = "userInfo", value = "用户详情", required = true, dataType = "UserInfo", paramType = "body")
    @PostMapping(value = "/completeUserInfo")
    public CropJSONResult completeUserInfo(@RequestBody UserInfo userInfo) {

        if (StringUtils.isBlank(userInfo.getUserId())) {
            return CropJSONResult.errorMsg("用户id不能为空");
        }

        UserInfo userInfoByUserId = userService.queryUserInfo(userInfo.getUserId());
        if (userInfoByUserId == null) {
            return CropJSONResult.errorMsg("用户id不存在");
        }

        // id 是唯一标识符，不可更改
        userInfo.setId(userInfoByUserId.getId());

        userService.updateUserInfo(userInfo);

        return CropJSONResult.ok();
    }

    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form")
    @PostMapping(value = "/uploadFace", headers = "content-type=multipart/form-data")
    public CropJSONResult uploadFace(String userId,
                                     /*@RequestParam(value = "file")  这两个注解不能搭配使用，会导致 文件上传按钮失效*/
                                     @ApiParam(value = "头像") MultipartFile file) throws Exception{
        if(StringUtils.isBlank(userId)){
            return CropJSONResult.errorMsg("用户id不能为空");
        }
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";


        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;


        try {
            if(file != null){
                String fileName = file.getOriginalFilename();
                System.out.println("fileName" + fileName);
                if (StringUtils.isNotBlank(fileName)) {
                    //文件上传的最终保存路径
                    String finalFacePath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalFacePath);
                    //创建用户文件夹
                    if (outFile.getParentFile() != null && !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);//把输入流赋值给输出流，就是把图片复制到输出流对应的路径下
                }
            }else {
                return CropJSONResult.errorMsg("上传出错1....");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CropJSONResult.errorMsg("上传出错2....");
        }finally {
            if(fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        /**
         * User 与 UserInfo 是 一一对应的关系，UserInfo 有两个候选键 id 和 userId
         */
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setAvatar(uploadPathDB);

        userService.updateUserInfo(userInfo);

        return CropJSONResult.ok(uploadPathDB);
    }







}
