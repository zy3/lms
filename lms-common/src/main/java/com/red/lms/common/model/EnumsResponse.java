package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel(value = "枚举参数")
public class EnumsResponse {
    @ApiModelProperty(value = "书籍位置枚举")
    private Map<String, String> bookPositionEnums;

    @ApiModelProperty(value = "书籍状态枚举")
    private Map<String, String> bookStatusEnums;

    @ApiModelProperty(value = "读者类型枚举")
    private Map<String, String> readerTypeEnums;

    @ApiModelProperty(value = "读者状态枚举")
    private Map<String, String> readerStatusEnums;

    @ApiModelProperty(value = "借还状态枚举")
    private Map<String, String> actionStatusEnums;

    @ApiModelProperty(value = "年级班级枚举")
    private Map<String, String> gradeClassEnums;

    @ApiModelProperty(value = "书籍是否逾期枚举")
    private Map<String, String> bookOverdueEnums;
}
