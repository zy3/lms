package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询读者详情请求参数")
public class ReaderDetailRequest {
    @ApiModelProperty(value = "唯一标识")
    private String readerUniqueId;


    @ApiModelProperty(value = "学（工）号", example = "001001")
    private String studentNum;
}
