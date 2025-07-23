package cn.iocoder.yudao.module.cooking.service.step;

import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.StepDO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 烹飪步驟 Service 接口
 *
 * @author 芋道源碼
 */
public interface StepService {

    /**
     * 創建烹飪步驟
     *
     * @param createReqVO 創建信息
     * @return 編號
     */
    Long createStep(@Valid StepCreateReqVO createReqVO);

    /**
     * 更新烹飪步驟
     *
     * @param updateReqVO 更新信息
     */
    void updateStep(@Valid StepUpdateReqVO updateReqVO);

    /**
     * 刪除烹飪步驟
     *
     * @param id 編號
     */
    void deleteStep(Long id);

    /**
     * 批量創建烹飪步驟
     *
     * @param reqVO 批量創建信息
     * @return 編號列表
     */
    List<Long> batchCreateStep(@Valid StepBatchCreateReqVO reqVO);

    /**
     * 獲得烹飪步驟
     *
     * @param id 編號
     * @return 烹飪步驟
     */
    StepDO getStep(Long id);

    /**
     * 獲得指定菜品的烹飪步驟列表
     *
     * @param dishId 菜品編號
     * @return 烹飪步驟列表
     */
    List<StepDO> getStepListByDishId(Long dishId);

    /**
     * 校驗步驟存在於指定菜品下
     *
     * @param id 步驟編號
     * @param dishId 菜品編號
     */
    void validateStepBelongsToDish(Long id, Long dishId);

    /**
     * 處理烹飪步驟圖片
     *
     * @param imageFile 圖片文件
     * @return 圖片名稱
     */
    String processImage(MultipartFile imageFile);

} 