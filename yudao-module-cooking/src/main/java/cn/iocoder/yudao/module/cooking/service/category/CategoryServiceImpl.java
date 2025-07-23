package cn.iocoder.yudao.module.cooking.service.category;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.iocoder.yudao.module.cooking.convert.category.CategoryConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.category.CategoryMapper;
import cn.iocoder.yudao.module.cooking.dal.mysql.dish.DishMapper;
import cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 菜品分類 Service 實現類
 *
 * @author 芋道源碼
 */
@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private DishMapper dishMapper;

    @Override
    public Long createCategory(CategoryCreateReqVO createReqVO) {
        // 校驗分類名稱是否已存在
        validateCategoryNameUnique(null, createReqVO.getName());

        // 插入
        CategoryDO category = CategoryConvert.INSTANCE.convert(createReqVO);
        categoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateCategory(CategoryUpdateReqVO updateReqVO) {
        // 校驗存在
        validateCategoryExists(updateReqVO.getId());
        // 校驗分類名稱是否已存在
        validateCategoryNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // 更新
        CategoryDO updateObj = CategoryConvert.INSTANCE.convert(updateReqVO);
        categoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校驗存在
        validateCategoryExists(id);
        // 校驗是否有菜品
        validateCategoryHasNoDish(id);

        // 刪除
        categoryMapper.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        if (categoryMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.CATEGORY_NOT_EXISTS);
        }
    }

    private void validateCategoryNameUnique(Long id, String name) {
        CategoryDO category = categoryMapper.selectByName(name);
        if (category == null) {
            return;
        }
        // 如果 id 為空，說明是創建時校驗，只要名稱重複則報錯
        if (id == null) {
            throw exception(ErrorCodeConstants.CATEGORY_NAME_DUPLICATE);
        }
        // 如果 id 不為空，說明是更新時校驗，如果名稱重複且不是當前菜品，則報錯
        if (!id.equals(category.getId())) {
            throw exception(ErrorCodeConstants.CATEGORY_NAME_DUPLICATE);
        }
    }

    private void validateCategoryHasNoDish(Long id) {
        List<DishDO> dishes = dishMapper.selectByCategoryId(id);
        if (!dishes.isEmpty()) {
            throw exception(ErrorCodeConstants.CATEGORY_HAS_DISHES);
        }
    }

    @Override
    public CategoryDO getCategory(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<CategoryDO> getCategoryList() {
        return categoryMapper.selectList();
    }

    @Override
    public List<CategoryDO> getCategoryList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return categoryMapper.selectBatchByIds(ids);
    }

    @Override
    public PageResult<CategoryDO> getCategoryPage(CategoryPageReqVO pageReqVO) {
        return categoryMapper.selectPage(pageReqVO);
    }

    @Override
    public CategoryDO getCategoryByName(String name) {
        return categoryMapper.selectByName(name);
    }

}
 