package com.red.lms.common.exceptions;

import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.model.base.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public RestResponse defaultErrorHandler(HttpServletRequest reguest, HttpServletResponse response, Exception e) {
        if (e instanceof CommonException) {
            if (CodeEnum.SUCCESS != CodeEnum.findByCode(((CommonException) e).getCode())) {
                log.warn("common error", e);
            }
            return RestResponse.builder().build().failCommonException((CommonException) e);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            if (validException != null && validException.getBindingResult() != null) {
                List<ObjectError> errors = validException.getBindingResult().getAllErrors();
                if (!CollectionUtils.isEmpty(errors)) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (ObjectError error : errors) {
                        errorMessage.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage() + " ");
                    }
                    return RestResponse.builder().build().fail(CodeEnum.PARAM_ERROR, errorMessage.toString().trim());
                }
            }
            log.error("param error", e);
            return RestResponse.builder().build().fail(CodeEnum.PARAM_ERROR);
        }
        log.error("system error", e);
        return RestResponse.builder().build().fail(CodeEnum.SYSTEM_ERROR);
    }
}
