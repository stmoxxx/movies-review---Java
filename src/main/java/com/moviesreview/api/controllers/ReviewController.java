package com.moviesreview.api.controllers;

import com.moviesreview.api.dto.ReviewDto;
import com.moviesreview.api.services.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/")
public class ReviewController {


    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @ApiOperation(value = "Mój endpoint do robienia review!", response = Void.class)
    @PostMapping("/movie/{movieId}/review")
    public ResponseEntity<ReviewDto> createReview (@PathVariable(value = "movieId") long movieId, @RequestBody ReviewDto reviewDto){
        log.info("Creating review at movie, movieId:{}, reviewDto:{}", movieId, reviewDto);

        return new ResponseEntity<>(reviewService.createReview(movieId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("movie/{movieId}/reviews")
    public List<ReviewDto> getReviewsBiMovieId (@PathVariable(value = "movieId") long movieId){
        log.info("Fetching all reviews at movie, movieId:{}", movieId);

        return reviewService.getReviewsByMovieId(movieId);
    }
    @GetMapping("movie/{movieId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "id")long reviewId,
                                                   @PathVariable(value = "movieId") long movieId){
        log.info("Fetching detailed review at movie, movieId:{}, review, reviewId:{}", movieId, reviewId);

        ReviewDto reviewDto = reviewService.getReviewById(reviewId, movieId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("movie/{movieId}/reviews/{id}/update")
    public ResponseEntity<ReviewDto> updateReview (@PathVariable(value = "id")long reviewId,
                                                   @PathVariable(value = "movieId") long movieId,
                                                   @RequestBody ReviewDto reviewDto){
        log.info("Updating review at movie, movieId:{}, review, reviewId:{}, reviewDto:{}", movieId, reviewId,reviewDto);

        ReviewDto updatedReview = reviewService.updateReview(reviewId, movieId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("movie/{movieId}/reviews/{id}/delete")
    public ResponseEntity<String> deleteReview (@PathVariable(value = "id")long reviewId,
                                                @PathVariable(value = "movieId") long movieId){
        log.info("Deleting review at movie, movieId:{}, review, reviewId:{}", movieId, reviewId);

        reviewService.deleteReview(reviewId, movieId);
        return new ResponseEntity<>("Review has been deleted successfully!",HttpStatus.OK);
    }
}
