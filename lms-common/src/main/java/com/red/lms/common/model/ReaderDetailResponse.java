package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "读者详情返回结果")
public class ReaderDetailResponse {
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

    @ApiModelProperty(value = "已经书借阅籍数量")
    private Integer borrowCount;

    @ApiModelProperty(value = "借阅上限")
    private Integer maxBorrowCount;
}
