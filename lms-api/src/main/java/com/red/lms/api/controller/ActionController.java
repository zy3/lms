package com.red.lms.api.controller;

import com.red.lms.api.aspect.ExtractUsertAuth;
import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.*;
import com.red.lms.common.model.base.PageResult;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.ActionService;
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
@Api(value = "借还", tags = "借还")
@RequestMapping("/api/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @ExtractUsertAuth
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "借还列表查询（分页）")
    public RestResponse<PageResult<ActionListResponse>> list(@Valid @RequestBody ActionListRequest actionListRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(actionService.list(actionListRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/borrow/detail", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询借阅信息（扫描枪扫描查询）")
    public RestResponse<ActionBorrowDetailResponse> borrowDetail(@Valid @RequestBody ActionBorrowDetailRequest actionBorrowDetailRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(actionService.borrowDetail(actionBorrowDetailRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "借阅提交")
    public RestResponse borrow(@Valid @RequestBody ActionBorrowRequest actionBorrowRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        actionService.borrow(actionBorrowRequest);
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/back/detail", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询归还信息（扫描枪扫描查询）")
    public RestResponse<ActionBackDetailResponse> backDetail(@Valid @RequestBody ActionBackDetailRequest actionBackDetailRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(actionService.backDetail(actionBackDetailRequest));
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/back", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "归还提交")
    public RestResponse back(@Valid @RequestBody ActionBackRequest actionBackRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        actionService.back(actionBackRequest);
        return restResponse;
    }
}
