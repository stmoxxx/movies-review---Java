package com.moviesreview.api.controllers;

import com.moviesreview.api.dto.MovieDto;
import com.moviesreview.api.dto.MovieGetAllResponse;
import com.moviesreview.api.services.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movie")
    public ResponseEntity<MovieGetAllResponse> getMovies(
            @RequestParam(value = "pageNumb", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        log.info("Fetching all movies, page number: {}, page size: {}", pageNumber, pageSize);

        return new ResponseEntity<>(movieService.getAllMovies(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("movie/{id}")
    public ResponseEntity<MovieDto> movieDetailed (@PathVariable long id){
        log.info("Fetching movie details, id: {}", id);

        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping("movie/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> createMovie (@RequestBody MovieDto movieDto){
        log.info("Creating new movie, movieDto:{}", movieDto);

        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @PutMapping("movie/{id}/update")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto, @PathVariable("id") long movieId){
        log.info("Updating movie, movieDto: {}, movieId:{}",movieDto, movieId);

        MovieDto response = movieService.updateMovie(movieDto, movieId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("movie/{id}/delete")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long movieId){
        log.info("Deleting movie, movieId:{}", movieId);

        movieService.deleteMovie(movieId);
        return new ResponseEntity<String>("Movie deleted successfully!", HttpStatus.OK);
    }
}
