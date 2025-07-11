#!/bin/bash

# Dify AI 对话接口测试脚本
# 使用前请确保服务已启动，并且已经登录获取到 token

BASE_URL="http://localhost:48080"
TOKEN="your-access-token-here"

echo "=== Dify AI 对话接口测试 ==="

# 1. 测试发送对话消息（阻塞模式）
echo "1. 测试发送对话消息（阻塞模式）"
curl -X POST "${BASE_URL}/admin-api/dify/chat/send-message" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "你好，请介绍一下你自己",
    "user": "test-user-123",
    "response_mode": "blocking",
    "inputs": {},
    "auto_generate_name": true
  }' | jq .

echo -e "\n"

# 2. 测试发送对话消息（流式模式）
echo "2. 测试发送对话消息（流式模式）"
curl -X POST "${BASE_URL}/admin-api/dify/chat/send-message" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "请告诉我今天的天气如何？",
    "user": "test-user-123",
    "response_mode": "streaming",
    "inputs": {}
  }' | jq .

echo -e "\n"

# 3. 测试继续对话（使用会话ID）
echo "3. 测试继续对话（使用会话ID）"
curl -X POST "${BASE_URL}/admin-api/dify/chat/send-message" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "谢谢你的回答",
    "user": "test-user-123",
    "conversation_id": "conversation-id-from-previous-response",
    "response_mode": "blocking",
    "inputs": {}
  }' | jq .

echo -e "\n"

# 4. 测试带文件的对话
echo "4. 测试带文件的对话"
curl -X POST "${BASE_URL}/admin-api/dify/chat/send-message" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "query": "这张图片是什么？",
    "user": "test-user-123",
    "response_mode": "blocking",
    "inputs": {},
    "files": [
      {
        "type": "image",
        "transfer_method": "remote_url",
        "url": "https://cloud.dify.ai/logo/logo-site.png"
      }
    ]
  }' | jq .

echo -e "\n"

# 5. 测试获取会话列表
echo "5. 测试获取会话列表"
curl -X GET "${BASE_URL}/admin-api/dify/chat/conversations?user=test-user-123&limit=10" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" | jq .

echo -e "\n"

# 6. 测试获取会话列表（分页）
echo "6. 测试获取会话列表（分页）"
curl -X GET "${BASE_URL}/admin-api/dify/chat/conversations?user=test-user-123&lastId=last-conversation-id&limit=5" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" | jq .

echo -e "\n"

# 7. 测试停止对话生成
echo "7. 测试停止对话生成"
curl -X POST "${BASE_URL}/admin-api/dify/chat/stop/task-id-from-previous-response" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "user": "test-user-123"
  }' | jq .

echo -e "\n"

# 8. 测试获取消息历史
echo "8. 测试获取消息历史"
curl -X GET "${BASE_URL}/admin-api/dify/chat/messages?user=test-user-123&conversationId=conversation-id-from-previous-response&limit=10" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" | jq .

echo -e "\n"

# 9. 测试获取建议问题
echo "9. 测试获取建议问题"
curl -X GET "${BASE_URL}/admin-api/dify/chat/messages/message-id-from-previous-response/suggested?user=test-user-123" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" | jq .

echo -e "\n"

# 10. 测试重命名会话
echo "10. 测试重命名会话"
curl -X POST "${BASE_URL}/admin-api/dify/chat/conversations/conversation-id-from-previous-response/rename" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "重命名的会话",
    "user": "test-user-123",
    "auto_generate": false
  }' | jq .

echo -e "\n"

# 11. 测试消息反馈（点赞）
echo "11. 测试消息反馈（点赞）"
curl -X POST "${BASE_URL}/admin-api/dify/chat/messages/message-id-from-previous-response/feedback" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "user": "test-user-123",
    "rating": "like"
  }' | jq .

echo -e "\n"

# 12. 测试消息反馈（点踩）
echo "12. 测试消息反馈（点踩）"
curl -X POST "${BASE_URL}/admin-api/dify/chat/messages/message-id-from-previous-response/feedback" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "user": "test-user-123",
    "rating": "dislike"
  }' | jq .

echo -e "\n"

# 13. 测试删除会话
echo "13. 测试删除会话"
curl -X DELETE "${BASE_URL}/admin-api/dify/chat/conversations/conversation-id-to-delete?user=test-user-123" \
  -H "Authorization: Bearer ${TOKEN}" \
  -H "Content-Type: application/json" | jq .

echo -e "\n=== 测试完成 ==="

# 使用说明：
# 1. 将 TOKEN 替换为实际的访问令牌
# 2. 确保服务在 localhost:48080 运行
# 3. 安装 jq 工具用于格式化 JSON 输出：brew install jq (macOS) 或 apt-get install jq (Ubuntu)
# 4. 给脚本执行权限：chmod +x test-chat-api.sh
# 5. 运行脚本：./test-chat-api.sh
