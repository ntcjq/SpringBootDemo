package com.cjq.SpringBootDemo.exception;

import com.cjq.SpringBootDemo.enums.ResultEnum;

public class MyException extends RuntimeException{

    private Integer code = 500;

    public MyException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
    public MyException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public MyException(String msg){

        super(msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
