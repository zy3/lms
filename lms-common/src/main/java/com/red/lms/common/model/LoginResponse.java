package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户登录结果")
public class LoginResponse {
    @ApiModelProperty(value = "token", example = "获取成功后将token放到header里面")
    private String token;
}
