package com.authskull.demo.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class HttpHelper {
    private HttpHelper() {
    }

    public static HttpHeaders getResultHeaders(Page<?> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Result-Count", Integer.toString(response.getNumberOfElements()));
        headers.set("Result-More", Boolean.toString(!response.isLast()));
        headers.set("Result-Total", Long.toString(response.getTotalElements()));
        return headers;
    }
}
