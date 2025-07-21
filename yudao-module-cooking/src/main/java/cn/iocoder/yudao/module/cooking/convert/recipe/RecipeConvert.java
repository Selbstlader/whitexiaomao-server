package cn.iocoder.yudao.module.cooking.convert.recipe;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.*;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipePageReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.*;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeExcelVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜谱 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface RecipeConvert {

    RecipeConvert INSTANCE = Mappers.getMapper(RecipeConvert.class);

    // =================== APP端转换方法 ===================

    /**
     * RecipeDO 转 RecipeVO (APP端)
     */
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "cookingTime", source = "cookTime")
    @Mapping(target = "cuisine", ignore = true)
    RecipeVO convert(RecipeDO bean);

    /**
     * RecipeDO 转 RecipeDetailVO (APP端)
     */
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "cookingTime", source = "cookTime")
    @Mapping(target = "cuisine", ignore = true)
    @Mapping(target = "tips", ignore = true)
    @Mapping(target = "nutrition", ignore = true)
    @Mapping(target = "favorited", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "steps", ignore = true)
    RecipeDetailVO convertDetail(RecipeDO bean);

    /**
     * RecipeDO 列表转 RecipeVO 列表 (APP端)
     */
    List<RecipeVO> convertList(List<RecipeDO> list);

    /**
     * 分页结果转换 (APP端)
     */
    PageResult<RecipeVO> convertPage(PageResult<RecipeDO> page);

    /**
     * APP端的查询VO转换为Admin端的查询VO
     */
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipePageReqVO convert(cn.iocoder.yudao.module.cooking.controller.app.vo.recipe.RecipePageReqVO pageReqVO);

    // =================== Admin端转换方法 ===================

    /**
     * RecipeCreateReqVO 转 RecipeDO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sourceUrl", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "totalTime", ignore = true)
    @Mapping(target = "fiber", ignore = true)
    @Mapping(target = "sugar", ignore = true)
    @Mapping(target = "sodium", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "favoriteCount", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    RecipeDO convert(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeCreateReqVO bean);

    /**
     * RecipeUpdateReqVO 转 RecipeDO
     */
    @Mapping(target = "sourceUrl", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "totalTime", ignore = true)
    @Mapping(target = "fiber", ignore = true)
    @Mapping(target = "sugar", ignore = true)
    @Mapping(target = "sodium", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "favoriteCount", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    RecipeDO convert(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeUpdateReqVO bean);

    /**
     * RecipeDO 转 RecipeUpdateReqVO
     */
    cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeUpdateReqVO convertToUpdateReqVO(RecipeDO bean);

    /**
     * RecipeDO 转 RecipeRespVO (Admin端)
     */
    RecipeRespVO convertToRespVO(RecipeDO bean);

    /**
     * RecipeDO 列表转 RecipeRespVO 列表 (Admin端)
     */
    List<RecipeRespVO> convertToRespVOList(List<RecipeDO> list);

    /**
     * RecipeDO 列表转 RecipeExcelVO 列表 (用于Excel导出)
     */
    List<RecipeExcelVO> convertList02(List<RecipeDO> list);

    /**
     * 分页结果转换 (Admin端)
     */
    PageResult<RecipeRespVO> convertToRespVOPage(PageResult<RecipeDO> page);

}
