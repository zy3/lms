package com.red.lms.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
@TableName("action")
public class Action {
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
    @TableField(value = "action_unique_id")
    private String actionUniqueId;


    /**
     * 读者唯一标识
     */
    @TableField(value = "reader_unique_id")
    private String readerUniqueId;

    /**
     * 书籍唯一标识
     */
    @TableField(value = "book_unique_id")
    private String bookUniqueId;

    /**
     * 书籍状态 R|借阅中 B|已归还
     */
    @TableField(value = "status")
    private String status;

    /**
     * 借出时间
     */
    @TableField(value = "borrow_time")
    private Date borrowTime;

    /**
     * 截止时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 归还时间
     */
    @TableField(value = "back_time")
    private Date backTime;
}
