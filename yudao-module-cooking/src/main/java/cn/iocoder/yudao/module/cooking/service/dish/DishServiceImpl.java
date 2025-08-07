package cn.iocoder.yudao.module.cooking.service.dish;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.io.FileUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.*;
import cn.iocoder.yudao.module.cooking.controller.admin.ingredient.vo.IngredientRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.step.vo.StepRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipRespVO;
import cn.iocoder.yudao.module.cooking.convert.dish.DishConvert;
import cn.iocoder.yudao.module.cooking.convert.ingredient.IngredientConvert;
import cn.iocoder.yudao.module.cooking.convert.step.StepConvert;
import cn.iocoder.yudao.module.cooking.convert.tip.TipConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.*;
import cn.iocoder.yudao.module.cooking.dal.mysql.category.CategoryMapper;
import cn.iocoder.yudao.module.cooking.dal.mysql.dish.DishMapper;
import cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 菜品 Service 实现类
 *
 * @author 芋道源碼
 */
@Service
@Validated
@Slf4j
public class DishServiceImpl implements DishService {

    @Resource
    private FileService fileService; // 注入统一文件服务

    // 图片存储路径
    private static final String IMAGE_PATH = "cooking_images/";
    
    // 允许的图片扩展名
    private static final String[] ALLOWED_IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

    @Resource
    private DishMapper dishMapper;
    
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private IngredientMapper ingredientMapper;

    @Resource
    private StepMapper stepMapper;

    @Resource
    private TipMapper tipMapper;

    @Resource
    private StarRatingMapper starRatingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDish(DishCreateReqVO createReqVO) {
        // 校验分類存在
        validateCategoryExists(createReqVO.getCategoryId());
        // 校验菜品名稱是否已存在
        validateDishNameUnique(null, createReqVO.getName());

        // 处理图片
        String imageName = null;
        if (createReqVO.getImageFile() != null && !createReqVO.getImageFile().isEmpty()) {
            imageName = processImage(createReqVO.getImageFile());
        }

        // 插入
        DishDO dish = DishConvert.INSTANCE.convert(createReqVO);
        dish.setImageName(imageName);
        dishMapper.insert(dish);
        return dish.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDish(DishUpdateReqVO updateReqVO) {
        // 校验存在
        validateDishExists(updateReqVO.getId());
        // 校验分類存在
        validateCategoryExists(updateReqVO.getCategoryId());
        // 校验菜品名稱是否已存在
        validateDishNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // 处理图片
        DishDO oldDish = dishMapper.selectById(updateReqVO.getId());
        String imageName = oldDish.getImageName();
        if (updateReqVO.getImageFile() != null && !updateReqVO.getImageFile().isEmpty()) {
            // 删除旧图片
            if (StrUtil.isNotBlank(imageName)) {
                deleteImage(imageName);
            }
            // 处理新图片
            imageName = processImage(updateReqVO.getImageFile());
        }

        // 更新
        DishDO updateObj = DishConvert.INSTANCE.convert(updateReqVO);
        updateObj.setImageName(imageName);
        dishMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(Long id) {
        // 校验存在
        validateDishExists(id);

        // 刪除菜品圖片
        DishDO dish = dishMapper.selectById(id);
        if (StrUtil.isNotBlank(dish.getImageName())) {
            deleteImage(dish.getImageName());
        }

        // 刪除相關數據
        ingredientMapper.deleteByDishId(id);
        stepMapper.deleteByDishId(id);
        tipMapper.deleteByDishId(id);
        starRatingMapper.deleteByDishId(id);

        // 刪除菜品
        dishMapper.deleteById(id);
    }

    private void validateDishExists(Long id) {
        if (dishMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.DISH_NOT_EXISTS);
        }
    }

    private void validateCategoryExists(Long categoryId) {
        if (categoryMapper.selectById(categoryId) == null) {
            throw exception(ErrorCodeConstants.CATEGORY_NOT_EXISTS);
        }
    }

    private void validateDishNameUnique(Long id, String name) {
        DishDO dish = dishMapper.selectByName(name);
        if (dish == null) {
            return;
        }
        // 如果 id 為空，說明是創建時校驗，只要名稱重複則報錯
        if (id == null) {
            throw exception(ErrorCodeConstants.DISH_NAME_DUPLICATE);
        }
        // 如果 id 不為空，說明是更新時校验，如果名稱重複且不是當前菜品，則报错
        if (!id.equals(dish.getId())) {
            throw exception(ErrorCodeConstants.DISH_NAME_DUPLICATE);
        }
    }

    @Override
    public DishDO getDish(Long id) {
        return dishMapper.selectById(id);
    }

    @Override
    public DishDetailRespVO getDishDetail(Long id) {
        // 獲取菜品基本信息
        DishDO dish = getDish(id);
        if (dish == null) {
            throw exception(ErrorCodeConstants.DISH_NOT_EXISTS);
        }

        // 獲取分類信息
        CategoryDO category = categoryMapper.selectById(dish.getCategoryId());
        DishDetailRespVO respVO = new DishDetailRespVO();
        if (category != null) {
            // 設置菜品基本信息，包含分類名稱
            DishRespVO dishRespVO = DishConvert.INSTANCE.convert(dish, category);
            // 複製屬性到詳情VO
            respVO = new DishDetailRespVO();
            org.springframework.beans.BeanUtils.copyProperties(dishRespVO, respVO);
        } else {
            // 如果沒有分類信息，直接轉換
            DishRespVO dishRespVO = DishConvert.INSTANCE.convert(dish);
            org.springframework.beans.BeanUtils.copyProperties(dishRespVO, respVO);
        }

        // 獲取配料列表
        List<IngredientDO> ingredients = ingredientMapper.selectListByDishId(id);
        respVO.setIngredients(IngredientConvert.INSTANCE.convertList(ingredients));

        // 獲取步驟列表
        List<StepDO> steps = stepMapper.selectListByDishId(id);
        respVO.setSteps(StepConvert.INSTANCE.convertList(steps));

        // 獲取小貼士列表
        List<TipDO> tips = tipMapper.selectListByDishId(id);
        respVO.setTips(TipConvert.INSTANCE.convertList(tips));

        // 計算平均星級
        List<StarRatingDO> ratings = starRatingMapper.selectListByDishId(id);
        if (!ratings.isEmpty()) {
            double avgRating = ratings.stream()
                    .mapToInt(StarRatingDO::getStarLevel)
                    .average()
                    .orElse(0.0);
            respVO.setAverageStarRating(avgRating);
        } else {
            respVO.setAverageStarRating(0.0);
        }

        return respVO;
    }

    @Override
    public List<DishDO> getDishList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return dishMapper.selectBatchByIds(ids);
    }

