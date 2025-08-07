package cn.iocoder.yudao.module.douban.controller.admin.movie;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.movie.vo.*;
import cn.iocoder.yudao.module.douban.convert.movie.MovieConvert;
import cn.iocoder.yudao.module.douban.dal.dataobject.MovieDO;
import cn.iocoder.yudao.module.douban.service.movie.MovieService;
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

@Tag(name = "管理后台 - 电影")
@RestController
@RequestMapping("/douban/movie")
@Validated
public class MovieController {

    @Resource
    private MovieService movieService;

    @PostMapping("/create")
    @Operation(summary = "创建电影")
    @PreAuthorize("@ss.hasPermission('douban:movie:create')")
    public CommonResult<Long> createMovie(@Valid @RequestBody MovieCreateReqVO createReqVO) {
        return success(movieService.createMovie(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电影")
    @PreAuthorize("@ss.hasPermission('douban:movie:update')")
    public CommonResult<Boolean> updateMovie(@Valid @RequestBody MovieUpdateReqVO updateReqVO) {
        movieService.updateMovie(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电影")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('douban:movie:delete')")
    public CommonResult<Boolean> deleteMovie(@RequestParam("id") Long id) {
        movieService.deleteMovie(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电影")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('douban:movie:query')")
    public CommonResult<MovieRespVO> getMovie(@RequestParam("id") Long id) {
        MovieDO movie = movieService.getMovie(id);
        return success(MovieConvert.INSTANCE.convert(movie));
    }

    @GetMapping("/detail")
    @Operation(summary = "获得电影详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('douban:movie:query')")
    public CommonResult<MovieDetailRespVO> getMovieDetail(@RequestParam("id") Long id) {
        return success(movieService.getMovieDetail(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获得电影列表")
    @PreAuthorize("@ss.hasPermission('douban:movie:query')")
    public CommonResult<List<MovieRespVO>> getMovieList() {
        List<MovieDO> list = movieService.getMovieList();
        return success(MovieConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电影分页")
    @PreAuthorize("@ss.hasPermission('douban:movie:query')")
    public CommonResult<PageResult<MovieRespVO>> getMoviePage(@Valid MoviePageReqVO pageVO) {
        PageResult<MovieDO> pageResult = movieService.getMoviePage(pageVO);
        return success(MovieConvert.INSTANCE.convertPage(pageResult));
    }

}