package com.fang.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.common.utils.ResultUtil;
import com.fang.constants.ApiPathConstants;
import com.fang.domain.dto.BasePageDTO;
import com.fang.domain.dto.SysUserCreateDTO;
import com.fang.domain.entity.sys.SysUser;
import com.fang.exception.BusinessException;
import com.fang.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiPathConstants.SYS_USER)
@RequiredArgsConstructor
@Api(tags = "系统用户管理")
public class SysUserController {

    private final SysUserService sysUserService;

    @PostMapping("save")
    @ApiOperation("添加用户")
    public ResponseEntity<String> save(@RequestBody @Valid SysUserCreateDTO dto){
        dto.setId(null);
        sysUserService.saveEntity(dto);
        return ResultUtil.success("保存成功");
    }

    @DeleteMapping("remove")
    @ApiOperation("删除用户")
    public ResponseEntity<String> remove(String id){
        sysUserService.removeById(id);
        return ResultUtil.success("删除成功");
    }

    @PutMapping("modify")
    @ApiOperation("修改用户")
    public ResponseEntity<String> remove(@RequestBody @Valid SysUserCreateDTO dto){
        if(StringUtils.isBlank(dto.getId())){
            throw new BusinessException("id不可为空");
        }
        sysUserService.modifyEntity(dto);
        return ResultUtil.success("修改成功");
    }

    @GetMapping("page")
    @ApiOperation("用户分页")
    public ResponseEntity<IPage<SysUser>> page(@Valid BasePageDTO dto){
        Page<SysUser> page = new Page<>(dto.getPage(),dto.getRow());
        return ResultUtil.success(sysUserService.page(page,new LambdaQueryWrapper<SysUser>()
                .orderByDesc(SysUser::getCreatedDate)));
    }

    @GetMapping("detail")
    @ApiOperation("用户详情")
    public ResponseEntity<SysUser> detail(String id){
        return ResultUtil.success(sysUserService.getById(id));
    }

}
