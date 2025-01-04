package com.red.lms.api.controller;

import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.EnumsResponse;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.PublicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(value = "公共", tags = "公共")
@RequestMapping("/api/public")
public class PublicController {
    @Autowired
    private PublicService publicService;

    @RequestMapping(value = "/queryAllEnums", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "查询枚举参数")
    public RestResponse<EnumsResponse> queryAllEnums() throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(publicService.queryAllEnums());
        return restResponse;
    }
}
