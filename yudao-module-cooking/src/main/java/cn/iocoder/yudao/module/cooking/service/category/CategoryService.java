package cn.iocoder.yudao.module.cooking.service.category;

import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 分类 Service 接口
 *
 * @author 芋道源码
 */
public interface CategoryService {

    /**
     * 创建分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCategory(@Valid Object createReqVO);

    /**
     * 更新分类
     *
     * @param updateReqVO 更新信息
     */
    void updateCategory(@Valid Object updateReqVO);

    /**
     * 删除分类
     *
     * @param id 编号
     */
    void deleteCategory(Long id);

    /**
     * 获得分类
     *
     * @param id 编号
     * @return 分类
     */
    CategoryDO getCategory(Long id);

    /**
     * 获得分类列表
     *
     * @return 分类列表
     */
    List<CategoryDO> getCategoryList();

    /**
     * 获得菜谱分类列表
     *
     * @return 菜谱分类列表
     */
    List<CategoryDO> getRecipeCategories();

    /**
     * 获得食材分类列表
     *
     * @return 食材分类列表
     */
    List<CategoryDO> getIngredientCategories();

} 