package com.crop.controller;

import com.crop.pojo.Picture;
import com.crop.pojo.User;
import com.crop.service.PictureService;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: fyp
 * @Date: 2022/4/1
 * @Description:
 * @Package: com.crop.controller
 * @Version: 1.0
 */

@RestController
@Api(value = "图片上传识别相关业务的接口", tags = {"图片上传识别相关业务的controller"})
@RequestMapping(value = "/picture")
public class PictureController extends BasicController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "上传图片", notes = "上传图片的接口")
    @ApiImplicitParam(name = "Picture", value = "图片信息", required = true, dataType = "Picture", paramType = "form")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public CropJSONResult upload(@RequestBody Picture picture,
            /*@RequestParam(value = "file")  这两个注解不能搭配使用，会导致 文件上传按钮失效*/
                                     @ApiParam(value = "图片") MultipartFile file) throws Exception{

        if(StringUtils.isBlank(picture.getUserId()) || StringUtils.isBlank(picture.getPictureDesc())){
            return CropJSONResult.errorMsg("用户id和图片描述不能为空");
        }

        String userId = picture.getUserId();
        String pictureDesc = picture.getPictureDesc();

        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/picture";


        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String fileName;

        try {
            if(file != null){
                fileName = file.getOriginalFilename();
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

        Picture p = new Picture();
        p.setUserId(userId);
        p.setPicturePath(uploadPathDB);
        p.setPictureDesc(pictureDesc);

        pictureService.upload(p);

        return CropJSONResult.ok(uploadPathDB);
    }

}
