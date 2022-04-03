package com.crop.service;

import com.crop.pojo.Classfication;

/**
 * @Auther: fyp
 * @Date: 2022/4/3
 * @Description:
 * @Package: com.crop.service
 * @Version: 1.0
 */
public interface ClassficationService {
    boolean queryClassficationIdIsExist(String classId);

    void saveClassfication(Classfication classfication);

    Classfication queryClssfication(Classfication classfication);
}
