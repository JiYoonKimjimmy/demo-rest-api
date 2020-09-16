package com.demo.restapi.config.redis;

public class CacheKey {

    public static final int DEFAULT_EXPIRE_SEC = 60;

    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = DEFAULT_EXPIRE_SEC * 5;

    public static final String BOARD = "board";
    public static final int BOARD_EXPIRE_SEC = DEFAULT_EXPIRE_SEC * 10;

}
