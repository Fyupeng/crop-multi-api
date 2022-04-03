package com.crop.service.impl;

import com.crop.mapper.PictureMapper;
import com.crop.pojo.Picture;
import com.crop.service.PictureService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Auther: fyp
 * @Date: 2022/4/1
 * @Description:
 * @Package: com.crop.service.impl
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private Sid sid;

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public void upload(Picture picture) {
        String pictureId = sid.nextShort();

        picture.setId(pictureId);
        picture.setUploadTime(new Date());

        pictureMapper.insert(picture);


    }
}
