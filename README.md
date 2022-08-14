# 快速开始

## 本项目是简易的新闻发布系统，很简陋，有如下功能
### 1.登陆
### 2.注册
### 3.后台管理
### 4.新闻首页展示

### 5.新闻搜索




后端技术栈为`Spring`+  `SpringBoot`+`Mybatis`+`Mybatis-Plus`的Maven项目，使用IDEA开发

**resources**目录下包括了前端的html，css，js文件以及SpringBoot的配置文件

`application.yaml`的内容:
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/news_demo?serverTimezone=UTC
    username: root
    password: '016016'

mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
```
自行修改MySQL数据库url，用户名和密码字段   
**另外**，端口号修改在`application.properties`的`server.port=4514`中


本项目的资源借鉴于此，[原作者](https://gitee.com/Xmee-w/news_demo) 。\
原项目的功能更多更完善,鄙人只是想来练练手，全部代码都是从0开始跟着手敲并理解
原作者没有提供sql文件，我累死累活建表输数据，我还是放出来吧(在项目的src目录下)。
因为源项目功能过多而鄙人能力和精力不足,评论，收藏，用户等级，文章图片路径均未完成（被注释掉了，嘻嘻），多多见谅
![img.png](img/img.png)

故只能提供文章，目录和用户的SQL文件

另外后台的管理系统现在默认是只有lid>=2才能登上后台，数据库里面就只有账户114514，密码114514的用户可以登陆反正到时候想怎么改都行，记住密码要用里面的MD5工具encode一遍再加入数据库才行（如果手动添加的话）不想再做过多的测试了，祝我好运，祝各位好运！

---
ps:MD也不会写……
