package cn.iocoder.yudao.module.cooking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 烹饪模块测试类
 *
 * @author 芋道源码
 */
@SpringBootTest
@ActiveProfiles("unit-test")
public class CookingModuleTest {

    @Test
    public void contextLoads() {
        // 测试Spring上下文是否能正常加载
        System.out.println("烹饪模块测试通过！");
    }

}
