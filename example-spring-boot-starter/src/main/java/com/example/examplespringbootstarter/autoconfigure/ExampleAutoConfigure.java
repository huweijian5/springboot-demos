package com.example.examplespringbootstarter.autoconfigure;

import com.example.examplespringbootstarter.bean.config.ExampleProperties;
import com.example.examplespringbootstarter.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ExampleService.class)//当指定类存在时才加入到ioc容器
@EnableConfigurationProperties(ExampleProperties.class)
//@ConditionalOnBean：当SpringIoc容器内存在指定Bean的条件
//@ConditionalOnExpression：基于SpEL表达式作为判断条件
//@ConditionalOnJava：基于JVM版本作为判断条件
//@ConditionalOnJndi：在JNDI存在时查找指定的位置
//@ConditionalOnMissingBean：当SpringIoc容器内不存在指定Bean的条件
//@ConditionalOnMissingClass：当SpringIoc容器内不存在指定Class的条件
//@ConditionalOnNotWebApplication：当前项目不是Web项目的条件
//@ConditionalOnResource：类路径是否有指定的值
//@ConditionalOnSingleCandidate：当指定Bean在SpringIoc容器内只有一个，或者虽然有多个但是指定首选的Bean

public class ExampleAutoConfigure {
    @Autowired
    private ExampleProperties properties;

    @Bean
    @ConditionalOnMissingBean//当容器内不存在此bean时创建
    @ConditionalOnProperty(prefix = "example.service",value = "enabled",havingValue = "true")////配置文件存在这个example.service.enabled=true才启动，允许不存在该配置
    ExampleService exampleService (){
        return  new ExampleService(properties.getPrefix(),properties.getSuffix());
    }

}
