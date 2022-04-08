package com.crop.user.controller;

import com.crop.pojo.Article;
import com.crop.pojo.Classfication;
import com.crop.pojo.Comment;
import com.crop.pojo.User;
import com.crop.pojo.vo.ArticleVO;
import com.crop.service.ArticleService;
import com.crop.service.ClassficationService;
import com.crop.service.CommentService;
import com.crop.service.UserService;
import com.crop.utils.CropJSONResult;
import com.crop.utils.PagedResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.utils.RequestAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther: fyp
 * @Date: 2022/4/2
 * @Description: 文章 Controller
 * @Package: com.crop.controller
 * @Version: 1.0
 */
@Slf4j
@RestController
@Api(value = "文章相关业务的接口", tags = {"文章相关业务的controller"})
@RequestMapping(value = "/user/article")
public class UserArticleController extends BasicController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassficationService classficationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/getAllArticles")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query")
    })
    public CropJSONResult getAllArticles(String key, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(key)) {
            return CropJSONResult.errorMsg("关键字不能为空");
        }

        //前端不传该参时会初始化
        if(page == null){
            page = 1;
        }
        //前端不传该参时会初始化
        if(pageSize == null){
            pageSize = ARTICLE_PAGE_SIZE;
        }

        List<Article> articleList = null;

        Classfication classfication = new Classfication();
        classfication.setName(key);

        Classfication cf = classficationService.queryClssfication(classfication);

        Article article = new Article();
        if (cf != null) {
            article.setClassId(cf.getId());
        } else {
            article.setTitle(key);
            article.setSummary(key);
            article.setContent(key);
        }
        // 优先 匹配 分类 id
        // 第二 优先 匹配 标题
        // 第三 匹配 摘要
        // 第四 优先 匹配 内容
        PagedResult pageResult = articleService.queryArticleSelective(article, page,pageSize);

        return CropJSONResult.ok(pageResult);


    }


    @PostMapping(value = "/getArticleDetail")
    @ApiOperation(value = "获取文章详细信息", notes = "获取文章详细信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "request",value = "请求", dataType = "HttpServletRequest", readOnly = true)
    })

    public CropJSONResult getArticleDetail(String articleId, HttpServletRequest request) {

        if (StringUtils.isBlank(articleId)) {
            return CropJSONResult.errorMsg("文章id不能为空");
        }

        Article article = new Article();
        article.setId(articleId);

        ArticleVO articleVO = articleService.queryArticleDetail(articleId);

        String userView = IS_VIEW + ":" + articleVO.getId() + ":" + RequestAddr.getClientIpAddress(request);

        String userViewCount = VIEW_COUNT + ":" + articleVO.getId();

        // 文章 id 不存在 或 用户短时间 已 访问，redis.get(userView) 返回值是 对象，不能 通过 ""判断
        if (articleVO == null || redis.get(userView) != null) {
            return CropJSONResult.ok(articleVO);
        }

        /**
         * 统计量 一小时 内 同一个 ip 地址 只能算 1 次 阅读
         */
        redis.set(userView, "1", 60 * 60);

        /**
         * userViewCount 如果 未 初始化，redis 会 初始化为 0
         */
        redis.incr(userViewCount,1);

        return CropJSONResult.ok(articleVO);
    }


    @PostMapping(value = "/saveArticle")
    @ApiOperation(value = "保存文章信息 - id 字段请忽略", notes = "保存文章信息的接口")
    @ApiImplicitParam(name = "article", value = "文章", required = true, dataType = "Article", paramType = "body")
    public CropJSONResult saveArticle(@RequestBody Article article) {


        if(StringUtils.isBlank(article.getUserId()) || StringUtils.isBlank(article.getTitle())
                || StringUtils.isBlank(article.getSummary() ) || StringUtils.isBlank(article.getClassId())
                || StringUtils.isBlank(article.getContent())){
            return CropJSONResult.errorMsg("用户id、标题、摘要、分类id和内容不能为空");
        }

        boolean userIdIsExist = userService.queryUserIdIsExist(article.getUserId());

        if (!userIdIsExist) {
            return CropJSONResult.errorMsg("用户id不存在");
        }

        boolean classficationIsExist = classficationService.queryClassficationIdIsExist(article.getClassId());

        if (!classficationIsExist) {
            return CropJSONResult.errorMsg("分类id不存在");
        }

        articleService.save(article);

        return CropJSONResult.ok();
    }

    @PostMapping(value = "/updateArticle")
    @ApiOperation(value = "更新文章信息", notes = "更新文章信息的接口")
    @ApiImplicitParam(name = "article", value = "文章", required = true, dataType = "Article", paramType = "body")
    public CropJSONResult updateArticle(@RequestBody  Article article) {

        if (StringUtils.isBlank(article.getId()) || StringUtils.isBlank(article.getUserId())) {
            return CropJSONResult.errorMsg("articleId 和 userId 不能为空");
        }

        Article articleWithIdAndUserId =  new Article();
        articleWithIdAndUserId.setId(article.getId());
        articleWithIdAndUserId.setUserId(article.getUserId());
        // 文章id 属于 用户id
        boolean articleIsUser = articleService.queryArticleIsUser(articleWithIdAndUserId);
        if (articleIsUser) {
            Article ac = new Article();
            ac.setId(article.getId());
            ac.setUserId(article.getUserId());
            // 文章 标题 要更新
            if (StringUtils.isNotBlank(article.getTitle())) {
                ac.setTitle(article.getTitle());
            }
            // 文章 摘要 要更新
            if (StringUtils.isNotBlank(article.getSummary())) {
                ac.setSummary(article.getSummary());
            }
            // 文章 内容 要更新
            if (StringUtils.isNotBlank(article.getContent())) {
                ac.setContent(article.getContent());
            }
            // 文章 分类 要更新
            if (StringUtils.isNotBlank(article.getClassId())) {
                boolean classficationIdIsExist = classficationService.queryClassficationIdIsExist(article.getClassId());
                if (!classficationIdIsExist)
                    return CropJSONResult.errorMsg("分类id不存在");
                ac.setClassId(article.getClassId());
            }
            articleService.saveWithIdAndUserId(ac);
            return CropJSONResult.ok();
        }

        return CropJSONResult.errorMsg("用户 id 与 articleId 不存在或匹配失败");

    }

    @PostMapping(value = "/getAllClassfications")
    @ApiOperation(value = "获取文章分类信息", notes = "获取文章分类信息的接口")
    public CropJSONResult getAllClassfications() {

        List<Classfication> classficationList = classficationService.queryAllClassfications();

        return CropJSONResult.ok(classficationList);
    }

    @PostMapping(value = "/getAllComments")
    @ApiOperation(value = "获取文章所有评论", notes = "获取文章所有评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", dataType = "Integer", paramType = "query")
    })

    public CropJSONResult getAllComments(String articleId, Integer page, Integer pageSize) {

        if (StringUtils.isBlank(articleId)) {
            return CropJSONResult.errorMsg("文章id不能为空");
        }

        //前端不传该参时会初始化
        if(page == null){
            page = 1;
        }
        //前端不传该参时会初始化
        if(pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedResult pageResult = commentService.queryAllComments(articleId, page, pageSize);

        return CropJSONResult.ok(pageResult);

    }


    @PostMapping(value = "saveComment")
    @ApiOperation(value = "发表文章评论", notes = "发表文章评论的接口")
    @ApiImplicitParam(name = "comment", value = "评论", required = true, dataType = "Comment", paramType = "body")
    public CropJSONResult saveComment(@RequestBody Comment comment) {
        
        if (StringUtils.isBlank(comment.getArticleId())) {
            return CropJSONResult.errorMsg("articleId不能为空");
        }
        
        if (StringUtils.isBlank(comment.getFromUserId())) {
            return CropJSONResult.errorMsg("留言者userId不能为空");
        }

        if (StringUtils.isBlank(comment.getComment())) {
            return CropJSONResult.errorMsg("评论内容comment不能为空");
        }

        if (StringUtils.isNotBlank(comment.getToUserId()) && comment.getToUserId().equals(comment.getFromUserId())) {
            return CropJSONResult.errorMsg("不能回复toUserId为fromUserId");
        }

        boolean articleIsExist = articleService.queryArticleIsExist(comment.getArticleId());
        boolean userIdIsExist = userService.queryUserIdIsExist(comment.getFromUserId());

        if (!articleIsExist || !userIdIsExist) {
            return CropJSONResult.errorMsg("文章id不存在或留言者id不存在");
        }

        // 父 评论 验证
        if (StringUtils.isNotBlank(comment.getFatherCommentId())) {
            boolean fatherCommentIdIsExist = commentService.queryCommentIsExist(comment.getFatherCommentId());
            if (!fatherCommentIdIsExist) {
                return CropJSONResult.errorMsg("父评论id不存在");
            }
        }
        // 被 回复用户验证
        if (StringUtils.isNotBlank(comment.getToUserId())) {
            boolean toUserIsExist = userService.queryUserIdIsExist(comment.getToUserId());
            if (!toUserIsExist) {
                return CropJSONResult.errorMsg("被回复用户id不存在");
            }
        }

        commentService.saveComment(comment);

        return CropJSONResult.ok();

    }

    @PostMapping(value = "/updateMyComment")
    @ApiOperation(value = "更新评论", notes = "更新评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "更新内容", required = true, dataType = "String", paramType = "query")
    })
    public CropJSONResult updateMyComment(String commentId, String userId, String content) {

        if (StringUtils.isBlank(commentId) || StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("commentId或userId不能为空");
        }

        Comment comment = commentService.queryComment(commentId);

        if (comment == null || !comment.getFromUserId().equals(userId)) {
            return CropJSONResult.errorMsg("commentId不存在或者userId与commentId约束的userId不同");
        }

        comment.setComment(content);

        boolean commentIsUpdate = commentService.updateComment(comment);

        return commentIsUpdate ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误");
    }

    @PostMapping(value = "/removeMyComment")
    @ApiOperation(value = "撤回评论", notes = "撤回评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    })
    public CropJSONResult removeMyComment(String commentId, String userId) {

        if (StringUtils.isBlank(commentId) || StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("commentId或userId不能为空");
        }

        Comment comment = commentService.queryComment(commentId);

        if (comment == null || !comment.getFromUserId().equals(userId)) {
            return CropJSONResult.errorMsg("commentId不存在或者userId与commentId约束的userId不同");
        }

        boolean commentIsRemove = commentService.removeCommentById(commentId);

        return commentIsRemove ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误");
    }








}
