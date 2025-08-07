package cn.iocoder.yudao.module.douban.service.movie;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.movie.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 豆瓣电影 Service 接口
 *
 * @author 芋道源码
 */
public interface MovieService {

    /**
     * 创建电影
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMovie(@Valid MovieCreateReqVO createReqVO);

    /**
     * 更新电影
     *
     * @param updateReqVO 更新信息
     */
    void updateMovie(@Valid MovieUpdateReqVO updateReqVO);

    /**
     * 删除电影
     *
     * @param id 编号
     */
    void deleteMovie(Long id);

    /**
     * 获得电影
     *
     * @param id 编号
     * @return 电影
     */
    MovieDO getMovie(Long id);

    /**
     * 获得电影列表（无参数）
     *
     * @return 电影列表
     */
    List<MovieDO> getMovieList();

    /**
     * 获得电影详情（App端）
     *
     * @param id 编号
     * @return 电影详情
     */
    cn.iocoder.yudao.module.douban.controller.app.movie.vo.MovieDetailRespVO getMovieDetailForApp(Long id);

    /**
     * 获得电影列表
     *
     * @param ids 编号
     * @return 电影列表
     */
    List<MovieDO> getMovieList(Collection<Long> ids);

    /**
     * 获得电影分页
     *
     * @param pageReqVO 分页查询
     * @return 电影分页
     */
    PageResult<MovieDO> getMoviePage(MoviePageReqVO pageReqVO);

    /**
     * 根据电影ID获得电影
     *
     * @param movieId 电影ID
     * @return 电影
     */
    MovieDO getMovieByMovieId(String movieId);

    /**
     * 获得电影详情
     *
     * @param id 编号
     * @return 电影详情
     */
    MovieDetailRespVO getMovieDetail(Long id);

    /**
     * 根据电影ID列表批量获得电影列表
     *
     * @param movieIds 电影ID列表
     * @return 电影列表
     */
    List<MovieDO> getMovieListByMovieIds(Collection<String> movieIds);
}