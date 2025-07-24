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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    @Resource
    private JdbcTemplate jdbcTemplate;

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
        // 嘗試直接使用框架方法
        List<IngredientDO> mbpResults = ingredientMapper.selectListByDishId(dishId);
        
        // 如果查詢結果為空，嘗試使用原生SQL查詢
        if (mbpResults == null || mbpResults.isEmpty()) {
            System.out.println("=== DEBUG: 框架查詢返回空，嘗試使用原生SQL查詢 ===");
            String sql = "SELECT id, dish_id, name, amount, unit, is_optional FROM ingredients WHERE dish_id = ?";
            
            try {
                List<Map<String, Object>> rawResults = jdbcTemplate.queryForList(sql, dishId);
                System.out.println("=== DEBUG: 原生SQL查詢到 " + rawResults.size() + " 筆結果 ===");
                
                // 如果原生查詢有結果，手動轉換為IngredientDO
                if (!rawResults.isEmpty()) {
                    List<IngredientDO> convertedResults = new ArrayList<>();
                    for (Map<String, Object> row : rawResults) {
                        IngredientDO ingredient = new IngredientDO();
                        
                        // 設置基本屬性
                        if (row.get("id") != null) ingredient.setId(((Number) row.get("id")).longValue());
                        if (row.get("dish_id") != null) ingredient.setDishId(((Number) row.get("dish_id")).longValue());
                        if (row.get("name") != null) ingredient.setName((String) row.get("name"));
                        if (row.get("amount") != null) ingredient.setAmount((String) row.get("amount"));
                        if (row.get("unit") != null) ingredient.setUnit((String) row.get("unit"));
                        if (row.get("is_optional") != null) {
                            Object val = row.get("is_optional");
                            if (val instanceof Number) {
                                ingredient.setIsOptional(((Number) val).intValue() == 1);
                            } else if (val instanceof Boolean) {
                                ingredient.setIsOptional((Boolean) val);
                            }
                        }
                        
                        convertedResults.add(ingredient);
                    }
                    return convertedResults;
                }
            } catch (Exception e) {
                System.out.println("=== ERROR: 原生SQL查詢出錯: " + e.getMessage() + " ===");
            }
        }
        
        // 如果原生查詢也沒有結果或出錯，返回框架查詢結果
        return mbpResults;
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