package cn.iocoder.yudao.module.douban.controller.app.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.CommentPageReqVO;
import cn.iocoder.yudao.module.douban.controller.app.comment.vo.*;
import cn.iocoder.yudao.module.douban.convert.comment.CommentAppConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 用户 App - 评论
 */
@Tag(name = "用户 App - 评论")
@RestController("appCommentController")
@RequestMapping("/douban/comment")
@Validated
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    @Operation(summary = "创建豆瓣评论")
    public CommonResult<Long> createComment(@Valid @RequestBody CommentCreateReqVO createReqVO) {
        // 转换为Admin端VO
        cn.iocoder.yudao.module.douban.controller.admin.comment.vo.CommentCreateReqVO adminCreateReqVO = 
            new cn.iocoder.yudao.module.douban.controller.admin.comment.vo.CommentCreateReqVO();
        adminCreateReqVO.setMovieId(createReqVO.getMovieId());
        adminCreateReqVO.setPeople(createReqVO.getPeople());
        adminCreateReqVO.setStar(createReqVO.getStar());
        adminCreateReqVO.setContent(createReqVO.getContent());
        
        return success(commentService.createComment(adminCreateReqVO));
    }

    @GetMapping("/list-by-movie")
    @Operation(summary = "获得豆瓣评论列表")
    @Parameter(name = "movieId", description = "电影编号", required = true)
    public CommonResult<List<CommentRespVO>> getCommentListByMovie(@RequestParam("movieId") String movieId) {
        List<CommentDO> list = commentService.getCommentListByMovieId(movieId);
        return success(CommentAppConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page-by-movie")
    @Operation(summary = "获得豆瓣评论分页")
    @Parameter(name = "movieId", description = "电影编号", required = true)
    public CommonResult<PageResult<CommentRespVO>> getCommentPageByMovie(@RequestParam("movieId") String movieId,
                                                                        @Valid PageParam pageParam) {
        CommentPageReqVO pageReqVO = new CommentPageReqVO();
        pageReqVO.setMovieId(movieId);
        pageReqVO.setPageNo(pageParam.getPageNo());
        pageReqVO.setPageSize(pageParam.getPageSize());
        
        PageResult<CommentRespVO> pageResult = commentService.getCommentPageWithMovieTitle(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CommentRespVO> getComment(@RequestParam("id") Long id) {
        CommentDO comment = commentService.getComment(id);
        return success(CommentAppConvert.INSTANCE.convert(comment));
    }

    @GetMapping("/list-by-movie-id")
    @Operation(summary = "根据电影ID获得评论列表")
    @Parameter(name = "movieId", description = "电影ID", required = true, example = "1024")
    public CommonResult<List<CommentRespVO>> getCommentListByMovieId(@RequestParam("movieId") String movieId) {
        List<CommentDO> list = commentService.getCommentListByMovieId(movieId);
        return success(CommentAppConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list")
    @Operation(summary = "获得评论列表")
    public CommonResult<List<CommentRespVO>> getCommentList(@Valid CommentListReqVO listReqVO) {
        List<CommentDO> list = commentService.getCommentListByMovieId(listReqVO.getMovieId());
        return success(CommentAppConvert.INSTANCE.convertList(list));
    }

}