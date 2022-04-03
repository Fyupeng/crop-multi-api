package com.crop.service;

import com.crop.pojo.Article;
import com.crop.utils.PagedResult;

import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/2
 * @Description:
 * @Package: com.crop.service
 * @Version: 1.0
 */
public interface ArticleService {
    void save(Article article);

    PagedResult queryArticleSelective(Article article, Integer begin, Integer end);

}
