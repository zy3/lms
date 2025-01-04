package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询归还信息请求参数")
public class ActionBackDetailRequest {
    @NotNull(message = "读者唯一标识")
    @ApiModelProperty(value = "读者唯一标识")
    private String readerUniqueId;

    @NotNull(message = "书号不能为空")
    @ApiModelProperty(value = "书号", example = "978-7-300-19831-6")
    private String isbn;
}
