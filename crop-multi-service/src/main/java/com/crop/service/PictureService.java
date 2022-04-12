package com.crop.service;

import com.crop.pojo.Picture;
import com.crop.utils.PagedResult;

import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/1
 * @Description:
 * @Package: com.crop.service
 * @Version: 1.0
 */

public interface PictureService {


    void upload(Picture picture);

    PagedResult getAllPictures(Picture picture, Integer page, Integer pageSize);

    boolean deletePicture(Picture picture);

    boolean queryPictureIsExist(Picture picture);

    boolean updatePicture(Picture picture);

    Picture queryPicture(Picture picture);
}
