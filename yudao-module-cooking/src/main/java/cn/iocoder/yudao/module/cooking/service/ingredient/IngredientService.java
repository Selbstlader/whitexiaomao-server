package cn.iocoder.yudao.module.cooking.service.ingredient;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient.IngredientDO;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 食材 Service 接口
 *
 * @author 芋道源码
 */
public interface IngredientService {

    /**
     * 创建食材
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createIngredient(@Valid IngredientCreateReqVO createReqVO);

    /**
     * 更新食材
     *
     * @param updateReqVO 更新信息
     */
    void updateIngredient(@Valid IngredientUpdateReqVO updateReqVO);

    /**
     * 删除食材
     *
     * @param id 编号
     */
    void deleteIngredient(Long id);

    /**
     * 获得食材
     *
     * @param id 编号
     * @return 食材
     */
    IngredientDO getIngredient(Long id);

    /**
     * 获得食材分页
     *
     * @param pageReqVO 分页查询
     * @return 食材分页
     */
    PageResult<IngredientDO> getIngredientPage(IngredientPageReqVO pageReqVO);

    /**
     * 获得食材列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 食材列表
     */
    List<IngredientDO> getIngredientList(IngredientExportReqVO exportReqVO);

    /**
     * 根据分类获得食材列表
     *
     * @param categoryId 分类编号
     * @return 食材列表
     */
    List<IngredientDO> getIngredientListByCategory(Long categoryId);

    /**
     * 搜索食材
     *
     * @param keyword 关键词
     * @return 食材列表
     */
    List<IngredientDO> searchIngredients(String keyword);

}
