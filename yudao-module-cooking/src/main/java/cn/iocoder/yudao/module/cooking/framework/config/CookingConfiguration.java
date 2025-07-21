package cn.iocoder.yudao.module.cooking.framework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cooking 配置类
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CookingProperties.class)
public class CookingConfiguration {

}
