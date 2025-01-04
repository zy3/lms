package com.red.lms.service;

import com.red.lms.common.config.*;
import com.red.lms.common.model.EnumsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicService {

    @Autowired
    private ActionStatusProperties actionStatusProperties;

    @Autowired
    private BookPositionProperties bookPositionProperties;

    @Autowired
    private BookStatusProperties bookStatusProperties;

    @Autowired
    private ReaderStatusProperties readerStatusProperties;

    @Autowired
    private ReaderTypeProperties readerTypeProperties;

    @Autowired
    private GradeClassProperties gradeClassProperties;

    @Autowired
    private BookOverdueProperties bookOverdueProperties;

    public EnumsResponse queryAllEnums() {
        EnumsResponse enumsResponse = new EnumsResponse();
        enumsResponse.setActionStatusEnums(actionStatusProperties.getEnums());
        enumsResponse.setBookPositionEnums(bookPositionProperties.getEnums());
        enumsResponse.setBookStatusEnums(bookStatusProperties.getEnums());
        enumsResponse.setReaderStatusEnums(readerStatusProperties.getEnums());
        enumsResponse.setReaderTypeEnums(readerTypeProperties.getEnums());
        enumsResponse.setGradeClassEnums(gradeClassProperties.getEnums());
        enumsResponse.setBookOverdueEnums(bookOverdueProperties.getEnums());
        return enumsResponse;
    }
}
