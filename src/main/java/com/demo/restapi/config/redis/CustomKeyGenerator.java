package com.demo.restapi.config.redis;

public class CustomKeyGenerator {
    public static Object create(Object o1) {
        return "FRONT:" + o1;
    }
    public static Object create(Object o1, Object o2) {
        return "FRONT:" + o1 + ":" + o2;
    }
}
