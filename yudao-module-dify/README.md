# yudao-module-dify

## 模块介绍

yudao-module-dify 是芋道框架的 Dify AI 平台对接模块，提供了与 Dify 平台的完整集成功能。

## 主要功能

### 1. 对话功能（参考 ChatGPT 完整功能）
- ✅ **发送消息** - 支持文本、图片等多模态输入
- ✅ **流式响应** - 实时打字机效果（streaming/blocking 模式）
- ✅ **会话管理** - 创建、查询、删除、重命名会话
- ✅ **消息历史** - 查看完整对话记录，支持分页
- ✅ **停止生成** - 中断正在生成的回答
- ✅ **建议问题** - 智能推荐下一轮问题
- ✅ **消息反馈** - 点赞/点踩评价系统
- ✅ **文件上传** - 支持图片等文件分析
- ✅ **自动标题** - 智能生成会话标题
- ✅ **变量输入** - 支持 App 定义的变量值

### 2. 知识库管理
- ✅ **知识库 CRUD** - 创建、查询、更新、删除知识库
- ✅ **权限管理** - 知识库访问权限控制
- ✅ **批量操作** - 支持批量管理操作

### 3. 文档管理
- ✅ **文档 CRUD** - 创建、查询、更新、删除文档
- ✅ **多种创建方式** - 文本创建、文件上传
- ✅ **文档分段** - 查询、更新、删除分段
- ✅ **检索增强** - RAG 检索结果展示

## 功能对比

| 功能 | ChatGPT | Dify AI | 本模块实现 |
|------|---------|---------|------------|
| 发送消息 | ✅ | ✅ | ✅ |
| 流式响应 | ✅ | ✅ | ✅ |
| 会话列表 | ✅ | ✅ | ✅ |
| 消息历史 | ✅ | ✅ | ✅ |
| 停止生成 | ✅ | ✅ | ✅ |
| 重命名会话 | ✅ | ✅ | ✅ |
| 删除会话 | ✅ | ✅ | ✅ |
| 消息反馈 | ✅ | ✅ | ✅ |
| 建议问题 | ✅ | ✅ | ✅ |
| 文件上传 | ✅ | ✅ | ✅ |
| 多模态输入 | ✅ | ✅ | ✅ |
| 知识库检索 | ❌ | ✅ | ✅ |
| 自定义变量 | ❌ | ✅ | ✅ |

## 配置说明

在 `application-dev.yaml` 中添加以下配置：

```yaml
yudao:
  dify:
    enabled: true # 是否启用 Dify 功能
    base-url: https://api.dify.ai # Dify API 基础地址
    chat-api-key: app-D7hSHUnPoMt5CQD2IEoBsPJz # 对话API密钥
    api-key: dataset-QQ8Y3Cxvss4RvQXvU7Gem4sb # 数据集级别API密钥
    timeout: 180 # 请求超时时间（秒）- 延长到3分钟
    file-upload-timeout: 600 # 文件上传超时时间（秒）- 10分钟
    max-retries: 8 # 最大重试次数 - 增加到8次
```

## API 接口

### 对话接口

#### 发送对话消息
- **URL**: `POST /dify/chat/send-message`
- **权限**: `dify:chat:send`
- **请求体**:
```json
{
  "query": "你好，请介绍一下你自己",
  "inputs": {},
  "response_mode": "blocking",
  "user": "user-123",
  "conversation_id": "1c7e55fb-1ba2-4e10-81b5-30addcea2276",
  "files": [
    {
      "type": "image",
      "transfer_method": "remote_url",
      "url": "https://example.com/image.jpg"
    }
  ],
  "auto_generate_name": true
}
```

#### 获取会话列表
- **URL**: `GET /dify/chat/conversations?user=user-123&lastId=&limit=20`
- **权限**: `dify:chat:query`
- **参数**:
  - `user`: 用户标识（必填）
  - `lastId`: 最后一个会话的 ID，用于分页（可选）
  - `limit`: 每页数量，默认 20（可选）

#### 停止对话生成
- **URL**: `POST /dify/chat/stop/{taskId}`
- **权限**: `dify:chat:send`
- **请求体**:
```json
{
  "user": "user-123"
}
```

#### 获取消息历史
- **URL**: `GET /dify/chat/messages?user=user-123&conversationId=conv-123&firstId=&limit=20`
- **权限**: `dify:chat:query`
- **参数**:
  - `user`: 用户标识（必填）
  - `conversationId`: 会话 ID（必填）
  - `firstId`: 第一条消息的 ID，用于分页（可选）
  - `limit`: 每页数量，默认 20（可选）

#### 获取建议问题
- **URL**: `GET /dify/chat/messages/{messageId}/suggested?user=user-123`
- **权限**: `dify:chat:query`
- **参数**:
  - `messageId`: 消息 ID（必填）
  - `user`: 用户标识（必填）

#### 删除会话
- **URL**: `DELETE /dify/chat/conversations/{conversationId}?user=user-123`
- **权限**: `dify:chat:delete`
- **参数**:
  - `conversationId`: 会话 ID（必填）
  - `user`: 用户标识（必填）

#### 重命名会话
- **URL**: `POST /dify/chat/conversations/{conversationId}/rename`
- **权限**: `dify:chat:update`
- **请求体**:
```json
{
  "name": "新的会话名称",
  "user": "user-123",
  "auto_generate": false
}
```

#### 消息反馈
- **URL**: `POST /dify/chat/messages/{messageId}/feedback`
- **权限**: `dify:chat:send`
- **请求体**:
```json
{
  "user": "user-123",
  "rating": "like"
}
```
- **说明**: rating 可选值：`like`（点赞）、`dislike`（点踩）、`null`（取消评分）

### 知识库接口

