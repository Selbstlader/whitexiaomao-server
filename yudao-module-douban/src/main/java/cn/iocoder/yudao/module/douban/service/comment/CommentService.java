package cn.iocoder.yudao.module.douban.service.comment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 豆瓣电影评论 Service 接口
 *
 * @author 芋道源码
 */
public interface CommentService {

    /**
     * 创建评论
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComment(@Valid CommentCreateReqVO createReqVO);

    /**
     * 更新评论
     *
     * @param updateReqVO 更新信息
     */
    void updateComment(@Valid CommentUpdateReqVO updateReqVO);

    /**
     * 删除评论
     *
     * @param id 编号
     */
    void deleteComment(Long id);

    /**
     * 获得评论
     *
     * @param id 编号
     * @return 评论
     */
    CommentDO getComment(Long id);

    /**
     * 获得评论列表
     *
     * @param ids 编号
     * @return 评论列表
     */
    List<CommentDO> getCommentList(Collection<Long> ids);

    /**
     * 获得所有评论列表
     *
     * @return 评论列表
     */
    List<CommentDO> getCommentList();

    /**
     * 获得评论分页
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<CommentDO> getCommentPage(CommentPageReqVO pageReqVO);

    /**
     * 根据电影ID获得评论列表
     *
     * @param movieId 电影ID
     * @return 评论列表
     */
    List<CommentDO> getCommentListByMovieId(String movieId);

    /**
     * 获得评论分页（包含电影标题）
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO> getCommentPageWithMovieTitle(CommentPageReqVO pageReqVO);

    /**
     * 获得评论分页（包含电影标题）- 管理后台
     *
     * @param pageReqVO 分页查询
     * @return 评论分页
     */
    PageResult<CommentRespVO> getCommentPageWithMovieTitleForAdmin(CommentPageReqVO pageReqVO);
}