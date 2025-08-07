package cn.iocoder.yudao.module.douban.controller.app.movie;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.app.movie.vo.*;
import cn.iocoder.yudao.module.douban.convert.movie.MovieAppConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.service.movie.MovieService;
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
 * 用户 App - 电影
 */
@Tag(name = "用户 App - 电影")
@RestController("appMovieController")
@RequestMapping("/douban/movie")
@Validated
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/get")
    @Operation(summary = "获得电影详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<MovieRespVO> getMovie(@RequestParam("id") Long id) {
        MovieDO movie = movieService.getMovie(id);
        return success(MovieAppConvert.INSTANCE.convert(movie));
    }

    @GetMapping("/detail")
    @Operation(summary = "获得电影详情（包含评论）")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<MovieDetailRespVO> getMovieDetail(@RequestParam("id") Long id) {
        return success(movieService.getMovieDetailForApp(id));
    }

    @GetMapping("/list-simple")
    @Operation(summary = "获得电影简略列表")
    public CommonResult<List<MovieSimpleRespVO>> getSimpleMovieList() {
        List<MovieDO> list = movieService.getMovieList();
        return success(MovieAppConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得豆瓣电影分页")
    public CommonResult<PageResult<MovieRespVO>> getMoviePage(@Valid MoviePageReqVO pageVO) {
        // 转换为Admin端分页请求
        cn.iocoder.yudao.module.douban.controller.admin.movie.vo.MoviePageReqVO adminPageVO = 
            new cn.iocoder.yudao.module.douban.controller.admin.movie.vo.MoviePageReqVO();
        adminPageVO.setMovieTitle(pageVO.getMovieTitle());
        adminPageVO.setDirectedBy(pageVO.getDirectedBy());
        adminPageVO.setGenre(pageVO.getGenre());
        adminPageVO.setMinRating(pageVO.getMinRating());
        adminPageVO.setPageNo(pageVO.getPageNo());
        adminPageVO.setPageSize(pageVO.getPageSize());
        
        PageResult<MovieDO> pageResult = movieService.getMoviePage(adminPageVO);
        return success(MovieAppConvert.INSTANCE.convertPage(pageResult));
    }

}