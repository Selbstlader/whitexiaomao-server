package cn.iocoder.yudao.module.dify.controller.admin.dataset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dify.controller.admin.dataset.vo.*;
import cn.iocoder.yudao.module.dify.service.dataset.DatasetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - Dify 知识库控制器
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - Dify 知识库")
@RestController
@RequestMapping("/dify/dataset")
@Validated
public class DatasetController {

    @Resource
    private DatasetService datasetService;

    @PostMapping("/create")
    @Operation(summary = "创建知识库")
    @PreAuthorize("@ss.hasPermission('dify:dataset:create')")
    public CommonResult<DatasetRespVO> createDataset(@Valid @RequestBody DatasetCreateReqVO reqVO) {
        DatasetRespVO response = datasetService.createDataset(reqVO);
        return success(response);
    }

    @GetMapping("/list")
    @Operation(summary = "获取知识库列表")
    @PreAuthorize("@ss.hasPermission('dify:dataset:query')")
    public CommonResult<PageResult<DatasetRespVO>> getDatasetList(
            @Parameter(description = "页码", example = "1") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页数量", example = "20") @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        PageResult<DatasetRespVO> response = datasetService.getDatasetList(page, limit);
        return success(response);
    }

    @DeleteMapping("/delete/{datasetId}")
    @Operation(summary = "删除知识库")
    @PreAuthorize("@ss.hasPermission('dify:dataset:delete')")
    public CommonResult<Boolean> deleteDataset(@PathVariable("datasetId") String datasetId) {
        datasetService.deleteDataset(datasetId);
        return success(true);
    }

    @PostMapping("/{datasetId}/document/create-by-text")
    @Operation(summary = "通过文本创建文档")
    @PreAuthorize("@ss.hasPermission('dify:document:create')")
    public CommonResult<DocumentRespVO> createDocumentByText(
            @PathVariable("datasetId") String datasetId,
            @Valid @RequestBody DocumentCreateByTextReqVO reqVO) {
        DocumentRespVO response = datasetService.createDocumentByText(datasetId, reqVO);
        return success(response);
    }

    @PostMapping("/{datasetId}/document/create-by-file")
    @Operation(summary = "通过文件创建文档")
    @PreAuthorize("@ss.hasPermission('dify:document:create')")
    public CommonResult<DocumentRespVO> createDocumentByFile(
            @PathVariable("datasetId") String datasetId,
            @Parameter(description = "文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "文档名称") @RequestParam(value = "name", required = false) String name) {
        DocumentRespVO response = datasetService.createDocumentByFile(datasetId, file, name);
        return success(response);
    }

    @GetMapping("/{datasetId}/documents")
    @Operation(summary = "获取文档列表")
    @PreAuthorize("@ss.hasPermission('dify:document:query')")
    public CommonResult<List<DocumentRespVO>> getDocumentList(@PathVariable("datasetId") String datasetId) {
        List<DocumentRespVO> response = datasetService.getDocumentList(datasetId);
        return success(response);
    }

    @DeleteMapping("/{datasetId}/document/{documentId}")
    @Operation(summary = "删除文档")
    @PreAuthorize("@ss.hasPermission('dify:document:delete')")
    public CommonResult<Boolean> deleteDocument(
            @PathVariable("datasetId") String datasetId,
            @PathVariable("documentId") String documentId) {
        datasetService.deleteDocument(datasetId, documentId);
        return success(true);
    }

}
