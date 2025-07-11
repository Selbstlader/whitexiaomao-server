package cn.iocoder.yudao.module.dify.controller.admin.proxy;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.framework.config.DifyProxyConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Dify API 代理转发控制器
 * 
 * 解决前端无法直接访问 Dify API 的网络问题
 * 通过后端服务转发所有 Dify API 请求
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - Dify API 代理")
@RestController
@RequestMapping("/dify/proxy")
@Slf4j
public class DifyProxyController {

    @Resource
    private DifyProperties difyProperties;

    @Resource
    private DifyProxyConfig difyProxyConfig;

    private RestTemplate getRestTemplate() {
        return difyProxyConfig.createDifyRestTemplate();
    }

    private RestTemplate getFileUploadRestTemplate() {
        return difyProxyConfig.createFileUploadRestTemplate();
    }

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    @Operation(summary = "Dify API 代理转发")
    @PreAuthorize("@ss.hasPermission('dify:dataset:query')")
    public ResponseEntity<String> proxyDifyApi(
            HttpServletRequest request,
            @RequestBody(required = false) String body) {
        
        try {
            // 获取原始请求路径，移除 /dify/proxy 前缀
            String originalPath = request.getRequestURI().replace("/admin-api/dify/proxy", "");
            String queryString = request.getQueryString();
            
            // 构建目标 URL
            String targetUrl = difyProperties.getBaseUrl() + originalPath;
            if (queryString != null && !queryString.isEmpty()) {
                targetUrl += "?" + queryString;
            }
            
            log.info("代理转发 Dify API: {} {} -> {}", request.getMethod(), request.getRequestURI(), targetUrl);
            
            // 创建请求头
            HttpHeaders headers = new HttpHeaders();
            
            // 复制原始请求头（排除一些不需要的头）
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (!shouldSkipHeader(headerName)) {
                    String headerValue = request.getHeader(headerName);
                    headers.add(headerName, headerValue);
                }
            }
            
            // 设置 Dify API 认证头
            headers.set("Authorization", "Bearer " + difyProperties.getApiKey());
            headers.set("User-Agent", "YudaoBot/1.0");
            
            // 创建请求实体
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            
            // 发送请求
            ResponseEntity<String> response = getRestTemplate().exchange(
                URI.create(targetUrl),
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class
            );
            
            log.info("Dify API 响应: status={}, contentLength={}",
                response.getStatusCode(),
                response.getBody() != null ? response.getBody().length() : 0);

            // 记录响应内容用于调试
            log.debug("Dify API 响应内容: {}", response.getBody());

            // 返回响应，保持原始状态码和头信息
            HttpHeaders responseHeaders = new HttpHeaders();
            response.getHeaders().forEach((key, value) -> {
                if (!shouldSkipResponseHeader(key)) {
                    responseHeaders.put(key, value);
                }
            });

            // 确保返回正确的 Content-Type
            responseHeaders.set("Content-Type", "application/json;charset=UTF-8");

            return new ResponseEntity<>(response.getBody(), responseHeaders, response.getStatusCode());
            
        } catch (Exception e) {
            log.error("Dify API 代理转发失败: method={}, uri={}, error={}", 
                request.getMethod(), request.getRequestURI(), e.getMessage(), e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"代理转发失败: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/upload/**")
    @Operation(summary = "Dify 文件上传代理")
    @PreAuthorize("@ss.hasPermission('dify:dataset:create')")
    public ResponseEntity<String> proxyFileUpload(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "data", required = false) String data) {
        
        try {
            // 获取原始请求路径
            String originalPath = request.getRequestURI().replace("/admin-api/dify/proxy", "");
            String queryString = request.getQueryString();
            
            // 构建目标 URL
            String targetUrl = difyProperties.getBaseUrl() + originalPath;
            if (queryString != null && !queryString.isEmpty()) {
                targetUrl += "?" + queryString;
            }
            
            log.info("代理转发 Dify 文件上传: {} -> {}, fileName={}, fileSize={}", 
                request.getRequestURI(), targetUrl, file.getOriginalFilename(), file.getSize());
            
            // 创建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", "Bearer " + difyProperties.getApiKey());
            headers.set("User-Agent", "YudaoBot/1.0");
            
            // 创建多部分请求体
            MultiValueMap<String, Object> parts = new org.springframework.util.LinkedMultiValueMap<>();
            parts.add("file", file.getResource());
            if (data != null && !data.isEmpty()) {
                parts.add("data", data);
            }
            
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, headers);
            
            // 发送请求（使用文件上传专用的 RestTemplate）
            ResponseEntity<String> response = getFileUploadRestTemplate().exchange(
                URI.create(targetUrl),
                HttpMethod.POST,
                entity,
                String.class
            );
            
            log.info("Dify 文件上传响应: status={}, contentLength={}", 
                response.getStatusCode(), 
                response.getBody() != null ? response.getBody().length() : 0);
            
            return response;
            
        } catch (Exception e) {
            log.error("Dify 文件上传代理转发失败: uri={}, fileName={}, error={}", 
                request.getRequestURI(), file.getOriginalFilename(), e.getMessage(), e);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"文件上传代理转发失败: " + e.getMessage() + "\"}");
        }
    }

    /**
     * 判断是否应该跳过某个请求头
     */
    private boolean shouldSkipHeader(String headerName) {
        String lowerName = headerName.toLowerCase();
        return lowerName.equals("host") || 
               lowerName.equals("authorization") || 
               lowerName.equals("content-length") ||
               lowerName.equals("connection") ||
               lowerName.startsWith("x-forwarded");
    }

    /**
     * 判断是否应该跳过某个响应头
     */
    private boolean shouldSkipResponseHeader(String headerName) {
        String lowerName = headerName.toLowerCase();
        return lowerName.equals("transfer-encoding") ||
               lowerName.equals("connection") ||
               lowerName.equals("server");
    }

    @GetMapping("/test")
    @Operation(summary = "测试代理连接")
    @PreAuthorize("@ss.hasPermission('dify:dataset:query')")
    public CommonResult<Object> testProxy() {
        try {
            String testUrl = difyProperties.getBaseUrl() + "/v1/datasets?page=1&limit=1";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + difyProperties.getApiKey());
            headers.set("User-Agent", "YudaoBot-Test/1.0");

            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = getRestTemplate().exchange(
                URI.create(testUrl),
                HttpMethod.GET,
                entity,
                String.class
            );

            log.info("代理测试成功: status={}", response.getStatusCode());
            return CommonResult.success(response.getBody());

        } catch (Exception e) {
            log.error("代理测试失败: {}", e.getMessage(), e);
            return CommonResult.error(500, "代理测试失败: " + e.getMessage());
        }
    }
}
