package com.red.lms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.lms.common.constants.Constants;
import com.red.lms.common.enums.ActionStatusEnum;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.enums.ReaderStatusEnum;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.utils.CommonUtils;
import com.red.lms.dal.entity.Action;
import com.red.lms.dal.entity.Reader;
import com.red.lms.dal.entity.SysConfig;
import com.red.lms.dal.mapper.ActionMapper;
import com.red.lms.dal.mapper.ReaderMapper;
import com.red.lms.dal.mapper.SysConfigMapper;
import com.red.lms.service.token.UserInfoContainer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public PageResult<ReaderListResponse> list(ReaderListRequest request) {
        Page<Reader> rowPage = new Page(request.getPageNo(), request.getPageSize());
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getName())) {
            queryWrapper.like(Constants.NAME, request.getName());
        }
        if (StringUtils.isNotBlank(request.getGradeClass())) {
            queryWrapper.eq(Constants.GRADE_CLASS, request.getGradeClass());
        }

        if (StringUtils.isNotBlank(request.getReaderType())) {
            queryWrapper.eq(Constants.READER_TYPE, request.getReaderType());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq(Constants.STATUS, request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getStudentNum())) {
            queryWrapper.like(Constants.STUDENT_NUM, request.getStudentNum());
        }
        queryWrapper.orderByDesc(Constants.UPD_DT);
        queryWrapper.eq(Constants.DEL_FLAG, false);
        rowPage = readerMapper.selectPage(rowPage, queryWrapper);
        PageResult<ReaderListResponse> pageResult = new PageResult<>();
        if (!CollectionUtils.isEmpty(rowPage.getRecords())) {
            List<ReaderListResponse> responseList = new ArrayList<>();
            for (Reader reader : rowPage.getRecords()) {
                ReaderListResponse response = initPageResponse(reader);
                responseList.add(response);
            }
            pageResult.setRows(responseList);
        }
        pageResult.setPageSize(rowPage.getSize());
        pageResult.setPages(rowPage.getPages());
        pageResult.setPageNo(rowPage.getCurrent());
        pageResult.setTotal(rowPage.getTotal());
        return pageResult;
    }

    public void add(ReaderAddRequest request) {
        QueryWrapper<Reader> studentNumQueryMapper = new QueryWrapper<>();
        studentNumQueryMapper.eq(Constants.STUDENT_NUM, request.getStudentNum());
        studentNumQueryMapper.eq(Constants.DEL_FLAG, false);
        List<Reader> readerList = readerMapper.selectList(studentNumQueryMapper);
        if (!CollectionUtils.isEmpty(readerList) && readerList.size() >= 1) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "已经存在相同学（工）号的读者");
        }
        Reader reader = new Reader();
        reader.setReaderUniqueId(CommonUtils.getUuid());
        reader.setName(request.getName());
        reader.setGradeClass(request.getGradeClass());
        reader.setStudentNum(request.getStudentNum());
        reader.setReaderType(request.getReaderType());
        reader.setStatus(ReaderStatusEnum.N.name());

        reader.setInsDt(new Date());
        reader.setUpdDt(new Date());
        reader.setInsPerson(UserInfoContainer.getUsername());
        reader.setUpdPerson(UserInfoContainer.getUsername());
        reader.setDelFlag(false);

        readerMapper.insert(reader);
    }

    public ReaderDetailResponse detail(ReaderDetailRequest request) {
        if (StringUtils.isBlank(request.getReaderUniqueId()) && StringUtils.isBlank(request.getStudentNum())) {
            throw new CommonException(CodeEnum.PARAM_ERROR, "唯一标识、学（工）号不能都为空");
        }
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getReaderUniqueId())) {
            queryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        }
        if (StringUtils.isNotBlank(request.getStudentNum())) {
            queryWrapper.eq(Constants.STUDENT_NUM, request.getStudentNum());
        }
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(queryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在");
        }
        QueryWrapper<Action> actionQueryWrapper = new QueryWrapper<>();
        actionQueryWrapper.eq(Constants.READER_UNIQUE_ID, reader.getReaderUniqueId());
        actionQueryWrapper.eq(Constants.STATUS, ActionStatusEnum.R.name());
        actionQueryWrapper.eq(Constants.DEL_FLAG, false);
        Long counts = actionMapper.selectCount(actionQueryWrapper);
        QueryWrapper<SysConfig> sysConfigQueryWrapper = new QueryWrapper<>();
        List<SysConfig> sysConfigList = sysConfigMapper.selectList(sysConfigQueryWrapper);
        if (CollectionUtils.isEmpty(sysConfigList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置数据为空");
        }
        SysConfig sysConfig = sysConfigList.get(0);
        if (sysConfig.getMaxBorrowCount() == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置借阅上限为空");
        }
        ReaderDetailResponse response = new ReaderDetailResponse();
        response.setReaderUniqueId(reader.getReaderUniqueId());
        response.setName(reader.getName());
        response.setGradeClass(reader.getGradeClass());
        response.setStudentNum(reader.getStudentNum());
        response.setReaderType(reader.getReaderType());
        response.setBorrowCount(counts.intValue());
        response.setMaxBorrowCount(sysConfig.getMaxBorrowCount());
        return response;
    }

    public void edit(ReaderEditRequest request) {
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(queryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在");
        }

        QueryWrapper<Reader> studentNumQueryMapper = new QueryWrapper<>();
        studentNumQueryMapper.eq(Constants.STUDENT_NUM, request.getStudentNum());
        studentNumQueryMapper.eq(Constants.DEL_FLAG, false);
        List<Reader> readerList = readerMapper.selectList(studentNumQueryMapper);
        if (!CollectionUtils.isEmpty(readerList)) {
            for (Reader existReader: readerList) {
                if (existReader.getReaderUniqueId().equals(reader.getReaderUniqueId())) {
                    continue;
                }
                throw new CommonException(CodeEnum.BIZ_ERROR, "已经存在相同学（工）号的读者");
            }
        }
        reader.setReaderUniqueId(CommonUtils.getUuid());
        reader.setName(request.getName());
        reader.setGradeClass(request.getGradeClass());
        reader.setStudentNum(request.getStudentNum());
        reader.setReaderType(request.getReaderType());
        reader.setStatus(ReaderStatusEnum.N.name());
        reader.setUpdDt(new Date());
        reader.setUpdPerson(UserInfoContainer.getUsername());
        readerMapper.updateById(reader);
    }

    public void status(ReaderStatusRequest request) {
        QueryWrapper<Reader> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(queryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在");
        }
        reader.setReaderUniqueId(CommonUtils.getUuid());
        reader.setStatus(request.getStatus());
        reader.setUpdDt(new Date());
        reader.setUpdPerson(UserInfoContainer.getUsername());
        readerMapper.updateById(reader);
    }


    private ReaderListResponse initPageResponse(Reader reader) {
        ReaderListResponse response = new ReaderListResponse();
        response.setReaderUniqueId(reader.getReaderUniqueId());
        response.setName(reader.getName());
        response.setGradeClass(reader.getGradeClass());
        response.setStudentNum(reader.getStudentNum());
        response.setReaderType(reader.getReaderType());
        response.setStatus(reader.getStatus());
        return response;
    }
}
