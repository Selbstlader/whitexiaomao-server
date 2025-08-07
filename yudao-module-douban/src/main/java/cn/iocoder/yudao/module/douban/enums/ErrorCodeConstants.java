package cn.iocoder.yudao.module.douban.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * 豆瓣模块错误码枚举类
 *
 * @author 芋道源码
 */
public interface ErrorCodeConstants {

    // ========== 电影相关 1-003-001-000 ==========
    ErrorCode MOVIE_NOT_EXISTS = new ErrorCode(1_003_001_000, "电影不存在");
    ErrorCode MOVIE_ID_EXISTS = new ErrorCode(1_003_001_001, "电影ID已存在");

    // ========== 用户相关 1-003-002-000 ==========
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_003_002_000, "用户不存在");
    ErrorCode USER_PEOPLE_EXISTS = new ErrorCode(1_003_002_001, "用户名已存在");

    // ========== 评论相关 1-003-003-000 ==========
    ErrorCode COMMENT_NOT_EXISTS = new ErrorCode(1_003_003_000, "评论不存在");
    ErrorCode COMMENT_ID_EXISTS = new ErrorCode(1_003_003_001, "评论ID已存在");
}