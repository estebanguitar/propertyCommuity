package com.test.propertyCommuity.util;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiResponseUtil<T> {
    private int statusCode;
    private String message;
    private T data;

    @Builder
    public ApiResponseUtil(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

//    public Map<String, Object> getResponseData() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("statusCode", this.statusCode);
//        map.put("message", this.message);
//        map.put("data", this.data);
//        return map;
//    }

}
