---
description: 
globs: 
alwaysApply: false
---

# Dify 知识库 API 集成规范（详细版）

本项目支持通过 Dify 平台的知识库 API 实现自定义知识问答、数据检索、文档管理等功能。

## 入口文档
- 详细配置与密钥说明见 [dify.md](mdc:aiConsultation/dify.md)
- 官方知识库 API 文档：https://docs.dify.ai/guides/knowledge-base/maintain-dataset-via-api
- 官方知识库管理文档（中文）：https://docs.dify.ai/zh-hans/guides/knowledge-base/knowledge-and-documents-maintenance
- dify 知识库api 文档：https://cloud.dify.ai/datasets?category=api

## 密钥管理
- dataset- 开头为知识库级别 API 密钥，详见 dify.md
- 建议密钥通过安全配置文件或环境变量注入，避免硬编码

## 典型场景
- 自动化同步企业知识库数据
- 批量上传、更新、删除知识文档
- 检索知识库内容用于智能问答

## 主要 API 列表

### 1. 创建知识库
POST /v1/datasets
```bash
curl -X POST 'https://api.dify.ai/v1/datasets' \
  -H 'Authorization: Bearer {api_key}' \
  -H 'Content-Type: application/json' \
  -d '{"name": "知识库名称", "permission": "only_me"}'
```

### 2. 获取知识库列表
GET /v1/datasets
```bash
curl -X GET 'https://api.dify.ai/v1/datasets?page=1&limit=20' \
  -H 'Authorization: Bearer {api_key}'
```

### 3. 创建文档（文本）
POST /v1/datasets/{dataset_id}/document/create_by_text
```bash
curl -X POST 'https://api.dify.ai/v1/datasets/{dataset_id}/document/create_by_text' \
  -H 'Authorization: Bearer {api_key}' \
  -H 'Content-Type: application/json' \
  -d '{"name": "text","text": "内容","indexing_technique": "high_quality","process_rule": {"mode": "automatic"}}'
```

### 4. 创建文档（文件）
POST /v1/datasets/{dataset_id}/document/create-by-file
```bash
curl -X POST 'https://api.dify.ai/v1/datasets/{dataset_id}/document/create-by-file' \
  -H 'Authorization: Bearer {api_key}' \
  -F 'data="{\"indexing_technique\":\"high_quality\",...}";type=text/plain' \
  -F 'file=@"/path/to/file"'
```

### 5. 检索知识库内容
POST /v1/datasets/{dataset_id}/retrieve
```bash
curl -X POST 'https://api.dify.ai/v1/datasets/{dataset_id}/retrieve' \
  -H 'Authorization: Bearer {api_key}' \
  -H 'Content-Type: application/json' \
  -d '{"query": "检索内容"}'
```

### 6. 其他常用接口
- 删除知识库：DELETE /v1/datasets/{dataset_id}
- 获取文档列表：GET /v1/datasets/{dataset_id}/documents
- 删除文档：DELETE /v1/datasets/{dataset_id}/documents/{document_id}
- 查看知识库详情： /v1/datasets/{dataset_id}
- 更多接口详见官方文档

## 安全与最佳实践
- 不要将密钥提交至代码仓库
- 仅在服务端安全环境下调用知识库 API
- 定期轮换密钥，防止泄露
- 推荐将 API 封装为后端服务，前端通过后端转发调用

## 参考与扩展
- 详细开发注意事项、密钥管理、接口参数说明请见 [dify.md](mdc:aiConsultation/dify.md)
- 官方 API 说明：https://docs.dify.ai/guides/knowledge-base/maintain-dataset-via-api
- 错误码与详细参数请查阅官方文档
