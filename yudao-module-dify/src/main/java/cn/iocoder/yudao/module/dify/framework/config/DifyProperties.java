package cn.iocoder.yudao.module.dify.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Dify 配置属性
 *
 * @author 芋道源码
 */
@ConfigurationProperties(prefix = "yudao.dify")
@Validated
@Data
public class DifyProperties {

    /**
     * 是否启用 Dify 功能
     */
    private Boolean enabled = true;

    /**
     * Dify API 基础地址
     */
    @NotBlank(message = "Dify API 基础地址不能为空")
    private String baseUrl = "https://api.dify.ai";

    /**
     * 对话 API 密钥
     */
    @NotBlank(message = "对话 API 密钥不能为空")
    private String chatApiKey;

    /**
     * 数据集级别 API 密钥
     */
    @NotBlank(message = "数据集 API 密钥不能为空")
    private String apiKey;

    /**
     * 请求超时时间（秒）
     */
    private Integer timeout = 180;

    /**
     * 文件上传超时时间（秒）
     */
    private Integer fileUploadTimeout = 600;

    /**
     * 最大重试次数
     */
    private Integer maxRetries = 8;

}
