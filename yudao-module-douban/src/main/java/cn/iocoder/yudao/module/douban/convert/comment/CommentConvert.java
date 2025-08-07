package cn.iocoder.yudao.module.douban.convert.comment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 豆瓣评论 Convert
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentConvert {

    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CommentDO convert(CommentCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CommentDO convert(CommentUpdateReqVO bean);

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
    @Mapping(source = "movie.movieTitle", target = "movieTitle")
    CommentRespVO convert(CommentDO bean, MovieDO movie);

    @IterableMapping(qualifiedByName = "convert")
    List<CommentRespVO> convertList(List<CommentDO> list);

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

    /**
     * 分页结果转换，包含电影标题
     */
    default PageResult<CommentRespVO> convertPage(PageResult<CommentDO> page, Map<String, MovieDO> movieMap) {
        if (page == null) {
            return null;
        }
        List<CommentRespVO> respList = page.getList().stream()
                .map(comment -> {
                    MovieDO movie = movieMap.get(comment.getMovieId());
                    return convert(comment, movie);
                })
                .toList();
        return new PageResult<>(respList, page.getTotal());
    }

}