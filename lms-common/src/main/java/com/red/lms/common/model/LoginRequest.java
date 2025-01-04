package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "用户登录请求参数")
public class LoginRequest {
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;
}
