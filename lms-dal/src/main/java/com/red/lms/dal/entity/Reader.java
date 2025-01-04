package com.red.lms.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
@TableName("reader")
public class Reader {
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
    @TableField(value = "reader_unique_id")
    private String readerUniqueId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 年级班级
     */
    @TableField(value = "grade_class")
    private String gradeClass;

    /**
     * 学号
     */
    @TableField(value = "student_num")
    private String studentNum;

    /**
     * 读者类型 S|学生 T|老师
     */
    @TableField(value = "reader_type")
    private String readerType;

    /**
     * 状态 N|已启用 S|停用
     */
    @TableField(value = "status")
    private String status;
}
