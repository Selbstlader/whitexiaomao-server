package cn.iocoder.yudao.module.dify.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Dify 错误码枚举类
 * 
 * dify 系统，使用 1-003-000-000 段
 *
 * @author 芋道源码
 */
public interface ErrorCodeConstants {

    // ========== Dify 通用错误 1-003-001-000 ==========
    ErrorCode DIFY_API_ERROR = new ErrorCode(1_003_001_000, "Dify API 调用失败");
    ErrorCode DIFY_CONFIG_ERROR = new ErrorCode(1_003_001_001, "Dify 配置错误");
    ErrorCode DIFY_TIMEOUT_ERROR = new ErrorCode(1_003_001_002, "Dify API 调用超时");
    ErrorCode DIFY_AUTH_ERROR = new ErrorCode(1_003_001_003, "Dify API 认证失败");

    // ========== 对话相关错误 1-003-002-000 ==========
    ErrorCode CHAT_MESSAGE_SEND_FAIL = new ErrorCode(1_003_002_000, "发送对话消息失败");
    ErrorCode CHAT_CONVERSATION_NOT_FOUND = new ErrorCode(1_003_002_001, "对话会话不存在");
    ErrorCode CHAT_MESSAGE_EMPTY = new ErrorCode(1_003_002_002, "对话消息不能为空");
    ErrorCode CHAT_USER_ID_EMPTY = new ErrorCode(1_003_002_003, "用户ID不能为空");
    ErrorCode CHAT_SUGGESTED_QUESTIONS_FAILED = new ErrorCode(1_003_002_004, "获取建议问题失败");

    // ========== 知识库相关错误 1-003-003-000 ==========
    ErrorCode DATASET_NOT_FOUND = new ErrorCode(1_003_003_000, "知识库不存在");
    ErrorCode DATASET_CREATE_FAIL = new ErrorCode(1_003_003_001, "创建知识库失败");
    ErrorCode DATASET_DELETE_FAIL = new ErrorCode(1_003_003_002, "删除知识库失败");
    ErrorCode DATASET_NAME_DUPLICATE = new ErrorCode(1_003_003_003, "知识库名称已存在");

    // ========== 文档相关错误 1-003-004-000 ==========
    ErrorCode DOCUMENT_NOT_FOUND = new ErrorCode(1_003_004_000, "文档不存在");
    ErrorCode DOCUMENT_UPLOAD_FAIL = new ErrorCode(1_003_004_001, "文档上传失败");
    ErrorCode DOCUMENT_DELETE_FAIL = new ErrorCode(1_003_004_002, "文档删除失败");
    ErrorCode DOCUMENT_UPDATE_FAIL = new ErrorCode(1_003_004_003, "文档更新失败");
    ErrorCode DOCUMENT_INDEXING = new ErrorCode(1_003_004_004, "文档正在索引中，无法操作");
    ErrorCode DOCUMENT_FILE_TOO_LARGE = new ErrorCode(1_003_004_005, "文档文件过大");
    ErrorCode DOCUMENT_UNSUPPORTED_TYPE = new ErrorCode(1_003_004_006, "不支持的文档类型");

}
