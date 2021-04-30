#  Data Generation System 

created in 4/30/2021



### How Did I Solve Those Blocking Issue/

4/30

#### 1. tomcat login page

Automatically jump into `/login` page, causing me could not enter my own page.

>  solution reference: https://blog.csdn.net/weixin_42988712/article/details/115311264?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0

**Main Steps :**

1. add `@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})` on your XxxApplication class.
2. rerun your project.

**Main Reason :**

*Spring security* dependency is the one who causing this situation.

