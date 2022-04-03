package com.crop.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@ApiModel(description = "操作文章上传和其他操作的model")
@Document(collection = "article")
public class Article implements Serializable {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    private String title;

    /**
     * 用户id
     */
    @Indexed
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户标识符[id]",required = true)
    private String userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 最后修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 文章摘要
     */
    @ApiModelProperty(value = "文章摘要",required = true)
    private String summary;

    /**
     * 文章内容，4000以内建议使用varchar，超过建议clob
     */
    @ApiModelProperty(value = "文章内容",required = true)
    private String content;

    /**
     * 分类id
     */
    @Column(name = "class_id")
    @ApiModelProperty(value = "文章分类", required = true)
    private String classId;

    /**
     * 评论数
     */
    @Column(name = "comment_counts")
    @ApiModelProperty(hidden = true)
    private Integer commentCounts;

    /**
     * 阅读量
     */
    @Column(name = "read_counts")
    @ApiModelProperty(hidden = true)
    private Integer readCounts;

    /**
     * 收藏量，用户喜欢、收藏
     */
    @Column(name = "receive_like_counts")
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后修改时间
     *
     * @return update_time - 最后修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置最后修改时间
     *
     * @param updateTime 最后修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取文章摘要
     *
     * @return summary - 文章摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置文章摘要
     *
     * @param summary 文章摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取文章内容，4000以内建议使用varchar，超过建议clob
     *
     * @return content - 文章内容，4000以内建议使用varchar，超过建议clob
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文章内容，4000以内建议使用varchar，超过建议clob
     *
     * @param content 文章内容，4000以内建议使用varchar，超过建议clob
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取分类id
     *
     * @return class_id - 分类id
     */
    public String getClassId() {
        return classId;
    }

    /**
     * 设置分类id
     *
     * @param classId 分类id
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * 获取评论数
     *
     * @return comment_counts - 评论数
     */
    public Integer getCommentCounts() {
        return commentCounts;
    }

    /**
     * 设置评论数
     *
     * @param commentCounts 评论数
     */
    public void setCommentCounts(Integer commentCounts) {
        this.commentCounts = commentCounts;
    }

    /**
     * 获取阅读量
     *
     * @return read_counts - 阅读量
     */
    public Integer getReadCounts() {
        return readCounts;
    }

    /**
     * 设置阅读量
     *
     * @param readCounts 阅读量
     */
    public void setReadCounts(Integer readCounts) {
        this.readCounts = readCounts;
    }

    /**
     * 获取收藏量，用户喜欢、收藏
     *
     * @return receive_like_counts - 收藏量，用户喜欢、收藏
     */
    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    /**
     * 设置收藏量，用户喜欢、收藏
     *
     * @param receiveLikeCounts 收藏量，用户喜欢、收藏
     */
    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }
}