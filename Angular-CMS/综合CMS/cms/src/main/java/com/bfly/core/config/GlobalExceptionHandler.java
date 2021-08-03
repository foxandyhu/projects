package com.bfly.core.config;

import com.bfly.cms.enums.SysError;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.exception.*;
import com.octo.captcha.service.CaptchaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/19 11:19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        SysError error = SysError.ERROR;

        if (ex instanceof WebPageNotFoundException) {
            try {
                response.sendRedirect("/404.html");
            } catch (IOException e) {
                logger.error(ex.getMessage(), ex);
            }
            return;
        }
        if (ex instanceof WebServerInnerErrorException) {
            try {
                response.sendRedirect("/500.html");
            } catch (IOException e) {
                logger.error(ex.getMessage(), ex);
            }
            return;
        }
        if (ex instanceof WebClosedException) {
            try {
                response.sendRedirect("/close.html");
            } catch (IOException e) {
                logger.error(ex.getMessage(), ex);
            }
            return;
        }

        if (ex instanceof UnAuthException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            //表示异步Ajax请求
            if (ContextUtil.isAjax(request)) {
                message = "未登录!";
                error = SysError.UNAUTHORIZED;
            } else {
                String returnUrl = request.getRequestURL().toString();
                try {
                    response.sendRedirect("/login.html?returnUrl=" + returnUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        } else if (ex instanceof CaptchaServiceException) {
            message = "验证码已失效!";
        } else if (ex instanceof UnRightException) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            message = ex.getMessage();
            error = SysError.RESOURCE_NOT_OWNER;
        }
        logger.error(ex.getMessage(), ex);
        ResponseUtil.writeJson(response, ResponseData.getFail(error, message));
    }
}