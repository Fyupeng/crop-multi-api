package com.crop.user.controller;

import com.crop.pojo.Picture;
import com.crop.service.PictureService;
import com.crop.utils.CropJSONResult;
import com.crop.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.FileCleaner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
@RequestMapping(value = "/user/picture")
@Api(value = "图片上传识别相关业务的接口", tags = {"图片上传识别相关业务的controller"})
public class UserPictureController extends BasicController {

    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "获取用户图片", notes = "获取用户图片的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/getAllPictures")
    public CropJSONResult getAllPictures(String userId, Integer page, Integer pageSize) {

        if(StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("用户id不能为空");
        }

        //前端不传该参时会初始化
        if(page == null){
            page = 1;
        }
        //前端不传该参时会初始化
        if(pageSize == null){
            pageSize = PICTURE_PAGE_SIZE;
        }

        Picture picture = new Picture();
        picture.setUserId(userId);

        PagedResult pageResult = pictureService.getAllPictures(picture, page, pageSize);

        return CropJSONResult.ok(pageResult);
    }

    @ApiOperation(value = "上传图片 - 图片id 请忽略", notes = "上传图片的接口")
    @ApiImplicitParam(name = "picture", value = "图片信息", required = true, dataType = "Picture", paramType = "body")
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

    @ApiOperation(value = "更改图片信息", notes = "更改图片信息的接口")
    @ApiImplicitParam(name = "picture", value = "图片", required = true, dataType = "Picture", paramType = "body")
    @PostMapping(value = "/modifyePicture")
    public CropJSONResult modifyePicture(Picture picture) {

        if (StringUtils.isBlank(picture.getUserId()) || StringUtils.isBlank(picture.getUserId())) {
            return CropJSONResult.errorMsg("图片id或用户id不能为空");
        }

        Picture pictureWithIdAndUserId = new Picture();
        pictureWithIdAndUserId.setId(picture.getId());
        pictureWithIdAndUserId.setUserId(picture.getUserId());
        boolean pictureIsExist = pictureService.queryPictureIsExist(pictureWithIdAndUserId);
        if (!pictureIsExist) {
            return CropJSONResult.errorMsg("用户不存在该图片");
        }

        boolean deletePictureIsTrue = pictureService.updatePicture(picture);

        return deletePictureIsTrue ?  CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致删除失败");

    }

    @ApiOperation(value = "删除用户图片", notes = "删除用户图片的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pictureId", value = "图片id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/removePicture")
    public CropJSONResult removePicture(String pictureId, String userId) {

        if (StringUtils.isBlank(pictureId) || StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("图片id或用户id不能为空");
        }

        Picture picture = new Picture();
        picture.setId(pictureId);
        picture.setUserId(userId);
        Picture result = pictureService.queryPicture(picture);
        if (result == null) {
            return CropJSONResult.errorMsg("用户不存在该图片");
        } else {
            String realPath = FILE_SPACE + result.getPicturePath();

            File file = new File(realPath);
            try {
                FileUtils.forceDeleteOnExit(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        boolean deletePictureIsTrue = pictureService.deletePicture(picture);

        return deletePictureIsTrue ?  CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致删除失败");

    }


}
