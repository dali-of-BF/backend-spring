package com.fang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fang.common.utils.ResultUtil;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.dto.BasePageDTO;
import com.fang.domain.dto.SysAccountDTO;
import com.fang.domain.entity.sys.SysAccount;
import com.fang.service.sys.SysAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<IPage<SysAccount>> getPage(@Valid BasePageDTO dto){
        return ResultUtil.success(sysAccountService.getPage(dto.getPage(), dto.getRow()));
    }

    @PostMapping("save")
    @ApiOperation("保存")
    public ResponseEntity<IPage<SysAccount>> save(@RequestBody @Valid SysAccountDTO dto){
        sysAccountService.saveEntity(dto);
        return ResultUtil.success("保存成功");
    }
}
