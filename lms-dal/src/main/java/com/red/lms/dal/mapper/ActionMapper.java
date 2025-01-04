package com.red.lms.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.red.lms.dal.entity.Action;
import com.red.lms.dal.model.ActionListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ActionMapper extends BaseMapper<Action> {
    public IPage<ActionListDTO> selectPageByCondition(IPage<ActionListDTO> page, @Param("ew") Wrapper<ActionListDTO> updateWrapper);
}
