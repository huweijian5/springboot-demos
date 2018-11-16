package com.example.lombok.bean;

import lombok.*;

import java.io.Serializable;

/**
 * 加了相关注解后再IDEA上可以通过快捷键ctrl+F12的方式看看为我们自动生成了什么
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor(access=AccessLevel.PRIVATE)//会生成标识了@NotNull的变量 的构造方法
//@Data//实现了@ToString、@Getter、@Setter、@EqualsAndHashCode、@NoArgsConstructor
//@Value//与data区别为如果变量不加@NonFinal ，@Value会将所有的弄成final的
public class User implements Serializable{
    @NonNull
    private String name="";
    @Singular
    private int age=1;
}
