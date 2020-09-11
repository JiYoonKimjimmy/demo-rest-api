package com.demo.restapi.model.publicApi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicApiParams {

    private final String apiUrl;
    private final String serviceKey;
    private final String responseType;
    private final int pageNo;
    private final int numOfRows;

}
