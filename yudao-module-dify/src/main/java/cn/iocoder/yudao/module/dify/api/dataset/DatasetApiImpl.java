package cn.iocoder.yudao.module.dify.api.dataset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dify.controller.admin.dataset.vo.*;
import cn.iocoder.yudao.module.dify.service.dataset.DatasetService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * Dify 知识库 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DatasetApiImpl implements DatasetApi {

    @Resource
    private DatasetService datasetService;

    @Override
    public DatasetRespVO createDataset(DatasetCreateReqVO reqVO) {
        return datasetService.createDataset(reqVO);
    }

    @Override
    public PageResult<DatasetRespVO> getDatasetList(Integer page, Integer limit) {
        return datasetService.getDatasetList(page, limit);
    }

    @Override
    public void deleteDataset(String datasetId) {
        datasetService.deleteDataset(datasetId);
    }

    @Override
    public DocumentRespVO createDocumentByText(String datasetId, DocumentCreateByTextReqVO reqVO) {
        return datasetService.createDocumentByText(datasetId, reqVO);
    }

    @Override
    public DocumentRespVO createDocumentByFile(String datasetId, MultipartFile file, String name) {
        return datasetService.createDocumentByFile(datasetId, file, name);
    }

    @Override
    public List<DocumentRespVO> getDocumentList(String datasetId) {
        return datasetService.getDocumentList(datasetId);
    }

    @Override
    public void deleteDocument(String datasetId, String documentId) {
        datasetService.deleteDocument(datasetId, documentId);
    }

}
