package com.red.lms.api.controller;

import com.red.lms.api.aspect.ExtractUsertAuth;
import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.ReaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "读者", tags = "读者")
@RequestMapping("/api/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @ExtractUsertAuth
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询读者列表（分页）")
    public RestResponse<PageResult<ReaderListResponse>> list(@Valid @RequestBody ReaderListRequest readerListRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(readerService.list(readerListRequest));
        return restResponse;
    }

    @ExtractUsertAuth
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "添加读者")
    public RestResponse add(@Valid @RequestBody ReaderAddRequest readerAddRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        readerService.add(readerAddRequest);
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询读者详情")
    public RestResponse<ReaderDetailResponse> detail(@Valid @RequestBody ReaderDetailRequest readerDetailRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(readerService.detail(readerDetailRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "保存读者信息")
    public RestResponse edit(@Valid @RequestBody ReaderEditRequest readerEditRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        readerService.edit(readerEditRequest);
        return restResponse;
    }

    @ExtractUsertAuth
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "停用、启用读者")
    public RestResponse status(@Valid @RequestBody ReaderStatusRequest readerStatusRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        readerService.status(readerStatusRequest);
        return restResponse;
    }
}
