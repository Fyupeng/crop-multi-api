package com.crop.service.impl;

import com.crop.mapper.*;
import com.crop.pojo.Article;
import com.crop.pojo.Articles2tags;
import com.crop.pojo.Classfication;
import com.crop.pojo.Tag;
import com.crop.pojo.vo.Articles2tagsVO;
import com.crop.pojo.vo.TagVO;
import com.crop.service.TagService;
import com.crop.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/8
 * @Description:
 * @Package: com.crop.service.impl
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private Articles2tagsMapper articles2tagsMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ClassficationMapper classficationMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryTagIsExist(Tag tag) {

        Tag result = tagMapper.selectOne(tag);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryArticleTagIsExist(String id) {

        Articles2tags result = articles2tagsMapper.selectByPrimaryKey(id);

        return result == null ? false : true;
    }

    @Override
    public boolean queryArticleTagIsExist(Articles2tags articles2tags) {

        List<Articles2tags> result = articles2tagsMapper.select(articles2tags);

        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Tag queryTag(String tagId) {

        Tag tag = tagMapper.selectByPrimaryKey(tagId);

        return tag;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Articles2tagsVO> queryArticleTag(Articles2tags articles2tags) {

        List<Articles2tags> articles2tagsList = articles2tagsMapper.select(articles2tags);

        List<Articles2tagsVO> articles2tagsVOList = new ArrayList<>();
        for (Articles2tags articleTags : articles2tagsList) {

            String articleId = articleTags.getArticleId();
            String tagId = articleTags.getTagId();

            Tag tag = tagMapper.selectByPrimaryKey(tagId);

            Article one = articleRepository.findOne(articleId);
            Classfication classfication = classficationMapper.selectByPrimaryKey(one.getClassId());

            Articles2tagsVO articles2tagsVO = new Articles2tagsVO();
            BeanUtils.copyProperties(articleTags, articles2tagsVO);

            articles2tagsVO.setTagName(tag.getName());
            articles2tagsVO.setTitle(one.getTitle());
            articles2tagsVO.setClassficationName(classfication.getName());
            articles2tagsVO.setCommentCounts(one.getCommentCounts());
            articles2tagsVO.setReadCounts(one.getReadCounts());
            articles2tagsVO.setReceiveLikeCounts(one.getReceiveLikeCounts());

            String createTimeAgo = TimeAgoUtils.format(one.getCreateTime());
            String updateTimaAgo = TimeAgoUtils.format(one.getUpdateTime());

            articles2tagsVO.setCreateTimeAgoStr(createTimeAgo);
            articles2tagsVO.setUpdateTimeAgoStr(updateTimaAgo);

            articles2tagsVOList.add(articles2tagsVO);

        }

        return articles2tagsVOList;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TagVO> queryAllTags(Tag tag) {

        List<Tag> select = tagMapper.select(tag);

        List<TagVO> tagVOList = new ArrayList<>();
        for (Tag one : select) {
            String tagId = one.getId();

            Articles2tags articles2tags = new Articles2tags();
            articles2tags.setTagId(tagId);
            List<Articles2tags> articles2tagsList = articles2tagsMapper.select(articles2tags);

            TagVO tagVO = new TagVO();

            BeanUtils.copyProperties(one, tagVO);

            tagVO.setArticleNum(articles2tagsList.size());

            tagVOList.add(tagVO);
        }

        return tagVOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveTag(Tag tag) {

        String tagId = sid.nextShort();
        tag.setId(tagId);

        int i = tagMapper.insert(tag);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveArticleTag(Articles2tags articles2tags) {

        String articles2tagsId = sid.nextShort();
        articles2tags.setId(articles2tagsId);

        int i = articles2tagsMapper.insert(articles2tags);

        return i > 0 ? true : false;
    }

    @Override
    public boolean updateArticleTag(Articles2tags articles2tags) {

        int i = articles2tagsMapper.updateByPrimaryKey(articles2tags);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateTag(Tag tag) {

        int i = tagMapper.updateByPrimaryKeySelective(tag);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteTag(String tagId) {

        int i = tagMapper.deleteByPrimaryKey(tagId);

        return i > 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delArticleTag(String tagId) {

        Example articles2tagsExample = new Example(Articles2tags.class);
        Example.Criteria criteria = articles2tagsExample.createCriteria();
        criteria.andEqualTo("tagId", tagId);

        int i = articles2tagsMapper.deleteByExample(articles2tagsExample);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteTagAndArticleTag(String tagId) {
        boolean result = deleteTag(tagId);
        if (result)
            delArticleTag(tagId);
        return result;
    }

}
