package cn.iocoder.yudao.module.dify.api.chat;

import cn.iocoder.yudao.module.dify.controller.admin.chat.vo.*;
import cn.iocoder.yudao.module.dify.service.chat.ChatService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * Dify 对话 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class ChatApiImpl implements ChatApi {

    @Resource
    private ChatService chatService;

    @Override
    public ChatMessageRespVO sendMessage(ChatMessageSendReqVO reqVO) {
        return chatService.sendMessage(reqVO);
    }

    @Override
    public ConversationListRespVO getConversationList(ConversationListReqVO reqVO) {
        return chatService.getConversationList(reqVO);
    }

    @Override
    public ChatStopRespVO stopMessage(ChatStopReqVO reqVO) {
        return chatService.stopMessage(reqVO);
    }

    @Override
    public MessageHistoryRespVO getMessageHistory(MessageHistoryReqVO reqVO) {
        return chatService.getMessageHistory(reqVO);
    }

    @Override
    public SuggestedQuestionsRespVO getSuggestedQuestions(SuggestedQuestionsReqVO reqVO) {
        return chatService.getSuggestedQuestions(reqVO);
    }

    @Override
    public void deleteConversation(ConversationDeleteReqVO reqVO) {
        chatService.deleteConversation(reqVO);
    }

    @Override
    public ConversationRespVO renameConversation(ConversationRenameReqVO reqVO) {
        return chatService.renameConversation(reqVO);
    }

    @Override
    public MessageFeedbackRespVO messageFeedback(MessageFeedbackReqVO reqVO) {
        return chatService.messageFeedback(reqVO);
    }

}
