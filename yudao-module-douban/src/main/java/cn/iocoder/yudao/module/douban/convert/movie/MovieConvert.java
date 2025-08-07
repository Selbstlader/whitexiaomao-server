package cn.iocoder.yudao.module.douban.convert.movie;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.movie.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.convert.comment.CommentConvert;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import cn.hutool.core.collection.CollUtil;

/**
 * 电影 Convert
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieConvert {

    MovieConvert INSTANCE = Mappers.getMapper(MovieConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    MovieDO convert(MovieCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    MovieDO convert(MovieUpdateReqVO bean);

    MovieRespVO convert(MovieDO bean);

    List<MovieRespVO> convertList(List<MovieDO> list);

    PageResult<MovieRespVO> convertPage(PageResult<MovieDO> page);



    /**
     * 转换详情（重载方法）
     */
    default MovieDetailRespVO convertDetail(MovieDO movie, List<CommentDO> comments) {
        // 计算平均评分
        BigDecimal averageRating = null;
        if (CollUtil.isNotEmpty(comments)) {
            double average = comments.stream()
                    .mapToInt(CommentDO::getStar)
                    .average()
                    .orElse(0.0);
            averageRating = BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP);
        }
        return convertToDetail(movie, comments, averageRating);
    }

    /**
     * 转换详情
     */
    default MovieDetailRespVO convertToDetail(MovieDO movie, List<CommentDO> comments, BigDecimal averageRating) {
        if (movie == null) {
            return null;
        }
        MovieDetailRespVO respVO = new MovieDetailRespVO();
        respVO.setId(movie.getId());
        respVO.setMovieId(movie.getMovieId());
        respVO.setMovieTitle(movie.getMovieTitle());
        respVO.setReleaseDate(movie.getReleaseDate());
        respVO.setDirectedBy(movie.getDirectedBy());
        respVO.setStarring(movie.getStarring());
        respVO.setGenre(movie.getGenre());
        respVO.setRuntime(movie.getRuntime());
        respVO.setCountry(movie.getCountry());
        respVO.setLanguage(movie.getLanguage());
        respVO.setRatingNum(movie.getRatingNum());
        respVO.setVoteNum(movie.getVoteNum());
        respVO.setRatingPerStars5(movie.getRatingPerStars5());
        respVO.setRatingPerStars4(movie.getRatingPerStars4());
        respVO.setRatingPerStars3(movie.getRatingPerStars3());
        respVO.setRatingPerStars2(movie.getRatingPerStars2());
        respVO.setRatingPerStars1(movie.getRatingPerStars1());
        respVO.setIntro(movie.getIntro());
        respVO.setCommentNum(movie.getCommentNum());
        respVO.setQuestionNum(movie.getQuestionNum());
        respVO.setCreateTime(movie.getCreateTime());
        
        // 设置评论列表
        if (CollUtil.isNotEmpty(comments)) {
            respVO.setComments(CommentConvert.INSTANCE.convertList(comments));
        }
        
        // 设置平均评分
        if (averageRating != null) {
            respVO.setAverageRating(averageRating.doubleValue());
        }
        
        return respVO;
    }



}