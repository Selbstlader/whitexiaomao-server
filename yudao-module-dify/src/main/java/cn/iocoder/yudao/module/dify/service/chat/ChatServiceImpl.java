package cn.iocoder.yudao.module.dify.service.chat;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.dify.controller.admin.chat.vo.*;
import cn.iocoder.yudao.module.dify.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.util.DifyHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 对话服务实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Resource
    private DifyHttpClient difyHttpClient;

    @Resource
    private DifyProperties difyProperties;

    @Override
    public ChatMessageRespVO sendMessage(ChatMessageSendReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);
        
        try {
            // 构建请求参数
            Map<String, Object> requestBody = buildChatRequest(reqVO);
            
            // 调用 Dify API
            String url = "/v1/chat-messages";
            ChatMessageRespVO response = difyHttpClient.post(url, difyProperties.getChatApiKey(), 
                    requestBody, ChatMessageRespVO.class);
            
            log.info("Dify 对话消息发送成功: conversationId={}, messageId={}", 
                    response.getConversationId(), response.getId());
            
            return response;
        } catch (ServiceException e) {
            log.error("Dify 对话消息发送失败: query={}, user={}", reqVO.getQuery(), reqVO.getUser(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 对话消息发送异常: query={}, user={}", reqVO.getQuery(), reqVO.getUser(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_MESSAGE_SEND_FAIL.getCode(), "发送对话消息失败: " + e.getMessage());
        }
    }

    /**
     * 构建对话请求参数
     *
     * @param reqVO 请求参数
     * @return 请求体
     */
    private Map<String, Object> buildChatRequest(ChatMessageSendReqVO reqVO) {
        Map<String, Object> requestBody = new HashMap<>();

        // 必填参数
        requestBody.put("query", reqVO.getQuery());
        requestBody.put("user", reqVO.getUser());
        requestBody.put("response_mode", reqVO.getResponseMode());

        // 输入参数，默认为空对象
        if (reqVO.getInputs() != null) {
            requestBody.put("inputs", reqVO.getInputs());
        } else {
            requestBody.put("inputs", new HashMap<>());
        }

        // 可选参数
        if (reqVO.getConversationId() != null && !reqVO.getConversationId().trim().isEmpty()) {
            requestBody.put("conversation_id", reqVO.getConversationId());
        }

        if (reqVO.getFiles() != null && !reqVO.getFiles().isEmpty()) {
            requestBody.put("files", reqVO.getFiles());
        }

        if (reqVO.getAutoGenerateName() != null) {
            requestBody.put("auto_generate_name", reqVO.getAutoGenerateName());
        }

        return requestBody;
    }

    @Override
    public ConversationListRespVO getConversationList(ConversationListReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求 URL
            StringBuilder urlBuilder = new StringBuilder("/v1/conversations");
            urlBuilder.append("?user=").append(reqVO.getUser());

            if (reqVO.getLastId() != null && !reqVO.getLastId().trim().isEmpty()) {
                urlBuilder.append("&last_id=").append(reqVO.getLastId());
            }

            if (reqVO.getLimit() != null) {
                urlBuilder.append("&limit=").append(reqVO.getLimit());
            }

            String url = urlBuilder.toString();

            // 调用 Dify API
            ConversationListRespVO response = difyHttpClient.get(url, difyProperties.getChatApiKey(),
                    ConversationListRespVO.class);

            log.info("Dify 获取会话列表成功: user={}, count={}", reqVO.getUser(),
                    response.getData() != null ? response.getData().size() : 0);

            return response;
        } catch (ServiceException e) {
            log.error("Dify 获取会话列表失败: user={}", reqVO.getUser(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 获取会话列表异常: user={}", reqVO.getUser(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_CONVERSATION_NOT_FOUND.getCode(), "获取会话列表失败: " + e.getMessage());
        }
    }

    @Override
    public ChatStopRespVO stopMessage(ChatStopReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user", reqVO.getUser());

            // 调用 Dify API
            String url = "/v1/chat-messages/" + reqVO.getTaskId() + "/stop";
            ChatStopRespVO response = difyHttpClient.post(url, difyProperties.getChatApiKey(),
                    requestBody, ChatStopRespVO.class);

            log.info("Dify 停止对话生成成功: taskId={}", reqVO.getTaskId());

            return response;
        } catch (ServiceException e) {
            log.error("Dify 停止对话生成失败: taskId={}", reqVO.getTaskId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 停止对话生成异常: taskId={}", reqVO.getTaskId(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_MESSAGE_SEND_FAIL.getCode(), "停止对话生成失败: " + e.getMessage());
        }
    }

    @Override
    public MessageHistoryRespVO getMessageHistory(MessageHistoryReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求 URL
            StringBuilder urlBuilder = new StringBuilder("/v1/messages");
            urlBuilder.append("?user=").append(reqVO.getUser());
            urlBuilder.append("&conversation_id=").append(reqVO.getConversationId());

            if (reqVO.getFirstId() != null && !reqVO.getFirstId().trim().isEmpty()) {
                urlBuilder.append("&first_id=").append(reqVO.getFirstId());
            }

            if (reqVO.getLimit() != null) {
                urlBuilder.append("&limit=").append(reqVO.getLimit());
            }

            String url = urlBuilder.toString();

            // 调用 Dify API
            MessageHistoryRespVO response = difyHttpClient.get(url, difyProperties.getChatApiKey(),
                    MessageHistoryRespVO.class);

            log.info("Dify 获取消息历史成功: conversationId={}, count={}", reqVO.getConversationId(),
                    response.getData() != null ? response.getData().size() : 0);

            return response;
        } catch (ServiceException e) {
            log.error("Dify 获取消息历史失败: conversationId={}", reqVO.getConversationId(), e);
            // 如果是404错误，说明会话不存在，返回更友好的错误信息
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                throw new ServiceException(ErrorCodeConstants.CHAT_CONVERSATION_NOT_FOUND.getCode(),
                    "会话不存在或已被删除，请检查会话ID是否正确");
            }
            throw e;
        } catch (Exception e) {
            log.error("Dify 获取消息历史异常: conversationId={}", reqVO.getConversationId(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_CONVERSATION_NOT_FOUND.getCode(), "获取消息历史失败: " + e.getMessage());
        }
    }

    @Override
    public SuggestedQuestionsRespVO getSuggestedQuestions(SuggestedQuestionsReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求 URL
            String url = "/v1/messages/" + reqVO.getMessageId() + "/suggested?user=" + reqVO.getUser();

            // 调用 Dify API
            SuggestedQuestionsRespVO response = difyHttpClient.get(url, difyProperties.getChatApiKey(),
                    SuggestedQuestionsRespVO.class);

            log.info("Dify 获取建议问题成功: messageId={}, count={}", reqVO.getMessageId(),
                    response.getData() != null ? response.getData().size() : 0);

            return response;
        } catch (ServiceException e) {
            log.error("Dify 获取建议问题失败: messageId={}", reqVO.getMessageId(), e);
            // 如果是建议问题功能被禁用，返回更友好的错误信息
            if (e.getMessage() != null && e.getMessage().contains("Suggested Questions Is Disabled")) {
                throw new ServiceException(ErrorCodeConstants.CHAT_SUGGESTED_QUESTIONS_FAILED.getCode(),
                    "建议问题功能未启用，请在 Dify 应用设置中启用此功能");
            }
            throw e;
        } catch (Exception e) {
            log.error("Dify 获取建议问题异常: messageId={}", reqVO.getMessageId(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_MESSAGE_SEND_FAIL.getCode(), "获取建议问题失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteConversation(ConversationDeleteReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求 URL
            String url = "/v1/conversations/" + reqVO.getConversationId();

            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user", reqVO.getUser());

            // 调用 Dify API
            difyHttpClient.delete(url, difyProperties.getChatApiKey(), Object.class);

            log.info("Dify 删除会话成功: conversationId={}", reqVO.getConversationId());

        } catch (ServiceException e) {
            log.error("Dify 删除会话失败: conversationId={}", reqVO.getConversationId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 删除会话异常: conversationId={}", reqVO.getConversationId(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_CONVERSATION_NOT_FOUND.getCode(), "删除会话失败: " + e.getMessage());
        }
    }

    @Override
    public ConversationRespVO renameConversation(ConversationRenameReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("name", reqVO.getName());
            requestBody.put("user", reqVO.getUser());
            requestBody.put("auto_generate", reqVO.getAutoGenerate());

            // 调用 Dify API
            String url = "/v1/conversations/" + reqVO.getConversationId() + "/name";
            ConversationRespVO response = difyHttpClient.post(url, difyProperties.getChatApiKey(),
                    requestBody, ConversationRespVO.class);

            log.info("Dify 重命名会话成功: conversationId={}, newName={}", reqVO.getConversationId(), reqVO.getName());

            return response;
        } catch (ServiceException e) {
            log.error("Dify 重命名会话失败: conversationId={}, newName={}", reqVO.getConversationId(), reqVO.getName(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 重命名会话异常: conversationId={}, newName={}", reqVO.getConversationId(), reqVO.getName(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_CONVERSATION_NOT_FOUND.getCode(), "重命名会话失败: " + e.getMessage());
        }
    }

    @Override
    public MessageFeedbackRespVO messageFeedback(MessageFeedbackReqVO reqVO) {
        // 参数校验
        ValidationUtils.validate(reqVO);

        try {
            // 构建请求参数
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user", reqVO.getUser());
            if (reqVO.getRating() != null) {
                requestBody.put("rating", reqVO.getRating());
            }

            // 调用 Dify API
            String url = "/v1/messages/" + reqVO.getMessageId() + "/feedbacks";
            MessageFeedbackRespVO response = difyHttpClient.post(url, difyProperties.getChatApiKey(),
                    requestBody, MessageFeedbackRespVO.class);

            log.info("Dify 消息反馈成功: messageId={}, rating={}", reqVO.getMessageId(), reqVO.getRating());

            return response;
        } catch (ServiceException e) {
            log.error("Dify 消息反馈失败: messageId={}, rating={}", reqVO.getMessageId(), reqVO.getRating(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 消息反馈异常: messageId={}, rating={}", reqVO.getMessageId(), reqVO.getRating(), e);
            throw new ServiceException(ErrorCodeConstants.CHAT_MESSAGE_SEND_FAIL.getCode(), "消息反馈失败: " + e.getMessage());
        }
    }

    @Override
    public Flux<String> sendMessageStream(ChatMessageSendReqVO reqVO) {
        try {
            // 验证请求参数
            ValidationUtils.validate(reqVO);

            log.info("Dify 流式对话消息发送开始: query={}, user={}, conversationId={}, responseMode={}",
                reqVO.getQuery(), reqVO.getUser(), reqVO.getConversationId(), reqVO.getResponseMode());

            // 输出完整的请求体用于调试
            try {
                String requestJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(reqVO);
                log.info("Dify 请求体: {}", requestJson);
                System.out.println("Dify 请求体: " + requestJson);
            } catch (Exception jsonEx) {
                log.warn("无法序列化请求体: {}", jsonEx.getMessage());
            }

            // 调用 DifyHttpClient 的流式方法
            return difyHttpClient.postStream("/v1/chat-messages", difyProperties.getChatApiKey(), reqVO);

        } catch (Exception e) {
            log.error("Dify 流式对话消息发送失败: query={}, user={}", reqVO.getQuery(), reqVO.getUser(), e);
            return Flux.error(new ServiceException(ErrorCodeConstants.CHAT_MESSAGE_SEND_FAIL.getCode(), "流式对话发送失败: " + e.getMessage()));
        }
    }

}
