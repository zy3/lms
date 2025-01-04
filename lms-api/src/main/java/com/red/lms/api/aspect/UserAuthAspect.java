package com.red.lms.api.aspect;

import com.red.lms.common.constants.Constants;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.base.UserLoginInfo;
import com.red.lms.service.token.TokenCacheService;
import com.red.lms.service.token.UserInfoContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Aspect
@Component
public class UserAuthAspect implements Ordered {

    @Value("${jasypt.encryptor.password}")
    private String jasyptPassword;

    @Value("${isAuth}")
    private boolean isAuth;

    @Autowired
    private TokenCacheService tokenCacheService;

    @Pointcut("@annotation(com.red.lms.api.aspect.ExtractUsertAuth)")
    public void annotation() {
    }

    @Before("annotation()")
    public void extract(JoinPoint joinPoint) {
        if (!isAuth) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(jasyptPassword);
        String splitToken = null;
        try {
            splitToken = encryptor.decrypt(token);
        } catch (Exception e) {
            log.error("token解密失败, token->{}", token, e);
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }

        if (StringUtils.isBlank(splitToken)) {
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }
        String[] splitTokenArr = splitToken.split("\\|");
        if (splitTokenArr == null || splitTokenArr.length != 2) {
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }
        boolean isSuccess = tokenCacheService.checkToken(splitTokenArr[0], splitTokenArr[1]);
        if (!isSuccess) {
            throw new CommonException(CodeEnum.LOGIN_TIMEOUT);
        }
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUsername(splitTokenArr[0]);
        UserInfoContainer.put(userLoginInfo);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
