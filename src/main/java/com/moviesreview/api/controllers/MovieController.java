package com.moviesreview.api.controllers;

import com.moviesreview.api.dto.MovieDto;
import com.moviesreview.api.dto.MovieGetAllResponse;
import com.moviesreview.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        return new ResponseEntity<>(movieService.getAllMovies(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("movie/{id}")
    public ResponseEntity<MovieDto> movieDetailed (@PathVariable long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping("movie/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> createMovie (@RequestBody MovieDto movieDto){
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @PutMapping("movie/{id}/update")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto, @PathVariable("id") long movieId){
        MovieDto response = movieService.updateMovie(movieDto, movieId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("movie/{id}/delete")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long movieId){
        movieService.deleteMovie(movieId);
        return new ResponseEntity<String>("Movie deleted successfully!", HttpStatus.OK);
    }
}
