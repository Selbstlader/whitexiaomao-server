package cn.iocoder.yudao.module.douban.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.douban.controller.app.user.vo.*;
import cn.iocoder.yudao.module.douban.convert.user.UserAppConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.UserDO;
import cn.iocoder.yudao.module.douban.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 用户
 */
@Tag(name = "用户 App - 用户")
@RestController("appUserController")
@RequestMapping("/douban/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/get")
    @Operation(summary = "获得用户信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<UserRespVO> getUser(@RequestParam("id") Long id) {
        UserDO user = userService.getUser(id);
        return success(UserAppConvert.INSTANCE.convert(user));
    }

    @GetMapping("/get-by-username")
    @Operation(summary = "根据用户名获得用户信息")
    @Parameter(name = "username", description = "用户名", required = true)
    public CommonResult<UserRespVO> getUserByUsername(@RequestParam("username") String username) {
        UserDO user = userService.getUserByUsername(username);
        return success(UserAppConvert.INSTANCE.convert(user));
    }

    @GetMapping("/list")
    @Operation(summary = "获得用户列表")
    public CommonResult<List<UserRespVO>> getUserList(@Valid UserListReqVO listReqVO) {
        List<UserDO> list = userService.getUserList();
        return success(UserAppConvert.INSTANCE.convertList(list));
    }

}