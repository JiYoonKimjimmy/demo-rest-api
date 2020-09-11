package com.demo.restapi.controller.board;

import com.demo.restapi.common.aspect.LogExecutionTime;
import com.demo.restapi.common.response.ListResult;
import com.demo.restapi.common.response.SingleResult;
import com.demo.restapi.common.service.ResponseService;
import com.demo.restapi.entity.Board;
import com.demo.restapi.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. Board"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardController {

    private final ResponseService responseService;
    private final BoardService boardService;

    @LogExecutionTime
    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "게시글 등록")
    @PostMapping
    public SingleResult save(@RequestBody Board board) {
        return responseService.getSingleResult(boardService.save(board));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping
    public ListResult getAll() {
        return responseService.getListResult(boardService.getAll());
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "게시글 상세 조회")
    @GetMapping(value = "/{id}")
    public SingleResult getOne(@PathVariable Long id) {
        return responseService.getSingleResult(boardService.getOne(id));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "게시글 수정")
    @PutMapping
    public SingleResult update(@RequestBody Board board) {
        return responseService.getSingleResult(boardService.update(board));
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "인증 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping
    public SingleResult delete(@RequestBody Board board) {
        return responseService.getSingleResult(boardService.delete(board));
    }

}
