package cn.iocoder.yudao.module.cooking.framework.security.config;

import cn.iocoder.yudao.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * Cooking 模块的 Security 配置
 */
@Configuration(proxyBeanMethods = false, value = "cookingSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("cookingAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                // Cooking 模块的所有接口都允许访问，无需权限验证
                registry.requestMatchers(buildAppApi("/cooking/**")).permitAll();
                registry.requestMatchers(buildAdminApi("/cooking/**")).permitAll();
                // 文件访问接口 - 修正路径配置
                registry.requestMatchers("/admin-api/cooking/file/**").permitAll();
                // 确保所有cooking相关的请求都不需要认证
                registry.requestMatchers("/cooking/**").permitAll();
            }

        };
    }

}