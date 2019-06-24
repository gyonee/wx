package com.xmcc.WX.exception;

import com.xmcc.WX.common.ResultEnums;

public class CustomException extends RuntimeException{
    private int code;

    public CustomException() {
        super();
    }

    public CustomException(int code,String message){
        super(message);
        this.code = code;
    }
    public CustomException(String message){
        this(ResultEnums.FAIL.getCode(),message);
    }

}
