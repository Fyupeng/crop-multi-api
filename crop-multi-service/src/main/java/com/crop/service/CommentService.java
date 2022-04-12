package com.crop.service;

import com.crop.pojo.Comment;
import com.crop.utils.PagedResult;

/**
 * @Auther: fyp
 * @Date: 2022/4/3
 * @Description:
 * @Package: com.crop.service
 * @Version: 1.0
 */
public interface CommentService {

    boolean saveComment(Comment comment);


    void removeCommentById(String commentId);

    Comment queryComment(String commentId);

    boolean queryCommentIsExist(String commentId);

    boolean updateComment(Comment comment);

    PagedResult queryAllComments(String articleId, Integer page, Integer pageSize);

    boolean queryCommentWithFatherCommentIsExist(String commentId);

    void removeCommentWithFatherCommentId(String fatherCommentId);
}
