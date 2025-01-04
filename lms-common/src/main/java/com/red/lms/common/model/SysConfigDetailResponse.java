package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "系统配置查询结果")
public class SysConfigDetailResponse {

    @ApiModelProperty(value = "借阅期限", example = "10")
    private String borrowPeriod;

    @ApiModelProperty(value = "最多借阅数量", example = "9")
    private String maxBorrowCount;
}
