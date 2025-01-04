package com.red.lms.common.utils;

import com.red.lms.common.constants.Constants;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import java.util.regex.Pattern;

public class VaildUtils {
    /**
     * 校验手机号
     *
     * @param cellphone
     * @return
     */
    public static boolean isMatchCellphone(String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            return Pattern.matches(Constants.VALID_PATTERN_REG_PHONE, cellphone);
        }
        return false;
    }

    /**
     * 校验身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean isMatchIdCard(String idCard) {
        if (StringUtils.isNotBlank(idCard)) {
            return Pattern.matches(Constants.VALID_PATTERN_ID_CARD, idCard);
        }
        return false;
    }

    /**
     * 校验姓名
     *
     * @param name
     * @return
     */
    public static boolean isMatchName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return Pattern.matches(Constants.VALID_PATTERN_NAME, name);
        }
        return false;
    }

    /**
     * 校验银行卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isMatchCardNo(String cardNo) {
        if (StringUtils.isNotBlank(cardNo)) {
            return Pattern.matches(Constants.VALID_PATTERN_CARD_NO, cardNo);
        }
        return false;
    }

    /**
     * 对象校验
     *
     * @param obj
     * @return
     */
    public static String checkObj(Object obj) {
        Set<ConstraintViolation<Object>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if (validate != null) {
            StringBuilder validMsg = new StringBuilder();
            for (ConstraintViolation<Object> constraintViolation : validate) {
                if (StringUtils.isBlank(constraintViolation.getMessage())) {
                    continue;
                }
                validMsg.append(constraintViolation.getMessage() + " ");
            }
            return validMsg.toString().trim();
        }
        return null;
    }
}
