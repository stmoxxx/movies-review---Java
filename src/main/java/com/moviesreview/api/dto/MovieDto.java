package com.moviesreview.api.dto;

import lombok.Data;

@Data
public class MovieDto {
    private long id;
    private String name;
    private String type;
    private int time;
    private String author;
}