    @Override
    public PageResult<DishDO> getDishPage(DishPageReqVO pageReqVO) {
        return dishMapper.selectPage(pageReqVO);
    }

    @Override
    public DishDO getDishByName(String name) {
        return dishMapper.selectByName(name);
    }

    @Override
    public String processImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        // 验证文件类型
        String originalFilename = imageFile.getOriginalFilename();
        if (!isValidImageExtension(originalFilename)) {
            throw exception(ErrorCodeConstants.DISH_IMAGE_UPLOAD_FAILED, "不支持的圖片格式");
        }

        try {
            // 调用框架封装的上传功能，返回访问 URL
            byte[] content = imageFile.getBytes();
            return fileService.createFile(
                    content,
                    originalFilename,
                    "cooking/dishes", // 业务目录
                    imageFile.getContentType()
            );
        } catch (IOException e) {
            log.error("圖片上傳失敗", e);
            throw exception(ErrorCodeConstants.DISH_IMAGE_UPLOAD_FAILED);
        }
    }

    private boolean isValidImageExtension(String filename) {
        if (StrUtil.isBlank(filename)) {
            return false;
        }
        String extension = "." + FileUtil.extName(filename).toLowerCase();
        return Arrays.asList(ALLOWED_IMAGE_EXTENSIONS).contains(extension);
    }

    private void deleteImage(String imageName) {
        if (StrUtil.isBlank(imageName)) {
            return;
        }
        try {
            File file = new File(IMAGE_PATH + imageName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            log.error("刪除圖片失敗: {}", imageName, e);
        }
    }

    @Override
    public Map<Long, DishDO> getDishMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<DishDO> list = dishMapper.selectBatchByIds(ids);
        return list.stream().collect(Collectors.toMap(DishDO::getId, dish -> dish));
    }
}