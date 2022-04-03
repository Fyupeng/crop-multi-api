package com.crop.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Documented;

public class Articles2tags implements Serializable {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 文章id
     */
    @Column(name = "article_id")
    private String articleId;

    /**
     * 标签id
     */
    @Column(name = "tag_id")
    private String tagId;

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
     * 获取文章id
     *
     * @return article_id - 文章id
     */
    public String getArticleId() {
        return articleId;
    }

    /**
     * 设置文章id
     *
     * @param articleId 文章id
     */
    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取标签id
     *
     * @return tag_id - 标签id
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 设置标签id
     *
     * @param tagId 标签id
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}