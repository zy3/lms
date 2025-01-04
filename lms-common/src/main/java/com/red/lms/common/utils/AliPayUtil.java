package com.red.lms.common.utils;//package com.red.credit.common.utils;
//
//
//import com.red.credit.common.constants.PayConstants;
//import com.red.credit.common.model.pay.PayBean;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//public class AliPayUtil {
//
//
//
//    /**
//     * 获取订单信息
//     *
//     * @param payBean 包括订单金额，订单描述，订单详细内容，创建订单时间戳
//     * @param version 接入的支付宝SDK版本号
//     * @return 返回string值为已经签名的订单信息
//     */
//    public static String getOrderInfo(PayBean payBean, String version) {
//        //生成map格式订单
//        Map<String, String> params = buildOrderParamMap(PayConstants.ALI_APP_ID, payBean, version);
//        //将订单信息由map转为string，并且排序
//        String orderParam = buildOrderParam(params);
//        String privateKey = PayConstants.RSA2_PRIVATE;
//        //签名，encode
//        String sign = getSign(params, privateKey);
//        //在订单信息后加入sign值,此时sign值已经拼接了sign=，不需要再进行拼接
//        return orderParam + "&" + sign;
//    }
//
//    /**
//     * 生成map格式订单
//     *
//     * @param app_id  支付平台中你APP的app_id
//     * @param payBean 包括订单金额，订单描述，订单详细内容，创建订单时间戳
//     * @param version 接入的支付宝SDK版本号
//     * @return
//     */
//    public static Map<String, String> buildOrderParamMap(String app_id, PayBean payBean, String version) {
//        Map<String, String> keyValues = new HashMap<>();
//        //支付宝的amount单位为元
//        float amount = (payBean.getAmount()) / 100;
//
//        keyValues.put("app_id", app_id);
//        /**
//         * biz_content参数包括所有的订单信息
//         */
//        keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"" + amount + "\",\"subject\":\"" + payBean.getDescribe() + "\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() + "\"}");
//
//        keyValues.put("charset", "utf-8");
//
//        keyValues.put("method", "alipay.trade.app.pay");
//
//        keyValues.put("notify_url", payBean.getNotify_url());
//
//        keyValues.put("sign_type", "RSA2");
//
//        keyValues.put("timestamp", getDateToString(payBean.getTime(), "yyyy-MM-dd HH:mm:ss"));
//
//        keyValues.put("version", version);
//
//        return keyValues;
//    }
//
//
//    /**
//     * 将map转换为string，构造原始支付订单参数信息
//     *
//     * @param map 支付订单参数
//     * @return
//     */
//    public static String buildOrderParam(Map<String, String> map) {
//        List<String> keys = new ArrayList<String>(map.keySet());
//        Collections.sort(keys);
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < keys.size() - 1; i++) {
//            String key = keys.get(i);
//            String value = map.get(key);
//            sb.append(buildKeyValue(key, value, true));
//            sb.append("&");
//        }
//
//        String tailKey = keys.get(keys.size() - 1);
//        String tailValue = map.get(tailKey);
//        sb.append(buildKeyValue(tailKey, tailValue, true));
//
//        return sb.toString();
//    }
//
//
//    /**
//     * 拼接键值对
//     *
//     * @param key
//     * @param value
//     * @param isEncode
//     * @return
//     */
//    private static String buildKeyValue(String key, String value, boolean isEncode) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(key);
//        sb.append("=");
//        if (isEncode) {
//            try {
//                sb.append(URLEncoder.encode(value, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                sb.append(value);
//            }
//        } else {
//            sb.append(value);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 将订单信息签名，encode
//     *
//     * @param map    待签名的订单信息
//     * @param rsaKey 支付宝私钥
//     * @return
//     */
//    public static String getSign(Map<String, String> map, String rsaKey) {
//
//        List<String> keys = new ArrayList<String>(map.keySet());
//        // key排序
//        Collections.sort(keys);
//
//        StringBuilder authInfo = new StringBuilder();
//        for (int i = 0; i < keys.size() - 1; i++) {
//            String key = keys.get(i);
//            String value = map.get(key);
//            authInfo.append(buildKeyValue(key, value, false));
//            authInfo.append("&");
//        }
//
//        String tailKey = keys.get(keys.size() - 1);
//        String tailValue = map.get(tailKey);
//        authInfo.append(buildKeyValue(tailKey, tailValue, false));
//
//        //将排序后的序列进行签名
//        String oriSign = sign(authInfo.toString(), rsaKey);
//        String encodedSign = "";
//
//        try {
//            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "sign=" + encodedSign;
//    }
//
//
//    private static final String ALGORITHM = "RSA";
//
//    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
//
//    private static final String DEFAULT_CHARSET = "UTF-8";
//
//    private static String getAlgorithms() {
//        return SIGN_SHA256RSA_ALGORITHMS;
//    }
//
//    /**
//     * 签名
//     *
//     * @param content    待签名信息
//     * @param privateKey 支付宝私钥
//     * @return
//     */
//    public static String sign(String content, String privateKey) {
//        try {
//            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
//                    Base64Utils.decode(privateKey));
//            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, "BC");
//            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
//
//            java.security.Signature signature = java.security.Signature
//                    .getInstance(getAlgorithms());
//
//            signature.initSign(priKey);
//            signature.update(content.getBytes(DEFAULT_CHARSET));
//
//            byte[] signed = signature.sign();
//
//            return Base64Utils.encode(signed);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    /**
//     * 要求外部订单号必须唯一。
//     *
//     * @return
//     */
//    private static String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }
//
//    /**
//     * 根据时间戳获取时间信息
//     *
//     * @param milSecond 时间戳
//     * @param pattern   时间信息表述形式
//     * @return
//     */
//    public static String getDateToString(long milSecond, String pattern) {
//        Date date = new Date(milSecond);
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
//
//}
