package com.red.lms.common.model;

import com.red.lms.common.model.base.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "书籍搜索请求参数")
public class BookSearchRequest extends PageRequest {
    @ApiModelProperty(value = "书籍名称")
    private String name;

    @ApiModelProperty(value = "书籍位置")
    private String position;

    @ApiModelProperty(value = "书号")
    private String isbn;

    @ApiModelProperty(value = "作者")
    private String author;
}
