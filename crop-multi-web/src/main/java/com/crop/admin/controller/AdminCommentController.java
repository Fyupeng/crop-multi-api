package com.crop.admin.controller;

import com.crop.pojo.User;
import com.crop.pojo.vo.CommentVO;
import com.crop.service.CommentService;
import com.crop.service.UserService;
import com.crop.user.controller.BasicController;
import com.crop.utils.CropJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: fyp
 * @Date: 2022/4/8
 * @Description:
 * @Package: com.crop.admin.controller
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/comment")
@Api(value = "评论相关业务的接口", tags = {"评论相关业务的controller"})
public class AdminCommentController extends BasicController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "removeComment")
    @ApiOperation(value = "撤回评论", notes = "撤回评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    })
    public CropJSONResult removeComment(String commentId, String userId) {

        if (StringUtils.isBlank(commentId) || StringUtils.isBlank(userId)) {
            return CropJSONResult.errorMsg("commentId或userId不能为空");
        }

        boolean commentIsExist = commentService.queryCommentIsExist(commentId);

        if (!commentIsExist) {
            return CropJSONResult.errorMsg("commentId不存在");
        }

        User identifyUser = userService.queryUser(userId);
        if (identifyUser == null || identifyUser.getPermission() != 3) {
            return CropJSONResult.errorMsg("用户Id不存在或无权限");
        }

        commentService.removeCommentById(commentId);
        commentService.removeCommentWithFatherCommentId(commentId);

        return CropJSONResult.ok();
    }


    @PostMapping(value = "/filterComments")
    @ApiOperation(value = "过滤查询评论", notes = "过滤查询评论的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aPattern", value = "文章匹配", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cPattern", value = "评论匹配", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "评论开始时间 - yyyy-MM-dd hh-mm-ss", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "评论结束时间 - yyyy-MM-dd hh-mm-ss", required = true, dataType = "String", paramType = "query")
    })
    public CropJSONResult filterComments(String aPattern, String cPattern, String userId, String startTime, String endTime) {

        if (StringUtils.isBlank(aPattern) && StringUtils.isBlank(cPattern)) {
            return CropJSONResult.errorMsg("至少指定文章匹配或评论匹配");
        }

        if (!(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime))) {
            return CropJSONResult.errorMsg("必须指定时间跨度");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return CropJSONResult.errorMsg("时间格式不正确");
        }

        List<CommentVO> result = null;
        if (StringUtils.isBlank(userId)) {
            result = commentService.queryAllComments(aPattern, cPattern, null, startDate, endDate);
        } else {
            result = commentService.queryAllComments(aPattern, cPattern, userId, startDate, endDate);
        }

        return CropJSONResult.ok(result);

    }

}
