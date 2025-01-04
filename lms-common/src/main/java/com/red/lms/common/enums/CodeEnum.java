package com.red.lms.common.enums;

/**
 * 码本类
 */
public enum CodeEnum {
    SUCCESS(200, "处理成功"),
    PARAM_ERROR(201, "参数异常"),
    BIZ_ERROR(202, "业务异常"),
    SYSTEM_ERROR(203, "系统异常"),
    REPEAT_ERROR(204, "重复提交"),
    FAIL_ERROR(205, "操作失败"),
    UNAUTHORIZED(206, "没有权限"),
    ILLEGAL_ACCESS(207, "非法访问"),
    LOGIN_TIMEOUT(208, "未登录或登录失效"),
    REGISTER_TIMEOUT(209, "注册已超时，请重新获取验证码"),
    NOT_VIP(210, "非VIP用户"),
    CELLPHONE(211, "请授权获取手机号"),
    USER_NOT_EXIST(212, "用户不存在，请注册"),
    LOGIN_FAILED(213, "用户名或者密码错误"),
    USERNAME_EXIST(214, "用户名已被占用"),
    NOTICE(215, "业务提示"),
    USER_PASS_NOT_EMPTY(216, "用户名密码不能为空"),
    ;

    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeEnum findByCode(int code) {
        CodeEnum[] codeEnums = CodeEnum.values();
        for (CodeEnum codeEnum : codeEnums) {
            if (code == codeEnum.getCode()) {
                return codeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[异常码:" + this.code + " 异常信息:" + this.getMessage() + "]";
    }
}
