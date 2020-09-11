package com.demo.restapi.controller.user;

import com.demo.restapi.common.response.CommonResult;
import com.demo.restapi.common.response.ListResult;
import com.demo.restapi.common.response.SingleResult;
import com.demo.restapi.common.service.ResponseService;
import com.demo.restapi.entity.User;
import com.demo.restapi.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "사용자 목록 조회")
    @GetMapping
    public ListResult<User> getAll() {
        return responseService.getListResult(userService.getAll());
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "사용자 상세 조회")
    @GetMapping(value = "/detail")
    public SingleResult<User> getOne() {
        // SecurityContext 에서 인증받은 사용자 정보 조회
        return responseService.getSingleResult(userService.getAuthOne());
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "사용자 정보 수정")
    @PutMapping
    public SingleResult<User> update(@ApiParam(value = "이름", required = true) @RequestParam String name) {
        return responseService.getSingleResult(userService.update(name));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "사용자 삭제")
    @DeleteMapping(value = "/{msrl}")
    public CommonResult delete(@ApiParam(value = "사용자 번호") @PathVariable long msrl) {
        userService.delete(msrl);
        return responseService.getSuccessResult();
    }

}
