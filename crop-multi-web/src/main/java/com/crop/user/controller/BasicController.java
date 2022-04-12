package com.crop.user.controller;

import com.crop.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";
    public static final String ADMIN_REDIS_SESSION = "admin-redis-session";

    public static final String IS_VIEW = "isView";

    public static final String VIEW_COUNT = "viewCount";
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


}
