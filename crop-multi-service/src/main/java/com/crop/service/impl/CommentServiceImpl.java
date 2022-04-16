package com.crop.service.impl;

import com.crop.mapper.CommentRepository;
import com.crop.mapper.UserInfoMapper;
import com.crop.pojo.Article;
import com.crop.pojo.Comment;
import com.crop.pojo.UserInfo;
import com.crop.pojo.vo.CommentVO;
import com.crop.service.CommentService;
import com.crop.utils.PagedResult;
import com.crop.utils.TimeAgoUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryCommentIsExist(String commentId) {
        return commentRepository.findOne(commentId) == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Comment queryComment(String commentId) {
        return commentRepository.findOne(commentId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult queryAllComments(String articleId, Integer page, Integer pageSize, Integer sortNum) {

        //分页查询对象
        if(page <= 0){
            page = 1;
        }
        // page 分页 在 mongodb 中是 从 0 开始的
        page = page - 1;

        if(pageSize <= 0){
            pageSize = 10;
        }

        ExampleMatcher matching = ExampleMatcher.matching();

        ExampleMatcher articleExampleMatcher = matching.withMatcher("articleId", ExampleMatcher.GenericPropertyMatchers.exact());

        Comment comment = new Comment();
        comment.setArticleId(articleId);

        Example<Comment> articleExample = Example.of(comment, articleExampleMatcher);

        //mongodb数据中默认是 按时间正序排序（顺着时间走向排序、升序）
        Pageable pageable = new PageRequest(page, pageSize);

        if (sortNum == 2) {
            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            pageable = new PageRequest(page, pageSize, sort);
        }


        Page<Comment> all = commentRepository.findAll(articleExample, pageable);

        int pages = all.getTotalPages();
        long total = all.getTotalElements();
        List<Comment> content = all.getContent();

        List<CommentVO> commentVOList = new ArrayList<>();
        // po -> vo 永久层对象 - 视图层对象
        for (Comment ct : content) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(ct, commentVO);

            // fromUser
            String fromUserId = ct.getFromUserId();
            UserInfo userInfoWithFromUserId = new UserInfo();
            userInfoWithFromUserId.setUserId(fromUserId);
            UserInfo fromUserInfo = userInfoMapper.selectOne(userInfoWithFromUserId);
            commentVO.setFromUserNickName(fromUserInfo.getNickname());
            commentVO.setFromUserAvatar(fromUserInfo.getAvatar());
            // fatherCommentContent
            String fatherCommentId = ct.getFatherCommentId();
            if (fatherCommentId != null) {
                Comment fatherComment = commentRepository.findOne(fatherCommentId);
                commentVO.setFatherCommentContent(fatherComment.getComment());
            }
            // toUser
            String toUserId = ct.getToUserId();
            if (toUserId != null) {
                UserInfo userInfoWithToUserId = new UserInfo();
                userInfoWithToUserId.setUserId(toUserId);
                UserInfo toUserInfo = userInfoMapper.selectOne(userInfoWithToUserId);
                commentVO.setToUserNickName(toUserInfo == null ? null : toUserInfo.getNickname());
            }

            //time
            String createTimeAgo = TimeAgoUtils.format(ct.getCreateTime());
            commentVO.setCreateTimeAgo(createTimeAgo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String normalCreateTime = sdf.format(ct.getCreateTime());
            commentVO.setNormalCreateTime(normalCreateTime);

            // 防止 被恶意使用
            commentVO.setToUserId(null);
            commentVOList.add(commentVO);
        }

        PagedResult pagedResult = new PagedResult();
        // 页数
        pagedResult.setTotal(pages);
        // 当前页
        pagedResult.setPage(page);
        // 总记录数
        pagedResult.setRecords(total);
        // 内容列表
        pagedResult.setRows(commentVOList);

        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryCommentWithFatherCommentIsExist(String commentId) {

        ExampleMatcher matching = ExampleMatcher.matching();
        ExampleMatcher exampleMatcher = matching.withMatcher("fatherCommentId", ExampleMatcher.GenericPropertyMatchers.exact());

        Comment comment = new Comment();
        comment.setFatherCommentId(commentId);

        Example<Comment> commentExample = Example.of(comment, exampleMatcher);

        Comment result = commentRepository.findOne(commentExample);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveComment(Comment comment) {

        String commentId = sid.nextShort();
        comment.setId(commentId);
        comment.setCreateTime(new Date());
        Comment result = commentRepository.save(comment);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateComment(Comment comment) {

        Comment result = commentRepository.save(comment);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeCommentById(String commentId) {

        commentRepository.delete(commentId);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeCommentWithFatherCommentId(String fatherCommentId) {

        Comment comment = new Comment();
        comment.setFatherCommentId(fatherCommentId);

        commentRepository.delete(comment);
    }


}
