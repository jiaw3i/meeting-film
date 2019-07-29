package com.stylefeng.guns.rest.common;

/**
 * 佛祖保佑    永无BUG
 *
 * @author Jerry
 **/
public class CurrentUser {
    // 线程绑定的存储空间
    private static final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    // 将用户信息存入存储空间
    public static void saveUserInfo(String userId){
        threadLocal.set(userId);
    }

    // 获取存储空间里的用户信息
    public static String getCurrentUser(){

        return threadLocal.get();
    }
}
