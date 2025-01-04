package com.red.lms.common.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Configuration
@ConfigurationProperties(prefix = "enums.reader.type")
public class ReaderTypeProperties {
    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }


    public Map<String, String> getEnums() {
        Map<String, String> enumMap = new LinkedHashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            return enumMap;
        }
        for (String position : list) {
            if (StringUtils.isBlank(position)) {
                continue;
            }
            String[] splitArr = position.split("\\|");
            if (splitArr.length == 2) {
                enumMap.put(splitArr[0], splitArr[1]);
            }
        }
        return enumMap;
    }
}
