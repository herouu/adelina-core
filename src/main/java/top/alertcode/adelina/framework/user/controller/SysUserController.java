package top.alertcode.adelina.framework.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alertcode.adelina.framework.controller.BaseController;
import top.alertcode.adelina.framework.user.entity.SysUser;
import top.alertcode.adelina.framework.user.service.impl.SysUserServiceImpl;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author bob
 * @since 2019-06-12
 */
@Api(tags = "系统用户表")
@RestController
@RequestMapping("/user/sys-user")
public class SysUserController extends BaseController<SysUserServiceImpl, SysUser> {

}

