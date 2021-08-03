package com.bfly.core.exception;

import com.bfly.cms.enums.SysError;
import org.apache.commons.lang3.StringUtils;

/**
 * 接口响应统一抛出异常
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:01
 */
public class WsResponseException extends RuntimeException {

    private String code;
    private SysError error;
    private String message;

    public WsResponseException(SysError error) {
        error = error == null ? SysError.ERROR : error;
        this.error = error;
        this.code = error.getCode();
    }

    public WsResponseException(SysError error, String message) {
        super(message);
        error = error == null ? SysError.ERROR : error;
        this.error = error;
        this.code = error.getCode();
    }

    @Override
    public String getMessage() {
        if (StringUtils.isEmpty(message)) {
            message = error.getMessage();
        }
        return super.getMessage();
    }

    public String getCode() {
        return code;
    }

    public SysError getError() {
        return error;
    }
}
