package com.crop.service.impl;

import com.crop.mapper.PictureMapper;
import com.crop.pojo.Picture;
import com.crop.service.PictureService;
import com.crop.utils.PagedResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryPictureIsExist(Picture picture) {
        List<Picture> result = pictureMapper.select(picture);

        return (result == null || result.size() == 0) ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Picture queryPicture(Picture picture) {

        List<Picture> result = pictureMapper.select(picture);

        return (result == null || result.size() == 0) ? null : result.get(0);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult getAllPictures(Picture picture, Integer page, Integer pageSize) {

        //分页查询对象
        if(page <= 0){
            page = 1;
        }

        page = page - 1;

        if(pageSize <= 0){
            pageSize = 10;
        }

        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, pageSize);

        List<Picture> pictureList = pictureMapper.select(picture);

        PageInfo<Picture> pageInfo = new PageInfo<>(pictureList);

        PagedResult pagedResult = new PagedResult();

        pagedResult.setRows(pageInfo.getList());
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setPage(page);

        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePicture(Picture picture) {

        int i = pictureMapper.updateByPrimaryKeySelective(picture);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void upload(Picture picture) {
        String pictureId = sid.nextShort();

        picture.setId(pictureId);
        picture.setUploadTime(new Date());

        pictureMapper.insert(picture);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deletePicture(Picture picture) {

        int i = pictureMapper.delete(picture);

        return i > 0 ? true : false;
    }


}
