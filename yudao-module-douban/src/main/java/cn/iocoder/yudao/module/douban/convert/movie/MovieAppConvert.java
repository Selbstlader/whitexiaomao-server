package cn.iocoder.yudao.module.douban.convert.movie;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.app.movie.vo.*;
import cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 电影 App Convert
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieAppConvert {

    MovieAppConvert INSTANCE = Mappers.getMapper(MovieAppConvert.class);

    @Mapping(source = "releaseDate", target = "releaseDate", qualifiedByName = "integerToLocalDate")
    MovieRespVO convert(MovieDO bean);

    List<MovieRespVO> convertList(List<MovieDO> list);

    /**
     * 分页结果转换
     */
    default PageResult<MovieRespVO> convertPage(PageResult<MovieDO> page) {
        if (page == null) {
            return null;
        }
        List<MovieRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

    @Named("convertToSimple")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "movieTitle", target = "movieTitle")
    @Mapping(source = "directedBy", target = "directedBy")
    @Mapping(source = "ratingNum", target = "ratingNum")
    MovieSimpleRespVO convertToSimple(MovieDO bean);

    @IterableMapping(qualifiedByName = "convertToSimple")
    List<MovieSimpleRespVO> convertSimpleList(List<MovieDO> list);

    /**
     * 转换为电影详情响应 VO
     */
    default MovieDetailRespVO convertToDetail(MovieDO movie, List<CommentRespVO> comments, BigDecimal averageRating) {
        if (movie == null) {
            return null;
        }
        MovieDetailRespVO detailVO = new MovieDetailRespVO();
        detailVO.setId(movie.getId());
        detailVO.setMovieTitle(movie.getMovieTitle());
        detailVO.setReleaseDate(integerToLocalDate(movie.getReleaseDate()));
        detailVO.setDirectedBy(movie.getDirectedBy());
        detailVO.setStarring(movie.getStarring());
        detailVO.setGenre(movie.getGenre());
        detailVO.setRatingNum(movie.getRatingNum());
        detailVO.setCreateTime(movie.getCreateTime());
        detailVO.setComments(comments);
        detailVO.setAverageRating(averageRating);
        detailVO.setCommentCount(comments != null ? (long) comments.size() : 0L);
        return detailVO;
    }

    /**
     * 将 Integer 年份转换为 LocalDate
     * @param year 年份
     * @return LocalDate 对象，如果年份为空则返回 null
     */
    @Named("integerToLocalDate")
    default LocalDate integerToLocalDate(Integer year) {
        if (year == null) {
            return null;
        }
        return LocalDate.of(year, 1, 1); // 默认设置为该年的1月1日
    }

}