package com.moviesreview.api.services;

import com.moviesreview.api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(long movieId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByMovieId(long id);
    ReviewDto getReviewById(long reviewId, long movieId);
    ReviewDto updateReview(long reviewId, long movieId, ReviewDto reviewDto);
    void deleteReview(long reviewId, long movieId);
}
