package cn.iocoder.yudao.module.cooking.service.category.impl;

import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.service.category.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * 分类 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Override
    public Long createCategory(Object createReqVO) {
        // TODO 待实现
        return 0L;
    }

    @Override
    public void updateCategory(Object updateReqVO) {
        // TODO 待实现
    }

    @Override
    public void deleteCategory(Long id) {
        // TODO 待实现
    }

    @Override
    public CategoryDO getCategory(Long id) {
        // TODO 待实现
        return null;
    }

    @Override
    public List<CategoryDO> getCategoryList() {
        // TODO 待实现
        return Collections.emptyList();
    }

    @Override
    public List<CategoryDO> getRecipeCategories() {
        // TODO 待实现
        return Collections.emptyList();
    }

    @Override
    public List<CategoryDO> getIngredientCategories() {
        // TODO 待实现
        return Collections.emptyList();
    }
} 