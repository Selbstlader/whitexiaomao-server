package cn.iocoder.yudao.module.cooking.framework.web.config;

import cn.iocoder.yudao.framework.swagger.config.YudaoSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * cooking 模块的 web 组件的 Configuration
 *
 * @author 芋道源码
 */
@Configuration(proxyBeanMethods = false)
public class CookingWebConfiguration implements WebMvcConfigurer {

    /**
     * cooking 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi cookingGroupedOpenApi() {
        return YudaoSwaggerAutoConfiguration.buildGroupedOpenApi("cooking");
    }

    /**
     * 配置消息转换器，添加对text/json媒体类型的支持
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 查找MappingJackson2HttpMessageConverter
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                // 获取原有的MediaType列表
                List<MediaType> supportedMediaTypes = new ArrayList<>(jsonConverter.getSupportedMediaTypes());
                // 添加text/json媒体类型
                supportedMediaTypes.add(MediaType.valueOf("text/json"));
                // 设置支持的媒体类型
                jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
                break;
            }
        }
    }
}
