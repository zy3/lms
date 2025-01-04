package com.red.lms.api.controller;

import com.red.lms.api.aspect.ExtractUsertAuth;
import com.red.lms.common.constants.HttpMethodConstant;
import com.red.lms.common.model.LoginRequest;
import com.red.lms.common.model.LoginResponse;
import com.red.lms.common.model.base.RestResponse;
import com.red.lms.service.AuthService;
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
@Api(value = "权限", tags = "权限")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "用户登录")
    public RestResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        restResponse.success(authService.login(loginRequest));
        return restResponse;
    }

    @ExtractUsertAuth
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(httpMethod = HttpMethodConstant.POST, value = "用户退出")
    public RestResponse logout() throws Exception {
        RestResponse restResponse = RestResponse.builder().build();
        authService.logout();
        return restResponse;
    }


}
