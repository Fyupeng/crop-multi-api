package com.crop.user.controller;

import com.crop.pojo.*;
import com.crop.pojo.vo.ArticleVO;
import com.crop.service.*;
import com.crop.utils.CropJSONResult;
import com.crop.utils.PagedResult;
import com.crop.utils.RedisUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.utils.RequestAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@Api(value = "文章相关业务的接口", tags = {"文章相关业务的controller"})
@RequestMapping(value = "/user/article")
public class UserArticleController extends BasicController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ClassficationService classficationService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/getAllArticles")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "String", paramType = "query")
    })
    public CropJSONResult getAllArticles(String key, Integer page, Integer pageSize, String userId) {
        if (StringUtils.isBlank(key)) {
            return CropJSONResult.errorMsg("关键字不能为空");
        }

        boolean addTrue = addSearchHistory(userId, key);
        if (!addTrue) {
            log.info("已存在key：{}",key);
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


    @PostMapping(value = "/getSearchHistory")
    @ApiOperation(value = "获取搜索历史记录", notes = "获取搜索历史记录的接口")
    @ApiImplicitParam(name = "userId",  value = "用户id",required = true, dataType = "String", paramType = "query")
    public CropJSONResult getSearchHistory(String userId) {
        List<String> searchHistoryList = querySearchHistory(userId);
        return CropJSONResult.ok(searchHistoryList);
    }

    @PostMapping(value = "/removeHistory")
    @ApiOperation(value = "删除搜索历史记录", notes = "删除搜索历史记录的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",  value = "用户id",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchKey",  value = "搜索关键字",required = true, dataType = "String", paramType = "query")
    })
    public CropJSONResult removeHistory(String userId, String searchKey) {
        Long history = delSearchHistory(userId, searchKey);
        return CropJSONResult.ok(history);
    }

    /**
     * 添加个人 历史数据
     * @param userId
     * @param searchKey
     * @return false: 已存在 - true : 添加成功
     */
    private boolean addSearchHistory(String userId, String searchKey) {
        String searchHistoryKey = RedisUtils.getSearchHistoryKey(userId);
        boolean keyIsExist = redis.hasKey(searchHistoryKey);
        if (keyIsExist) {
            String hk = redis.hget(searchHistoryKey, searchKey);
            if (hk != null) {
                return false;
            } else {
                redis.hset(searchHistoryKey, searchKey, "1");
            }

        } else {
            redis.hset(searchHistoryKey, searchKey, "1");
        }
        return true;
    }

    /**
     * 获取个人 历史数据
     * @param userId
     * @return null : 没有数据 - List<String> ${ketList}
     */
    private List<String> querySearchHistory(String userId) {
        String searchHistoryKey = RedisUtils.getSearchHistoryKey(userId);
        boolean keyExist = redis.hasKey(searchHistoryKey);
        if (keyExist) {
            Cursor<Map.Entry<Object, Object>> cursor = redis.hscan(searchHistoryKey);
            List<String> keyList = new ArrayList<>();
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> current = cursor.next();
                String key = current.getKey().toString();
                keyList.add(key);
            }
            return keyList;
        }
        return null;
    }
    /**
     * 删除个人 历史数据
     * @param userId
     * @param searchKey
     * @return
     */
    private Long delSearchHistory(String userId, String searchKey) {
        String searchHistoryKey = RedisUtils.getSearchHistoryKey(userId);
        return redis.hdel(searchHistoryKey, searchKey);
    }

    @PostMapping(value = "/getRecommendArticles")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query")
    })
    public CropJSONResult getRecommendArticles(String key, Integer page, Integer pageSize) {
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



        return CropJSONResult.ok(null);


    }

    @PostMapping(value = "/getLatestdArticles")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query")
    })
    public CropJSONResult getLatestdArticles(String key, Integer page, Integer pageSize) {
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



        return CropJSONResult.ok(null);

    }

    @PostMapping(value = "/getHotpotdArticles")
    @ApiOperation(value = "查找文章信息", notes = "查找文章信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = false, dataType = "String", paramType = "query")
    })
    public CropJSONResult getHotpotdArticles(String key, Integer page, Integer pageSize) {
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


        return CropJSONResult.ok(null);

    }


    @PostMapping(value = "/getArticleDetail")
    @ApiOperation(value = "获取文章详细信息", notes = "获取文章详细信息的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "query"),
            //@ApiImplicitParam(name = "request",value = "请求", dataType = "HttpServletRequest", readOnly = true)
    })

    public CropJSONResult getArticleDetail(String articleId, @ApiParam(hidden = true)HttpServletRequest request) {

        if (StringUtils.isBlank(articleId)) {
            return CropJSONResult.errorMsg("文章id不能为空");
        }

        Article article = new Article();
        article.setId(articleId);

        ArticleVO articleVO = articleService.queryArticleDetail(articleId);
        // 文章 id 不存在
        if (articleVO == null) {
            return CropJSONResult.ok("articleId不存在");
        }

        String articleView = RedisUtils.getIdView(articleVO.getId(), request);
        String articleViewCount = RedisUtils.getIdViewCount(articleVO.getId());
        //用户短时间 已 访问，redis.get(userView) 返回值是 对象，不能 通过 ""判断
        if (redis.get(articleView) != null) {
            return CropJSONResult.ok(articleVO);
        }
        /**
         * 统计量 一小时 内 同一个 ip 地址 只能算 1 次 阅读
         */
        redis.set(articleView, "1", 60 * 60);
        /**
         * articleViewCount 如果 未 初始化，redis 会 初始化为 0
         */
        redis.incr(articleViewCount,1);

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

        boolean saveIsTrue = articleService.save(article);

        return saveIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误导致保存失败");
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
            boolean updateIsTrue = articleService.saveWithIdAndUserId(ac);

            return updateIsTrue ? CropJSONResult.ok() : CropJSONResult.errorMsg("内部错误更新失败");
        }

        return CropJSONResult.errorMsg("用户 id 与 articleId 不存在或匹配失败");

    }

    @PostMapping(value = "/removeArticle")
    @ApiOperation(value = "删除文章", notes = "删除文章的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    })

    public CropJSONResult removeArticle(String articleId, String userId) {
        if (StringUtils.isBlank(articleId) || StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("articleId或userId不能为空");
        }

        Article article = new Article();
        article.setId(articleId);
        article.setUserId(userId);
        boolean articleIsUser = articleService.queryArticleIsUser(article);

        if (!articleIsUser) {
            return CropJSONResult.errorMsg("articleId不存在或者userId与commentId约束的userId不同");
        }

        articleService.removeArticle(articleId);

        return CropJSONResult.ok();
    }

    @PostMapping(value = "/getAllClassfications")
    @ApiOperation(value = "获取文章分类信息", notes = "获取文章分类信息的接口")
    public CropJSONResult getAllClassfications() {

        List<Classfication> classficationList = classficationService.queryAllClassfications();

        return CropJSONResult.ok(classficationList);
    }


}