#### 创建知识库
- **URL**: `POST /dify/dataset/create`
- **权限**: `dify:dataset:create`
- **请求体**:
```json
{
  "name": "产品知识库",
  "description": "包含产品相关的所有文档和资料",
  "permission": "only_me"
}
```

#### 获取知识库列表
- **URL**: `GET /dify/dataset/list?page=1&limit=20`
- **权限**: `dify:dataset:query`

#### 删除知识库
- **URL**: `DELETE /dify/dataset/delete/{datasetId}`
- **权限**: `dify:dataset:delete`

### 文档接口

#### 通过文本创建文档
- **URL**: `POST /dify/dataset/{datasetId}/document/create-by-text`
- **权限**: `dify:document:create`
- **请求体**:
```json
{
  "name": "产品介绍",
  "text": "这是一个产品介绍文档...",
  "indexingTechnique": "high_quality",
  "processRule": {
    "mode": "automatic"
  }
}
```

#### 通过文件创建文档
- **URL**: `POST /dify/dataset/{datasetId}/document/create-by-file`
- **权限**: `dify:document:create`
- **请求**: 表单提交，包含 `file` 字段

#### 获取文档列表
- **URL**: `GET /dify/dataset/{datasetId}/documents`
- **权限**: `dify:document:query`

#### 删除文档
- **URL**: `DELETE /dify/dataset/{datasetId}/document/{documentId}`
- **权限**: `dify:document:delete`

## 使用示例

### 1. 注入服务
```java
@Resource
private ChatApi chatApi;

@Resource
private DatasetApi datasetApi;
```

### 2. 发送对话消息
```java
ChatMessageSendReqVO reqVO = new ChatMessageSendReqVO();
reqVO.setQuery("你好");
reqVO.setUser("user-123");
reqVO.setResponseMode("blocking");
reqVO.setInputs(new HashMap<>());
reqVO.setAutoGenerateName(true);

ChatMessageRespVO response = chatApi.sendMessage(reqVO);
System.out.println("AI回复: " + response.getAnswer());
System.out.println("会话ID: " + response.getConversationId());
```

### 3. 获取会话列表
```java
ConversationListReqVO reqVO = new ConversationListReqVO();
reqVO.setUser("user-123");
reqVO.setLimit(20);

ConversationListRespVO response = chatApi.getConversationList(reqVO);
System.out.println("会话数量: " + response.getData().size());
```

### 4. 停止对话生成
```java
ChatStopReqVO reqVO = new ChatStopReqVO();
reqVO.setTaskId("task-123");
reqVO.setUser("user-123");

ChatStopRespVO response = chatApi.stopMessage(reqVO);
System.out.println("停止结果: " + response.getResult());
```

### 5. 获取消息历史
```java
MessageHistoryReqVO reqVO = new MessageHistoryReqVO();
reqVO.setUser("user-123");
reqVO.setConversationId("conv-123");
reqVO.setLimit(20);

MessageHistoryRespVO response = chatApi.getMessageHistory(reqVO);
System.out.println("消息数量: " + response.getData().size());
```

### 6. 获取建议问题
```java
SuggestedQuestionsReqVO reqVO = new SuggestedQuestionsReqVO();
reqVO.setMessageId("msg-123");
reqVO.setUser("user-123");

SuggestedQuestionsRespVO response = chatApi.getSuggestedQuestions(reqVO);
System.out.println("建议问题: " + response.getData());
```

### 7. 删除会话
```java
ConversationDeleteReqVO reqVO = new ConversationDeleteReqVO();
reqVO.setConversationId("conv-123");
reqVO.setUser("user-123");

chatApi.deleteConversation(reqVO);
System.out.println("会话已删除");
```

### 8. 重命名会话
```java
ConversationRenameReqVO reqVO = new ConversationRenameReqVO();
reqVO.setConversationId("conv-123");
reqVO.setName("新的会话名称");
reqVO.setUser("user-123");

ConversationRespVO response = chatApi.renameConversation(reqVO);
System.out.println("新名称: " + response.getName());
```

### 9. 消息反馈
```java
MessageFeedbackReqVO reqVO = new MessageFeedbackReqVO();
reqVO.setMessageId("msg-123");
reqVO.setUser("user-123");
reqVO.setRating("like"); // like: 点赞, dislike: 点踩, null: 取消评分

MessageFeedbackRespVO response = chatApi.messageFeedback(reqVO);
System.out.println("反馈结果: " + response.getResult());
```

### 3. 创建知识库
```java
DatasetCreateReqVO reqVO = new DatasetCreateReqVO();
reqVO.setName("我的知识库");
reqVO.setDescription("测试知识库");

DatasetRespVO response = datasetApi.createDataset(reqVO);
System.out.println("知识库ID: " + response.getId());
```

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 1_003_001_000 | Dify API 调用失败 |
| 1_003_001_001 | Dify 配置错误 |
| 1_003_001_002 | Dify API 调用超时 |
| 1_003_001_003 | Dify API 认证失败 |
| 1_003_002_000 | 发送对话消息失败 |
| 1_003_003_000 | 知识库不存在 |
| 1_003_004_000 | 文档不存在 |

## 注意事项

1. 确保 Dify API 密钥配置正确
2. 网络环境需要能够访问 Dify API 服务
3. 文件上传时注意文件大小限制
4. 建议在生产环境中配置适当的超时时间和重试次数

## 技术架构

- **Controller**: 提供 RESTful API 接口
- **Service**: 业务逻辑处理
- **API**: 对外提供的接口定义
- **Util**: HTTP 客户端工具类
- **Config**: 配置管理

## 依赖说明

- Spring Boot WebFlux: HTTP 客户端
- Jackson: JSON 序列化/反序列化
- Validation: 参数校验
- Lombok: 代码简化
