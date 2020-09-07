package com.demo.restapi.service.common;

import com.demo.restapi.common.advice.exception.CCommunicationException;
import com.demo.restapi.model.social.KakaoProfile;
import com.demo.restapi.model.social.RetKakaoAuth;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Environment environment;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * kakao profile 조회 처리
     * @param accessToken
     * @return
     */
    public KakaoProfile getKakaoProfile(String accessToken) {
        // http header
        HttpHeaders httpHeaders = new HttpHeaders();
        // Content-type: application/x-www-form-urlencoded
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        // http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(environment.getProperty("spring.social.kakao.url.profile"), request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // 200
                return gson.fromJson(response.getBody(), KakaoProfile.class);
            }
        } catch (Exception e) {
            throw new CCommunicationException();
        }

        throw new CCommunicationException();
    }

    /**
     * Kakao Token 조회 처리
     * @param code
     * @return
     */
    public RetKakaoAuth getKakaoTokenInfo(String code) {
        // http header
        HttpHeaders httpHeaders = new HttpHeaders();
        // Content-type: application/x-www-form-urlencoded
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // parameters
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("code", code);

        // http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(environment.getProperty("spring.social.kakao.url.token"), request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RetKakaoAuth.class);
        }

        return null;
    }

}
