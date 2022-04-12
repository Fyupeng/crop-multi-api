# crop-multi-api

#### 介绍
农作物多端检测平台

#### 软件架构
软件架构说明

接口文档：[http://47.107.63.171:8083/swagger-ui.htm](http://47.107.63.171:8083/swagger-ui.html)

数据映射url：[http://47.107.63.171:9001/crop_multi_data/](http://47.107.63.171:9001/crop_multi_data/)

后端数据存储采用两台服务器支持，一台作为mysql和reids缓存和基本类型数据存储，另一台作为大数据文章类型存储。

数据库支持：mysql、redis、mongodb

- redis作为缓存数据库，使用它来生成 userToken 秘钥
![输入图片说明](crop-multi-api/img/%E7%BC%93%E5%AD%98%E7%B1%BB%E5%9E%8B%E6%95%B0%E6%8D%AE%E5%AE%9E%E4%BE%8B%E5%9B%BE.png)

- mongodb作为文档数据库，可存储复杂类型的数据，我们的文章表存储成json格式到mongodb中
实例数据：
![输入图片说明](crop-multi-api/img/%E6%96%87%E6%A1%A3%E7%B1%BB%E5%9E%8B%E5%AE%9E%E4%BE%8B%E5%9B%BE.png)

#### 功能

1. ##### 定时任务

阅读量不会即时更新到mongodb,但会及时更新到 redis缓存起来
开启定时任务，每隔5分钟将数据同步到mongodb中。

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 更新
2022/4/7
1. 配合 nginx 的url 映射技术，新增浏览器 查阅后台日志文件

2022/4/8
1. 更新评论内容、管理员注册和登录；
2. 修复发表评论时，父评论id和被回复用户id为空仍可以保存的bug；

2022/4/8
1. 更新标签功能；
2. 修复标签为私有，其他人不可见；

2022/4/9
1. 增加删除文章功能;






#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
