package cn.iocoder.yudao.module.dify.api.chat;

import cn.iocoder.yudao.module.dify.controller.admin.chat.vo.*;

/**
 * Dify 对话 API 接口
 *
 * @author 芋道源码
 */
public interface ChatApi {

    /**
     * 发送对话消息
     *
     * @param reqVO 发送消息请求
     * @return 对话响应
     */
    ChatMessageRespVO sendMessage(ChatMessageSendReqVO reqVO);

    /**
     * 获取会话列表
     *
     * @param reqVO 获取会话列表请求
     * @return 会话列表响应
     */
    ConversationListRespVO getConversationList(ConversationListReqVO reqVO);

    /**
     * 停止对话生成
     *
     * @param reqVO 停止请求
     * @return 停止响应
     */
    ChatStopRespVO stopMessage(ChatStopReqVO reqVO);

    /**
     * 获取消息历史
     *
     * @param reqVO 获取消息历史请求
     * @return 消息历史响应
     */
    MessageHistoryRespVO getMessageHistory(MessageHistoryReqVO reqVO);

    /**
     * 获取建议问题
     *
     * @param reqVO 获取建议问题请求
     * @return 建议问题响应
     */
    SuggestedQuestionsRespVO getSuggestedQuestions(SuggestedQuestionsReqVO reqVO);

    /**
     * 删除会话
     *
     * @param reqVO 删除会话请求
     */
    void deleteConversation(ConversationDeleteReqVO reqVO);

    /**
     * 重命名会话
     *
     * @param reqVO 重命名会话请求
     * @return 会话信息
     */
    ConversationRespVO renameConversation(ConversationRenameReqVO reqVO);

    /**
     * 消息反馈
     *
     * @param reqVO 消息反馈请求
     * @return 反馈响应
     */
    MessageFeedbackRespVO messageFeedback(MessageFeedbackReqVO reqVO);

}
