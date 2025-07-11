package cn.iocoder.yudao.module.dify.controller.admin.chat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dify.controller.admin.chat.vo.*;
import cn.iocoder.yudao.module.dify.service.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder.getTenantId;

/**
 * 管理后台 - Dify 对话控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - Dify 对话")
@RestController
@RequestMapping("/dify/chat")
@Validated
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 获取当前客户的 Dify 用户标识
     *
     * 使用租户ID作为客户标识，确保同一客户下的所有用户共享相同的 Dify 用户标识
     *
     * @return Dify 用户标识
     */
    private String getCurrentDifyUser() {
        Long tenantId = getTenantId();
        if (tenantId != null) {
            return "client-" + tenantId;
        }
        // 如果没有租户ID，使用默认标识
        return "default-client";
    }

    @PostMapping("/send-message")
    @Operation(summary = "发送对话消息")
    @PreAuthorize("@ss.hasPermission('dify:chat:send')")
    public CommonResult<ChatMessageRespVO> sendMessage(@Valid @RequestBody ChatMessageSendReqVO reqVO) {
        // 自动设置用户标识，确保一致性
        reqVO.setUser(getCurrentDifyUser());

        // 如果是新对话（没有 conversation_id），则清空该字段
        if (reqVO.getConversationId() != null && reqVO.getConversationId().trim().isEmpty()) {
            reqVO.setConversationId(null);
        }

        // 为 Chatflow 应用设置 original_text 到 inputs 中
        if (reqVO.getInputs() == null) {
            reqVO.setInputs(new HashMap<>());
        }
        reqVO.getInputs().put("original_text", reqVO.getQuery());
        System.out.println("普通接口设置 inputs.original_text: " + reqVO.getQuery());

        ChatMessageRespVO response = chatService.sendMessage(reqVO);
        return success(response);
    }

    @GetMapping("/conversations")
    @Operation(summary = "获取会话列表")
    @PreAuthorize("@ss.hasPermission('dify:chat:query')")
    public CommonResult<ConversationListRespVO> getConversationList(
            @Parameter(description = "最后一个会话的 ID，用于分页") @RequestParam(value = "lastId", required = false) String lastId,
            @Parameter(description = "每页数量", example = "20") @RequestParam(value = "limit", defaultValue = "20") Integer limit) {

        ConversationListReqVO reqVO = new ConversationListReqVO();
        reqVO.setUser(getCurrentDifyUser());
        reqVO.setLastId(lastId);
        reqVO.setLimit(limit);

        ConversationListRespVO response = chatService.getConversationList(reqVO);
        return success(response);
    }

    @PostMapping("/stop/{taskId}")
    @Operation(summary = "停止对话生成")
    @PreAuthorize("@ss.hasPermission('dify:chat:send')")
    public CommonResult<ChatStopRespVO> stopMessage(
            @Parameter(description = "任务 ID", required = true) @PathVariable("taskId") String taskId,
            @Valid @RequestBody ChatStopReqVO reqVO) {

        reqVO.setTaskId(taskId);
        reqVO.setUser(getCurrentDifyUser()); // 自动设置用户标识
        ChatStopRespVO response = chatService.stopMessage(reqVO);
        return success(response);
    }

    @GetMapping("/messages")
    @Operation(summary = "获取消息历史")
    @PreAuthorize("@ss.hasPermission('dify:chat:query')")
    public CommonResult<MessageHistoryRespVO> getMessageHistory(
            @Parameter(description = "会话 ID", required = true) @RequestParam("conversationId") String conversationId,
            @Parameter(description = "第一条消息的 ID，用于分页") @RequestParam(value = "firstId", required = false) String firstId,
            @Parameter(description = "每页数量", example = "20") @RequestParam(value = "limit", defaultValue = "20") Integer limit) {

        // 验证会话ID不能为空
        if (conversationId == null || conversationId.trim().isEmpty()) {
            throw new IllegalArgumentException("会话ID不能为空");
        }

        MessageHistoryReqVO reqVO = new MessageHistoryReqVO();
        reqVO.setUser(getCurrentDifyUser());
        reqVO.setConversationId(conversationId);
        reqVO.setFirstId(firstId);
        reqVO.setLimit(limit);

        MessageHistoryRespVO response = chatService.getMessageHistory(reqVO);
        return success(response);
    }

    @GetMapping("/messages/{messageId}/suggested")
    @Operation(summary = "获取建议问题")
    @PreAuthorize("@ss.hasPermission('dify:chat:query')")
    public CommonResult<SuggestedQuestionsRespVO> getSuggestedQuestions(
            @Parameter(description = "消息 ID", required = true) @PathVariable("messageId") String messageId) {

        SuggestedQuestionsReqVO reqVO = new SuggestedQuestionsReqVO();
        reqVO.setMessageId(messageId);
        reqVO.setUser(getCurrentDifyUser());

        SuggestedQuestionsRespVO response = chatService.getSuggestedQuestions(reqVO);
        return success(response);
    }

    @DeleteMapping("/conversations/{conversationId}")
    @Operation(summary = "删除会话")
    @PreAuthorize("@ss.hasPermission('dify:chat:delete')")
    public CommonResult<Boolean> deleteConversation(
            @Parameter(description = "会话 ID", required = true) @PathVariable("conversationId") String conversationId) {

        ConversationDeleteReqVO reqVO = new ConversationDeleteReqVO();
        reqVO.setConversationId(conversationId);
        reqVO.setUser(getCurrentDifyUser());

        chatService.deleteConversation(reqVO);
        return success(true);
    }

    @PostMapping("/conversations/{conversationId}/rename")
    @Operation(summary = "重命名会话")
    @PreAuthorize("@ss.hasPermission('dify:chat:update')")
    public CommonResult<ConversationRespVO> renameConversation(
            @Parameter(description = "会话 ID", required = true) @PathVariable("conversationId") String conversationId,
            @Valid @RequestBody ConversationRenameReqVO reqVO) {

        reqVO.setConversationId(conversationId);
        reqVO.setUser(getCurrentDifyUser()); // 自动设置用户标识
        ConversationRespVO response = chatService.renameConversation(reqVO);
        return success(response);
    }

    @PostMapping("/messages/{messageId}/feedback")
    @Operation(summary = "消息反馈")
    @PreAuthorize("@ss.hasPermission('dify:chat:send')")
    public CommonResult<MessageFeedbackRespVO> messageFeedback(
            @Parameter(description = "消息 ID", required = true) @PathVariable("messageId") String messageId,
            @Valid @RequestBody MessageFeedbackReqVO reqVO) {

        reqVO.setMessageId(messageId);
        reqVO.setUser(getCurrentDifyUser()); // 自动设置用户标识
        MessageFeedbackRespVO response = chatService.messageFeedback(reqVO);
        return success(response);
    }

    @PostMapping(value = "/send-message-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "发送对话消息（流式响应）")
    @PreAuthorize("@ss.hasPermission('dify:chat:send')")
    public ResponseEntity<Flux<String>> sendMessageStream(@Valid @RequestBody ChatMessageSendReqVO reqVO) {
        // 自动设置用户标识，确保一致性
        reqVO.setUser(getCurrentDifyUser());
        // 强制设置为流式模式
        reqVO.setResponseMode("streaming");

        // 如果是新对话（没有 conversation_id），则清空该字段
        if (reqVO.getConversationId() != null && reqVO.getConversationId().trim().isEmpty()) {
            reqVO.setConversationId(null);
        }

        // 为 Chatflow 应用设置 original_text 到 inputs 中
        if (reqVO.getInputs() == null) {
            reqVO.setInputs(new HashMap<>());
        }
        reqVO.getInputs().put("original_text", reqVO.getQuery());
        System.out.println("设置 inputs.original_text: " + reqVO.getQuery());

        Flux<String> stream = chatService.sendMessageStream(reqVO)
                .doOnNext(data -> System.out.println("Controller 发送数据: " + data))
                .doOnComplete(() -> System.out.println("Controller 流式响应完成"))
                .doOnError(error -> System.out.println("Controller 流式响应错误: " + error.getMessage()));

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(stream);
    }

    @GetMapping(value = "/test-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "测试流式响应")
    public Flux<String> testStream() {
        return Flux.just("data: {\"test\": \"message1\"}\n", "data: {\"test\": \"message2\"}\n", "data: {\"test\": \"message3\"}\n")
                .delayElements(java.time.Duration.ofSeconds(1))
                .doOnNext(data -> System.out.println("测试发送数据: " + data));
    }

    @PostMapping("/test-dify-format")
    @Operation(summary = "测试不同的 Dify 请求格式")
    @PreAuthorize("@ss.hasPermission('dify:chat:send')")
    public CommonResult<String> testDifyFormat(@Valid @RequestBody ChatMessageSendReqVO reqVO) {
        // 尝试不同的请求格式
        Map<String, Object> testRequest1 = new HashMap<>();
        testRequest1.put("query", reqVO.getQuery());
        testRequest1.put("inputs", reqVO.getInputs() != null ? reqVO.getInputs() : new HashMap<>());
        testRequest1.put("response_mode", "blocking");
        testRequest1.put("user", getCurrentDifyUser());
        if (reqVO.getConversationId() != null && !reqVO.getConversationId().trim().isEmpty()) {
            testRequest1.put("conversation_id", reqVO.getConversationId());
        }

        Map<String, Object> testRequest2 = new HashMap<>();
        testRequest2.put("original_text", reqVO.getQuery()); // 尝试 original_text
        testRequest2.put("inputs", reqVO.getInputs() != null ? reqVO.getInputs() : new HashMap<>());
        testRequest2.put("response_mode", "blocking");
        testRequest2.put("user", getCurrentDifyUser());
        if (reqVO.getConversationId() != null && !reqVO.getConversationId().trim().isEmpty()) {
            testRequest2.put("conversation_id", reqVO.getConversationId());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String format1 = mapper.writeValueAsString(testRequest1);
            String format2 = mapper.writeValueAsString(testRequest2);

            String result = String.format(
                "格式1 (query): %s\n\n格式2 (original_text): %s",
                format1, format2
            );

            System.out.println("测试格式:");
            System.out.println(result);

            return success(result);
        } catch (Exception e) {
            return success("格式化失败: " + e.getMessage());
        }
    }

}
