package com.crop.service;

import com.crop.pojo.Article;
import com.crop.pojo.Articles2tags;
import com.crop.pojo.vo.ArticleVO;
import com.crop.utils.PagedResult;

import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: fyp
 * @Date: 2022/4/2
 * @Description:
 * @Package: com.crop.service
 * @Version: 1.0
 */
public interface ArticleService {

    boolean queryArticleIsExist(String articleId);

    boolean queryArticleIsUser(Article article);

    boolean save(Article article);

    PagedResult queryArticleSelective(Article article, Integer page, Integer pageSize);

    ArticleVO queryArticleDetail(String articleId);

    boolean saveWithIdAndUserId(Article article);

    void multiUpdateArticleReadCounts(List<String> articleIdKeys, Map<String, String> articleMap);

    void removeArticle(String articleId);

    PagedResult queryArticleByTime(Long timeDifference, Integer page, Integer pageSize);
}
