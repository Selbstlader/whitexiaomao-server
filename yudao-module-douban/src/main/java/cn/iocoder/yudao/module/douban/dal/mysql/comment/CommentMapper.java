package cn.iocoder.yudao.module.douban.dal.mysql.comment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.CommentPageReqVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 豆瓣电影评论 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CommentMapper extends BaseMapperX<CommentDO> {

    default PageResult<CommentDO> selectPage(CommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CommentDO>()
                .eqIfPresent(CommentDO::getMovieId, reqVO.getMovieId())
                .likeIfPresent(CommentDO::getPeople, reqVO.getPeople())
                .eqIfPresent(CommentDO::getStar, reqVO.getStar())
                .likeIfPresent(CommentDO::getContent, reqVO.getContent())
                .geIfPresent(CommentDO::getUsefulNum, reqVO.getMinUsefulNum())
                .orderByDesc(CommentDO::getId));
    }

    default List<CommentDO> selectListByMovieId(String movieId) {
        return selectList(CommentDO::getMovieId, movieId);
    }

    default CommentDO selectByCommentId(String commentId) {
        return selectOne(CommentDO::getCommentId, commentId);
    }

    /**
     * 分页查询评论并关联电影标题
     */
    @Select("SELECT c.*, m.movie_title as movieTitle " +
            "FROM douban_comments c " +
            "LEFT JOIN douban_movies m ON c.movie_id = m.movie_id AND m.tenant_id = c.tenant_id " +
            "WHERE c.deleted = 0 " +
            "AND (#{reqVO.movieId} IS NULL OR c.movie_id = #{reqVO.movieId}) " +
            "AND (#{reqVO.people} IS NULL OR c.people LIKE CONCAT('%', #{reqVO.people}, '%')) " +
            "AND (#{reqVO.star} IS NULL OR c.star = #{reqVO.star}) " +
            "AND (#{reqVO.content} IS NULL OR c.content LIKE CONCAT('%', #{reqVO.content}, '%')) " +
            "AND (#{reqVO.minUsefulNum} IS NULL OR c.useful_num >= #{reqVO.minUsefulNum}) " +
            "ORDER BY c.id DESC")
    IPage<Map<String, Object>> selectPageWithMovieTitle(IPage<Map<String, Object>> page, @Param("reqVO") CommentPageReqVO reqVO);
}