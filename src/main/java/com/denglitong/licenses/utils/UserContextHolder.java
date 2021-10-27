package com.denglitong.licenses.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/27
 */
@Component
public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    /**
     * 获取当前线程上下文中绑定的userContext
     *
     * @return
     */
    public static UserContext getContext() {
        if (userContext.get() == null) {
            setContext(createEmptyContext());
        }
        return userContext.get();
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only not-null UserContext instance are permitted");
        userContext.set(context);
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
