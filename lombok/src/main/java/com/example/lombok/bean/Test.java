package com.example.lombok.bean;

import com.alibaba.fastjson.JSON;
import com.example.lombok.bean.Car;
import com.example.lombok.bean.User;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Log
//@JBossLog
//@CommonsLog
//@Log4j2
public class Test {
    public static void main(String[] args) throws FileNotFoundException {

        log.info("a log");
        Car car=Car.builder().color(1).weight(1).build();

        //自动关闭流
        @Cleanup
        InputStream in = new FileInputStream(args[0]);
        @Cleanup
        OutputStream out = new FileOutputStream(args[1]);


        //类型推导
        // var: 可变变量，相当于普通的变量
        // val：只读变量，相当于 final
        val example = new ArrayList<String>();
        example.add("Hello, World!");
    }

    /**
     * @param user
     * @NonNull相当于加了
     * if (user == null) {
     * throw new NullPointerException("user");
     * }
     */
    public void NonNullExample(@NonNull User user) {
        user.getName();
    }
}
