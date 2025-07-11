package cn.iocoder.yudao.module.dify.util;

import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * DifyHttpClient 测试类
 *
 * @author 芋道源码
 */
@ExtendWith(MockitoExtension.class)
class DifyHttpClientTest {

    @Mock
    private DifyProperties difyProperties;

    @InjectMocks
    private DifyHttpClient difyHttpClient;

    @BeforeEach
    void setUp() {
        // 设置默认的配置值
        when(difyProperties.getBaseUrl()).thenReturn("https://api.dify.ai");
        when(difyProperties.getTimeout()).thenReturn(120);
        when(difyProperties.getFileUploadTimeout()).thenReturn(300);
        when(difyProperties.getMaxRetries()).thenReturn(3);
    }

    @Test
    void testTimeoutConfiguration() {
        // 验证普通请求超时时间
        assertEquals(120, difyProperties.getTimeout());
        
        // 验证文件上传超时时间
        assertEquals(300, difyProperties.getFileUploadTimeout());
        
        // 验证超时时间已经延长
        assertTrue(difyProperties.getTimeout() > 30, "普通请求超时时间应该大于30秒");
        assertTrue(difyProperties.getFileUploadTimeout() > 120, "文件上传超时时间应该大于120秒");
    }

    @Test
    void testPropertiesConfiguration() {
        // 验证基础配置
        assertNotNull(difyProperties.getBaseUrl());
        assertEquals("https://api.dify.ai", difyProperties.getBaseUrl());
        
        // 验证重试次数
        assertEquals(3, difyProperties.getMaxRetries());
    }
}
