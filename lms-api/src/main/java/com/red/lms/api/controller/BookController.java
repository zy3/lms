package com.red.lms.api.controller;

import com.red.lms.api.aspect.ExtractUsertAuth;
import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "书籍", tags = "书籍")
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ExtractUsertAuth
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询书籍列表（分页）")
    public RestResponse<PageResult<BookListResponse>> list(@Valid @RequestBody BookListRequest bookListRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(bookService.list(bookListRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/discern", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "图片识别（识别CIP数据）")
    public RestResponse<BookDiscernResponse> discern(@RequestParam("file") MultipartFile file) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(bookService.discern(file));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "添加书籍")
    public RestResponse add(@Valid @RequestBody BookAddRequest bookAddRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        bookService.add(bookAddRequest);
        return restResponse;
    }

    @ExtractUsertAuth
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "书籍详情")
    public RestResponse<BookDetailResponse> detail(@Valid @RequestBody BookDetailRequest bookDetailRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(bookService.detail(bookDetailRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "编辑书籍")
    public RestResponse edit(@Valid @RequestBody BookEditRequest bookEditRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        bookService.edit(bookEditRequest);
        return restResponse;
    }

    @ExtractUsertAuth
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "更改书籍状态")
    public RestResponse status(@Valid @RequestBody BookStatusRequest bookStatusRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        bookService.status(bookStatusRequest);
        return restResponse;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "书籍列表查询（分页且无登录）")
    public RestResponse<PageResult<BookSearchResponse>> search(@Valid @RequestBody BookSearchRequest bookSearchRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(bookService.search(bookSearchRequest));
        return restResponse;
    }
}
