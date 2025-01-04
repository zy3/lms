package com.red.lms.common.model;

import com.red.lms.common.model.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "借还列表请求参数")
public class ActionListRequest extends PageRequest {
    @ApiModelProperty(value = "学（工）号", example = "0010001")
    private String studentNum;

    @ApiModelProperty(value = "年级班级", example = "2004-1")
    private String gradeClass;

    @ApiModelProperty(value = "读者姓名", example = "张三")
    private String readerName;

    @ApiModelProperty(value = "读者类型 S|学生 T|老师", example = "S")
    private String readerType;

    @ApiModelProperty(value = "借还状态 R|借阅中 B|已归还", example = "R")
    private String status;

    @ApiModelProperty(value = "书号", example = "978-7-300-19831-6")
    private String isbn;

    @ApiModelProperty(value = "书名", example = "毛泽东：雄关漫道：插图本")
    private String name;

    @ApiModelProperty(value = "是否逾期 Y-是|N-否", example = "Y")
    private String overdueInd;
}
