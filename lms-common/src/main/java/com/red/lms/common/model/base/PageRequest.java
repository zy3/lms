package com.red.lms.common.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class PageRequest {
    @ApiModelProperty(value = "页数", example = "1")
    @NotNull(message = "页数不能为空")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数", example = "10")
    @NotNull(message = "每页条数不能为空")
    @Max(value = 20, message = "每页条数不能超过20")
    private Integer pageSize;
}
