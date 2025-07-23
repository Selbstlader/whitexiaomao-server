package cn.iocoder.yudao.module.cooking.service.ingredient;

import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 配料 Service 接口
 *
 * @author 芋道源碼
 */
public interface IngredientService {

    /**
     * 創建配料
     *
     * @param createReqVO 創建信息
     * @return 編號
     */
    Long createIngredient(@Valid IngredientCreateReqVO createReqVO);

    /**
     * 更新配料
     *
     * @param updateReqVO 更新信息
     */
    void updateIngredient(@Valid IngredientUpdateReqVO updateReqVO);

    /**
     * 刪除配料
     *
     * @param id 編號
     */
    void deleteIngredient(Long id);

    /**
     * 批量創建配料
     *
     * @param reqVO 批量創建信息
     * @return 編號列表
     */
    List<Long> batchCreateIngredient(@Valid IngredientBatchCreateReqVO reqVO);

    /**
     * 獲得配料
     *
     * @param id 編號
     * @return 配料
     */
    IngredientDO getIngredient(Long id);

    /**
     * 獲得指定菜品的配料列表
     *
     * @param dishId 菜品編號
     * @return 配料列表
     */
    List<IngredientDO> getIngredientListByDishId(Long dishId);

    /**
     * 校驗配料存在於指定菜品下
     *
     * @param id 配料編號
     * @param dishId 菜品編號
     */
    void validateIngredientBelongsToDish(Long id, Long dishId);

} 