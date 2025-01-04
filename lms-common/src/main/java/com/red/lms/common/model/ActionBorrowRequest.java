package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "提交借阅请求参数")
public class ActionBorrowRequest {
    @NotNull(message = "读者唯一标识")
    @ApiModelProperty(value = "读者唯一标识")
    private String readerUniqueId;

    @NotNull(message = "书籍唯一标识列表不能为空")
    @ApiModelProperty(value = "书籍唯一标识列表")
    private List<String> bookUniqueIdList;
}
