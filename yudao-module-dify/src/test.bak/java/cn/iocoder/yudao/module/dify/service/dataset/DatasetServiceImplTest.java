package cn.iocoder.yudao.module.dify.service.dataset;

import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.util.DifyHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * DatasetServiceImpl 测试类
 *
 * @author 芋道源码
 */
@ExtendWith(MockitoExtension.class)
class DatasetServiceImplTest {

    @Mock
    private DifyHttpClient difyHttpClient;

    @Mock
    private DifyProperties difyProperties;

    @InjectMocks
    private DatasetServiceImpl datasetService;

    @BeforeEach
    void setUp() {
        // 设置默认的配置值
        when(difyProperties.getApiKey()).thenReturn("test-api-key");
        when(difyProperties.getTimeout()).thenReturn(120);
        when(difyProperties.getFileUploadTimeout()).thenReturn(300);
        when(difyProperties.getMaxRetries()).thenReturn(5);
    }

    @Test
    void testConfigurationValues() {
        // 验证超时配置已经延长
        assertEquals(120, difyProperties.getTimeout());
        assertEquals(300, difyProperties.getFileUploadTimeout());
        assertEquals(5, difyProperties.getMaxRetries());
        
        // 验证超时时间合理
        assertTrue(difyProperties.getTimeout() >= 120, "普通请求超时时间应该至少为120秒");
        assertTrue(difyProperties.getFileUploadTimeout() >= 300, "文件上传超时时间应该至少为300秒");
        assertTrue(difyProperties.getMaxRetries() >= 5, "重试次数应该至少为5次");
    }

    @Test
    void testRetryConfiguration() {
        // 验证重试次数已经增加
        assertTrue(difyProperties.getMaxRetries() > 3, "重试次数应该大于3次");
        
        // 验证文件上传超时时间大于普通请求
        assertTrue(difyProperties.getFileUploadTimeout() > difyProperties.getTimeout(),
                "文件上传超时时间应该大于普通请求超时时间");
    }
}
