package cn.iocoder.yudao.module.douban.dal.mysql.movie;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.douban.controller.admin.movie.vo.MoviePageReqVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 豆瓣电影 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MovieMapper extends BaseMapperX<MovieDO> {

    default PageResult<MovieDO> selectPage(MoviePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MovieDO>()
                .likeIfPresent(MovieDO::getMovieTitle, reqVO.getMovieTitle())
                .eqIfPresent(MovieDO::getReleaseDate, reqVO.getReleaseDate())
                .likeIfPresent(MovieDO::getCountry, reqVO.getCountry())
                .likeIfPresent(MovieDO::getLanguage, reqVO.getLanguage())
                .geIfPresent(MovieDO::getRatingNum, reqVO.getMinRating())
                .leIfPresent(MovieDO::getRatingNum, reqVO.getMaxRating())
                .orderByDesc(MovieDO::getId));
    }

    default MovieDO selectByMovieId(String movieId) {
        return selectOne(MovieDO::getMovieId, movieId);
    }

    default List<MovieDO> selectListByMovieIds(Collection<String> movieIds) {
        return selectList(new LambdaQueryWrapperX<MovieDO>()
                .in(MovieDO::getMovieId, movieIds));
    }
}