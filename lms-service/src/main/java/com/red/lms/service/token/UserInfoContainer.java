package com.red.lms.service.token;


import com.red.lms.common.model.base.UserLoginInfo;

/**
 * 客户登录信息容器
 */
public class UserInfoContainer {
    private static final ThreadLocal<UserLoginInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void put(UserLoginInfo userLoginInfo) {
        USER_INFO_THREAD_LOCAL.set(userLoginInfo);
    }

    public static UserLoginInfo getUserLoginInfo() {
        UserLoginInfo userLoginInfo = USER_INFO_THREAD_LOCAL.get();
        return userLoginInfo;
    }

    public static String getUsername() {
        UserLoginInfo userLoginInfo = USER_INFO_THREAD_LOCAL.get();
        if (userLoginInfo == null) {
            return null;
        }
        return userLoginInfo.getUsername();
    }
}
