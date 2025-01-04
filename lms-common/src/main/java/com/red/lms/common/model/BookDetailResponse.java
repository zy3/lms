package com.red.lms.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "书籍详情返回结果")
public class BookDetailResponse {

    @ApiModelProperty(value = "唯一标识", example = "jfsdfheww8345783rsfhsdj")
    private String bookUniqueId;

    @ApiModelProperty(value = "书号", example = "978-7-300-19831-6")
    private String isbn;

    @ApiModelProperty(value = "书籍位置", example = "A1")
    private String position;

    @ApiModelProperty(value = "书名", example = "毛泽东：雄关漫道：插图本")
    private String name;

    @ApiModelProperty(value = "作者", example = "鲁林")
    private String author;

    @ApiModelProperty(value = "译者", example = "毕笑")
    private String translator;

    @ApiModelProperty(value = "出版单位", example = "中国人民大学出版社")
    private String publisher;

    @ApiModelProperty(value = "出版版次", example = "2014.9")
    private String edition;

    @ApiModelProperty(value = "书名检索词", example = "毛")
    private String nameIndex;

    @ApiModelProperty(value = "作者检索词", example = "鲁;毕")
    private String authorIndex;

    @ApiModelProperty(value = "主题检索词", example = "毛泽东（1893~1976）-任务研究")
    private String topicIndex;

    @ApiModelProperty(value = "分类号", example = "A755")
    private String classify;

    @ApiModelProperty(value = "CIP核字", example = "2014-198609")
    private String cipNum;

    @ApiModelProperty(value = "数量", example = "10")
    private Integer counts;
}
