package cn.iocoder.yudao.module.dify.controller.admin.debug;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.util.DifyHttpClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Dify 调试控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - Dify 调试")
@RestController
@RequestMapping("/dify/debug")
@Slf4j
public class DifyDebugController {

    @Resource
    private DifyHttpClient difyHttpClient;

    @Resource
    private DifyProperties difyProperties;

    @GetMapping("/network-test")
    @Operation(summary = "网络连接测试")
    @PreAuthorize("@ss.hasPermission('dify:dataset:query')")
    public CommonResult<Map<String, Object>> networkTest() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("开始 Dify 网络诊断...");
            
            // 1. 基本配置信息
            result.put("baseUrl", difyProperties.getBaseUrl());
            result.put("timeout", difyProperties.getTimeout());
            result.put("maxRetries", difyProperties.getMaxRetries());
            
            // 2. DNS 解析测试
            try {
                String host = difyProperties.getBaseUrl().replace("https://", "").replace("http://", "");
                InetAddress address = InetAddress.getByName(host);
                result.put("dnsResolution", "成功");
                result.put("resolvedIp", address.getHostAddress());
                log.info("DNS 解析成功: {} -> {}", host, address.getHostAddress());
            } catch (Exception e) {
                result.put("dnsResolution", "失败: " + e.getMessage());
                log.error("DNS 解析失败", e);
            }
            
            // 3. 网络连接测试
            try {
                difyHttpClient.testConnection();
                result.put("connectionTest", "成功");
            } catch (Exception e) {
                result.put("connectionTest", "失败: " + e.getMessage());
                log.error("连接测试失败", e);
            }
            
            // 4. 系统网络信息
            try {
                result.put("localHost", InetAddress.getLocalHost().getHostAddress());
            } catch (Exception e) {
                result.put("localHost", "获取失败: " + e.getMessage());
            }
            
            // 5. Java 网络属性
            Map<String, String> networkProps = new HashMap<>();
            networkProps.put("java.net.useSystemProxies", System.getProperty("java.net.useSystemProxies", "未设置"));
            networkProps.put("http.proxyHost", System.getProperty("http.proxyHost", "未设置"));
            networkProps.put("http.proxyPort", System.getProperty("http.proxyPort", "未设置"));
            networkProps.put("https.proxyHost", System.getProperty("https.proxyHost", "未设置"));
            networkProps.put("https.proxyPort", System.getProperty("https.proxyPort", "未设置"));
            result.put("networkProperties", networkProps);
            
            log.info("网络诊断完成");
            return CommonResult.success(result);
            
        } catch (Exception e) {
            log.error("网络诊断异常", e);
            result.put("error", e.getMessage());
            return CommonResult.error(500, "网络诊断失败");
        }
    }

    @GetMapping("/config-info")
    @Operation(summary = "配置信息查看")
    @PreAuthorize("@ss.hasPermission('dify:dataset:query')")
    public CommonResult<Map<String, Object>> configInfo() {
        Map<String, Object> result = new HashMap<>();
        
        result.put("enabled", difyProperties.getEnabled());
        result.put("baseUrl", difyProperties.getBaseUrl());
        result.put("timeout", difyProperties.getTimeout());
        result.put("fileUploadTimeout", difyProperties.getFileUploadTimeout());
        result.put("maxRetries", difyProperties.getMaxRetries());
        
        // 隐藏敏感信息
        String chatApiKey = difyProperties.getChatApiKey();
        String apiKey = difyProperties.getApiKey();
        
        result.put("chatApiKey", chatApiKey != null ? maskApiKey(chatApiKey) : "未配置");
        result.put("apiKey", apiKey != null ? maskApiKey(apiKey) : "未配置");
        
        return CommonResult.success(result);
    }
    
    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() < 8) {
            return "***";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }
}
