package cn.iocoder.yudao.module.douban.service.movie;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.douban.controller.admin.movie.vo.*;
import cn.iocoder.yudao.module.douban.convert.movie.MovieConvert;
import cn.iocoder.yudao.module.douban.convert.movie.MovieAppConvert;
import cn.iocoder.yudao.module.douban.convert.comment.CommentAppConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.dal.mysql.comment.CommentMapper;
import cn.iocoder.yudao.module.douban.dal.mysql.movie.MovieMapper;
import cn.iocoder.yudao.module.douban.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 豆瓣电影 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Resource
    private MovieMapper movieMapper;
    
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Long createMovie(MovieCreateReqVO createReqVO) {
        // 校验电影ID是否存在
        validateMovieIdExists(createReqVO.getMovieId(), null);
        
        // 插入
        MovieDO movie = BeanUtils.toBean(createReqVO, MovieDO.class);
        movieMapper.insert(movie);
        // 返回
        return movie.getId();
    }

    @Override
    public void updateMovie(MovieUpdateReqVO updateReqVO) {
        // 校验存在
        validateMovieExists(updateReqVO.getId());
        // 校验电影ID是否存在
        validateMovieIdExists(updateReqVO.getMovieId(), updateReqVO.getId());
        
        // 更新
        MovieDO updateObj = BeanUtils.toBean(updateReqVO, MovieDO.class);
        movieMapper.updateById(updateObj);
    }

    @Override
    public void deleteMovie(Long id) {
        // 校验存在
        validateMovieExists(id);
        // 删除
        movieMapper.deleteById(id);
    }

    private void validateMovieExists(Long id) {
        if (movieMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.MOVIE_NOT_EXISTS);
        }
    }

    private void validateMovieIdExists(String movieId, Long id) {
        MovieDO movie = movieMapper.selectByMovieId(movieId);
        if (movie == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的电影
        if (id == null) {
            throw exception(ErrorCodeConstants.MOVIE_ID_EXISTS);
        }
        if (!movie.getId().equals(id)) {
            throw exception(ErrorCodeConstants.MOVIE_ID_EXISTS);
        }
    }

    @Override
    public MovieDO getMovie(Long id) {
        return movieMapper.selectById(id);
    }

    @Override
    public List<MovieDO> getMovieList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return CollUtil.newArrayList();
        }
        return movieMapper.selectList("id", ids);
    }

    @Override
    public PageResult<MovieDO> getMoviePage(MoviePageReqVO pageReqVO) {
        return movieMapper.selectPage(pageReqVO);
    }

    @Override
    public MovieDO getMovieByMovieId(String movieId) {
        return movieMapper.selectByMovieId(movieId);
    }

    @Override
    public MovieDetailRespVO getMovieDetail(Long id) {
        MovieDO movie = getMovieForValidation(id);
        
        // 获取电影评论
        List<CommentDO> comments = commentMapper.selectListByMovieId(movie.getMovieId());
        
        // 计算平均评分
        BigDecimal averageRating = BigDecimal.ZERO;
        if (CollUtil.isNotEmpty(comments)) {
            double avg = comments.stream()
                .mapToInt(CommentDO::getStar)
                .average()
                .orElse(0.0);
            averageRating = BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP);
        }
        
        // 转换并返回
        return MovieConvert.INSTANCE.convertToDetail(movie, comments, averageRating);
    }

    @Override
    public cn.iocoder.yudao.module.douban.controller.app.movie.vo.MovieDetailRespVO getMovieDetailForApp(Long id) {
        MovieDO movie = getMovieForValidation(id);
        
        // 获取电影评论
        List<CommentDO> comments = commentMapper.selectListByMovieId(movie.getMovieId());
        
        // 计算平均评分
        BigDecimal averageRating = BigDecimal.ZERO;
        if (CollUtil.isNotEmpty(comments)) {
            double avg = comments.stream()
                .mapToInt(CommentDO::getStar)
                .average()
                .orElse(0.0);
            averageRating = BigDecimal.valueOf(avg).setScale(1, RoundingMode.HALF_UP);
        }
        
        // 转换评论为app格式
        List<cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO> appComments = 
            CommentAppConvert.INSTANCE.convertListWithMovie(comments, movie);
        
        // 转换并返回app格式的详情
        return MovieAppConvert.INSTANCE.convertToDetail(movie, appComments, averageRating);
    }

    @Override
    public List<MovieDO> getMovieList() {
        return movieMapper.selectList();
    }
    
    private MovieDO getMovieForValidation(Long id) {
        MovieDO movie = movieMapper.selectById(id);
        if (movie == null) {
            throw exception(ErrorCodeConstants.MOVIE_NOT_EXISTS);
        }
        return movie;
    }

    @Override
    public List<MovieDO> getMovieListByMovieIds(Collection<String> movieIds) {
        if (CollUtil.isEmpty(movieIds)) {
            return CollUtil.newArrayList();
        }
        return movieMapper.selectListByMovieIds(movieIds);
    }
}