package cn.iocoder.yudao.module.dify.framework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Dify 配置类
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DifyProperties.class)
public class DifyConfiguration {

}
