package com.red.lms.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenScheduleTask {

    @Autowired
    private TokenCacheService tokenCacheService;


    @Scheduled(fixedDelay = 60 * 1000)
    public void checkInvalidToken() {
        tokenCacheService.removeInvalidToken();
    }
}
