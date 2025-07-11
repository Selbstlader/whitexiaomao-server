package cn.iocoder.yudao.module.dify.framework.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import jakarta.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DifyProperties 配置测试类
 *
 * @author 芋道源码
 */
@SpringBootTest(classes = DifyPropertiesTest.TestConfiguration.class)
@TestPropertySource(properties = {
    "yudao.dify.enabled=true",
    "yudao.dify.base-url=https://api.dify.ai",
    "yudao.dify.chat-api-key=test-chat-key",
    "yudao.dify.api-key=test-api-key",
    "yudao.dify.timeout=120",
    "yudao.dify.file-upload-timeout=300",
    "yudao.dify.max-retries=3"
})
class DifyPropertiesTest {

    @Resource
    private DifyProperties difyProperties;

    @Test
    void testPropertiesBinding() {
        // 验证基础配置
        assertTrue(difyProperties.getEnabled());
        assertEquals("https://api.dify.ai", difyProperties.getBaseUrl());
        assertEquals("test-chat-key", difyProperties.getChatApiKey());
        assertEquals("test-api-key", difyProperties.getApiKey());
        
        // 验证超时配置
        assertEquals(120, difyProperties.getTimeout());
        assertEquals(300, difyProperties.getFileUploadTimeout());
        
        // 验证重试配置
        assertEquals(3, difyProperties.getMaxRetries());
    }

    @Test
    void testTimeoutValues() {
        // 验证超时时间已经延长
        assertTrue(difyProperties.getTimeout() >= 120, "普通请求超时时间应该至少为120秒");
        assertTrue(difyProperties.getFileUploadTimeout() >= 300, "文件上传超时时间应该至少为300秒");
        
        // 验证文件上传超时时间大于普通请求超时时间
        assertTrue(difyProperties.getFileUploadTimeout() > difyProperties.getTimeout(),
                "文件上传超时时间应该大于普通请求超时时间");
    }

    @EnableConfigurationProperties(DifyProperties.class)
    static class TestConfiguration {
    }
}
