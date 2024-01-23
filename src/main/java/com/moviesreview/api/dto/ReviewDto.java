package com.moviesreview.api.dto;

import lombok.Data;


@Data
public class ReviewDto {
    private long id;
    private String title;
    private String content;
    private int likes;
    private int stars;
}
