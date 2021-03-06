package com.hokage.biz.enums;

/**
 * @author linyimin
 * @date 2020/8/24 12:16 am
 * @email linyimin520812@gmail.com
 * @description
 */
public enum ResultCodeEnum {

    /**
     * user error code
     */
    USERNAME_NULL_ERROR("A-0001", "username can't be null"),

    USERNAME_DUPLICATE_ERROR("A-0002", "user email has existed"),

    USER_REGISTER_FAIL("A-0003", "register failed"),

    USER_PASSWD_ERROR("A-0004", "username or password error"),

    USER_NO_LOGIN("A-005", "user is not login"),

    USER_NO_PERMISSION("A-006", "require permission"),

    NO_SUCH_USER("A-007", "no such user"),

    /**
     * server error code
     */
    SERVER_SYSTEM_ERROR("B-001", "system error"),

    /**
     * server group
     */
    SERVER_GROUP_EXISTED("B-002", "server group has existed."),

    /**
     * upload file error
     */
    SERVER_UPLOAD_FILE_ERROR("C-001", "file is empty."),

    SUCCESS("0000", "success"),


    /**
     * server file management error code
     */
    SERVER_NO_FOUND("F-0001", "server is not found."),
    COMMAND_EXECUTED_FAILED("F-0002", "command execute failed."),
    FILE_DOWNLOAD_FAILED("F-0003", "download file execute failed."),
    FILE_TAR_FAILED("F-0004", "package folder failed."),
    FILE_UPLOAD_FAILED("F-0005", "upload file execute failed."),

    FIXED_DATE_NOT_FOUND("E-0001", "task not found"),
    TASK_RESULT_NOT_FOUND("E-0002", "task result not found"),

    CREATE_ACCOUNT_ERROR("G-0001", "create account error"),
    DELETE_ACCOUNT_ERROR("G-0002", "delete account error"),
    ;

    private String code;
    private String msg;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
