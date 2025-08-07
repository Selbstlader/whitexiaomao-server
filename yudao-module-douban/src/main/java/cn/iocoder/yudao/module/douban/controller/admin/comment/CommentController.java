package cn.iocoder.yudao.module.douban.controller.admin.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.comment.vo.*;
import cn.iocoder.yudao.module.douban.convert.comment.CommentConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.CommentDO;
import cn.iocoder.yudao.module.douban.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 评论")
@RestController
@RequestMapping("/douban/comment")
@Validated
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/create")
    @Operation(summary = "创建评论")
    @PreAuthorize("@ss.hasPermission('douban:comment:create')")
    public CommonResult<Long> createComment(@Valid @RequestBody CommentCreateReqVO createReqVO) {
        return success(commentService.createComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新评论")
    @PreAuthorize("@ss.hasPermission('douban:comment:update')")
    public CommonResult<Boolean> updateComment(@Valid @RequestBody CommentUpdateReqVO updateReqVO) {
        commentService.updateComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除评论")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('douban:comment:delete')")
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        commentService.deleteComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得评论")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('douban:comment:query')")
    public CommonResult<CommentRespVO> getComment(@RequestParam("id") Long id) {
        CommentDO comment = commentService.getComment(id);
        return success(CommentConvert.INSTANCE.convert(comment));
    }

    @GetMapping("/list")
    @Operation(summary = "获得评论列表")
    @PreAuthorize("@ss.hasPermission('douban:comment:query')")
    public CommonResult<List<CommentRespVO>> getCommentList() {
        List<CommentDO> list = commentService.getCommentList();
        return success(CommentConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得评论分页")
    @PreAuthorize("@ss.hasPermission('douban:comment:query')")
    public CommonResult<PageResult<CommentRespVO>> getCommentPage(@Valid CommentPageReqVO pageVO) {
        PageResult<CommentRespVO> pageResult = commentService.getCommentPageWithMovieTitleForAdmin(pageVO);
        return success(pageResult);
    }

    @GetMapping("/list-by-movie")
    @Operation(summary = "根据电影ID获得评论列表")
    @Parameter(name = "movieId", description = "电影ID", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('douban:comment:query')")
    public CommonResult<List<CommentRespVO>> getCommentListByMovieId(@RequestParam("movieId") String movieId) {
        List<CommentDO> list = commentService.getCommentListByMovieId(movieId);
        return success(CommentConvert.INSTANCE.convertList(list));
    }

}