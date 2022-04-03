package com.crop.service.impl;

import com.crop.mapper.ClassficationMapper;
import com.crop.pojo.Classfication;
import com.crop.service.ClassficationService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Auther: fyp
 * @Date: 2022/4/3
 * @Description:
 * @Package: com.crop.service.impl
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Service
public class ClassficationServiceImpl implements ClassficationService {

    @Autowired
    private ClassficationMapper classficationMapper;

    @Autowired
    private Sid sid;

    @Override
    public boolean queryClassficationIdIsExist(String classId) {

        Classfication classfication = classficationMapper.selectByPrimaryKey(classId);

        return classfication == null ? false : true;
    }

    @Override
    public Classfication queryClssfication(Classfication classfication) {

        Example classficationExample = new Example(Classfication.class);
        Criteria criteria = classficationExample.createCriteria();
        criteria.andEqualTo("name", classfication.getName());

        Classfication result = classficationMapper.selectOneByExample(classficationExample);

        return result;
    }

    @Override
    public void saveClassfication(Classfication classfication) {

        String classficationId = sid.nextShort();

        classfication.setId(classficationId);

        classficationMapper.insert(classfication);
    }



}
