package com.red.lms.common.constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RedisKeyConstants {
    private static final String APP_NAME = "credit";

    private static final String VERFIY_SERIAL = "verfiySerial";

    private static final String MESSAGE_COUNT = "messageCount";

    private static final String CONTACT_COUNT = "contactCount";

    private static final String ACCESS_TOKEN = "accessToken";

    private static final String USER_TOKEN = "userToken";

    private static final String DOSSIER_PUBLISH = "dossierPublish";

    private static final String DOSSIER_PUBLISH_COUNT = "dossierPublishCount";

    public static String generateMessageCountKey(String cellphoneFlag) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String nowDate = formatter.format(new Date());
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                MESSAGE_COUNT + Constants.SEPARATE_UNDER_LINE
                + cellphoneFlag + Constants.SEPARATE_UNDER_LINE + nowDate;
    }


    public static String generateContactCountKey(String redisKey) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String nowDate = formatter.format(new Date());
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                CONTACT_COUNT + Constants.SEPARATE_UNDER_LINE
                + redisKey + Constants.SEPARATE_UNDER_LINE + nowDate;
    }

    public static String generateAccessTokenKey() {
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                ACCESS_TOKEN;
    }

    public static String generateUserTokenKey(String custUniqueId) {
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                USER_TOKEN + Constants.SEPARATE_UNDER_LINE
                + custUniqueId;
    }

    public static String generateDossierPublishKey(String custUniqueId) {
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                DOSSIER_PUBLISH + Constants.SEPARATE_UNDER_LINE
                + custUniqueId;
    }

    public static String generateDossierPublishCountKey(String custUniqueId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String nowDate = formatter.format(new Date());
        return APP_NAME + Constants.SEPARATE_UNDER_LINE +
                DOSSIER_PUBLISH_COUNT + Constants.SEPARATE_UNDER_LINE
                + custUniqueId + Constants.SEPARATE_UNDER_LINE + nowDate;
    }
}
