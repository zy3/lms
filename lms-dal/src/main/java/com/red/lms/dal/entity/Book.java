package com.red.lms.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
@TableName("book")
public class Book {
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
    @TableField(value = "book_unique_id")
    private String bookUniqueId;

    /**
     * '书籍位置'
     */
    @TableField(value = "position")
    private String position;

    /**
     * 书号
     */
    @TableField(value = "isbn")
    private String isbn;

    /**
     * 书名
     */
    @TableField(value = "name")
    private String name;

    /**
     *  作者
     */
    @TableField(value = "author")
    private String author;

    /**
     *  译者
     */
    @TableField(value = "translator")
    private String translator;

    /**
     *  出版单位
     */
    @TableField(value = "publisher")
    private String publisher;

    /**
     *  版次
     */
    @TableField(value = "edition")
    private String edition;

    /**
     *  书名检索词
     */
    @TableField(value = "name_index")
    private String nameIndex;

    /**
     *  作者检索词
     */
    @TableField(value = "author_index")
    private String authorIndex;

    /**
     *  主题检索词
     */
    @TableField(value = "topic_index")
    private String topicIndex;

    /**
     *  分类号
     */
    @TableField(value = "classify")
    private String classify;

    /**
     *  CIP核字
     */
    @TableField(value = "cip_num")
    private String cipNum;

    /**
     *  数量
     */
    @TableField(value = "counts")
    private Integer counts;

    /**
     *  状态 N|已上架 D|已下架
     */
    @TableField(value = "status")
    private String status;
}
