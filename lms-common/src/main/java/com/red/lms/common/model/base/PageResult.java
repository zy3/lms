package com.red.lms.common.model.base;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private Long pageNo;
    private Long pageSize;
    private Long total;
    private Long pages;
    private List<T> rows;
}
