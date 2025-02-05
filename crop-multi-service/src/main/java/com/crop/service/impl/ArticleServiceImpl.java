package com.crop.service.impl;

import com.crop.mapper.ArticleRepository;
import com.crop.mapper.Articles2tagsMapper;
import com.crop.mapper.ClassficationMapper;
import com.crop.mapper.UserInfoMapper;
import com.crop.pojo.*;
import com.crop.pojo.vo.ArticleVO;
import com.crop.pojo.vo.Articles2tagsVO;
import com.crop.service.ArticleService;
import com.crop.service.TagService;
import com.crop.utils.PagedResult;
import com.crop.utils.TimeAgoUtils;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserInfoMapper userinfoMapper;

    @Autowired
    private ClassficationMapper classficationMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private Articles2tagsMapper articles2tagsMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryArticleIsExist(String articleId) {

        Article article = new Article();
        article.setId(articleId);

        Example<Article> articleExample = Example.of(article);

        Optional<Article> one = articleRepository.findOne(articleExample);
        Article result = one.isPresent() ? one.get() : null;

        return article == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryArticleIsUser(Article article) {

        ExampleMatcher matching = ExampleMatcher.matching();
        ExampleMatcher exampleMatcher = matching.withMatcher("id", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<Article> articleExample = Example.of(article, exampleMatcher);

        Optional<Article> one = articleRepository.findOne(articleExample);
        Article result = one.isPresent() ? one.get() : null;

        return result == null ? false : true;
    }

    /**
     * 查询 所有文章 时， 不做 流量统计，查询单篇文章，统计 阅读量
     * @param article
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult queryArticleSelective(Article article, Integer page, Integer pageSize) {

        //分页查询对象
        if(page <= 0){
            page = 1;
        }
        // page 分页 在 mongodb 中是 从 0 开始的，转换为物理位置
        page = page - 1;

        if(pageSize <= 0){
            pageSize = 6;
        }

        ExampleMatcher matching = ExampleMatcher.matching();
        Page<Article> all = null;

        Article a = new Article();

        // 存在 分类名 为 key
        if (!StringUtils.isBlank(article.getClassId())) {
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
        List<ArticleVO> artileVOList = new ArrayList<>();

        // 时间处理
        for (Article ac : content) {
            String createTimeAgo = TimeAgoUtils.format(ac.getCreateTime());
            String updateTimeAgo = TimeAgoUtils.format(ac.getUpdateTime());
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(ac, articleVO);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String normalCreateTime = sdf.format(ac.getCreateTime());
            String normalUpdateTime = sdf.format(ac.getUpdateTime());
            articleVO.setNormalCreateTime(normalCreateTime);
            articleVO.setNormalUpdateTime(normalUpdateTime);
            articleVO.setCreateTimeAgoStr(createTimeAgo);
            articleVO.setUpdateTimeAgoStr(updateTimeAgo);
            /**
             * 查询量 太大，流量大，而且 用户 只是在 查询而已，不需要评论 和 内容
             */
            articleVO.setContent(null);
            artileVOList.add(articleVO);
        }

        // 降序排序，最近发表的文章 放到了 最前面
        Collections.sort(artileVOList);

        // 总 页数
        int pages = all.getTotalPages();

        PagedResult pagedResult = new PagedResult();
        // 设置 总记录数
        pagedResult.setRecords(total);
        // 设置 内容列表
        //pagedResult.setRows(content); 原始数据
        pagedResult.setRows(artileVOList);

        // 设置 当前页,转换为逻辑位置
        pagedResult.setPage(page+1);
        // 设置 总页数
        pagedResult.setTotal(pages);

        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ArticleVO queryArticleDetail(String articleId) {

        /**
         * 每次获取 文章，该 文章的 阅读数要 自增 1
         */

        Article article = new Article();
        article.setId(articleId);
        Example<Article> articleExample = Example.of(article);

        Optional<Article> one = articleRepository.findOne(articleExample);
        Article result = one.isPresent() ? one.get() : null;
        ArticleVO articleVO = new ArticleVO();
        if (result != null) {
            BeanUtils.copyProperties(result, articleVO);

            String createTimeAgo = TimeAgoUtils.format(result.getCreateTime());
            String updateTimeAgo = TimeAgoUtils.format(result.getUpdateTime());

            tk.mybatis.mapper.entity.Example userInfoExample = new tk.mybatis.mapper.entity.Example(UserInfo.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = userInfoExample.createCriteria();

            criteria.andEqualTo("userId", result.getUserId());

            UserInfo userInfo = userinfoMapper.selectOneByExample(userInfoExample);
            Classfication classfication = classficationMapper.selectByPrimaryKey(result.getClassId());

            articleVO.setAvatar(userInfo.getAvatar());
            articleVO.setNickName(userInfo.getNickname());
            articleVO.setClassficationName(classfication.getName());

            // 标签
            String aid = result.getId();
            Articles2tags articles2tags = new Articles2tags();
            articles2tags.setArticleId(aid);
            List<Articles2tagsVO> select = tagService.queryArticleTag(articles2tags);
            if (select.size() != 0) {
                Tag tag = tagService.queryTag(select.get(0).getTagId());
                articleVO.setTagId(tag.getId());
                articleVO.setTagName(tag.getName());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String normalCreateTime = sdf.format(result.getCreateTime());
            String normalUpdateTime = sdf.format(result.getUpdateTime());
            articleVO.setNormalCreateTime(normalCreateTime);
            articleVO.setNormalUpdateTime(normalUpdateTime);

            articleVO.setCreateTimeAgoStr(createTimeAgo);
            articleVO.setUpdateTimeAgoStr(updateTimeAgo);
            return articleVO;
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void multiUpdateArticleReadCounts(List<String> articleIdKeys, Map<String, String> articleMap) {
        for (String articleId : articleIdKeys) {

            Article article = new Article();
            article.setId(articleId);

            Example<Article> articleExample = Example.of(article);

            Optional<Article> one = articleRepository.findOne(articleExample);
            Article oldArticle = one.isPresent() ? one.get() : null;
            // 获取 articleId 对应的 readCounts
            String readCounts = articleMap.get(articleId);
            // 更新 readCounts
            if (oldArticle != null) {
                oldArticle.setReadCounts(Integer.parseInt(readCounts));

                articleRepository.save(oldArticle);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeArticle(String articleId) {


        /**
         * 必须根据 id 删除

        Article article = new Article();
        article.setId(articleId);
        articleRepository.delete(article);
         */
        Query queryArticle = new Query();
        queryArticle.addCriteria(Criteria.where("id").is(articleId));
        mongoTemplate.remove(queryArticle, Article.class);

        // 删除评论
        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("articleId").is(articleId));
        mongoTemplate.remove(queryComment, Comment.class);

        // 删除关联标签nav
        tagService.deleteTagAndArticleTagWithArticleId(articleId);

    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult queryArticleByTime(Long timeDifference, Integer page, Integer pageSize) {

        //分页查询对象
        if(page <= 0){
            page = 1;
        }
        // page 分页 在 mongodb 中是 从 0 开始的，转换为 物理位置
        page = page - 1;

        if(pageSize <= 0){
            pageSize = 10;
        }

        // 当前时间
        Long now = System.currentTimeMillis();
        Long time = now - timeDifference;
        // 一周前的 日期
        Date oneWekkAgodate = new Date(time);

        Query queryAll = new Query();
        Query queryCurrentPage = new Query();

        queryAll.addCriteria(Criteria.where("createTime").gt(oneWekkAgodate));
        queryCurrentPage.addCriteria(Criteria.where("createTime").gt(oneWekkAgodate));

        // 倒序 时间
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        queryAll.with(sort);
        queryCurrentPage.with(sort);
        PageRequest pageRequest = new PageRequest(page, pageSize);
        queryCurrentPage.with(pageRequest);
        // 分页

        /**
         * PageHelper 不支持 mongodb 查询
         */
        //PageHelper.startPage(page, pageSize);
        List<Article> allArticleList = mongoTemplate.find(queryAll, Article.class);
        List<Article> currentPageArticleList = mongoTemplate.find(queryCurrentPage, Article.class);


        List<ArticleVO> artileVOList = new ArrayList<>();

        // 时间处理
        for (Article ac : currentPageArticleList) {
            String createTimeAgo = TimeAgoUtils.format(ac.getCreateTime());
            String updateTimeAgo = TimeAgoUtils.format(ac.getUpdateTime());
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(ac, articleVO);
            articleVO.setCreateTimeAgoStr(createTimeAgo);
            articleVO.setUpdateTimeAgoStr(updateTimeAgo);
            /**
             * 查询量 太大，流量大，而且 用户 只是在 查询而已，不需要评论 和 内容
             */
            articleVO.setContent(null);
            artileVOList.add(articleVO);
        }

        long records = allArticleList.size();
        int total = 1;
        // 每页 大小 小于 总记录数 时才需要分页
        if (pageSize < records) {
            // 最后一页 还有 数据
            if (records % pageSize != 0) {
                total += records / pageSize;
            } else {
                total = (int) (records / pageSize);
            }
        }

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(total);
        pagedResult.setRecords(records);
        pagedResult.setRows(artileVOList);
        // page 最后转换为 逻辑位置
        pagedResult.setPage(page+1);


        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ArticleVO> queryArticleWithNoneTagByUser(String userId) {

        ExampleMatcher matching = ExampleMatcher.matching();
        ExampleMatcher articleExampleMatcher = matching.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.exact());

        Article article = new Article();
        article.setUserId(userId);

        Example<Article> articleExample = Example.of(article, articleExampleMatcher);

        List<Article> all = articleRepository.findAll(articleExample);

        List<ArticleVO> artileVOList = new ArrayList<>();
        for (Article ac : all) {

            String articleId = ac.getId();
            Articles2tags articles2tags = new Articles2tags();
            articles2tags.setArticleId(articleId);
            // 过滤掉 有标签 标记的 用户文章
            boolean articleTagIsExist = tagService.queryArticleTagIsExist(articles2tags);
            if (articleTagIsExist) {
                continue;
            }

            String createTimeAgo = TimeAgoUtils.format(ac.getCreateTime());
            String updateTimeAgo = TimeAgoUtils.format(ac.getUpdateTime());
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(ac, articleVO);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String normalCreateTime = sdf.format(ac.getCreateTime());
            String normalUpdateTime = sdf.format(ac.getUpdateTime());
            articleVO.setNormalCreateTime(normalCreateTime);
            articleVO.setNormalUpdateTime(normalUpdateTime);
            articleVO.setCreateTimeAgoStr(createTimeAgo);
            articleVO.setUpdateTimeAgoStr(updateTimeAgo);
            /**
             * 查询量 太大，流量大，而且 用户 只是在 查询而已，不需要评论 和 内容
             */
            articleVO.setContent(null);
            artileVOList.add(articleVO);
        }

        return artileVOList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(Article article) {

        String articleId = sid.nextShort();
        article.setId(articleId);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setReadCounts(0);
        article.setCommentCounts(0);
        article.setReceiveLikeCounts(0);

        Article result = articleRepository.save(article);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveWithIdAndUserId(Article article) {

        Article article1 = new Article();
        article1.setId(article.getId());

        Example<Article> articleExample = Example.of(article1);

        Optional<Article> one = articleRepository.findOne(articleExample);
        Article oldArticle = one.isPresent() ? one.get() : null;

        article.setCreateTime(oldArticle.getCreateTime());
        article.setReadCounts(oldArticle.getReadCounts());
        article.setReceiveLikeCounts(oldArticle.getReceiveLikeCounts());
        article.setCommentCounts(oldArticle.getCommentCounts());
        article.setUpdateTime(new Date());
        Article result = articleRepository.save(article);

        return result == null ? false : true;
    }


    private Page<Article> readAndExcute(String metcherProperty, ExampleMatcher matching, Article article, Integer page, Integer pageSize) {
        ExampleMatcher exampleMatcher = matching.withMatcher(metcherProperty, ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Article> articleExample = Example.of(article, exampleMatcher);
        Pageable pageable = new PageRequest(page, pageSize);
        return articleRepository.findAll(articleExample, pageable);
    }


}
