package com.bfly.core.security;

/**
 * 密码加密接口
 *
 * @author 胡礼波
 * 2012-4-25 下午05:51:43
 */
public interface PwdEncoder {

    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @return 加密后密码
     * @author 胡礼波
     * 2012-4-25 下午05:52:02
     */
     String encodePassword(String rawPass);

    /**
     * 密码加密
     *
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return
     * @author 胡礼波
     * 2012-4-25 下午05:52:02
     */
    String encodePassword(String rawPass, String salt);

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @return true:密码正确；false:密码错误
     * @author 胡礼波
     * 2012-4-25 下午05:52:02
     */
    boolean isPasswordValid(String encPass, String rawPass);

    /**
     * 验证密码是否正确
     *
     * @param encPass 加密密码
     * @param rawPass 未加密密码，null作为空串
     * @param salt    混淆码
     * @return true:密码正确；false:密码错误
     * @author 胡礼波
     * 2012-4-25 下午05:52:02
     */
    boolean isPasswordValid(String encPass, String rawPass, String salt);
}
