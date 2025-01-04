package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "读者列表返回结果")
public class ReaderListResponse {
    @ApiModelProperty(value = "唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String readerUniqueId;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "年级班级", example = "2024-1")
    private String gradeClass;

    @ApiModelProperty(value = "学（工）号", example = "0010001")
    private String studentNum;

    @ApiModelProperty(value = "类型 S|学生 T|老师", example = "S")
    private String readerType;

    @ApiModelProperty(value = "状态 N|已启用 S|已停用", example = "N")
    private String status;
}
