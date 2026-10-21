package com.gra.ecm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class News {
    private Integer id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createTime;
}