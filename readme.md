### 单词网站

#### 配置环境要求
* apache-tomcat：version > 9.0
* mysql
* java

#### How to run
1. 将数据库文件导入mysql
2. 将`apache-tomcat-9.0.8／webapps/assets/jar`下的两个jar包放在`apache-tomcat-9.0.8／lib`中。
3. 将lessword.war放在`apache-tomcat-9.0.8／webapps`下。
4. 重启tomcat
5. 通过http://localhost:8080/lessword/index.html访问（端口号可能不同）

另外，由于每个人的mysql设置都不一样，所以需要重新更改数据库的相关配置。第一次运行tomcat后，会在webapps下解压lessword。更改`webapps/lessword/assets/js/main.js`中关于mysql的**用户名、密码、mysql路径**三个信息。



