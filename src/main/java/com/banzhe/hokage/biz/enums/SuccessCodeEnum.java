package com.banzhe.hokage.biz.enums;

/**
 * @author linyimin
 * @date 2020/8/30 3:56 下午
 * @email linyimin520812@gmail.com
 * @description
 */
public enum  SuccessCodeEnum {

    success("00000");

    private String code;

    public String getCode() {
        return code;
    }

    SuccessCodeEnum(String code) {
        this.code = code;
    }
}