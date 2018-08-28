package com.cjq.SpringBootDemo.enums;

public enum ResultEnum {
    UNKNOW_EXCEPTION(500,"未知异常"),
    DB_EXCEPTION(500,"数据库操作异常");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
