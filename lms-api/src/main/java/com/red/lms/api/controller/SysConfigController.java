package com.red.lms.api.controller;

import com.red.lms.api.aspect.ExtractUsertAuth;
import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.SysConfigDetailResponse;
import com.red.lms.common.model.SysConfigEditRequest;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.SysConfigService;
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
@Api(value = "系统配置", tags = "系统配置")
@RequestMapping("/api/sysconfig")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;
    @ExtractUsertAuth
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询自定义参数")
    public RestResponse<SysConfigDetailResponse> query() throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(sysConfigService.query());
        return restResponse;
    }
    @ExtractUsertAuth
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "保存自定义参数")
    public RestResponse edit(@Valid @RequestBody SysConfigEditRequest sysConfigEditRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        sysConfigService.edit(sysConfigEditRequest);
        return restResponse;
    }
}
