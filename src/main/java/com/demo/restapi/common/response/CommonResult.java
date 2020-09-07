package com.demo.restapi.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공 여부")
    private boolean success;

    @ApiModelProperty(value = "응답 코드")
    private int code;

    @ApiModelProperty(value = "응답 메시")
    private String message;
}
