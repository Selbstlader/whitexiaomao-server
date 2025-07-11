package cn.iocoder.yudao.module.dify.service.chat;

import cn.iocoder.yudao.module.dify.controller.admin.chat.vo.*;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.util.DifyHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * ChatService 测试类
 *
 * @author 芋道源码
 */
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private DifyHttpClient difyHttpClient;

    @Mock
    private DifyProperties difyProperties;

    @InjectMocks
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        when(difyProperties.getChatApiKey()).thenReturn("test-chat-api-key");
    }

    @Test
    void testSendMessage() {
        // 准备测试数据
        ChatMessageSendReqVO reqVO = new ChatMessageSendReqVO();
        reqVO.setQuery("你好");
        reqVO.setUser("user-123");
        reqVO.setResponseMode("blocking");
        reqVO.setInputs(new HashMap<>());

        ChatMessageRespVO mockResponse = new ChatMessageRespVO();
        mockResponse.setId("msg-123");
        mockResponse.setConversationId("conv-123");
        mockResponse.setAnswer("你好！我是 AI 助手。");
        mockResponse.setCreatedAt(System.currentTimeMillis() / 1000);

        // Mock HTTP 客户端响应
        when(difyHttpClient.post(eq("/v1/chat-messages"), eq("test-chat-api-key"), any(), eq(ChatMessageRespVO.class)))
                .thenReturn(mockResponse);

        // 执行测试
        ChatMessageRespVO response = chatService.sendMessage(reqVO);

        // 验证结果
        assertNotNull(response);
        assertEquals("msg-123", response.getId());
        assertEquals("conv-123", response.getConversationId());
        assertEquals("你好！我是 AI 助手。", response.getAnswer());
    }

    @Test
    void testGetConversationList() {
        // 准备测试数据
        ConversationListReqVO reqVO = new ConversationListReqVO();
        reqVO.setUser("user-123");
        reqVO.setLimit(20);

        ConversationListRespVO mockResponse = new ConversationListRespVO();
        mockResponse.setLimit(20);
        mockResponse.setHasMore(false);

        // Mock HTTP 客户端响应
        when(difyHttpClient.get(anyString(), eq("test-chat-api-key"), eq(ConversationListRespVO.class)))
                .thenReturn(mockResponse);

        // 执行测试
        ConversationListRespVO response = chatService.getConversationList(reqVO);

        // 验证结果
        assertNotNull(response);
        assertEquals(20, response.getLimit());
        assertFalse(response.getHasMore());
    }

    @Test
    void testSendMessageWithFiles() {
        // 准备测试数据
        ChatMessageSendReqVO reqVO = new ChatMessageSendReqVO();
        reqVO.setQuery("这张图片是什么？");
        reqVO.setUser("user-123");
        reqVO.setResponseMode("blocking");

        // 添加文件
        ChatMessageSendReqVO.FileInfo fileInfo = new ChatMessageSendReqVO.FileInfo();
        fileInfo.setType("image");
        fileInfo.setTransferMethod("remote_url");
        fileInfo.setUrl("https://example.com/image.jpg");
        reqVO.setFiles(java.util.Arrays.asList(fileInfo));

        ChatMessageRespVO mockResponse = new ChatMessageRespVO();
        mockResponse.setId("msg-456");
        mockResponse.setAnswer("这是一张美丽的风景图片。");

        // Mock HTTP 客户端响应
        when(difyHttpClient.post(eq("/v1/chat-messages"), eq("test-chat-api-key"), any(), eq(ChatMessageRespVO.class)))
                .thenReturn(mockResponse);

        // 执行测试
        ChatMessageRespVO response = chatService.sendMessage(reqVO);

        // 验证结果
        assertNotNull(response);
        assertEquals("msg-456", response.getId());
        assertEquals("这是一张美丽的风景图片。", response.getAnswer());
    }

}
