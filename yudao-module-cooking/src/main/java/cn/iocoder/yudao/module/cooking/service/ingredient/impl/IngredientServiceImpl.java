package cn.iocoder.yudao.module.cooking.service.ingredient.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.ingredient.IngredientDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.ingredient.IngredientMapper;
import cn.iocoder.yudao.module.cooking.service.ingredient.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 食材 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    @Resource
    private IngredientMapper ingredientMapper;

    @Override
    public Long createIngredient(IngredientCreateReqVO createReqVO) {
        // TODO 待实现
        return 0L;
    }

    @Override
    public void updateIngredient(IngredientUpdateReqVO updateReqVO) {
        // TODO 待实现
    }

    @Override
    public void deleteIngredient(Long id) {
        // TODO 待实现
    }

    @Override
    public IngredientDO getIngredient(Long id) {
        return ingredientMapper.selectById(id);
    }

    @Override
    public PageResult<IngredientDO> getIngredientPage(IngredientPageReqVO pageReqVO) {
        return ingredientMapper.selectPage(pageReqVO);
    }

    @Override
    public List<IngredientDO> getIngredientList(IngredientExportReqVO exportReqVO) {
        return ingredientMapper.selectList(exportReqVO);
    }

    @Override
    public List<IngredientDO> getIngredientListByCategory(Long categoryId) {
        return ingredientMapper.selectListByCategory(categoryId);
    }

    @Override
    public List<IngredientDO> searchIngredients(String keyword) {
        return ingredientMapper.searchIngredients(keyword);
    }

}
