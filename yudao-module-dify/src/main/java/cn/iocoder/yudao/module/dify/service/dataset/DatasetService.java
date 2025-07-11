package cn.iocoder.yudao.module.dify.service.dataset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dify.controller.admin.dataset.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 知识库服务接口
 *
 * @author 芋道源码
 */
public interface DatasetService {

    /**
     * 创建知识库
     *
     * @param reqVO 创建请求
     * @return 知识库信息
     */
    DatasetRespVO createDataset(DatasetCreateReqVO reqVO);

    /**
     * 获取知识库列表
     *
     * @param page  页码
     * @param limit 每页数量
     * @return 知识库列表
     */
    PageResult<DatasetRespVO> getDatasetList(Integer page, Integer limit);

    /**
     * 删除知识库
     *
     * @param datasetId 知识库 ID
     */
    void deleteDataset(String datasetId);

    /**
     * 通过文本创建文档
     *
     * @param datasetId 知识库 ID
     * @param reqVO 创建请求
     * @return 文档信息
     */
    DocumentRespVO createDocumentByText(String datasetId, DocumentCreateByTextReqVO reqVO);

    /**
     * 通过文件创建文档
     *
     * @param datasetId 知识库 ID
     * @param file 文件
     * @param name 文档名称
     * @return 文档信息
     */
    DocumentRespVO createDocumentByFile(String datasetId, MultipartFile file, String name);

    /**
     * 获取文档列表
     *
     * @param datasetId 知识库 ID
     * @return 文档列表
     */
    List<DocumentRespVO> getDocumentList(String datasetId);

    /**
     * 删除文档
     *
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     */
    void deleteDocument(String datasetId, String documentId);

}
