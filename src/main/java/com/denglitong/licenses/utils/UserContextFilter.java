package com.denglitong.licenses.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.denglitong.licenses.utils.UserContext.*;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/10/26
 */
@Component
public class UserContextFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    /**
     * 拦截servlet请求，从header中提取参数写入到ThreadLocal中，即关联到当前线程上下文中
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        UserContext userContext = UserContextHolder.getContext();
        userContext.setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        userContext.setUserId(httpServletRequest.getHeader(USER_ID));
        userContext.setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        userContext.setOrgId(httpServletRequest.getHeader(ORG_ID));

        logger.info("UserContextFilter Correlation id: {}", userContext.getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
