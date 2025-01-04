package com.red.lms.common.model.base;

import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "返回结果")
public class RestResponse<T> {
    @Builder.Default
    @ApiModelProperty(value = "返回码", example = "200")
    private Integer code = CodeEnum.SUCCESS.getCode();
    @Builder.Default
    @ApiModelProperty(value = "描述", example = "处理成功")
    private String message = CodeEnum.SUCCESS.getMessage();

    @ApiModelProperty(value = "数据结果")
    private T data;

    private void setCodeEnum(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public void setCodeEnum(CodeEnum codeEnum, String message) {
        this.code = codeEnum.getCode();
        this.message = message;
    }

    public void setCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse<T> success(T data) {
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = CodeEnum.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    public RestResponse<T> fail(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        return this;
    }

    public RestResponse<T> fail(CodeEnum codeEnum, String message) {
        this.code = codeEnum.getCode();
        this.message = message;
        return this;
    }

    public RestResponse<T> failCommonException(CommonException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        return this;
    }
}
