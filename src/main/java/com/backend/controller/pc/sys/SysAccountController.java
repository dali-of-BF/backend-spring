package com.backend.controller.pc.sys;

import com.backend.annotation.Log;
import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.domain.dto.sys.SysAccountPwdChangeDTO;
import com.backend.domain.entity.base.BasePageDTO;
import com.backend.domain.entity.sys.SysAccount;
import com.backend.service.sys.SysAccountService;
import com.backend.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@Log
public class SysAccountController {
    private final SysAccountService sysAccountService;

    @GetMapping("page")
    @ApiOperation("分页")
    public Result<IPage<SysAccount>> getPage(@Valid BasePageDTO dto){
        return Result.success(sysAccountService.getPage(dto.getPage(), dto.getSize()));
    }

    @DeleteMapping("deleteById/{id}")
    @ApiOperation("通过用户ID删除用户")
    public Result<String> deleteById(@PathVariable("id") String id){
        sysAccountService.removeById(id);
        return Result.success("删除成功");
    }

    @PostMapping("resetPassword")
    @ApiOperation("重置用户密码")
    public Result<String> resetPassword(String id){
        sysAccountService.resetPassword(id);
        return Result.success("重置成功");
    }

    @PostMapping("changePassword")
    @ApiOperation("修改用户密码")
    public Result<String> changePassword(@Valid @RequestBody SysAccountPwdChangeDTO dto){
        sysAccountService.changePassword(SecurityUtils.getAccountId(),dto.getNewPassword(), dto.getOldPassword());
        return Result.success("修改成功");
    }

//    @PostMapping("update")
//    @ApiOperation("修改用户信息")
//    public Result<String> updateUserInfo(@Valid @RequestBody SysAccountPwdChangeDTO dto){
//        sysAccountService.updateUserInfo();
//        return Result.success("修改成功");
//    }
}
