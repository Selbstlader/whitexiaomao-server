package cn.iocoder.yudao.module.douban.service.comment;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.douban.convert.comment.CommentConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.dal.mysql.comment.CommentMapper;
import cn.iocoder.yudao.module.douban.service.movie.MovieService;
import cn.iocoder.yudao.module.douban.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 豆瓣电影评论 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    
    @Resource
    private MovieService movieService;

    @Override
    public Long createComment(CommentCreateReqVO createReqVO) {
        // 插入
        CommentDO comment = BeanUtils.toBean(createReqVO, CommentDO.class);
        commentMapper.insert(comment);
        // 返回
        return comment.getId();
    }

    @Override
    public void updateComment(CommentUpdateReqVO updateReqVO) {
        // 校验存在
        validateCommentExists(updateReqVO.getId());
        
        // 更新
        CommentDO updateObj = BeanUtils.toBean(updateReqVO, CommentDO.class);
        commentMapper.updateById(updateObj);
    }

    @Override
    public void deleteComment(Long id) {
        // 校验存在
        validateCommentExists(id);
        // 删除
        commentMapper.deleteById(id);
    }

    private void validateCommentExists(Long id) {
        if (commentMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.COMMENT_NOT_EXISTS);
        }
    }

    @Override
    public CommentDO getComment(Long id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<CommentDO> getCommentList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return CollUtil.newArrayList();
        }
        return commentMapper.selectList("id", ids);
    }

    @Override
    public List<CommentDO> getCommentList() {
        return commentMapper.selectList();
    }

    @Override
    public PageResult<CommentDO> getCommentPage(CommentPageReqVO pageReqVO) {
        return commentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CommentDO> getCommentListByMovieId(String movieId) {
        return commentMapper.selectListByMovieId(movieId);
    }

    @Override
    public PageResult<cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO> getCommentPageWithMovieTitle(CommentPageReqVO pageReqVO) {
        log.info("开始执行原生SQL分页查询评论和电影信息, 查询参数: {}", pageReqVO);
        
        // 1. 创建分页对象
        IPage<Map<String, Object>> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        
        // 2. 使用原生SQL查询评论和电影信息
        IPage<Map<String, Object>> resultPage = commentMapper.selectPageWithMovieTitle(page, pageReqVO);
        log.info("原生SQL查询结果: 总数={}, 当前页数据量={}", resultPage.getTotal(), resultPage.getRecords().size());
        
        if (CollUtil.isEmpty(resultPage.getRecords())) {
            return PageResult.empty(resultPage.getTotal());
        }
        
        // 3. 转换Map结果为CommentRespVO
        List<cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO> commentRespVOList = new ArrayList<>();
        for (Map<String, Object> record : resultPage.getRecords()) {
            cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO respVO = new cn.iocoder.yudao.module.douban.controller.app.comment.vo.CommentRespVO();
            
            // 设置评论基本信息
            respVO.setId(((Number) record.get("id")).longValue());
            respVO.setCommentId((String) record.get("comment_id"));
            respVO.setMovieId((String) record.get("movie_id"));
            respVO.setPeople((String) record.get("people"));
            respVO.setStar(record.get("star") != null ? ((Number) record.get("star")).intValue() : null);
            respVO.setContent((String) record.get("content"));
            respVO.setUsefulNum(record.get("useful_num") != null ? ((Number) record.get("useful_num")).intValue() : null);
            respVO.setCreateTime((java.time.LocalDateTime) record.get("create_time"));
            respVO.setUpdateTime((java.time.LocalDateTime) record.get("update_time"));
            
            // 设置电影标题
            respVO.setMovieTitle((String) record.get("movieTitle"));
            
            commentRespVOList.add(respVO);
            log.debug("转换记录: id={}, movieId={}, movieTitle={}", respVO.getId(), respVO.getMovieId(), respVO.getMovieTitle());
        }
        
        log.info("成功转换{}条记录", commentRespVOList.size());
        
        // 4. 返回分页结果
        return new PageResult<>(commentRespVOList, resultPage.getTotal());
    }

    @Override
    public PageResult<CommentRespVO> getCommentPageWithMovieTitleForAdmin(CommentPageReqVO pageReqVO) {
        log.info("开始执行原生SQL分页查询评论和电影信息(管理后台), 查询参数: {}", pageReqVO);
        
        // 1. 创建分页对象
        IPage<Map<String, Object>> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        
        // 2. 使用原生SQL查询评论和电影信息
        IPage<Map<String, Object>> resultPage = commentMapper.selectPageWithMovieTitle(page, pageReqVO);
        log.info("原生SQL查询结果: 总数={}, 当前页数据量={}", resultPage.getTotal(), resultPage.getRecords().size());
        
        if (CollUtil.isEmpty(resultPage.getRecords())) {
            return PageResult.empty(resultPage.getTotal());
        }
        
        // 3. 转换Map结果为admin包下的CommentRespVO
        List<CommentRespVO> commentRespVOList = new ArrayList<>();
        for (Map<String, Object> record : resultPage.getRecords()) {
            CommentRespVO respVO = new CommentRespVO();
            
            // 设置评论基本信息
            respVO.setId(((Number) record.get("id")).longValue());
            respVO.setMovieId((String) record.get("movie_id"));
            respVO.setPeople((String) record.get("people"));
            respVO.setStar(record.get("star") != null ? ((Number) record.get("star")).intValue() : null);
            respVO.setContent((String) record.get("content"));
            respVO.setCreateTime((java.time.LocalDateTime) record.get("create_time"));
            
            // 设置电影标题
            respVO.setMovieTitle((String) record.get("movieTitle"));
            
            commentRespVOList.add(respVO);
            log.debug("转换记录: id={}, movieId={}, movieTitle={}", respVO.getId(), respVO.getMovieId(), respVO.getMovieTitle());
        }
        
        log.info("成功转换{}条记录", commentRespVOList.size());
        
        // 4. 返回分页结果
        return new PageResult<>(commentRespVOList, resultPage.getTotal());
    }
}