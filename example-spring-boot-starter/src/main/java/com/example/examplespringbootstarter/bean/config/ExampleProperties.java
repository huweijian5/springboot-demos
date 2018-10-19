package com.example.examplespringbootstarter.bean.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("example.service")
public class ExampleProperties {

    private String prefix = "111";
    private String suffix = "222";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
