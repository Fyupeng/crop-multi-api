package com.crop.controller;

import com.crop.pojo.Article;
import com.crop.pojo.Classfication;
import com.crop.service.ArticleService;
import com.crop.service.ClassficationService;
import com.crop.service.UserService;
import com.crop.utils.CropJSONResult;
import com.crop.utils.PagedResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.CDATASection;

import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/2
 * @Description: 文章 Controller
 * @Package: com.crop.controller
 * @Version: 1.0
 */
@RestController
@Api(value = "文章相关业务的接口", tags = {"文章相关业务的controller"})
@Controller(value = "/article")
public class ArticleController extends BasicController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassficationService classficationService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/query")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query")
    })

    public CropJSONResult queryArticle(String key, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(key)) {
            return CropJSONResult.errorMsg("关键字不能为空");
        }

        //前端不传该参时会初始化
        if(page == null){
            page = 1;
        }
        //前端不传该参时会初始化
        if(pageSize == null){
            pageSize = PAGE_SIZE;
        }

        System.out.println(key);

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
        PagedResult pageResult = null;
        if ((pageResult = articleService.queryArticleSelective(article, page,pageSize)) != null) {
            return CropJSONResult.ok(pageResult);
        }


        return CropJSONResult.ok("无匹配项");

    }

    @PostMapping(value = "/saveArticle")
    @ApiOperation(value = "保存文章信息", notes = "保存文章信息的接口")
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

        return CropJSONResult.ok("文章 \"" + article.getTitle() + "\" 发布成功");
    }

    @PostMapping(value = "/saveClassfication")
    @ApiOperation(value = "新建文章分类", notes = "新建文章分类的接口")
    @ApiImplicitParam(name = "classficationName", value = "分类名", required = true, dataType = "String", paramType = "query")
    public CropJSONResult saveClassFication(String classficationName) {

        if (StringUtils.isBlank(classficationName)) {
            return CropJSONResult.errorMsg("分类名不能为空");
        }

        Classfication classfication = new Classfication();
        classfication.setName(classficationName);

        classficationService.saveClassfication(classfication);

        return CropJSONResult.ok("新建文章分类 \"" + classficationName + "\" 成功");
    }



}
