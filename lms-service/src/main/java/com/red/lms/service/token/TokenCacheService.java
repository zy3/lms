package com.red.lms.service.token;

import com.red.lms.common.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class TokenCacheService {

    /**
     * 具体存储缓存数据的容器
     */
    private Map<String, String> cacheTokenMap = new HashMap<>();
    /**
     * 过期时间记录
     */
    private Map<String, Date> expireRecord = new HashMap<>();


    public boolean checkToken(String username, String token) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(token)) {
            log.info("登录的用户名或者token为空, 获取token失败, username->{}", username);
            return false;
        }
        Date expire = expireRecord.get(username);
        if (expire == null) {
            log.info("用户登录已失效, username->{}", username);
            return false;
        }

        String cacheToken = cacheTokenMap.get(username);
        if (StringUtils.isBlank(cacheToken)) {
            log.info("用户登录已失效, 获取的token值为空, username->{}", username);
            return false;
        }
        // 命中缓存后 返回缓存数据
        if (new Date().before(expire) && token.equals(cacheToken)) {
            Date newExpireDate = DateUtils.addMinutes(new Date(), 30);
            expireRecord.put(username, newExpireDate);
            return true;
        } else {
            //  数据过期移除数据存储和过期记录存储
            expireRecord.remove(username);
            cacheTokenMap.remove(username);
            log.info("用户登录过期, 移除token消息, username->{}", username);
            return false;
        }
    }

    public void removeInvalidToken() {
        List<String> invalidUserList = new ArrayList<>();
        Iterator<Map.Entry<String, Date>> iterator = expireRecord.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Date> entry = iterator.next();
            Date expire = entry.getValue();
            if (new Date().after(expire)) {
                invalidUserList.add(entry.getKey());
            }
        }
        for (String username : invalidUserList) {
            expireRecord.remove(username);
            cacheTokenMap.remove(username);
            log.info("用户登录过期, 定时任务检查移除token消息, username->{}", username);
        }
    }


    public void addToken(String username, String token) {
        //30分钟时效
        Date expireDate = DateUtils.addMinutes(new Date(), 30);
        expireRecord.put(username, expireDate);
        cacheTokenMap.put(username, token);
        log.info("用户添加token成功, username->{}, 预设时效时间->{}", username, DateTimeUtils.dateToStr(expireDate));
    }

    public void removeToken(String username) {
        //30分钟时效
        expireRecord.remove(username);
        cacheTokenMap.remove(username);
        log.info("用户移除token成功, username->{}, 预设时效时间->{}", username);
    }
}
