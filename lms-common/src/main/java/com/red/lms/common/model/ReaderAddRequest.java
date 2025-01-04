package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "读者添加请求参数")
public class ReaderAddRequest {
    @NotNull(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "年级班级", example = "2024-1")
    private String gradeClass;

    @NotNull(message = "学（工）号不能为空")
    @ApiModelProperty(value = "学（工）号", example = "001001")
    private String studentNum;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型 S|学生 T|老师", example = "S")
    private String readerType;
}
