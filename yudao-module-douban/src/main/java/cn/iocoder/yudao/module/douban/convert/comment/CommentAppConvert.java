package cn.iocoder.yudao.module.douban.convert.comment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentCreateReqVO;
import cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 豆瓣评论 App Convert
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentAppConvert {

    CommentAppConvert INSTANCE = Mappers.getMapper(CommentAppConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CommentDO convert(CommentCreateReqVO bean);

    @Named("convert")
    @Mapping(target = "movieTitle", ignore = true)
    CommentRespVO convert(CommentDO bean);

    @Named("convertWithMovie")
    @Mapping(source = "bean.id", target = "id")
    @Mapping(source = "bean.movieId", target = "movieId")
    @Mapping(source = "bean.people", target = "people")
    @Mapping(source = "bean.star", target = "star")
    @Mapping(source = "bean.content", target = "content")
    @Mapping(source = "bean.createTime", target = "createTime")
    @Mapping(source = "bean.updateTime", target = "updateTime")
    @Mapping(source = "movie.movieTitle", target = "movieTitle")
    CommentRespVO convert(CommentDO bean, MovieDO movie);

    @IterableMapping(qualifiedByName = "convert")
    List<CommentRespVO> convertList(List<CommentDO> list);

    /**
     * 转换评论列表，包含电影标题
     */
    default List<CommentRespVO> convertListWithMovie(List<CommentDO> comments, MovieDO movie) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }
        return comments.stream()
                .map(comment -> convert(comment, movie))
                .toList();
    }

    /**
     * 分页结果转换
     */
    default PageResult<CommentRespVO> convertPage(PageResult<CommentDO> page) {
        if (page == null) {
            return null;
        }
        List<CommentRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

}