package com.demo.restapi.service.publicApi;

import com.demo.restapi.common.advice.exception.PublicApiFailException;
import com.demo.restapi.model.publicApi.PublicApiParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class PublicApiService {

    private final RestTemplate restTemplate;

    /**
     * call api
     * @param params
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> T callApi(PublicApiParams params, Class<T> responseType) {
        try {
            UriComponents uriComponents = UriComponentsBuilder
                    .fromHttpUrl(params.getApiUrl())
                    .queryParam("serviceKey", params.getServiceKey())
                    .queryParam("pageNo", params.getPageNo())
                    .queryParam("numOfRows", params.getNumOfRows())
                    .queryParam("type", params.getResponseType())
                    .build();

            URI uri = new URI(uriComponents.toString());

            log.info("public api call start [" + params.getPageNo() + "]");

            return restTemplate.getForObject(uri, responseType);

        } catch (Exception e) {
            throw new PublicApiFailException();
        } finally {
            log.info("public api call end [" + params.getPageNo() + "]");
        }
    }
}
