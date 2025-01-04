package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "书籍详情查询请求参数")
public class BookDetailRequest {

    @NotNull(message = "唯一标识不能为空")
    @ApiModelProperty(value = "唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String bookUniqueId;
}
