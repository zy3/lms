package com.red.lms.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
@TableName("test")
public class Test {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "ins_dt")
    private Date insDt;
    /**
     * 更新时间
     */
    @TableField(value = "upd_dt")
    private Date updDt;
    /**
     * 创建人
     */
    @TableField(value = "ins_person")
    private String insPerson;
    /**
     * 更新人
     */
    @TableField(value = "upd_person")
    private String updPerson;
    /**
     * 删除标识
     */
    @TableField(value = "del_flag")
    private Boolean delFlag;
    /**
     * 备注
     */
    @TableField(value = "mark")
    private Boolean mark;

    /**
     * 版本
     */
    @Version
    private Integer version;

    /**
     * 唯一标识
     */
    @TableField(value = "test_unique_id")
    private String testUniqueId;
}
