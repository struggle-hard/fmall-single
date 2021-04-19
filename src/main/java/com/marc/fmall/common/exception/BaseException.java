package com.marc.fmall.common.exception;

public class BaseException extends RuntimeException {

    private String code;
    private String message;

    public BaseException(){
        super();
    }
    public BaseException(String message){
        super(message);
        this.message=message;
    }
    public BaseException(String code, String message){
        this.code=code;
        this.message=message;
    }
}
