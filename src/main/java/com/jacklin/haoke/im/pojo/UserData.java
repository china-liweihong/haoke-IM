package com.jacklin.haoke.im.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午3:03
 * 构造默认用户数据
 */
public class UserData {

    public static final Map<Long, User> USER_MAP = new HashMap<>();

    static {
        USER_MAP.put(1001L, User.builder().id(1001L).username("zhangsan").build());
        USER_MAP.put(1002L, User.builder().id(1002L).username("lisi").build());
        USER_MAP.put(1003L, User.builder().id(1003L).username("wangwu").build());
        USER_MAP.put(1004L, User.builder().id(1004L).username("zhaoliu").build());
        USER_MAP.put(1005L, User.builder().id(1005L).username("sunqi").build());
    }
}
