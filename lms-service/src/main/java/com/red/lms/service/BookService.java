package com.red.lms.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.lms.common.constants.Constants;
import com.red.lms.common.enums.ActionStatusEnum;
import com.red.lms.common.enums.BookStatusEnum;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.utils.CommonUtils;
import com.red.lms.common.utils.DateTimeUtils;
import com.red.lms.dal.entity.Action;
import com.red.lms.dal.entity.Book;
import com.red.lms.dal.mapper.ActionMapper;
import com.red.lms.dal.mapper.BookMapper;
import com.red.lms.service.token.UserInfoContainer;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Slf4j
@Service
public class BookService implements InitializingBean {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ActionMapper actionMapper;

    @Value("${data.path}")
    private String dataPath;

    @Value("${tess.basic.config.path}")
    private String tessBasicConfigPath;

    private static final String TESSFILE_PATH = "/tessfile/";

    private ITesseract tesseract = null;

    public void add(BookAddRequest request) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.ISBN, request.getIsbn());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> existBookList = bookMapper.selectList(queryWrapper);
        Book book = null;
        if (!CollectionUtils.isEmpty(existBookList) && existBookList.size() >= 1) {
            if (existBookList.size() > 1) {
                log.warn("存在多本相同书号的书籍, isbn->", request.getIsbn());
            }
            book = existBookList.get(0);
        } else {
            book = new Book();
        }
        book.setIsbn(request.getIsbn());
        book.setPosition(request.getPosition());
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setTranslator(request.getTranslator());
        book.setPublisher(request.getPublisher());
        book.setEdition(request.getEdition());
        book.setNameIndex(request.getNameIndex());
        book.setAuthorIndex(request.getAuthorIndex());
        book.setTopicIndex(request.getTopicIndex());
        book.setClassify(request.getClassify());
        book.setCipNum(request.getCipNum());
        if (StringUtils.isBlank(book.getStatus())) {
            book.setStatus(BookStatusEnum.N.name());
        }
        if (StringUtils.isNotBlank(book.getBookUniqueId())) {
            int existCount = (book.getCounts() == null ? 0 : book.getCounts());
            book.setCounts(existCount + request.getCounts());
            book.setUpdDt(new Date());
            book.setUpdPerson(UserInfoContainer.getUsername());
            bookMapper.updateById(book);
        } else {
            book.setBookUniqueId(CommonUtils.getUuid());
            book.setInsDt(new Date());
            book.setUpdDt(new Date());
            book.setInsPerson(UserInfoContainer.getUsername());
            book.setUpdPerson(UserInfoContainer.getUsername());
            book.setCounts(request.getCounts());
            book.setDelFlag(false);
            bookMapper.insert(book);
        }
    }


    public void edit(BookEditRequest request) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.BOOK_UNIQUE_ID, request.getBookUniqueId());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Book book = bookMapper.selectOne(queryWrapper);
        if (book == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "需要修改的书籍信息不存在");
        }
        QueryWrapper<Book> isbnQueryWrapper = new QueryWrapper<>();
        isbnQueryWrapper.eq(Constants.ISBN, request.getIsbn());
        isbnQueryWrapper.eq(Constants.DEL_FLAG, false);
        List<Book> existBookList = bookMapper.selectList(isbnQueryWrapper);
        if (!CollectionUtils.isEmpty(existBookList)) {
            for (Book existBook : existBookList) {
                if (existBook.getBookUniqueId().equals(book.getBookUniqueId())) {
                    continue;
                }
                throw new CommonException(CodeEnum.BIZ_ERROR, "存在多本相同书号的书籍");
            }
        }
        book.setIsbn(request.getIsbn());
        book.setPosition(request.getPosition());
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setTranslator(request.getTranslator());
        book.setPublisher(request.getPublisher());
        book.setEdition(request.getEdition());
        book.setNameIndex(request.getNameIndex());
        book.setAuthorIndex(request.getAuthorIndex());
        book.setTopicIndex(request.getTopicIndex());
        book.setClassify(request.getClassify());
        book.setCipNum(request.getCipNum());
        book.setCounts(request.getCounts());
        book.setUpdDt(new Date());
        book.setUpdPerson(UserInfoContainer.getUsername());
        bookMapper.updateById(book);
    }


    public void status(BookStatusRequest request) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.BOOK_UNIQUE_ID, request.getBookUniqueId());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Book book = bookMapper.selectOne(queryWrapper);
        if (book == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "需要修改的书籍信息不存在");
        }
        book.setStatus(request.getStatus());
        book.setUpdDt(new Date());
        book.setUpdPerson(UserInfoContainer.getUsername());
        bookMapper.updateById(book);
    }

    public BookDetailResponse detail(BookDetailRequest request) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Constants.BOOK_UNIQUE_ID, request.getBookUniqueId());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        Book book = bookMapper.selectOne(queryWrapper);
        if (book == null) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "需要修改的书籍信息不存在");
        }
        BookDetailResponse response = new BookDetailResponse();
        response.setBookUniqueId(book.getBookUniqueId());
        response.setIsbn(book.getIsbn());
        response.setPosition(book.getPosition());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setTranslator(book.getTranslator());
        response.setPublisher(book.getPublisher());
        response.setEdition(book.getEdition());
        response.setNameIndex(book.getNameIndex());
        response.setAuthorIndex(book.getAuthorIndex());
        response.setTopicIndex(book.getTopicIndex());
        response.setClassify(book.getClassify());
        response.setCipNum(book.getCipNum());
        response.setCipNum(book.getCipNum());
        response.setCounts(book.getCounts());
        return response;
    }

    public PageResult<BookListResponse> list(BookListRequest request) {
        Page<Book> rowPage = new Page(request.getPageNo(), request.getPageSize());
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getName())) {
            queryWrapper.like(Constants.NAME, request.getName());
        }
        if (StringUtils.isNotBlank(request.getPosition())) {
            queryWrapper.eq(Constants.POSITION, request.getPosition());
        }
        if (StringUtils.isNotBlank(request.getIsbn())) {
            queryWrapper.like(Constants.ISBN, request.getIsbn());
        }
        if (StringUtils.isNotBlank(request.getAuthor())) {
            queryWrapper.like(Constants.AUTHOR, request.getAuthor());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            queryWrapper.eq(Constants.STATUS, request.getStatus());
        }
        queryWrapper.eq(Constants.DEL_FLAG, false);
        queryWrapper.orderByDesc(Constants.UPD_DT);
        rowPage = bookMapper.selectPage(rowPage, queryWrapper);
        PageResult<BookListResponse> pageResult = new PageResult<>();
        if (!CollectionUtils.isEmpty(rowPage.getRecords())) {
            List<BookListResponse> responseList = new ArrayList<>();
            Set<String> bookUniqueIds = new HashSet<>();
            for (Book book : rowPage.getRecords()) {
                bookUniqueIds.add(book.getBookUniqueId());
                BookListResponse response = initPageResponse(book);
                responseList.add(response);
            }
            Map<String, Integer> borrowCountMap = queryBorrowCount(bookUniqueIds);
            for (BookListResponse response : responseList) {
                Integer borrowCount = borrowCountMap.get(response.getBookUniqueId());
                if (borrowCount == null) {
                    borrowCount = 0;
                }
                response.setLibCounts(response.getCounts() - borrowCount);
            }
            pageResult.setRows(responseList);
        }
        pageResult.setPageSize(rowPage.getSize());
        pageResult.setPages(rowPage.getPages());
        pageResult.setPageNo(rowPage.getCurrent());
        pageResult.setTotal(rowPage.getTotal());
        return pageResult;
    }

    private Map<String, Integer> queryBorrowCount(Set<String> bookUniqueIds) {
        Map<String, Integer> borrowCountMap = new HashMap<>();
        if (CollectionUtils.isEmpty(bookUniqueIds)) {
            return borrowCountMap;
        }
        QueryWrapper<Action> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Constants.BOOK_UNIQUE_ID, bookUniqueIds);
        queryWrapper.eq(Constants.STATUS, ActionStatusEnum.R.name());
        queryWrapper.eq(Constants.DEL_FLAG, false);
        List<Action> actionList = actionMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(actionList)) {
            for (Action action : actionList) {
                Integer count = borrowCountMap.get(action.getBookUniqueId());
                if (count == null) {
                    count = 0;
                }
                count = count + 1;
                borrowCountMap.put(action.getBookUniqueId(), count);
            }
        }
        return borrowCountMap;
    }

    public PageResult<BookSearchResponse> search(BookSearchRequest request) {
        Page<Book> rowPage = new Page(request.getPageNo(), request.getPageSize());
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(request.getName())) {
            queryWrapper.like(Constants.NAME, request.getName());
        }
        if (StringUtils.isNotBlank(request.getPosition())) {
            queryWrapper.eq(Constants.POSITION, request.getPosition());
        }
        if (StringUtils.isNotBlank(request.getIsbn())) {
            queryWrapper.like(Constants.ISBN, request.getIsbn());
        }
        if (StringUtils.isNotBlank(request.getAuthor())) {
            queryWrapper.like(Constants.AUTHOR, request.getAuthor());
        }
        queryWrapper.eq(Constants.DEL_FLAG, false);
        queryWrapper.orderByDesc(Constants.UPD_DT);
        rowPage = bookMapper.selectPage(rowPage, queryWrapper);
        PageResult<BookSearchResponse> pageResult = new PageResult<>();
        if (!CollectionUtils.isEmpty(rowPage.getRecords())) {
            List<BookSearchResponse> responseList = new ArrayList<>();
            Set<String> bookUniqueIds = new HashSet<>();
            for (Book book : rowPage.getRecords()) {
                bookUniqueIds.add(book.getBookUniqueId());
                BookSearchResponse response = initPageSearchResponse(book);
                responseList.add(response);
            }
            Map<String, Integer> borrowCountMap = queryBorrowCount(bookUniqueIds);
            for (BookSearchResponse response : responseList) {
                Integer borrowCount = borrowCountMap.get(response.getBookUniqueId());
                if (borrowCount == null) {
                    borrowCount = 0;
                }
                response.setLibCounts(response.getCounts() - borrowCount);
            }
            pageResult.setRows(responseList);
        }
        pageResult.setPageSize(rowPage.getSize());
        pageResult.setPages(rowPage.getPages());
        pageResult.setPageNo(rowPage.getCurrent());
        pageResult.setTotal(rowPage.getTotal());
        return pageResult;
    }

