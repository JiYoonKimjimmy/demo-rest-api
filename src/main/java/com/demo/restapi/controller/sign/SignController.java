package com.demo.restapi.controller.sign;

import com.demo.restapi.common.advice.exception.CEmailSigninFailedException;
import com.demo.restapi.common.advice.exception.CUserExistException;
import com.demo.restapi.common.advice.exception.CUserNotFoundException;
import com.demo.restapi.common.response.CommonResult;
import com.demo.restapi.common.response.SingleResult;
import com.demo.restapi.common.service.ResponseService;
import com.demo.restapi.config.security.JwtTokenProvider;
import com.demo.restapi.entity.User;
import com.demo.restapi.model.social.KakaoProfile;
import com.demo.restapi.service.common.KakaoService;
import com.demo.restapi.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/sign")
public class SignController {

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final ResponseService responseService;
    private final UserService userService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "가입")
    @PostMapping(value = "/signup")
    public CommonResult signup(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "이름", required = true) @RequestParam String name
    ) {
        User user = User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userService.save(user);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그인")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(
            @ApiParam(value = "회원 ID", required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password
    ) {
        User user = userService.getOne(id);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CEmailSigninFailedException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 로그인 가입")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupByProvider(
            @ApiParam(value = "서비스 제공사", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "ACCESS_TOKEN", required = true) @RequestParam String accessToken,
            @ApiParam(value = "이름", required = true) @RequestParam String name
    ) {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);
        Optional<User> user = userService.getSocialOne(String.valueOf(kakaoProfile.getId()), provider);

        if (user.isPresent()) {
            throw new CUserExistException();
        }

        User param = User.builder()
                .uid(String.valueOf(kakaoProfile.getId()))
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userService.save(param);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 로그인 요청")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "서비스 제공사", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "ACCESS_TOKEN", required = true) @RequestParam String accessToken
    ) {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);

        User user = userService.getSocialOne(String.valueOf(kakaoProfile.getId()), provider).orElseThrow(CUserNotFoundException::new);

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

}
