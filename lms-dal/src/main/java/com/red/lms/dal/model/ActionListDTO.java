package com.red.lms.dal.model;

import lombok.Data;

import java.util.Date;

@Data
public class ActionListDTO {
    private String isbn;

    private String bookName;

    private String readerName;

    private String readerType;

    private String gradeClass;

    private String studentNum;

    private String status;

    private Date borrowTime;

    private Date endTime;

    private Date backTime;

    private String bookUniqueId;

    private String readerUniqueId;
}
