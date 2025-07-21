package cn.iocoder.yudao.module.cooking.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Cooking 配置属性
 *
 * @author 芋道源码
 */
@ConfigurationProperties(prefix = "yudao.cooking")
@Validated
@Data
public class CookingProperties {

    /**
     * 是否启用烹饪功能
     */
    private Boolean enabled = true;

    /**
     * 营养分析API配置
     */
    private NutritionApi nutritionApi = new NutritionApi();

    /**
     * 图片配置
     */
    private Image image = new Image();

    @Data
    public static class NutritionApi {
        /**
         * 是否启用营养分析API
         */
        private Boolean enabled = true;

        /**
         * 营养数据提供商
         */
        private String provider = "default";
    }

    @Data
    public static class Image {
        /**
         * 图片最大大小（字节）
         */
        private Long maxSize = 10 * 1024 * 1024L; // 10MB

        /**
         * 允许的图片类型
         */
        private List<String> allowedTypes = List.of("jpg", "jpeg", "png", "gif");
    }

}
