package com.red.lms.service;

import com.red.lms.common.config.AdminDataProperties;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.LoginRequest;
import com.red.lms.common.model.LoginResponse;
import com.red.lms.common.utils.CommonUtils;
import com.red.lms.common.utils.EncryptUtils;
import com.red.lms.service.token.TokenCacheService;
import com.red.lms.service.token.UserInfoContainer;
import org.apache.commons.lang.StringUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private TokenCacheService tokenCacheService;

    @Autowired
    private AdminDataProperties adminDataProperties;

    @Value("${jasypt.encryptor.password}")
    private String jasyptPassword;


    public LoginResponse login(LoginRequest request) {
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
            throw new CommonException(CodeEnum.USER_PASS_NOT_EMPTY);
        }
        Map<String, String> adminDataMap = adminDataProperties.getEnums();
        if (adminDataMap.containsKey(request.getUsername())
                && EncryptUtils.getMD5(request.getPassword()).equals(adminDataMap.get(request.getUsername()))) {
            //返回token
            LoginResponse response = new LoginResponse();
            String token = CommonUtils.getUuid();
            tokenCacheService.addToken(request.getUsername(), token);
            BasicTextEncryptor encryptor = new BasicTextEncryptor();
            encryptor.setPassword(jasyptPassword);
            response.setToken(encryptor.encrypt(request.getUsername() + "|" + token));
            return response;
        } else {
            throw new CommonException(CodeEnum.LOGIN_FAILED);
        }
    }

    public void logout() {
        if (UserInfoContainer.getUserLoginInfo() == null
                || StringUtils.isBlank(UserInfoContainer.getUserLoginInfo().getUsername())) {
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }
        tokenCacheService.removeToken(UserInfoContainer.getUserLoginInfo().getUsername());
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtils.getMD5("123456"));
    }

}
