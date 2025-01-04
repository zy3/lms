package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "归还列表返回结果")
public class ActionBackDetailResponse {

    @ApiModelProperty(value = "书籍唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String bookUniqueId;

    @ApiModelProperty(value = "书号", example = "978-7-300-19831-6")
    private String isbn;

    @ApiModelProperty(value = "书名", example = "毛泽东：雄关漫道：插图本")
    private String bookName;

    @ApiModelProperty(value = "借出时间", example = "2024-09-15 12:00:00")
    private String borrowTime;

    @ApiModelProperty(value = "截止时间", example = "2024-09-15 12:00:00")
    private String endTime;

    @ApiModelProperty(value = "是否逾期 Y-是|N-否", example = "Y")
    private String overdueInd;

    @ApiModelProperty(value = "读者姓名", example = "张三")
    private String readerName;

    @ApiModelProperty(value = "读者类型 S|学生 T|老师", example = "S")
    private String readerType;
}
