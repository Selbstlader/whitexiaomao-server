package cn.iocoder.yudao.module.cooking.service.step;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepUpdateReqVO;
import cn.iocoder.yudao.module.cooking.convert.step.StepConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.StepMapper;
import cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 烹飪步驟 Service 實現類
 *
 * @author 芋道源碼
 */
@Service
@Validated
@Slf4j
public class StepServiceImpl implements StepService {

    @Resource
    private StepMapper stepMapper;

    @Resource
    private DishService dishService;

    private static final String IMAGE_PATH = "cooking_images/steps/";
    private static final String[] ALLOWED_IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};

    @Override
    public Long createStep(StepCreateReqVO createReqVO) {
        // 校驗菜品是否存在
        validateDishExists(createReqVO.getDishId());
        // 校驗步驟序號是否已存在
        validateStepNumberUnique(null, createReqVO.getDishId(), createReqVO.getStepNumber());

        // 處理圖片
        String imageName = null;
        if (createReqVO.getImageFile() != null && !createReqVO.getImageFile().isEmpty()) {
            imageName = processImage(createReqVO.getImageFile());
        }

        // 插入
        StepDO step = StepConvert.INSTANCE.convert(createReqVO);
        step.setImageName(imageName);
        stepMapper.insert(step);
        return step.getId();
    }

    @Override
    public void updateStep(StepUpdateReqVO updateReqVO) {
        // 校驗存在
        validateStepExists(updateReqVO.getId());
        // 校驗菜品是否存在
        validateDishExists(updateReqVO.getDishId());
        // 校驗步驟序號是否已存在
        validateStepNumberUnique(updateReqVO.getId(), updateReqVO.getDishId(), updateReqVO.getStepNumber());

        // 處理圖片
        StepDO oldStep = stepMapper.selectById(updateReqVO.getId());
        String imageName = oldStep.getImageName();
        if (updateReqVO.getImageFile() != null && !updateReqVO.getImageFile().isEmpty()) {
            // 刪除舊圖片
            if (StrUtil.isNotBlank(imageName)) {
                deleteImage(imageName);
            }
            // 處理新圖片
            imageName = processImage(updateReqVO.getImageFile());
        }

        // 更新
        StepDO updateObj = StepConvert.INSTANCE.convert(updateReqVO);
        updateObj.setImageName(imageName);
        stepMapper.updateById(updateObj);
    }

    @Override
    public void deleteStep(Long id) {
        // 校驗存在
        validateStepExists(id);

        // 刪除步驟圖片
        StepDO step = stepMapper.selectById(id);
        if (StrUtil.isNotBlank(step.getImageName())) {
            deleteImage(step.getImageName());
        }

        // 刪除步驟
        stepMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchCreateStep(StepBatchCreateReqVO reqVO) {
        // 校驗菜品是否存在
        validateDishExists(reqVO.getDishId());

        // 校驗步驟序號是否重複
        List<Integer> numbers = new ArrayList<>();
        for (StepBatchCreateReqVO.StepInfoVO step : reqVO.getSteps()) {
            if (numbers.contains(step.getStepNumber())) {
                throw exception(ErrorCodeConstants.STEP_NUMBER_DUPLICATE);
            }
            numbers.add(step.getStepNumber());

            // 校驗步驟序號是否已存在於菜品中
            validateStepNumberUnique(null, reqVO.getDishId(), step.getStepNumber());
        }

        // 轉換並插入
        List<StepDO> stepList = StepConvert.INSTANCE.convertList(reqVO);
        List<Long> ids = new ArrayList<>(stepList.size());
        for (int i = 0; i < stepList.size(); i++) {
            StepDO step = stepList.get(i);
            StepBatchCreateReqVO.StepInfoVO stepInfo = reqVO.getSteps().get(i);

            // 處理圖片 - 注意：如果 StepInfoVO 中沒有 imageFile 屬性，需要註釋或刪除這段代碼
            /* 
            if (stepInfo.getImageFile() != null && !stepInfo.getImageFile().isEmpty()) {
                String imageName = processImage(stepInfo.getImageFile());
                step.setImageName(imageName);
            }
            */

            stepMapper.insert(step);
            ids.add(step.getId());
        }
        return ids;
    }

    private void validateStepExists(Long id) {
        if (stepMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.STEP_NOT_EXISTS);
        }
    }

    private void validateDishExists(Long dishId) {
        if (dishService.getDish(dishId) == null) {
            throw exception(ErrorCodeConstants.DISH_NOT_EXISTS);
        }
    }

    private void validateStepNumberUnique(Long id, Long dishId, Integer stepNumber) {
        StepDO step = stepMapper.selectByDishIdAndStepNumber(dishId, stepNumber);
        if (step == null) {
            return;
        }
        // 如果 id 為空，說明是創建時校驗，只要序號重複則報錯
        if (id == null) {
            throw exception(ErrorCodeConstants.STEP_NUMBER_DUPLICATE);
        }
        // 如果 id 不為空，說明是更新時校驗，如果序號重複且不是當前步驟，則報錯
        if (!id.equals(step.getId())) {
            throw exception(ErrorCodeConstants.STEP_NUMBER_DUPLICATE);
        }
    }

    @Override
    public StepDO getStep(Long id) {
        return stepMapper.selectById(id);
    }

    @Override
    public List<StepDO> getStepListByDishId(Long dishId) {
        return stepMapper.selectListByDishId(dishId);
    }

    @Override
    public void validateStepBelongsToDish(Long id, Long dishId) {
        StepDO step = stepMapper.selectById(id);
        if (step == null) {
            throw exception(ErrorCodeConstants.STEP_NOT_EXISTS);
        }
        if (!step.getDishId().equals(dishId)) {
            throw exception(ErrorCodeConstants.STEP_DISH_NOT_MATCH);
        }
    }

    @Override
    public String processImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        // 驗證文件類型
        String originalFilename = imageFile.getOriginalFilename();
        if (!isValidImageExtension(originalFilename)) {
            throw exception(ErrorCodeConstants.STEP_IMAGE_UPLOAD_FAILED, "不支持的圖片格式");
        }

        try {
            // 確保目錄存在
            File uploadDir = new File(IMAGE_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成唯一文件名
            String fileExtension = FileUtil.extName(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString(true) + "." + fileExtension;
            String filePath = IMAGE_PATH + uniqueFilename;

            // 保存文件
            File targetFile = new File(filePath);
            imageFile.transferTo(targetFile);

            return uniqueFilename;
        } catch (IOException e) {
            log.error("步驟圖片上傳失敗", e);
            throw exception(ErrorCodeConstants.STEP_IMAGE_UPLOAD_FAILED);
        }
    }

    private boolean isValidImageExtension(String filename) {
        if (StrUtil.isBlank(filename)) {
            return false;
        }
        String extension = "." + FileUtil.extName(filename).toLowerCase();
        return Arrays.asList(ALLOWED_IMAGE_EXTENSIONS).contains(extension);
    }

    private void deleteImage(String imageName) {
        if (StrUtil.isBlank(imageName)) {
            return;
        }
        try {
            File file = new File(IMAGE_PATH + imageName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error("刪除圖片失敗: {}", imageName, e);
        }
    }
} 