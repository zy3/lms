package com.red.lms.dal.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.segments.NormalSegmentList;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Objects;

public class LmsQueryWrapper<T> extends QueryWrapper {
    @Override
    public String getCustomSqlSegment() {
        MergeSegments expression = this.getExpression();
        if (Objects.nonNull(expression)) {
            NormalSegmentList normal = expression.getNormal();
            String sqlSegment = this.getSqlSegment();
            if (StringUtils.isNotBlank(sqlSegment)) {
                if (normal.isEmpty()) {
                    if (!sqlSegment.toLowerCase().trim().startsWith("order by")) {
                        return " and " + sqlSegment;
                    }
                    return sqlSegment;
                }
                if (!sqlSegment.toLowerCase().trim().startsWith("order by")) {
                    return " and " + sqlSegment;
                }
                return sqlSegment;
            }
        }

        return "";
    }
}
