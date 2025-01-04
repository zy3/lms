package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "系统配置编辑")
public class SysConfigEditRequest {
    @NotNull(message = "借阅期限不能为空")
    @ApiModelProperty(value = "借阅期限", example = "10")
    private String borrowPeriod;

    @NotNull(message = "最多借阅数量不能为空")
    @ApiModelProperty(value = "最多借阅数量", example = "9")
    private String maxBorrowCount;
}
