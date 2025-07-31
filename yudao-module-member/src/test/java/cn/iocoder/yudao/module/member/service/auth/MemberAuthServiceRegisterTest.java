package cn.iocoder.yudao.module.member.service.auth;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.member.controller.app.auth.vo.AppAuthRegisterReqVO;
import cn.iocoder.yudao.module.member.controller.app.auth.vo.AppAuthRegisterRespVO;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import jakarta.annotation.Resource;

import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * {@link MemberAuthServiceImpl} 的单元测试类 - 注册功能
 *
 * @author 芋道源码
 */
@Import(MemberAuthServiceImpl.class)
class MemberAuthServiceRegisterTest extends BaseDbUnitTest {

    @Resource
    private MemberAuthService authService;

    @MockBean
    private MemberUserService userService;

    @Test
    public void testRegister_success() {
        // 准备参数
        AppAuthRegisterReqVO reqVO = new AppAuthRegisterReqVO();
        reqVO.setUsername("testuser");
        reqVO.setPassword("123456");
        reqVO.setConfirmPassword("123456");
        reqVO.setNickname("测试用户");

        // mock 方法
        when(userService.getUserByMobile(eq("testuser"))).thenReturn(null); // 用户不存在
        MemberUserDO mockUser = randomPojo(MemberUserDO.class, o -> {
            o.setId(1L);
            o.setMobile("testuser");
            o.setNickname("测试用户");
        });
        when(userService.createUserIfAbsent(eq("testuser"), eq("127.0.0.1"), eq(1)))
                .thenReturn(mockUser);
        when(userService.getUser(eq(1L))).thenReturn(mockUser);

        // 调用
        AppAuthRegisterRespVO result = authService.register(reqVO);

        // 断言
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("测试用户", result.getNickname());
    }

    @Test
    public void testRegister_passwordNotMatch() {
        // 准备参数
        AppAuthRegisterReqVO reqVO = new AppAuthRegisterReqVO();
        reqVO.setUsername("testuser");
        reqVO.setPassword("123456");
        reqVO.setConfirmPassword("654321"); // 密码不一致
        reqVO.setNickname("测试用户");

        // 调用并断言异常
        assertThrows(Exception.class, () -> authService.register(reqVO));
    }

    @Test
    public void testRegister_usernameExists() {
        // 准备参数
        AppAuthRegisterReqVO reqVO = new AppAuthRegisterReqVO();
        reqVO.setUsername("existuser");
        reqVO.setPassword("123456");
        reqVO.setConfirmPassword("123456");
        reqVO.setNickname("测试用户");

        // mock 方法 - 用户已存在
        MemberUserDO existUser = randomPojo(MemberUserDO.class);
        when(userService.getUserByMobile(eq("existuser"))).thenReturn(existUser);

        // 调用并断言异常
        assertThrows(Exception.class, () -> authService.register(reqVO));
    }
}