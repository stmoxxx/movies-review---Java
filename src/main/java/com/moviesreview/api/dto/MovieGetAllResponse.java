package com.moviesreview.api.dto;

import lombok.Data;

import java.util.List;
@Data
public class MovieGetAllResponse {
    private List<MovieDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
