package cn.iocoder.yudao.module.cooking.service.category;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 菜品分類 Service 接口
 *
 * @author 芋道源碼
 */
public interface CategoryService {

    /**
     * 創建菜品分類
     *
     * @param createReqVO 創建信息
     * @return 編號
     */
    Long createCategory(@Valid CategoryCreateReqVO createReqVO);

    /**
     * 更新菜品分類
     *
     * @param updateReqVO 更新信息
     */
    void updateCategory(@Valid CategoryUpdateReqVO updateReqVO);

    /**
     * 刪除菜品分類
     *
     * @param id 編號
     */
    void deleteCategory(Long id);

    /**
     * 獲得菜品分類
     *
     * @param id 編號
     * @return 菜品分類
     */
    CategoryDO getCategory(Long id);

    /**
     * 獲得所有菜品分類列表
     *
     * @return 菜品分類列表
     */
    List<CategoryDO> getCategoryList();

    /**
     * 根據編號列表獲取菜品分類列表
     *
     * @param ids 編號列表
     * @return 菜品分類列表
     */
    List<CategoryDO> getCategoryList(Collection<Long> ids);

    /**
     * 獲得菜品分類分頁
     *
     * @param pageReqVO 分頁查詢
     * @return 菜品分類分頁
     */
    PageResult<CategoryDO> getCategoryPage(CategoryPageReqVO pageReqVO);

    /**
     * 獲得指定名稱的菜品分類
     *
     * @param name 名稱
     * @return 菜品分類
     */
    CategoryDO getCategoryByName(String name);

} 