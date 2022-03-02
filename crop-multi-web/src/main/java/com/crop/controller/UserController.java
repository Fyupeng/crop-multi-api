package com.crop.controller;

import com.crop.pojo.User;
import com.crop.pojo.vo.UserVO;
import com.crop.service.UserService;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping(value = "/user")
public class UserController extends BasicController{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/uploadFace"/*, headers = "content-type=multipart/form-data"*/)
    public CropJSONResult uploadFace(String userId,
                                     @RequestParam(value = "file")
                                              //swagger2暂时不支持多文件上传,留着以后维护
                                      /*@ApiParam(value = "用户头像")*/ MultipartFile[] files) throws Exception{

        if(StringUtils.isBlank(userId)){
            return CropJSONResult.errorMsg("用户id不能为空");
        }
        //文件保存的命名空间
        //String fileSpace = "D:/imooc_videos_dev";
        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";


        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;


        try {
            if(files != null && files.length > 0){
                String fileName = files[0].getOriginalFilename();
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
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);//把输入流赋值给输出流，就是把图片复制到输出流对应的路径下
                }
            }else {
                return CropJSONResult.errorMsg("上传出错....");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CropJSONResult.errorMsg("上传出错....");
        }finally {
            if(fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        User user = new User();
        user.setId(userId);
        userService.updateUserInfo(user);

        return CropJSONResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/query")
    public CropJSONResult query(String userId, String fanId) {

        if(StringUtils.isBlank(userId)){
            return CropJSONResult.errorMsg("用户id不能为空");
        }

        User userInfo = userService.queryUserInfo(userId);
        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(userInfo,usersVO);

        return CropJSONResult.ok(usersVO);
    }







}
