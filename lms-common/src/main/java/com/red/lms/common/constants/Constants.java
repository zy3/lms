package com.red.lms.common.constants;

public class Constants {
    public static final String ALIYUN_SYS_VERSION = "2017-05-25";

    public static final String ALIYUN_RESPONSE_OK = "OK";

    public static final String SEPARATE_UNDER_LINE = "_";

    //验证码有效期
    public static final long EXPIRE_VERFICATION_MSG = 2;
    //短信发送计数统计时间范围1田
    public static final long EXPIRE_MESSAGE_COUNT = 1440;
    //校验手机号
    public static final String VALID_PATTERN_REG_PHONE = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$";

    //校验身份证号
    public static final String VALID_PATTERN_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";

    //校验姓名
    public static final String VALID_PATTERN_NAME = "^[\\u4E00-\\u9FA5]{2,10}(·[\\u4E00-\\u9FA5]{2,10}){0,2}$";

    /**
     * 校验银行卡号
     */
    public static final String VALID_PATTERN_CARD_NO = "^([1-9]{1})(\\d{15}|\\d{16}|\\d{18})$";

    //手机号
    public static final String CELLPHONE = "cellphone";
    //名称
    public static final String NAME = "name";
    //编码
    public static final String CODE = "code";
    //描述
    public static final String DESCRIPTION = "description";

    public static final String DEL_FLAG = "del_flag";

    public static final String TOKEN = "token";

    public static final String PIC = "PIC";
    public static final String FILE = "FILE";

    public static final String ORDERY_BY_ORDER_NO_ASC = "order_no asc ";
    public static final String ORDERY_BY_PIC_NO_ASC = "pic_no asc ";
    public static final String ORDERY_BY_COMPLETE_TIME_DESC = "complete_time desc ";
    public static final String ORDERY_BY_RELEASE_TIME_DESC = "release_time desc ";
    public static final String ORDERY_BY_RAND = "RAND() asc ";
    public static final String ORDERY_BY_UPD_DT = "upd_dt desc ";
    public static final String CHAT_UPD_TIME = "chat_update_time ";
    public static final String ORDERY_BY_SEND_TIME = "send_time desc ";
    public static final String ORDERY_BY_CREATE_TIME = "create_time desc ";


    public static final String CODE_CATEGORY = "codeCategory";
    public static final String CODE_SUPER = "codeSuper";

    public static final String CODE_CUSTOMER = "codeCustomer";
    public static final String CODE_CARGO = "codeCargo";
    public static final String STATUS = "status";
    public static final String SHOW_STATUS = "showStatus";


    public static final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;

    public static final long TEN_MINUTES = 600000;

    public static final String TP = "tp";

    public static final String CUST_UNIQUE_ID = "cust_unique_id";
    public static final String SEX = "sex";
    public static final String HEIGHT = "height";
    public static final String BIRTH = "birth";
    public static final String COMPANY_TYPE = "companyType";
    public static final String EDUCATION = "education";
    public static final String INCOME = "income";
    public static final String DOSSIER_UNIQUE_ID = "dossierUniqueId";
    public static final String LIVE_PROVICE = "liveProvice";
    public static final String LIVE_CITY = "live_city";
    public static final int ACCURATE_SCOR = 60;
    public static final String PIC_UNIQUE_ID = "picUniqueId";
    public static final String CUST_BLACK_UNIQUE_ID = "custBlackUniqueId";
    public static final String CUST_FROM_UNIQUE_ID = "cust_from_unique_id";
    public static final String CUST_TO_UNIQUE_ID = "cust_to_unique_id";
    public static final String CHAT_UNIQUE_ID = "chat_unique_id";
    public static final String SEND_TIME = "send_time";
    public static final String PAY_RECORD_UNIQUE_ID = "payRecordUniqueId";
    public static final String CREATE_TIME = "createTime";
    public static final String INS_DT = "insDt";
    public static final String CARD_NO = "card_no";
    public static final String IS_MAIN = "is_main";

    public static final String BANK_CARD_UNIQUE_ID = "bank_card_unique_id";
    public static final String PRODUCT_CODE = "product_code";
    public static final String PRODUCT_CONFIG_UNIQUE_ID = "product_config_unique_id";

    public static final String CONTRACT_NO = "contract_no";

    public static final String OPEN_TIME = "open_time";

    public static final String TRADE_TYPE = "trade_type";

    public static final String DEBT_PRINCIPAL = "debt_principal";

    public static final String TRADE_UNIQUE_ID = "trade_unique_id";

    public static final String ORDER_UNIQUE_ID = "order_unique_id";

    public static final String CHANNEL = "channel";

    public static final String TRADE_NO = "trade_no";

    public static final String SCORE = "score";
    public static final String OPEN_ID = "openId";

    public static final String BANNER_PATH = "/banners";

    public static final String CONTENT_PATH = "/content";

    public static final String VIP_AMOUNT_FILE = "/vipamount";

    public static final Integer DOSSIER_MAX_COUNT = 3;

    public static final Integer DOSSIER_PIC_MAX_COUNT = 12;

