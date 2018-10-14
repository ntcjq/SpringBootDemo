package com.cjq.SpringBootDemo.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    UNKNOW_EXCEPTION(500,"未知异常"),
    DB_EXCEPTION(500,"数据库操作异常"),

    REDIS_UNCLOCK_FAILD(6379,"解锁失败"),
    REDIS_LOCKKEY_IS_REGET(6380,"锁已被其他用户获取到");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
