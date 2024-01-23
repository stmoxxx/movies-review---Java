package com.moviesreview.api.exceptions;

public class MovieNotFoundEx extends RuntimeException{
    public static final long serialVersionTDI = 1;

    public MovieNotFoundEx(String message){
        super(message);
    }
}
