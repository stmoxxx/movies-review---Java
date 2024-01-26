package com.moviesreview.api.services.impl;

import com.moviesreview.api.dto.MovieDto;
import com.moviesreview.api.dto.MovieGetAllResponse;
import com.moviesreview.api.exceptions.MovieNotFoundEx;
import com.moviesreview.api.models.Movie;
import com.moviesreview.api.repos.MovieRepository;
import com.moviesreview.api.services.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());
        movie.setTime(movieDto.getTime());
        movie.setAuthor(movieDto.getAuthor());

        Movie newMovie = movieRepository.save(movie);

        MovieDto movieResponse = new MovieDto();
        movieResponse.setId(newMovie.getId());
        movieResponse.setName(newMovie.getName());
        movieResponse.setType(newMovie.getType());
        movieResponse.setTime(newMovie.getTime());
        movieResponse.setAuthor(newMovie.getAuthor());
        log.info("Movie has been created successfully, movieDto:{}", movieDto);
        return movieResponse;
    }

    @Override
    public MovieGetAllResponse getAllMovies(int pageNumb, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumb, pageSize);
        Page<Movie> movies = movieRepository.findAll(pageable);
        List<Movie> listOfMovie = movies.getContent();
        List<MovieDto> content = listOfMovie.stream().map(ms -> mapToDto(ms)).collect(Collectors.toList());

        MovieGetAllResponse movieGetAllResponse =  new MovieGetAllResponse();
        movieGetAllResponse.setContent(content);
        movieGetAllResponse.setPageNumber(movies.getNumber());
        movieGetAllResponse.setPageSize(movies.getSize());
        movieGetAllResponse.setTotalElements(movies.getTotalElements());
        movieGetAllResponse.setTotalPages(movies.getTotalPages());
        movieGetAllResponse.setLast(movies.isLast());

        log.info("Movies has been fetched successfully");  //movieGetAllResponse:{}", movieGetAllResponse);

        return movieGetAllResponse;
    }

    @Override
    public MovieDto getMovieById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundEx("Movie doesn't exist"));
        log.info("Movie details fetched successfully, id:{}", id);
        return mapToDto(movie);
    }

    @Override
    public void deleteMovie(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundEx("Movie doesn't exist"));
        movieRepository.delete(movie);
        log.info("Movie has been deleted successfully by id, id:{}", id);
    }


    @Override
    public MovieDto updateMovie(MovieDto movieDto, long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundEx("Movie doesn't exist"));
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());
        movie.setTime(movieDto.getTime());
        movie.setAuthor(movieDto.getAuthor());

        Movie movieUpdated = movieRepository.save(movie);
        log.info("Movie has been updated successfully by id, id:{}, movieDto:{}", id, movieDto);
        return mapToDto(movieUpdated);
    }

    private MovieDto mapToDto (Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setName(movie.getName());
        movieDto.setType(movie.getType());
        movieDto.setTime(movie.getTime());
        movieDto.setAuthor(movie.getAuthor());
        return movieDto;
    }

    private Movie mapToEntity (MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());
        movie.setTime(movieDto.getTime());
        movie.setAuthor(movieDto.getAuthor());
        return movie;
    }


}
