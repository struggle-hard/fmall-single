package com.marc.fmall.common.exception;

public class ValidDataException extends BaseException {
    private String code;
    private String message;

    public ValidDataException(){}

    public ValidDataException(String message){
        super(message);
        this.message=message;
    }
    public ValidDataException(String code, String message){
        super();
        this.code=code;
        this.message=message;
    }
}
