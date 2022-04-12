package com.crop.service.impl;

import com.crop.mapper.ClassficationMapper;
import com.crop.pojo.Classfication;
import com.crop.service.ClassficationService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

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
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryClassficationIdIsExist(String classId) {

        Classfication classfication = classficationMapper.selectByPrimaryKey(classId);

        return classfication == null ? false : true;
    }

    /**
     * 当前没有事务，以 无事务方式 执行，有 事务，多个查询线程 使用同一个 事务
     * @param classfication
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Classfication queryClssfication(Classfication classfication) {

        List<Classfication> result = classficationMapper.select(classfication);

        return (result == null || result.size() == 0) ? null : result.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Classfication> queryAllClassfications() {

        Example classficationExample = new Example(Classfication.class);
        Criteria criteria = classficationExample.createCriteria();

        List<Classfication> result = classficationMapper.selectAll();

        return result;
    }


    /**
     * 当前没有其他事务，新建事务，这样该方法 调用多次 实际上是 多个事务，体现原子性
     * @param classfication
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveClassfication(Classfication classfication) {

        String classficationId = sid.nextShort();

        classfication.setId(classficationId);

        int i = classficationMapper.insert(classfication);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateClassfication(Classfication clssfication) {

        int i = classficationMapper.updateByPrimaryKeySelective(clssfication);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteClassfication(String classficationId) {

        int i = classficationMapper.deleteByPrimaryKey(classficationId);

        return i > 0 ? true : false;
    }



}







