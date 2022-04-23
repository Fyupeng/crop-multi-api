package com.crop.service.impl;

import com.crop.enums.CommentStatus;
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
    private MongoTemplate mongoTemplate;

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

        ExampleMatcher articleExampleMatcher = matching
                .withMatcher("articleId", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact());

        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setStatus(CommentStatus.NORMAL.getStatus());

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
        forCopyComment(content, commentVOList);


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
        ExampleMatcher exampleMatcher = matching
                .withMatcher("fatherCommentId", ExampleMatcher.GenericPropertyMatchers.exact());


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
        comment.setStatus(CommentStatus.NORMAL.getStatus());
        Comment result = commentRepository.save(comment);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateComment(Comment comment) {

        comment.setStatus(CommentStatus.NORMAL.getStatus());
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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CommentVO> queryAllComments(String aPattern, String cPattern, String userId, Date startDate, Date endDate) {

        if (StringUtils.isBlank(aPattern)) {
            aPattern = "";
        }
        Article article = new Article();
        article.setTitle(aPattern);
        article.setSummary(aPattern);

        ExampleMatcher articleMatcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("summary", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Article> articleExample = Example.of(article, articleMatcher);

        if (StringUtils.isBlank(cPattern)) {
            cPattern = "";
        }
        Comment comment = new Comment();
        comment.setComment(cPattern);

        ExampleMatcher commentMatcher = ExampleMatcher.matching()
                .withMatcher("comment", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Comment> commentExample = Example.of(comment, commentMatcher);

        // 不去匹配cPattern, 做时间 跨度 过滤
        Query queryAllArticle = new Query();
        // 不去 查询文章 时间
        //queryAllArticle.addCriteria(Criteria.where("createTime").gt(startDate).lt(endDate));

        List<Comment> resultCommentList = new ArrayList<>();

        if (StringUtils.isNotBlank(aPattern) && StringUtils.isNotBlank(cPattern)) {
            queryAllArticle.addCriteria(new Criteria().alike(articleExample));

            List<Article> articleAllList = mongoTemplate.find(queryAllArticle, Article.class);
            for (Article ac : articleAllList) {
                Query queryAllComment = new Query();
                prepareQuery(queryAllComment, startDate, endDate);
                queryAllComment.addCriteria(Criteria.where("articleId").is(ac.getId()));
                if (userId != null)
                    queryAllComment.addCriteria(Criteria.where("fromUserId").is(userId));

                queryAllComment.addCriteria(new Criteria().alike(commentExample));

                List<Comment> comments = mongoTemplate.find(queryAllComment, Comment.class);

                resultCommentList.addAll(comments);
            }
        } else {
            if (StringUtils.isBlank(cPattern)) {
                queryAllArticle.addCriteria(new Criteria().alike(articleExample));

                List<Article> articleAllList = mongoTemplate.find(queryAllArticle, Article.class);
                for (Article ac : articleAllList) {
                    Query queryAllComment = new Query();
                    prepareQuery(queryAllComment, startDate, endDate);
                    queryAllComment.addCriteria(Criteria.where("articleId").is(ac.getId()));
                    if (userId != null)
                        queryAllComment.addCriteria(Criteria.where("fromUserId").is(userId));

                    List<Comment> comments = mongoTemplate.find(queryAllComment, Comment.class);

                    resultCommentList.addAll(comments);
                }
            }
            if (StringUtils.isBlank(aPattern)) {
                Query queryAllComment = new Query();
                prepareQuery(queryAllComment, startDate, endDate);
                queryAllComment.addCriteria(new Criteria().alike(commentExample));
                if (userId != null)
                    queryAllComment.addCriteria(Criteria.where("fromUserId").is(userId));

                List<Comment> comments = mongoTemplate.find(queryAllComment, Comment.class);

                resultCommentList.addAll(comments);
            }
        }

        List<CommentVO> commentVOList = new ArrayList<>();
        forCopyComment(resultCommentList, commentVOList);

        return commentVOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setCommentStatusWithFatherId(Comment comment, CommentStatus commentStatus) {
        // 本评论
        comment.setStatus(commentStatus.getStatus());
        commentRepository.save(comment);

        String fatherCommentId = comment.getId();
        Comment childComment = new Comment();
        childComment.setFatherCommentId(fatherCommentId);

        ExampleMatcher commentMatcher = ExampleMatcher.matching();
        ExampleMatcher commentExampleMatcher = commentMatcher.withMatcher("fatherCommentId", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<Comment> commentExample = Example.of(childComment, commentExampleMatcher);

        // 子评论
        List<Comment> childCommentList = commentRepository.findAll(commentExample);
        for (Comment ct : childCommentList) {
            ct.setStatus(commentStatus.getStatus());
            commentRepository.save(ct);
        }
    }


    private void forCopyComment(List<Comment> commentList, List<CommentVO> commentVOList) {
        for (Comment ct : commentList) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String normalCreateTime = sdf.format(ct.getCreateTime());
            commentVO.setNormalCreateTime(normalCreateTime);

            // 防止 被恶意使用
            commentVO.setToUserId(null);
            commentVOList.add(commentVO);
        }
    }

    private void prepareQuery(Query query, Date startDate, Date endDate) {
        query.addCriteria(Criteria.where("createTime").gt(startDate).lt(endDate));
        /**
         * 管理员应该 获取所有状态的 评论
         */
        //query.addCriteria(Criteria.where("status").is(CommentStatus.NORMAL.getStatus()));
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        query.with(sort);
    }


}
