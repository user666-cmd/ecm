package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private Integer userId;
    private Integer newsId;

    // 用于查询我赞过的资讯时返回资讯详情
    private Integer id;
    private String title;
    private String content;
    private String author;
    private String createTime;
}
