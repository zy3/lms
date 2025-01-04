package com.red.lms.common.model;

import com.red.lms.common.model.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "书籍列表请求参数")
public class BookListRequest extends PageRequest {
    @ApiModelProperty(value = "书籍名称")
    private String name;

    @ApiModelProperty(value = "书籍位置")
    private String position;

    @ApiModelProperty(value = "书号")
    private String isbn;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "状态 N|已上架 D|已下架")
    private String status;
}
