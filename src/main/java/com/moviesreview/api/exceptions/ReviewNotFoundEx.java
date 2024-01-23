package com.moviesreview.api.exceptions;

public class ReviewNotFoundEx extends RuntimeException{
    public static final long serialVersionTDI = 2;

    public ReviewNotFoundEx(String message){
        super(message);
    }
}
