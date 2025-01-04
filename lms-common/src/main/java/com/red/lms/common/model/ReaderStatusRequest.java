package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "读者状态更改请求参数")
public class ReaderStatusRequest {

    @NotNull(message = "唯一标识不能为空")
    @ApiModelProperty(value = "唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String readerUniqueId;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "N|已启用 S|已停用", example = "N")
    private String status;
}