//    public BookDiscernResponse discern(MultipartFile file) {
//        //TODO 先用模拟数据
//        BookDiscernResponse response = new BookDiscernResponse();
//        response.setIsbn(System.currentTimeMillis() + "");
//        response.setName("测试书籍" + DateTimeUtils.dateToStr(new Date()));
//        response.setAuthor("测试作者");
//        response.setTranslator("测试译者");
//        response.setPublisher("测试出版单位");
//        response.setEdition("2020.01");
//        response.setNameIndex("测1");
//        response.setAuthor("测2");
//        response.setAuthorIndex("测3");
//        response.setClassify("A001");
//        response.setCipNum("20200101");
//        return response;
//    }

    public BookDiscernResponse discern(MultipartFile file) {
        //将文件写入到本地
        File localFile = new File(dataPath + TESSFILE_PATH +
                CommonUtils.getUuid() +"."+ file.getOriginalFilename().split("\\.")[1]);
        //开始识别
        BookDiscernResponse response = new BookDiscernResponse();
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
            String result = tesseract.doOCR(localFile);
            if (StringUtils.isBlank(result)) {
                throw new CommonException(CodeEnum.BIZ_ERROR, "识别图片失败, 重拍或者手动输入");
            }
            String[] resultArr = result.split("\n");
            List<String> lines = new ArrayList<>();
            for (String line : resultArr) {
                String completeLine = line.replaceAll("\\s", "");
                if (completeLine.contains("图书在") && completeLine.contains("CIP")) {
                    lines.add(completeLine);
                } else {
                    lines.add(completeLine);
                }
                if (completeLine.contains("核字")) {
                    break;
                }
            }
            for (String line : lines) {
                String completeLine = line.replaceAll("\\s", "");
                if (completeLine.contains("图书在") && completeLine.contains("CIP")) {
                    continue;
                }
                if (completeLine.contains("/") && completeLine.contains("著")) {
                    String delimiters = "[/;,。]+";
                    String[] splitWrodArr = completeLine.split(delimiters);
                    System.out.println(JSON.toJSONString(splitWrodArr, true));
                    if (response.getName() == null) {
                        response.setName(splitWrodArr[0]);
                    }
                    for (String splitWord : splitWrodArr) {
                        if (splitWord.contains("编著") && response.getAuthor() == null) {
                            response.setAuthor(splitWord.split("编著")[0]);
                            continue;
                        }
                        if (splitWord.contains("著") && response.getAuthor() == null) {
                            response.setAuthor(splitWord.split("著")[0]);
                            continue;
                        }
                        if (splitWord.endsWith("译") && response.getTranslator() == null) {
                            response.setTranslator(splitWord.replaceAll("译", ""));
                        }
                    }
                    continue;
                }
                if (completeLine.contains("/")) {
                    String delimiters = "[/;,。]+";
                    String[] splitWrodArr = completeLine.split(delimiters);
                    if (response.getName() == null) {
                        response.setName(splitWrodArr[0]);
                    }
                    continue;
                }
                if (completeLine.toLowerCase().contains("sbn")) {
                    StringBuilder digits = new StringBuilder();
                    for (String part : completeLine.split("\\D+")) {  // \\D+ 匹配非数字字符
                        if (!part.isEmpty()) {
                            digits.append(part);
                        }
                    }
                    if (StringUtils.isNotBlank(digits.toString()) && response.getIsbn() == null) {
                        response.setIsbn(digits.toString());
                    }
                }
            }
            if (StringUtils.isNotBlank(response.getName())) {
                response.setNameIndex(response.getName().substring(0, 1));
            }
            if (StringUtils.isNotBlank(response.getAuthor())) {
                if (response.getAuthor().contains(")")) {
                    response.setAuthorIndex(response.getAuthor().split("\\)")[1].substring(0, 1));
                } else {
                    response.setAuthorIndex(response.getAuthor().substring(0, 1));
                }
            }
            if (StringUtils.isNotBlank(response.getIsbn())) {
                QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
                bookQueryWrapper.eq(Constants.ISBN, response.getIsbn());
                bookQueryWrapper.eq(Constants.DEL_FLAG, false);
                Book book = bookMapper.selectOne(bookQueryWrapper);
                if (book != null) {
                    response.setName(book.getName());
                    response.setAuthor(book.getAuthor());
                    response.setTranslator(book.getTranslator());
                    response.setPublisher(book.getPublisher());
                    response.setEdition(book.getEdition());
                    response.setNameIndex(book.getNameIndex());
                    response.setAuthorIndex(book.getAuthorIndex());
                    response.setTopicIndex(book.getTopicIndex());
                    response.setClassify(book.getClassify());
                    response.setCipNum(book.getCipNum());
                }
            }
        } catch (Exception e) {
            log.error("识别图片失败", e);
            throw new CommonException(CodeEnum.BIZ_ERROR, "识别图片失败, 重拍或者手动输入");
        } finally {
            if (localFile.exists()) {
                localFile.delete();
            }
        }
        return response;
    }

    private BookListResponse initPageResponse(Book book) {
        BookListResponse response = new BookListResponse();
        response.setBookUniqueId(book.getBookUniqueId());
        response.setIsbn(book.getIsbn());
        response.setPosition(book.getPosition());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setTranslator(book.getTranslator());
        response.setPublisher(book.getPublisher());
        response.setEdition(book.getEdition());
        response.setNameIndex(book.getNameIndex());
        response.setAuthorIndex(book.getAuthorIndex());
        response.setTopicIndex(book.getTopicIndex());
        response.setClassify(book.getClassify());
        response.setCipNum(book.getCipNum());
        response.setCounts(book.getCounts());
        response.setStatus(book.getStatus());
        return response;
    }


    private BookSearchResponse initPageSearchResponse(Book book) {
        BookSearchResponse response = new BookSearchResponse();
        response.setBookUniqueId(book.getBookUniqueId());
        response.setIsbn(book.getIsbn());
        response.setPosition(book.getPosition());
        response.setName(book.getName());
        response.setAuthor(book.getAuthor());
        response.setTranslator(book.getTranslator());
        response.setPublisher(book.getPublisher());
        response.setEdition(book.getEdition());
        response.setClassify(book.getClassify());
        response.setCipNum(book.getCipNum());
        response.setCounts(book.getCounts());
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        tesseract = new Tesseract();
        tesseract.setDatapath(tessBasicConfigPath);  // 设置tessdata路径
        tesseract.setLanguage("chi_sim");
    }
}
