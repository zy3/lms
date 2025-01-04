package com.red.lms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.lms.common.constants.Constants;
import com.red.lms.common.enums.*;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.utils.CommonUtils;
import com.red.lms.common.utils.DateTimeUtils;
import com.red.lms.dal.entity.Action;
import com.red.lms.dal.entity.Book;
import com.red.lms.dal.entity.Reader;
import com.red.lms.dal.entity.SysConfig;
import com.red.lms.dal.mapper.ActionMapper;
import com.red.lms.dal.mapper.BookMapper;
import com.red.lms.dal.mapper.ReaderMapper;
import com.red.lms.dal.mapper.SysConfigMapper;
import com.red.lms.dal.model.ActionListDTO;
import com.red.lms.dal.wrapper.LmsQueryWrapper;
import com.red.lms.service.token.UserInfoContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class ActionService {

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public PageResult<ActionListResponse> list(ActionListRequest request) {
        IPage<ActionListDTO> rowPage = new Page(request.getPageNo(), request.getPageSize());
        LmsQueryWrapper<ActionListDTO> queryWrapper = new LmsQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getStudentNum())) {
            queryWrapper.like("t3.student_num", "%" + request.getStudentNum() +"%");
        }
        if (StringUtils.isNotBlank(request.getGradeClass())) {
            queryWrapper.eq("t3.grade_class", request.getGradeClass());
        }
        if (StringUtils.isNotBlank(request.getReaderName())) {
            queryWrapper.like("t3.name", "%" + request.getReaderName()+"%");
        }
        if (StringUtils.isNotBlank(request.getReaderType())) {
            queryWrapper.eq("t3.reader_type", request.getReaderType());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq("t1.status", request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getIsbn())) {
            queryWrapper.like("t2.isbn", "%" + request.getIsbn()+"%");
        }
        if (StringUtils.isNotBlank(request.getName())) {
            queryWrapper.like("t2.name", "%" + request.getName()+"%");
        }
        if (ActionOverdueIndEnum.Y.name().equals(request.getOverdueInd())) {
            queryWrapper.lt("t1.end_time", new Date());
        }
        queryWrapper.orderByDesc("t1.upd_dt");
        rowPage = actionMapper.selectPageByCondition(rowPage, queryWrapper);
        PageResult<ActionListResponse> pageResult = new PageResult<>();
        if (!CollectionUtils.isEmpty(rowPage.getRecords())) {
            List<ActionListResponse> responseList = new ArrayList<>();
            for (ActionListDTO actionListDTO : rowPage.getRecords()) {
                ActionListResponse response = initPageResponse(actionListDTO);
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


    public ActionBorrowDetailResponse borrowDetail(ActionBorrowDetailRequest request) {
        QueryWrapper<Reader> readerQueryWrapper = new QueryWrapper<>();
        readerQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        readerQueryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(readerQueryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在, 不支持借阅");
        }
        if (ReaderStatusEnum.S.name().equals(reader.getStatus())) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "该读者已经被停用, 不支持借阅");
        }
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq(Constants.ISBN, request.getIsbn());
        bookQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        if (CollectionUtils.isEmpty(bookList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍信息不存在, 不支持借阅");
        }
        if (bookList.size() > 1) {
            log.warn("存在多个相同书号书籍信息, isbn->{}", request.getIsbn());
        }
        Book book = bookList.get(0);
        if (BookStatusEnum.D.name().equals(book.getStatus())) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍已经下架, 不支持借阅");
        }
        ActionBorrowDetailResponse response = new ActionBorrowDetailResponse();
        response.setBookUniqueId(book.getBookUniqueId());
        response.setIsbn(book.getIsbn());
        response.setBookName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setPublisher(book.getPublisher());
        response.setPosition(book.getPosition());
        response.setReaderName(reader.getName());
        response.setReaderType(reader.getReaderType());
        return response;
    }

    @Transactional
    public void borrow(ActionBorrowRequest request) {
        QueryWrapper<Reader> readerQueryWrapper = new QueryWrapper<>();
        readerQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        readerQueryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(readerQueryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在, 不支持借阅");
        }
        if (ReaderStatusEnum.S.name().equals(reader.getStatus())) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "该读者已经被停用, 不支持借阅");
        }

        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.in(Constants.BOOK_UNIQUE_ID, request.getBookUniqueIdList());
        bookQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        if (CollectionUtils.isEmpty(bookList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍信息不存在, 不支持借阅");
        }
        if (bookList.size() != new HashSet<>(request.getBookUniqueIdList()).size()) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍状态发生变化, 请全部移除后重新扫描添加");
        }

        String notMatchBookInfo = null;
        for (Book book: bookList) {
            if (BookStatusEnum.D.name().equals(book.getStatus())) {
                notMatchBookInfo = book.getIsbn() + "-" + book.getName();
                break;
            }
        }
        if (StringUtils.isNotBlank(notMatchBookInfo)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍信息不存在或者已下架, 请移除后再提交, 书籍:" + notMatchBookInfo);
        }

        QueryWrapper<SysConfig> sysConfigQueryWrapper = new QueryWrapper<>();
        List<SysConfig> sysConfigList = sysConfigMapper.selectList(sysConfigQueryWrapper);
        if (CollectionUtils.isEmpty(sysConfigList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置数据为空");
        }
        SysConfig sysConfig = sysConfigList.get(0);
        if (sysConfig.getBorrowPeriod() == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置借阅期限为空");
        }

        if (sysConfig.getMaxBorrowCount() == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置借阅上限为空");
        }

        QueryWrapper<Action> actionQueryWrapper = new QueryWrapper<>();
        actionQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        actionQueryWrapper.eq(Constants.STATUS, ActionStatusEnum.R.name());
        actionQueryWrapper.eq(Constants.DEL_FLAG, false);
        Long alreadyBorrowCount = actionMapper.selectCount(actionQueryWrapper);
        if (alreadyBorrowCount == null) {
            alreadyBorrowCount = 0L;
        }
        if (sysConfig.getMaxBorrowCount() - alreadyBorrowCount.intValue() - request.getBookUniqueIdList().size() < 0) {
            throw new CommonException(CodeEnum.BIZ_ERROR,
                    "超过借阅上限"+sysConfig.getMaxBorrowCount()+"本, 只能借阅" + (sysConfig.getMaxBorrowCount() - alreadyBorrowCount.intValue()) + "本");
        }

        for (String bookUniqueId: request.getBookUniqueIdList()) {
            Action action = new Action();
            action.setActionUniqueId(CommonUtils.getUuid());
            action.setReaderUniqueId(request.getReaderUniqueId());
            action.setBookUniqueId(bookUniqueId);
            action.setStatus(ActionStatusEnum.R.name());
            action.setBorrowTime(new Date());
            Date endDateTime = DateUtils.addDays(new Date(), sysConfig.getBorrowPeriod() + 1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDateTime);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            action.setEndTime(calendar.getTime());
            action.setInsDt(new Date());
            action.setUpdDt(new Date());
            action.setInsPerson(UserInfoContainer.getUsername());
            action.setUpdPerson(UserInfoContainer.getUsername());
            action.setDelFlag(false);
            actionMapper.insert(action);
        }
    }

    public ActionBackDetailResponse backDetail(ActionBackDetailRequest request) {
        QueryWrapper<Reader> readerQueryWrapper = new QueryWrapper<>();
        readerQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        readerQueryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(readerQueryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在, 不支持归还");
        }
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq(Constants.ISBN, request.getIsbn());
        bookQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        if (CollectionUtils.isEmpty(bookList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍信息不存在, 不支持归还");
        }
        if (bookList.size() > 1) {
            log.warn("存在多个相同书号书籍信息, isbn->{}", request.getIsbn());
        }
        Book book = bookList.get(0);
        QueryWrapper<Action> actionQueryWrapper = new QueryWrapper<>();
        actionQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        actionQueryWrapper.eq(Constants.BOOK_UNIQUE_ID, book.getBookUniqueId());
        actionQueryWrapper.eq(Constants.STATUS, ActionStatusEnum.R.name());
        actionQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Action> actionList = actionMapper.selectList(actionQueryWrapper);
        if (CollectionUtils.isEmpty(actionList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "未查询到借阅信息, 不支持归还");
        }
        if (actionList.size() > 1) {
            log.warn("存在多个相同借阅信息, readerUniqueId->{}, isbn->{}", request.getReaderUniqueId(), request.getIsbn());
        }
        Action action = actionList.get(0);

        ActionBackDetailResponse response = new ActionBackDetailResponse();
        response.setBookUniqueId(book.getBookUniqueId());
        response.setIsbn(book.getIsbn());
        response.setBookName(book.getName());
        response.setBorrowTime(DateTimeUtils.dateToStr(action.getBorrowTime()));
        response.setEndTime(DateTimeUtils.dateToStr(action.getEndTime()));
        response.setOverdueInd(new Date().compareTo(action.getEndTime()) > 0
                ? ActionOverdueIndEnum.Y.name() : ActionOverdueIndEnum.N.name());
        response.setReaderName(reader.getName());
        response.setReaderType(reader.getReaderType());
        return response;


    }

    @Transactional
    public void back(ActionBackRequest request) {
        QueryWrapper<Reader> readerQueryWrapper = new QueryWrapper<>();
        readerQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        readerQueryWrapper.eq(Constants.DEL_FLAG, false);
        Reader reader = readerMapper.selectOne(readerQueryWrapper);
        if (reader == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "读者信息不存在, 不支持归还");
        }

        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.in(Constants.BOOK_UNIQUE_ID, request.getBookUniqueIdList());
        bookQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> bookList = bookMapper.selectList(bookQueryWrapper);
        if (CollectionUtils.isEmpty(bookList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍信息不存在, 不支持归还");
        }
        if (bookList.size() != new HashSet<>(request.getBookUniqueIdList()).size()) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "书籍状态发生变化, 请全部移除后重新扫描添加");
        }
        Map<String, Book> bookMap = new HashMap<>();
        for (Book book: bookList) {
            bookMap.put(book.getBookUniqueId(), book);
        }
        QueryWrapper<Action> actionQueryWrapper = new QueryWrapper<>();
        actionQueryWrapper.eq(Constants.READER_UNIQUE_ID, request.getReaderUniqueId());
        actionQueryWrapper.in(Constants.BOOK_UNIQUE_ID, request.getBookUniqueIdList());
        actionQueryWrapper.eq(Constants.STATUS, ActionStatusEnum.R.name());
        actionQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Action> actionList = actionMapper.selectList(actionQueryWrapper);

        List<String> actionUniqueIds = new ArrayList<>();
        for (String bookUniqueId: request.getBookUniqueIdList()) {
            Book book = bookMap.get(bookUniqueId);
            if (CollectionUtils.isEmpty(actionList)) {
                if (book == null) {
                    throw new CommonException(CodeEnum.BIZ_ERROR, "未查询到借阅信息, 不支持归还");
                }
                throw new CommonException(CodeEnum.BIZ_ERROR, "未查询到借阅信息, 书号:" + book.getName());
            }
            Action targetAction = null;
            for (Action action: actionList) {
                if (actionUniqueIds.contains(action.getActionUniqueId())) {
                    continue;
                }
                targetAction = action;
                actionUniqueIds.add(action.getActionUniqueId());
                break;
            }
            if (targetAction == null) {
                throw new CommonException(CodeEnum.BIZ_ERROR, "未查询到借阅信息或者归还数量超过借阅数量, 不支持归还, 书号:" + book.getIsbn());
            }
            targetAction.setStatus(ActionStatusEnum.B.name());
            targetAction.setBackTime(new Date());
            targetAction.setUpdPerson(UserInfoContainer.getUsername());
            targetAction.setUpdDt(new Date());
            actionMapper.updateById(targetAction);
        }
    }

    public ActionListResponse initPageResponse(ActionListDTO actionListDTO) {
        ActionListResponse actionListResponse = new ActionListResponse();
        actionListResponse.setIsbn(actionListDTO.getIsbn());
        actionListResponse.setBookName(actionListDTO.getBookName());
        actionListResponse.setReaderName(actionListDTO.getReaderName());
        actionListResponse.setReaderType(actionListDTO.getReaderType());
        actionListResponse.setGradeClass(actionListDTO.getGradeClass());
        actionListResponse.setStudentNum(actionListDTO.getStudentNum());
        actionListResponse.setStatus(actionListDTO.getStatus());
        if (actionListDTO.getBorrowTime() != null) {
            actionListResponse.setBorrowTime(DateTimeUtils.dateToStr(actionListDTO.getBorrowTime()));
        }
        if (actionListDTO.getEndTime() != null) {
            actionListResponse.setEndTime(DateTimeUtils.dateToStr(actionListDTO.getEndTime()));
        }
        if (actionListDTO.getBackTime() != null) {
            actionListResponse.setBackTime(DateTimeUtils.dateToStr(actionListDTO.getBackTime()));
        }
        actionListResponse.setOverdueInd((actionListDTO.getEndTime() != null && new Date().compareTo(actionListDTO.getEndTime()) > 0)
                ? ActionOverdueIndEnum.Y.name() : ActionOverdueIndEnum.N.name());
        actionListResponse.setBookUniqueId(actionListDTO.getBookUniqueId());
        actionListResponse.setReaderUniqueId(actionListDTO.getReaderUniqueId());
        return actionListResponse;
    }
}
