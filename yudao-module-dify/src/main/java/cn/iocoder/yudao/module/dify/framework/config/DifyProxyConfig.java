package cn.iocoder.yudao.module.dify.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;

/**
 * Dify 代理配置
 *
 * @author 芋道源码
 */
@Configuration
public class DifyProxyConfig {

    @Resource
    private DifyProperties difyProperties;

    /**
     * 创建用于 Dify API 代理的 RestTemplate
     */
    public RestTemplate createDifyRestTemplate() {
        // 创建请求工厂并设置超时时间
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000); // 连接超时 30 秒
        factory.setReadTimeout(difyProperties.getTimeout() * 1000); // 读取超时使用配置的时间

        RestTemplate restTemplate = new RestTemplate(factory);

        // 可以在这里添加拦截器来处理请求和响应
        restTemplate.getInterceptors().add((request, body, execution) -> {
            // 添加通用请求头
            request.getHeaders().add("User-Agent", "YudaoBot-Proxy/1.0");
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    /**
     * 创建用于文件上传的 RestTemplate（更长的超时时间）
     */
    public RestTemplate createFileUploadRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(60000); // 连接超时 60 秒
        factory.setReadTimeout(difyProperties.getFileUploadTimeout() * 1000); // 文件上传超时

        return new RestTemplate(factory);
    }
}
