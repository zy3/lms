package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "书籍状态更改请求参数")
public class BookStatusRequest {

    @NotNull(message = "唯一标识不能为空")
    @ApiModelProperty(value = "唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String bookUniqueId;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态 N|已上架 D|已下架", example = "N")
    private String status;
}
