package cn.iocoder.yudao.module.cooking.service.ingredient;

import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientUpdateReqVO;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.IngredientDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.IngredientMapper;
import cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 配料 Service 實現類
 *
 * @author 芋道源碼
 */
@Service
@Validated
public class IngredientServiceImpl implements IngredientService {

    @Resource
    private IngredientMapper ingredientMapper;
    
    @Resource
    private DishService dishService;

    @Override
    public Long createIngredient(IngredientCreateReqVO createReqVO) {
        // 校驗菜品是否存在
        validateDishExists(createReqVO.getDishId());
        // 校驗配料是否已存在
        validateIngredientNameUnique(null, createReqVO.getDishId(), createReqVO.getName());
        
        // 插入
        IngredientDO ingredient = IngredientConvert.INSTANCE.convert(createReqVO);
        // 刪除不存在的方法調用
        /* if (ingredient.getOptional() == null) {
            ingredient.setOptional(false); // 默認非可選
        } */
        ingredientMapper.insert(ingredient);
        return ingredient.getId();
    }

    @Override
    public void updateIngredient(IngredientUpdateReqVO updateReqVO) {
        // 校驗存在
        validateIngredientExists(updateReqVO.getId());
        // 校驗菜品是否存在
        validateDishExists(updateReqVO.getDishId());
        // 校驗配料是否已存在
        validateIngredientNameUnique(updateReqVO.getId(), updateReqVO.getDishId(), updateReqVO.getName());
        
        // 更新
        IngredientDO updateObj = IngredientConvert.INSTANCE.convert(updateReqVO);
        ingredientMapper.updateById(updateObj);
    }

    @Override
    public void deleteIngredient(Long id) {
        // 校驗存在
        validateIngredientExists(id);
        
        // 刪除
        ingredientMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchCreateIngredient(IngredientBatchCreateReqVO reqVO) {
        // 校驗菜品是否存在
        validateDishExists(reqVO.getDishId());
        
        // 校驗配料名稱是否重複
        List<String> names = new ArrayList<>();
        for (IngredientBatchCreateReqVO.IngredientBaseInfoVO ingredient : reqVO.getIngredients()) {
            if (names.contains(ingredient.getName())) {
                throw exception(ErrorCodeConstants.INGREDIENT_NAME_DUPLICATE);
            }
            names.add(ingredient.getName());
            
            // 校驗配料是否已存在於菜品中
            validateIngredientNameUnique(null, reqVO.getDishId(), ingredient.getName());
        }
        
        // 轉換並插入
        List<IngredientDO> ingredientList = IngredientConvert.INSTANCE.convertList(reqVO);
        List<Long> ids = new ArrayList<>(ingredientList.size());
        for (IngredientDO ingredient : ingredientList) {
            // 刪除不存在的方法調用
            /* if (ingredient.getOptional() == null) {
                ingredient.setOptional(false); // 默認非可選
            } */
            ingredientMapper.insert(ingredient);
            ids.add(ingredient.getId());
        }
        return ids;
    }

    private void validateIngredientExists(Long id) {
        if (ingredientMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.INGREDIENT_NOT_EXISTS);
        }
    }
    
    private void validateDishExists(Long dishId) {
        if (dishService.getDish(dishId) == null) {
            throw exception(ErrorCodeConstants.DISH_NOT_EXISTS);
        }
    }
    
    private void validateIngredientNameUnique(Long id, Long dishId, String name) {
        IngredientDO ingredient = ingredientMapper.selectByDishIdAndName(dishId, name);
        if (ingredient == null) {
            return;
        }
        // 如果 id 為空，說明是創建時校驗，只要名稱重複則報錯
        if (id == null) {
            throw exception(ErrorCodeConstants.INGREDIENT_NAME_DUPLICATE);
        }
        // 如果 id 不為空，說明是更新時校驗，如果名稱重複且不是當前配料，則報錯
        if (!id.equals(ingredient.getId())) {
            throw exception(ErrorCodeConstants.INGREDIENT_NAME_DUPLICATE);
        }
    }

    @Override
    public IngredientDO getIngredient(Long id) {
        return ingredientMapper.selectById(id);
    }

    @Override
    public List<IngredientDO> getIngredientListByDishId(Long dishId) {
        return ingredientMapper.selectListByDishId(dishId);
    }
    
    @Override
    public void validateIngredientBelongsToDish(Long id, Long dishId) {
        IngredientDO ingredient = ingredientMapper.selectById(id);
        if (ingredient == null) {
            throw exception(ErrorCodeConstants.INGREDIENT_NOT_EXISTS);
        }
        if (!ingredient.getDishId().equals(dishId)) {
            throw exception(ErrorCodeConstants.INGREDIENT_DISH_NOT_MATCH);
        }
    }
} 