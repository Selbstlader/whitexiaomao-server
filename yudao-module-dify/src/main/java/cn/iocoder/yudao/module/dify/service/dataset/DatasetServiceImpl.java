package cn.iocoder.yudao.module.dify.service.dataset;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.module.dify.controller.admin.dataset.vo.*;
import cn.iocoder.yudao.module.dify.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.dify.framework.config.DifyProperties;
import cn.iocoder.yudao.module.dify.util.DifyHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识库服务实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DatasetServiceImpl implements DatasetService {

    @Resource
    private DifyHttpClient difyHttpClient;

    @Resource
    private DifyProperties difyProperties;

    @Override
    public DatasetRespVO createDataset(DatasetCreateReqVO reqVO) {
        ValidationUtils.validate(reqVO);
        
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("name", reqVO.getName());
            requestBody.put("permission", reqVO.getPermission());
            if (reqVO.getDescription() != null) {
                requestBody.put("description", reqVO.getDescription());
            }
            
            String url = "/v1/datasets";
            DatasetRespVO response = difyHttpClient.post(url, difyProperties.getApiKey(), 
                    requestBody, DatasetRespVO.class);
            
            log.info("Dify 知识库创建成功: datasetId={}, name={}", response.getId(), response.getName());
            return response;
        } catch (ServiceException e) {
            log.error("Dify 知识库创建失败: name={}", reqVO.getName(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 知识库创建异常: name={}", reqVO.getName(), e);
            throw new ServiceException(ErrorCodeConstants.DATASET_CREATE_FAIL.getCode(), "创建知识库失败: " + e.getMessage());
        }
    }

    @Override
    public PageResult<DatasetRespVO> getDatasetList(Integer page, Integer limit) {
        try {
            String url = "/v1/datasets?page=" + page + "&limit=" + limit;
            DatasetListRespVO response = difyHttpClient.get(url, difyProperties.getApiKey(), DatasetListRespVO.class);

            log.info("Dify 获取知识库列表成功: page={}, limit={}, total={}", page, limit, response.getTotal());

            // 构造分页结果
            PageResult<DatasetRespVO> pageResult = new PageResult<>();
            pageResult.setList(response.getData());
            pageResult.setTotal(response.getTotal());

            return pageResult;
        } catch (ServiceException e) {
            log.error("Dify 获取知识库列表失败: page={}, limit={}", page, limit, e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 获取知识库列表异常: page={}, limit={}", page, limit, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "获取知识库列表失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteDataset(String datasetId) {
        try {
            String url = "/v1/datasets/" + datasetId;
            difyHttpClient.delete(url, difyProperties.getApiKey(), String.class);

            log.info("Dify 知识库删除成功: datasetId={}", datasetId);
        } catch (ServiceException e) {
            log.error("Dify 知识库删除失败: datasetId={}", datasetId, e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 知识库删除异常: datasetId={}", datasetId, e);
            throw new ServiceException(ErrorCodeConstants.DATASET_DELETE_FAIL.getCode(), "删除知识库失败: " + e.getMessage());
        }
    }

    @Override
    public DocumentRespVO createDocumentByText(String datasetId, DocumentCreateByTextReqVO reqVO) {
        ValidationUtils.validate(reqVO);
        
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("name", reqVO.getName());
            requestBody.put("text", reqVO.getText());
            requestBody.put("indexing_technique", reqVO.getIndexingTechnique());
            requestBody.put("process_rule", reqVO.getProcessRule());
            
            String url = "/v1/datasets/" + datasetId + "/document/create_by_text";
            DocumentCreateRespVO response = difyHttpClient.post(url, difyProperties.getApiKey(), 
                    requestBody, DocumentCreateRespVO.class);
            
            log.info("Dify 文档创建成功: datasetId={}, documentId={}, name={}", 
                    datasetId, response.getDocument().getId(), response.getDocument().getName());
            
            return response.getDocument();
        } catch (ServiceException e) {
            log.error("Dify 文档创建失败: datasetId={}, name={}", datasetId, reqVO.getName(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 文档创建异常: datasetId={}, name={}", datasetId, reqVO.getName(), e);
            throw new ServiceException(ErrorCodeConstants.DOCUMENT_UPLOAD_FAIL.getCode(), "创建文档失败: " + e.getMessage());
        }
    }

    @Override
    public DocumentRespVO createDocumentByFile(String datasetId, MultipartFile file, String name) {
        try {
            // 创建临时文件
            Path tempFile = Files.createTempFile("dify-upload-", file.getOriginalFilename());
            file.transferTo(tempFile.toFile());
            
            Map<String, Object> data = new HashMap<>();
            data.put("indexing_technique", "high_quality");
            data.put("process_rule", Map.of("mode", "automatic"));
            
            String url = "/v1/datasets/" + datasetId + "/document/create_by_file";
            DocumentCreateRespVO response = difyHttpClient.postFile(url, difyProperties.getApiKey(), 
                    tempFile.toFile(), data, DocumentCreateRespVO.class);
            
            // 删除临时文件
            Files.deleteIfExists(tempFile);
            
            log.info("Dify 文件上传成功: datasetId={}, documentId={}, fileName={}", 
                    datasetId, response.getDocument().getId(), file.getOriginalFilename());
            
            return response.getDocument();
        } catch (IOException e) {
            log.error("文件处理失败: datasetId={}, fileName={}", datasetId, file.getOriginalFilename(), e);
            throw new ServiceException(ErrorCodeConstants.DOCUMENT_UPLOAD_FAIL.getCode(), "文件处理失败: " + e.getMessage());
        } catch (ServiceException e) {
            log.error("Dify 文件上传失败: datasetId={}, fileName={}", datasetId, file.getOriginalFilename(), e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 文件上传异常: datasetId={}, fileName={}", datasetId, file.getOriginalFilename(), e);
            throw new ServiceException(ErrorCodeConstants.DOCUMENT_UPLOAD_FAIL.getCode(), "文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public List<DocumentRespVO> getDocumentList(String datasetId) {
        try {
            String url = "/v1/datasets/" + datasetId + "/documents";
            DocumentListRespVO response = difyHttpClient.get(url, difyProperties.getApiKey(), DocumentListRespVO.class);
            
            return response.getData();
        } catch (ServiceException e) {
            log.error("Dify 获取文档列表失败: datasetId={}", datasetId, e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 获取文档列表异常: datasetId={}", datasetId, e);
            throw new ServiceException(ErrorCodeConstants.DIFY_API_ERROR.getCode(), "获取文档列表失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteDocument(String datasetId, String documentId) {
        try {
            String url = "/v1/datasets/" + datasetId + "/documents/" + documentId;
            difyHttpClient.delete(url, difyProperties.getApiKey(), String.class);
            
            log.info("Dify 文档删除成功: datasetId={}, documentId={}", datasetId, documentId);
        } catch (ServiceException e) {
            log.error("Dify 文档删除失败: datasetId={}, documentId={}", datasetId, documentId, e);
            throw e;
        } catch (Exception e) {
            log.error("Dify 文档删除异常: datasetId={}, documentId={}", datasetId, documentId, e);
            throw new ServiceException(ErrorCodeConstants.DOCUMENT_DELETE_FAIL.getCode(), "删除文档失败: " + e.getMessage());
        }
    }

}
