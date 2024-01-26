package com.moviesreview.api.services.impl;

import com.moviesreview.api.dto.ReviewDto;
import com.moviesreview.api.exceptions.MovieNotFoundEx;
import com.moviesreview.api.exceptions.ReviewNotFoundEx;
import com.moviesreview.api.models.Movie;
import com.moviesreview.api.models.Review;
import com.moviesreview.api.repos.MovieRepository;
import com.moviesreview.api.repos.ReviewRepository;
import com.moviesreview.api.services.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepostory;
    private MovieRepository movieRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepostory = reviewRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public ReviewDto createReview (long movieId, ReviewDto reviewDto){
        Review review = mapToEntity(reviewDto);

        Movie movie = movieRepository.findById(movieId).orElseThrow(( ) ->
                new MovieNotFoundEx("The movie with this review doesn't exist"));
        review.setMovie(movie);

        Review newReview = reviewRepostory.save(review);
        log.info("Created review successfully at movie, movieId:{}, reviewDto:{}", movieId,reviewDto);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByMovieId(long id) {
        List<Review> reviews = reviewRepostory.findByMovieId(id);
        log.info("Fetched reviews successfully at movie, movieId:{}", id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(long reviewId, long movieId) {
        Review review = reviewRepostory.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundEx("This review doesn't exist"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(( ) ->
                new MovieNotFoundEx("The movie with this review doesn't exist"));

        if (review.getMovie().getId() != movie.getId()){
            throw new ReviewNotFoundEx("Review doesn't match with movie");
        }

        log.info("Fetched review by id successfully at movie, movieId:{}, review, reviewId:{}", movieId, reviewId);


        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(long reviewId, long movieId, ReviewDto reviewDto) {
        Review review = reviewRepostory.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundEx("This review doesn't exist"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(( ) ->
                new MovieNotFoundEx("The movie with this review doesn't exist"));

        if (review.getMovie().getId() != movie.getId()){
            throw new ReviewNotFoundEx("Review doesn't match with movie");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setLikes(reviewDto.getLikes());

        Review updatedReview = reviewRepostory.save(review);

        log.info("Updated review successfully at movie, movieId:{}, review, reviewId:{} reviewDto:{}", movieId, reviewId,reviewDto);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(long reviewId, long movieId) {
        Review review = reviewRepostory.findById(reviewId).orElseThrow(()->
                new ReviewNotFoundEx("This review doesn't exist"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(( ) ->
                new MovieNotFoundEx("The movie with this review doesn't exist"));

        if (review.getMovie().getId() != movie.getId()){
            throw new ReviewNotFoundEx("Review doesn't match with movie");
        }

        log.info("Deleted review successfully at movie, movieId:{}, review, reviewId:{}", movieId, reviewId);

        reviewRepostory.delete(review);
    }

    private ReviewDto mapToDto (Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        reviewDto.setLikes(review.getLikes());
        return reviewDto;
    }

    private Review mapToEntity (ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setLikes(reviewDto.getLikes());
        return review;
    }


}
