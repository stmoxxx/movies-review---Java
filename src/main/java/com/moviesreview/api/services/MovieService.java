package com.moviesreview.api.services;

import com.moviesreview.api.dto.MovieDto;
import com.moviesreview.api.dto.MovieGetAllResponse;
import com.moviesreview.api.models.Movie;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto);
    MovieDto getMovieById(long id);
    void deleteMovie(long id);
    MovieDto updateMovie(MovieDto movieDto,long id);

    MovieGetAllResponse getAllMovies(int pageNumb, int pageSize);

}
