package cn.iocoder.yudao.module.dify.util;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.dify.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.util.retry.Retry;

import jakarta.annotation.Resource;
import java.io.File;
import java.time.Duration;
import java.util.Map;

/**
 * Dify HTTP 客户端工具类
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class DifyHttpClient {

    @Resource
    private DifyProperties difyProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送 GET 请求
     *
     * @param url API 路径
     * @param apiKey API 密钥
     * @param responseClass 响应类型
     * @return 响应结果
     */
    public <T> T get(String url, String apiKey, Class<T> responseClass) {
        String fullUrl = difyProperties.getBaseUrl() + url;
        try {
            WebClient webClient = createWebClient(apiKey);
            log.info("Dify GET 请求开始: fullUrl={}, baseUrl={}, path={}", fullUrl, difyProperties.getBaseUrl(), url);

            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(difyProperties.getMaxRetries(), Duration.ofSeconds(5))
                            .maxBackoff(Duration.ofSeconds(30))
                            .filter(this::shouldRetry)
                            .doBeforeRetry(retrySignal -> {
                                Throwable failure = retrySignal.failure();
                                String errorDetail = failure.getMessage();
                                if (failure.getCause() != null) {
                                    errorDetail += " | 根本原因: " + failure.getCause().getMessage();
                                }
                                log.warn("Dify GET 请求重试: fullUrl={}, 重试次数={}, 异常详情={}",
                                    fullUrl, retrySignal.totalRetries() + 1, errorDetail);
                            }))
                    .timeout(Duration.ofSeconds(difyProperties.getTimeout()))
                    .block();

            log.info("Dify GET 请求成功: fullUrl={}", fullUrl);
            return objectMapper.readValue(response, responseClass);
        } catch (WebClientResponseException e) {
            log.error("Dify GET 请求失败: fullUrl={}, status={}, body={}", fullUrl, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "API 调用失败: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Dify GET 网络请求异常: fullUrl={}, message={}, rootCause={}",
                fullUrl, e.getMessage(), e.getRootCause() != null ? e.getRootCause().getMessage() : "无");
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "网络连接失败: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Dify 响应解析失败: fullUrl={}", fullUrl, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "响应解析失败");
        } catch (Exception e) {
            log.error("Dify 请求异常: fullUrl={}, message={}, cause={}, stackTrace={}",
                fullUrl, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "无",
                e.getClass().getSimpleName());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "请求异常: " + e.getMessage());
        }
    }

    /**
     * 发送 POST 请求（JSON 数据）
     *
     * @param url API 路径
     * @param apiKey API 密钥
     * @param requestBody 请求体
     * @param responseClass 响应类型
     * @return 响应结果
     */
    public <T> T post(String url, String apiKey, Object requestBody, Class<T> responseClass) {
        try {
            WebClient webClient = createWebClient(apiKey);
            log.debug("Dify POST 请求开始: url={}", url);

            String response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(requestBody), Object.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(difyProperties.getMaxRetries(), Duration.ofSeconds(5))
                            .maxBackoff(Duration.ofSeconds(30))
                            .filter(this::shouldRetry)
                            .doBeforeRetry(retrySignal ->
                                log.warn("Dify POST 请求重试: url={}, 重试次数={}, 异常={}",
                                    url, retrySignal.totalRetries() + 1, retrySignal.failure().getMessage())))
                    .timeout(Duration.ofSeconds(difyProperties.getTimeout()))
                    .block();

            log.debug("Dify POST 请求成功: url={}", url);
            return objectMapper.readValue(response, responseClass);
        } catch (WebClientResponseException e) {
            log.error("Dify POST 请求失败: url={}, status={}, body={}", url, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "API 调用失败: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Dify POST 网络请求异常: url={}, message={}", url, e.getMessage());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "网络连接失败: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Dify 响应解析失败: url={}", url, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "响应解析失败");
        } catch (Exception e) {
            log.error("Dify 请求异常: url={}, message={}, cause={}", url, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "无");
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "请求异常: " + e.getMessage());
        }
    }

    /**
     * 发送 POST 请求（文件上传）
     *
     * @param url API 路径
     * @param apiKey API 密钥
     * @param file 文件
     * @param data 额外数据
     * @param responseClass 响应类型
     * @return 响应结果
     */
    public <T> T postFile(String url, String apiKey, File file, Map<String, Object> data, Class<T> responseClass) {
        try {
            WebClient webClient = createFileUploadWebClient(apiKey);

            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("file", new FileSystemResource(file));

            if (data != null) {
                String dataJson = objectMapper.writeValueAsString(data);
                parts.add("data", dataJson);
            }

            log.debug("Dify 文件上传开始: url={}, fileName={}", url, file.getName());

            String response = webClient.post()
                    .uri(url)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(parts))
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(difyProperties.getMaxRetries(), Duration.ofSeconds(10))
                            .maxBackoff(Duration.ofSeconds(60))
                            .filter(this::shouldRetry)
                            .doBeforeRetry(retrySignal ->
                                log.warn("Dify 文件上传重试: url={}, fileName={}, 重试次数={}, 异常={}",
                                    url, file.getName(), retrySignal.totalRetries() + 1, retrySignal.failure().getMessage())))
                    .timeout(Duration.ofSeconds(difyProperties.getFileUploadTimeout()))
                    .block();

            log.debug("Dify 文件上传成功: url={}, fileName={}", url, file.getName());

            return objectMapper.readValue(response, responseClass);
        } catch (WebClientResponseException e) {
            log.error("Dify 文件上传失败: url={}, status={}, body={}", url, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "文件上传失败: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Dify 响应解析失败: url={}", url, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "响应解析失败");
        } catch (Exception e) {
            log.error("Dify 文件上传异常: url={}", url, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "文件上传异常: " + e.getMessage());
        }
    }

    /**
     * 发送 DELETE 请求
     *
     * @param url API 路径
     * @param apiKey API 密钥
     * @param responseClass 响应类型
     * @return 响应结果
     */
    public <T> T delete(String url, String apiKey, Class<T> responseClass) {
        try {
            WebClient webClient = createWebClient(apiKey);
            log.debug("Dify DELETE 请求开始: url={}", url);

            String response = webClient.delete()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .retryWhen(Retry.backoff(difyProperties.getMaxRetries(), Duration.ofSeconds(5))
                            .maxBackoff(Duration.ofSeconds(30))
                            .filter(this::shouldRetry)
                            .doBeforeRetry(retrySignal ->
                                log.warn("Dify DELETE 请求重试: url={}, 重试次数={}, 异常={}",
                                    url, retrySignal.totalRetries() + 1, retrySignal.failure().getMessage())))
                    .timeout(Duration.ofSeconds(difyProperties.getTimeout()))
                    .block();

            log.debug("Dify DELETE 请求成功: url={}", url);
            return objectMapper.readValue(response, responseClass);
        } catch (WebClientResponseException e) {
            log.error("Dify DELETE 请求失败: url={}, status={}, body={}", url, e.getStatusCode(), e.getResponseBodyAsString());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "API 调用失败: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Dify DELETE 网络请求异常: url={}, message={}", url, e.getMessage());
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "网络连接失败: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Dify 响应解析失败: url={}", url, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "响应解析失败");
        } catch (Exception e) {
            log.error("Dify 请求异常: url={}, message={}, cause={}", url, e.getMessage(), e.getCause() != null ? e.getCause().getMessage() : "无");
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "请求异常: " + e.getMessage());
        }
    }

    /**
     * 创建 WebClient
     *
     * @param apiKey API 密钥
     * @return WebClient 实例
     */
    private WebClient createWebClient(String apiKey) {
        // 创建连接池配置
        ConnectionProvider connectionProvider = ConnectionProvider.builder("dify-pool")
                .maxConnections(50)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120))
                .build();

        // 创建 HttpClient 配置
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .responseTimeout(Duration.ofSeconds(difyProperties.getTimeout()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // 连接超时30秒
                .followRedirect(true)
                .resolver(spec -> spec
                    .queryTimeout(Duration.ofSeconds(30)) // DNS查询超时30秒
                    .ndots(1) // 减少 DNS 查询次数
                ); // 使用系统默认 DNS

        WebClient.Builder builder = WebClient.builder()
                .baseUrl(difyProperties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "YudaoBot/1.0");

        // 如果使用 IP 地址作为 baseUrl，需要添加 Host 头
        String baseUrl = difyProperties.getBaseUrl();
        if (baseUrl.matches("https?://\\d+\\.\\d+\\.\\d+\\.\\d+.*")) {
            builder.defaultHeader(HttpHeaders.HOST, "api.dify.ai");
        }

        return builder.build();
    }

    /**
     * 创建用于文件上传的 WebClient（使用更长的超时时间）
     *
     * @param apiKey API 密钥
     * @return WebClient 实例
     */
    private WebClient createFileUploadWebClient(String apiKey) {
        // 创建连接池配置
        ConnectionProvider connectionProvider = ConnectionProvider.builder("dify-file-pool")
                .maxConnections(20)
                .maxIdleTime(Duration.ofSeconds(30))
                .maxLifeTime(Duration.ofSeconds(120))
                .pendingAcquireTimeout(Duration.ofSeconds(120))
                .evictInBackground(Duration.ofSeconds(180))
                .build();

        // 创建 HttpClient 配置，使用文件上传专用的超时时间
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .responseTimeout(Duration.ofSeconds(difyProperties.getFileUploadTimeout()))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000) // 文件上传连接超时60秒
                .followRedirect(true)
                .resolver(spec -> spec.queryTimeout(Duration.ofSeconds(30))); // DNS查询超时30秒

        return WebClient.builder()
                .baseUrl(difyProperties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.USER_AGENT, "YudaoBot/1.0")
                .build();
    }

    /**
     * 判断是否应该重试
     *
     * @param throwable 异常
     * @return 是否重试
     */
    private boolean shouldRetry(Throwable throwable) {
        // 网络连接异常，应该重试
        if (throwable instanceof WebClientRequestException) {
            return true;
        }

        // 超时异常，应该重试
        if (throwable instanceof java.util.concurrent.TimeoutException) {
            return true;
        }

        // 5xx 服务器错误，应该重试
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException responseException = (WebClientResponseException) throwable;
            int statusCode = responseException.getStatusCode().value();
            // 500-599 服务器错误和 429 限流错误可以重试
            return statusCode >= 500 || statusCode == 429;
        }

        // 其他异常不重试
        return false;
    }

    /**
     * 测试网络连接
     */
    public void testConnection() {
        try {
            log.info("开始测试 Dify 网络连接...");
            log.info("目标地址: {}", difyProperties.getBaseUrl());

            WebClient webClient = createWebClient("test-key");
            String response = webClient.get()
                    .uri("/")
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();

            log.info("网络连接测试成功");
        } catch (Exception e) {
            log.error("网络连接测试失败: message={}, cause={}, type={}",
                e.getMessage(),
                e.getCause() != null ? e.getCause().getMessage() : "无",
                e.getClass().getSimpleName());

            // 尝试解析具体的网络问题
            if (e.getMessage() != null) {
                if (e.getMessage().contains("Failed to resolve")) {
                    log.error("DNS 解析失败，请检查:");
                    log.error("1. 网络连接是否正常");
                    log.error("2. DNS 服务器配置是否正确");
                    log.error("3. 是否有防火墙阻止访问");
                } else if (e.getMessage().contains("Connection refused")) {
                    log.error("连接被拒绝，请检查:");
                    log.error("1. 目标服务是否可用");
                    log.error("2. 端口是否被阻止");
                } else if (e.getMessage().contains("timeout")) {
                    log.error("连接超时，请检查:");
                    log.error("1. 网络延迟是否过高");
                    log.error("2. 服务器响应是否缓慢");
                }
            }
        }
    }

    /**
     * 发送 POST 请求（流式响应）
     *
     * @param url API 路径
     * @param apiKey API 密钥
     * @param requestBody 请求体
     * @return 流式响应
     */
    public Flux<String> postStream(String url, String apiKey, Object requestBody) {
        try {
            WebClient webClient = createWebClient(apiKey);
            log.info("Dify POST 流式请求开始: url={}", url);

            return webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.TEXT_EVENT_STREAM) // 明确指定接受 SSE
                    .body(Mono.just(requestBody), Object.class)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(errorBody -> {
                                        log.error("Dify API 错误: status={}, body={}", response.statusCode(), errorBody);
                                        System.out.println("Dify API 错误: " + response.statusCode() + ", body: " + errorBody);
                                        return new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(),
                                                "Dify API 错误: " + response.statusCode() + " - " + errorBody);
                                    }))
                    .bodyToFlux(String.class)
                    .doOnNext(line -> {
                        log.info("收到原始流式数据: {}", line);
                        System.out.println("DifyHttpClient 收到原始数据: " + line);
                    })
                    .map(this::convertToSSE) // 转换为 SSE 格式
                    .filter(line -> !line.isEmpty())
                    .doOnNext(sseData -> {
                        log.info("转换为SSE格式: {}", sseData);
                        System.out.println("DifyHttpClient SSE数据: " + sseData);
                    })
                    .doOnComplete(() -> {
                        log.info("Dify POST 流式请求完成: url={}", url);
                        System.out.println("DifyHttpClient 流式请求完成");
                    })
                    .doOnError(error -> {
                        log.error("Dify POST 流式请求失败: url={}, error={}", url, error.getMessage());
                        System.out.println("DifyHttpClient 流式请求失败: " + error.getMessage());
                    });

        } catch (Exception e) {
            log.error("Dify 流式请求异常: url={}, message={}", url, e.getMessage());
            return Flux.error(new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "流式请求异常: " + e.getMessage()));
        }
    }

    /**
     * 将 JSON 数据转换为 SSE 格式
     *
     * @param jsonData 原始 JSON 数据
     * @return SSE 格式的数据
     */
    private String convertToSSE(String jsonData) {
        if (jsonData == null || jsonData.trim().isEmpty()) {
            return "";
        }

        // 将 JSON 数据转换为 SSE 格式
        String sseData = "data: " + jsonData.trim() + "\n\n";
        log.debug("转换JSON为SSE: {} -> {}", jsonData, sseData);
        return sseData;
    }

}
