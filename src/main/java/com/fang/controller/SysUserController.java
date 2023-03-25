package com.fang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fang.common.result.Result;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.dto.BasePageDTO;
import com.fang.domain.dto.SysAccountDTO;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.exception.BusinessException;
import com.fang.service.sys.SysAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author FPH
 * @since 2022年8月7日14:40:04
 */
@RestController
@RequestMapping(ApiPathConstants.SYS_ACCOUNT)
@RequiredArgsConstructor
@Api(tags = "系统用户管理")
public class SysUserController {
    private final SysAccountService sysAccountService;

    @GetMapping("page")
    @ApiOperation("分页")
    public Result<IPage<SysAccount>> getPage(@Valid BasePageDTO dto){
        return Result.success(sysAccountService.getPage(dto.getPage(), dto.getRow()));
    }

    @PostMapping("save")
    @ApiOperation("保存")
    public Result<String> save(@RequestBody @Valid SysAccountDTO dto){
        sysAccountService.saveEntity(dto);
        return Result.success("保存成功");
    }

    @DeleteMapping("delete")
    @ApiOperation("删除")
    public Result<String> delete(){
        throw new BusinessException("asd");
    }
}
