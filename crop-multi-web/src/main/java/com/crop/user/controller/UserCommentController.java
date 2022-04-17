package com.crop.user.controller;

import com.crop.pojo.Comment;
import com.crop.service.*;
import com.crop.utils.CropJSONResult;
import com.crop.utils.PagedResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: fyp
 * @Date: 2022/4/8
 * @Description:
 * @Package: com.crop.user.controller
 * @Version: 1.0
 */
@Slf4j
@RestController
@CrossOrigin
@Api(value = "评论相关业务的接口", tags = {"评论相关业务的controller"})
@RequestMapping(value = "/user/comment")
public class UserCommentController extends BasicController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassficationService classficationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @PostMapping(value = "/getAllComments")
    @ApiOperation(value = "获取文章所有评论", notes = "获取文章所有评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序-1-缺省[正序时间]-2[倒序时间]", dataType = "Integer", paramType = "query")
    })
    public CropJSONResult getAllComments(String articleId, Integer page, Integer pageSize, Integer sort) {

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

        if (sort == null) {
            sort = 1;
        }

        PagedResult pageResult = commentService.queryAllComments(articleId, page, pageSize, sort);

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

        // fateherCommentId 是可以为 null 但是 不能是 空串 或 空白串
        if (comment.getFatherCommentId() != null && StringUtils.isBlank(comment.getFatherCommentId())) {
            return CropJSONResult.errorMsg("不允许fatherCommentId为空串");
        }
        // toUserId 是可以为 null 但是 不能是 空串 或 空白串
        if (comment.getToUserId() != null && StringUtils.isBlank(comment.getToUserId())) {
            return CropJSONResult.errorMsg("不允许toUserId为空串");
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

        boolean saveIsTrue = commentService.saveComment(comment);

        return saveIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致保存失败");

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

        return commentIsUpdate ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致更新失败");
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

        // 如果评论 已经被 追评，则不可撤销
        boolean commentWithFatherCommentIsExist = commentService.queryCommentWithFatherCommentIsExist(commentId);

        if (commentWithFatherCommentIsExist) {
            return CropJSONResult.errorMsg("有子评论约束，普通用户无权限");
        }

        commentService.removeCommentById(commentId);

        return CropJSONResult.ok();
    }

}
