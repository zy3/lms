package com.red.lms.common.model;

import com.red.lms.common.model.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "读者列表请求参数")
public class ReaderListRequest extends PageRequest {
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年级班级")
    private String gradeClass;

    @ApiModelProperty(value = "读者类型")
    private String readerType;

    @ApiModelProperty(value = "状态 N|已启用 S|已停用")
    private String status;

    @ApiModelProperty(value = "学（工）号")
    private String studentNum;
}
