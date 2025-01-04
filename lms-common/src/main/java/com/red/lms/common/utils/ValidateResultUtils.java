package com.red.lms.common.utils;

import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidateResultUtils {

    public static void process(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for (int i = 0; i < fieldErrorList.size(); i++) {
                String message = fieldErrorList.get(i).getDefaultMessage();
                builder.append(message);
                if (i != fieldErrorList.size()) {
                    builder.append(";");
                }
            }
            String errorMessage = builder.toString();
            if (StringUtils.isNotBlank(errorMessage)) {
                throw new CommonException(CodeEnum.PARAM_ERROR, errorMessage);
            }
        }
    }
}
