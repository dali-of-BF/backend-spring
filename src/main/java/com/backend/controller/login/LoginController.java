package com.backend.controller.login;

import com.backend.common.result.Result;
import com.backend.constants.ApiPathConstants;
import com.backend.security.domain.LoginDTO;
import com.backend.security.domain.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author FPH
 * @since 2023年4月21日15:27:19
 */
@RestController
@RequestMapping(ApiPathConstants.AUTH)
@RequiredArgsConstructor
@Api(tags = "登录")
@Slf4j
public class LoginController {
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO login){
        log.info("登录参数：{}",login);
        return null;
    }

    @PostMapping("/logout")
    @ApiOperation("登录")
    public Result<LoginVO> logout() {
        log.info("成功退出登录");
        return null;
    }
}
