package com.red.lms.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.red.lms.common.enums.CodeEnum;
import com.red.lms.common.exceptions.CommonException;
import com.red.lms.common.model.SysConfigDetailResponse;
import com.red.lms.common.model.SysConfigEditRequest;
import com.red.lms.dal.entity.SysConfig;
import com.red.lms.dal.mapper.SysConfigMapper;
import com.red.lms.service.token.UserInfoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public SysConfigDetailResponse query() {
        QueryWrapper<SysConfig> sysConfigQueryWrapper = new QueryWrapper<>();
        List<SysConfig> sysConfigList = sysConfigMapper.selectList(sysConfigQueryWrapper);
        if (CollectionUtils.isEmpty(sysConfigList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置数据为空");
        }
        SysConfig sysConfig = sysConfigList.get(0);
        SysConfigDetailResponse response = new SysConfigDetailResponse();
        response.setBorrowPeriod(sysConfig.getBorrowPeriod() == null ? null : sysConfig.getBorrowPeriod().toString());
        response.setMaxBorrowCount(sysConfig.getMaxBorrowCount() == null ? null : sysConfig.getMaxBorrowCount().toString());
        return response;
    }

    public void edit(SysConfigEditRequest request) {
        QueryWrapper<SysConfig> sysConfigQueryWrapper = new QueryWrapper<>();
        List<SysConfig> sysConfigList = sysConfigMapper.selectList(sysConfigQueryWrapper);
        if (CollectionUtils.isEmpty(sysConfigList)) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "系统配置数据为空");
        }
        if (!StringUtils.isNumber(request.getBorrowPeriod())) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "借阅期限需要为数字");
        }
        if (!StringUtils.isNumber(request.getMaxBorrowCount())) {
            throw new CommonException(CodeEnum.BIZ_ERROR, "最多借阅数量需要为数字");
        }
        SysConfig sysConfig = sysConfigList.get(0);
        sysConfig.setBorrowPeriod(Integer.parseInt(request.getBorrowPeriod()));
        sysConfig.setMaxBorrowCount(Integer.parseInt(request.getMaxBorrowCount()));
        sysConfig.setUpdDt(new Date());
        sysConfig.setUpdPerson(UserInfoContainer.getUsername());
        sysConfigMapper.updateById(sysConfig);
    }
}
