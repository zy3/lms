package com.red.lms.common.exceptions;


import com.red.lms.common.enums.CodeEnum;

public class CommonException extends RuntimeException {

    /**
     * 状态码
     */
    private int code = 500;

    /**
     * 自定义异常信息
     */
    private String message;


    /**
     * 使用枚举类限制异常信息
     *
     * @param codeEnum 异常封装枚举类
     */
    public CommonException(CodeEnum codeEnum) {
        super(codeEnum.toString());
        this.message = codeEnum.getMessage();
        this.code = codeEnum.getCode();
    }

    /**
     * 使用枚举类和描述
     *
     * @param codeEnum
     * @param message
     */
    public CommonException(CodeEnum codeEnum, String message) {
        this.message = message;
        this.code = codeEnum.getCode();
    }


    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
