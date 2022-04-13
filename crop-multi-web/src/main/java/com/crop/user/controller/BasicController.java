package com.crop.user.controller;

import com.crop.utils.RedisOperator;
import com.mysql.cj.exceptions.StatementIsClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;
    /**
     * fix - 解耦到 RedisUtils
     */
    //public static final String CROP = "crop";
    //
    //public static final String USER_REDIS_SESSION = CROP + ":" + "user-redis-session";
    //public static final String ADMIN_REDIS_SESSION = CROP + ":" + "admin-redis-session";
    //
    //
    //public static final String IS_VIEW = CROP + ":" + "isView";
    //
    //public static final String VIEW_COUNT = CROP + ":" + "viewCount";
    //
    //public static final String SEARCH_HISTORY = CROP + ":" + "searchHistory";

    ////文件保存的命名空间
    //public static final String FILE_SPACE = "D:/tony_videos_dev";
    ////ffmpeg所在目录
    //public static final String FFMpPEG_EXE = "D:\\study\\software\\ffmpeg\\bin\\ffmpeg.exe";
    ////每页分页的记录数
    //public static final Integer PAGE_SIZE = 5;

    //文件保存的命名空间
    public static final String FILE_SPACE =
            System.getProperties().getProperty("user.home") + File.separator + "webapps" + File.separator + "crop_multi_data";
    //ffmpeg所在目录
    public static final String FFMPEG_EXE = "ffmpeg";//需要预先设置好ffmpeg的环境变量
    //每页分页的记录数
    public static final Integer ARTICLE_PAGE_SIZE = 6;
    // 评论分页
    public static final Integer COMMENT_PAGE_SIZE = 10;
    // 图片分页
    public static final Integer PICTURE_PAGE_SIZE = 10;

    public static final Integer SEARCH_SIZE = 10;

    public static final Long ONE_WEEK = 604800000L;

    public static final Long TWO_WEEK = 1209600000L;

    public static final Long ONE_MONTH = 2592000000L; // 30天

    public static final Long ONE_YEAR = 31536000000L;


}
