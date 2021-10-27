package com.denglitong.licenses.hystrix;

import com.denglitong.licenses.utils.UserContext;
import com.denglitong.licenses.utils.UserContextHolder;

import java.util.concurrent.Callable;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/27
 */
public final class DelegatingUserContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;

    private UserContext originalUserContext;

    // 初始化时候传入父线程的userContext，开启子线程执行call()时将userContext关联到线程上下文
    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);
        try {
            return delegate.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }
}
