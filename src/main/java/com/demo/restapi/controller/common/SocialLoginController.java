package com.demo.restapi.controller.common;

import com.demo.restapi.service.common.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialLoginController {

    private final Environment environment;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * social login 페이지 호출
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView socialLogin(ModelAndView model) {

        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);

        model.addObject("loginUrl", loginUrl);
        model.setViewName("social/login");

        return model;
    }

    /**
     * Kokao 인증 완료 후 redirect 페이지 호출
     * @param model
     * @param code
     * @return
     */
    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView model, @RequestParam String code) {
        model.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
        model.setViewName("social/redirectKakao");

        return model;
    }

}
