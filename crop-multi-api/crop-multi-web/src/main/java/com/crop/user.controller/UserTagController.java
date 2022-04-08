package com.crop.user.controller;

import com.crop.pojo.Articles2tags;
import com.crop.pojo.Tag;
import com.crop.pojo.vo.Articles2tagsVO;
import com.crop.pojo.vo.TagVO;
import com.crop.service.*;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/8
 * @Description:
 * @Package: com.crop.user.controller
 * @Version: 1.0
 */
@Slf4j
@RestController
@Api(value = "标签相关业务的接口", tags = {"标签相关业务的controller"})
@RequestMapping(value = "/user/tag")
public class UserTagController extends BasicController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @PostMapping(value = "/getTag")
    @ApiOperation(value = "获取标签", notes = "获取标签的接口")
    @ApiImplicitParam(name = "tagId", value = "标签id", required = true, dataType = "String", paramType = "query")
    public CropJSONResult getTag(String tagId) {

        if (StringUtils.isBlank(tagId)) {
            return CropJSONResult.errorMsg("标签id不能为空");
        }

        Tag tagList = tagService.queryTag(tagId);

        return CropJSONResult.ok(tagList);
    }

    @PostMapping(value = "/getAllTags")
    @ApiOperation(value = "获取所有标签", notes = "获取所有标签的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    public CropJSONResult getAllTags(String userId) {
        Tag tagWithUserId = new Tag();
        tagWithUserId.setUserId(userId);

        List<TagVO> tagVOList = tagService.queryAllTags(tagWithUserId);

        return CropJSONResult.ok(tagVOList);
    }

    @PostMapping(value = "/saveTag")
    @ApiOperation(value = "保存标签 - id字段请忽略", notes = "保存标签的接口")
    @ApiImplicitParam(name = "tag", value = "标签", required = true, dataType = "Tag", paramType = "body")
    public CropJSONResult saveTag(@RequestBody Tag tag) {

        if (StringUtils.isBlank(tag.getName()) || StringUtils.isBlank(tag.getUserId())) {
            return CropJSONResult.errorMsg("标签名或用户id不能为空");
        }

        tag.setId(null);
        /**
         * id 是候选键 - 标签名 + userId 也是候选键，能唯一 识别 标签
         */
        if (tagService.queryTagIsExist(tag)) {
            return CropJSONResult.errorMsg("标签名已存在");
        }

        boolean saveIsTrue = tagService.saveTag(tag);

        return saveIsTrue ? CropJSONResult.ok(): CropJSONResult.errorMsg("内部错误导致保存失败");
    }

    @PostMapping(value = "/updateTag")
    @ApiOperation(value = "更新标签 - userId字段请忽略", notes = "更新标签的接口")
    @ApiImplicitParam(name = "tag", value = "标签", required = true, dataType = "Tag", paramType = "body")
    public CropJSONResult updateTag(@RequestBody Tag tag) {

        if (StringUtils.isBlank(tag.getId()) || StringUtils.isBlank(tag.getName())) {
            return CropJSONResult.errorMsg("标签id或标签名不能为空");
        }

        Tag tagWithId = new Tag();
        tagWithId.setId(tag.getId());

        if (!tagService.queryTagIsExist(tagWithId)) {
            return CropJSONResult.errorMsg("标签id不存在");
        }

        tag.setUserId(null);
        boolean updateIsTrue = tagService.updateTag(tag);

        return updateIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误");
    }

    @PostMapping(value = "/removeTag")
    @ApiOperation(value = "删除标签 - 连同已标记的文章标签一并删除", notes = "删除标签签的接口")
    @ApiImplicitParam(name = "tagId", value = "标签id", required = true, dataType = "String", paramType = "query")
    public CropJSONResult removeTag(String tagId) {

        if (StringUtils.isBlank(tagId)) {
            return CropJSONResult.errorMsg("tagId不能为空");
        }

        // 1. 移除 Tag
        // 2. 移除 Articles2Tags
        boolean delTagIsTrue = tagService.deleteTagAndArticleTag(tagId);

        if (!delTagIsTrue) {
            return CropJSONResult.errorMsg("标签id不存在或内部错误导致删除失败");
        }

        return CropJSONResult.ok();
    }

    @PostMapping(value = "/getArticleTag")
    @ApiOperation(value = "获取标签文章", notes = "获取标签文章的接口")
    @ApiImplicitParam(name = "tagId", value = "标签id", required = true, dataType = "String", paramType = "query")
    public CropJSONResult getArticleTag(String tagId) {

        if (StringUtils.isBlank(tagId)) {
            return CropJSONResult.errorMsg("标签id不能为空");
        }

        Articles2tags articles2tagsWithTagId = new Articles2tags();
        articles2tagsWithTagId.setTagId(tagId);

        List<Articles2tagsVO> articles2tagsVOList = tagService.queryArticleTag(articles2tagsWithTagId);

        return CropJSONResult.ok(articles2tagsVOList);
    }

    @PostMapping(value = "/markArticleTag")
    @ApiOperation(value = "标记文章标签 - id字段请忽略", notes = "标记文章标签的接口")
    @ApiImplicitParam(name = "articles2tags", value = "文章标签关联", required = true, dataType = "Articles2tags", paramType = "body")
    public CropJSONResult markArticleTag(@RequestBody Articles2tags articles2tags) {

        if (StringUtils.isBlank(articles2tags.getArticleId()) || StringUtils.isBlank(articles2tags.getTagId())) {
            return CropJSONResult.errorMsg("文章id或标签id不能为空");
        }

        boolean articleIsExist = articleService.queryArticleIsExist(articles2tags.getArticleId());

        Tag tagWithId = new Tag();
        tagWithId.setId(articles2tags.getTagId());
        boolean tagIsExist = tagService.queryTagIsExist(tagWithId);

        if (!articleIsExist || !tagIsExist) {
            return CropJSONResult.errorMsg("文章id或标签id不存在");
        }

        articles2tags.setId(null);
        // 已标记 的 不能 重复标记 - 文章id和标签id 也是一组 候选键
        if (tagService.queryArticleTagIsExist(articles2tags)) {
            return CropJSONResult.errorMsg("关联id已存在,不可重复标记");
        }

        // 标记 Articles2Tags
        boolean saveIsTrue = tagService.updateArticleTag(articles2tags);

        return saveIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致保存失败");
    }

    @PostMapping(value = "/reMarkArticleTag")
    @ApiOperation(value = "重新标记文章标签", notes = "重新标记文章标签的接口")
    @ApiImplicitParam(name = "articles2tags", value = "文章标签关联", required = true, dataType = "Articles2tags", paramType = "body")
    public CropJSONResult reMarkArticleTag(@RequestBody Articles2tags articles2tags) {

        if (StringUtils.isBlank(articles2tags.getId())) {
            return CropJSONResult.errorMsg("文章标签关联id不能为空");
        }

        // 重新标记 Articles2Tags
        if (StringUtils.isBlank(articles2tags.getArticleId()) || StringUtils.isBlank(articles2tags.getTagId())) {
            return CropJSONResult.errorMsg("文章id或标签id不能为空");
        }

        boolean articleIsExist = articleService.queryArticleIsExist(articles2tags.getArticleId());

        Tag tagWithId = new Tag();
        tagWithId.setId(articles2tags.getTagId());
        boolean tagIsExist = tagService.queryTagIsExist(tagWithId);

        if (!articleIsExist || !tagIsExist) {
            return CropJSONResult.errorMsg("文章id或标签id不存在");
        }

        // 已标记 的 才可以更新
        if (!tagService.queryArticleTagIsExist(articles2tags.getId())) {
            return CropJSONResult.errorMsg("关联id不存在");
        }

        // 标记 Articles2Tags
        boolean saveIsTrue = tagService.saveArticleTag(articles2tags);

        return saveIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致保存失败");
    }


}
