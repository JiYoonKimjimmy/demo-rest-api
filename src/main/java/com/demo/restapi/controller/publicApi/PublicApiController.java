package com.demo.restapi.controller.publicApi;

import com.demo.restapi.common.advice.exception.PublicApiFailException;
import com.demo.restapi.common.aspect.LogExecutionTime;
import com.demo.restapi.common.response.CommonResult;
import com.demo.restapi.common.response.ListResult;
import com.demo.restapi.common.service.ResponseService;
import com.demo.restapi.service.publicApi.CarWashService;
import com.demo.restapi.service.publicApi.ParkPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@Api(tags = {"4. Public-Api"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class PublicApiController {

    private final ParkPlaceService parkPlaceService;
    private final CarWashService carWashService;
    private final ResponseService responseService;

    @LogExecutionTime
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "공공 API 데이터 가져오기")
    @GetMapping(value = "/pullData/{type}")
    public CommonResult pullData(@PathVariable String type) {
        if (executeMethod(type, "pullData") != null) {
            return responseService.getSuccessResult();
        } else {
            throw new PublicApiFailException();
        }
    }

    @LogExecutionTime
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "공공 API 전체 목록 조회")
    @GetMapping(value = "/{type}")
    public ListResult getAll(@PathVariable String type) {
        return responseService.getListResult((List) executeMethod(type, "getAll"));
    }

    /**
     * 동적 service method 처리
     * @param apiType
     * @param methodName
     * @return
     */
    public Object executeMethod(String apiType, String methodName) {
        Object service = null;

        try {
            if ("park-place".equals(apiType)) service = parkPlaceService;
            if ("car-wash".equals(apiType)) service = carWashService;

            Class<?> serviceClass = service.getClass();
            Method method = serviceClass.getMethod(methodName);

            return method.invoke(service);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