    public static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    public static final String UTF_8 = "UTF-8";

    //微信支付
    public static final String WXPAY_APPID = "appid";
    public static final String WXPAY_MCHID = "mch_id";
    public static final String WXPAY_PARTNERID = "partnerid";
    public static final String WXPAY_PREPAYID = "prepayid";
    public static final String WXPAY_PACKAGE = "package";
    public static final String WXPAY_SIGN_TYPE = "signType";
    public static final String WXPAY_NONCESTR = "nonceStr";
    public static final String WXPAY_TIMESTAMP = "timeStamp";
    public static final String WXPAY_BODY = "body";
    public static final String WXPAY_DESCRIPTION = "description";
    public static final String WXPAY_OUT_TRADE_NO = "out_trade_no";
    public static final String WXPAY_TIME_EXPIRE = "time_expire";
    public static final String WXPAY_NOTIFY_URL = "notify_url";
    public static final String WXPAY_TOTAL_FEE = "total_fee";
    public static final String WXPAY_TOTAL = "total";
    public static final String WXPAY_SCENE_INFO = "scene_info";
    public static final String WXPAY_SPBILL_CREATE_IP = "spbill_create_ip";
    public static final String WXPAY_PREPAY_ID = "prepay_id";
    public static final String WXPAY_NONCE_STR = "nonce_str";
    public static final String WXPAY_TRADE_TYP = "trade_type";
    public static final String WXPAY_TRADE_TYPE_JSAPI = "JSAPI";
    public static final String WX_SECRET = "secret";
    public static final String WX_ACCESS_TOKEN = "access_token";
    public static final String WX_CODE = "code";
    public static final String WX_ERR_CODE = "errcode";
    public static final String WX_PHONE_INFO = "phone_info";
    public static final String WX_WXPIRES_IN = "expires_in";
    public static final String WX_PHONE_NUMBER = "phoneNumber";
    public static final String WX_JS_CODE = "js_code";
    public static final String WX_SESSION_KEY = "session_key";



    public static final String WXPAY_ID = "id";
    public static final String WXPAY_CREATE_TIME = "create_time";
    public static final String WXPAY_EVENT_TYPE = "event_type";
    public static final String WXPAY_RESOURCE_TYPE = "resource_type";
    public static final String WXPAY_RESOURCE = "resource";
    public static final String WXPAY_ALGORITHM = "algorithm";
    public static final String WXPAY_CIPHERTEXT = "ciphertext";
    public static final String WXPAY_NONCE = "nonce";
    public static final String WXPAY_TRANSACTION_ID = "transaction_id";
    public static final String WXPAY_TRADE_STATE = "trade_state";
    public static final String WXPAY_SUCCESS_TIME = "success_time";
    public static final String WXPAY_PAYER = "payer";
    public static final String WXPAY_OPENID = "openid";
    public static final String WXPAY_PAYER_TOTAL = "payer_total";
    public static final String WXPAY_ASSOCIATED_DATA = "associated_data";
    public static final String WXPAY_EVENT_TYPE_SUCCESS = "TRANSACTION.SUCCESS";
    public static final String WXPAY_RESOURCE_TYPE_VALUE = "encrypt-resource";
    public static final String WXPAY_SUCCESS = "SUCCESS";
    public static final String WXPAY_FAIL = "FAIL";
    public static final String WXPAY_SIGE_TYPE_MD5 = "MD5";
    public static final String WXPAY_SIGN = "sign";
    public static final String WXPAY_RETURN_CODE = "return_code";
    public static final String WXPAY_RESULT_CODE = "result_code";



    //微信支付有效时间(分钟)
    public static final Integer WXPAY_TIME_OUT = 30;

    public static final String DATE_PATTERN_TIMEZONE = "YYYY-MM-DDTHH:mm:ss+TIMEZONE";

    //微信支付下单返回码
    public static final Integer WXPAY_RETURN_SUCCESS_CODE = 200;

    //微信支付回调返回结果
    public static final String WXPAY_NOTIFY_SUCCESS = "SUCCESS";
    public static final String WXPAY_NOTIFY_SUCCESS_MESSAGE = "处理成功";
    public static final String WXPAY_NOTIFY_FAIL = "FAIL";
    public static final String WXPAY_NOTIFY_FAIL_MESSAGE = "处理失败";

    public static final String SEN_SPLIT = "#";
    public static final String AES_KEY = "aesKey";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    //男、女
    public static final String SEX_MALE = "MALE";
    public static final String SEX_FEMALE = "FEMALE";


    public static final String GRADE_CLASS = "grade_class";
    public static final String READER_TYPE = "reader_type";

    public static final String STUDENT_NUM = "student_num";

    public static final String READER_UNIQUE_ID = "reader_unique_id";

    public static final String ISBN = "isbn";

    public static final String BOOK_UNIQUE_ID = "book_unique_id";

    public static final String POSITION = "position";

    public static final String AUTHOR = "author";

    public static final String END_TIME = "end_time";

    public static final String UPD_DT = "upd_dt";
}
