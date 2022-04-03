package com.crop.service.impl;

import com.crop.mapper.ArticleRepository;
import com.crop.pojo.Article;
import com.crop.service.ArticleService;
import com.crop.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/2
 * @Description:
 * @Package: com.crop.service.impl
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private Sid sid;

    @Override
    public void save(Article article) {

        String articleId = sid.nextShort();
        article.setId(articleId);
        article.setCreateTime(new Date());

        articleRepository.save(article);
    }

    @Override
    public PagedResult queryArticleSelective(Article article, Integer page, Integer pageSize) {

        //分页查询对象
        if(page <= 0){
            page = 1;
        }

        page = page - 1;

        if(pageSize <= 0){
            pageSize = 6;
        }

        ExampleMatcher matching = ExampleMatcher.matching();
        Page<Article> all = null;

        Article a = new Article();

        // 存在 分类名 为 key
        if (!StringUtils.isBlank(article.getClassId())) {
            System.out.println(0);
            all = readAndExcute("classId", matching, article, page, pageSize);
        } else {
            Article titleArticle = new Article();
            titleArticle.setTitle(article.getTitle());
            all = readAndExcute("title", matching, titleArticle, page, pageSize);
            if (all.getTotalElements() == 0) {
                Article summaryArticle = new Article();
                summaryArticle.setSummary(article.getSummary());
                all = readAndExcute("summary", matching, summaryArticle, page, pageSize);
                if (all.getTotalElements() == 0) {
                    Article contentArticle = new Article();
                    contentArticle.setContent(article.getSummary());
                    all = readAndExcute("content", matching, contentArticle, page, pageSize);
                    if (all.getTotalElements() == 0)
                        return null;
                }
            }
        }
        // 总 记录数
        long total = all.getTotalElements();

        List<Article> content = all.getContent();
        // 总 页数
        int Pages = all.getTotalPages();

        PagedResult pagedResult = new PagedResult();
        // 设置 总记录数
        pagedResult.setRecords(total);
        // 设置 内容列表
        pagedResult.setRows(content);
        // 设置 当前页
        pagedResult.setPage(page);
        // 设置 总页数
        pagedResult.setTotal(Pages);

        return pagedResult;
    }

    private Page<Article> readAndExcute(String metcherProperty, ExampleMatcher matching, Article article, Integer page, Integer pageSize) {
        ExampleMatcher exampleMatcher = matching.withMatcher(metcherProperty, ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Article> articleExample = Example.of(article, exampleMatcher);
        Pageable pageable = new PageRequest(page, pageSize);
        return articleRepository.findAll(articleExample, pageable);
    }


}
