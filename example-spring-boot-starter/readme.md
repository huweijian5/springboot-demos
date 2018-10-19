* https://www.cnblogs.com/shaobin0604/p/8366234.html
* 切换到当前目录
* gradlew assemble
* gradlew publishToMavenLocal -Dorg.gradle.warning.mode=all --warning-mode=all
* https://docs.gradle.org/4.8.1/userguide/command_line_interface.html#sec:command_line_warnings
* https://docs.gradle.org/4.8.1/userguide/publishing_maven.html#publishing_maven:deferred_configuration


* implementation('com.example:example-spring-boot-starter:1.0.0')

## 发布到本地
    - 在settings.gradle中添加enableFeaturePreview('STABLE_PUBLISHING')
    - 在项目目录下执行gradlew assemble，从而生成jar,放置在/build/libs/下
    - 在项目目录下执行gradlew publishToMavenLocal -Dorg.gradle.warning.mode=all --warning-mode=all
    - 以上都无错误则已经发布到本地仓库了
    
## 本地引用
* 添加mavenLocal()
```gradle
repositories {
	mavenLocal()
}
```


