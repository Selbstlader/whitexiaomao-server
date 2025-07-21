 // ... existing code ...
    /**
     * RecipeCreateReqVO 转 RecipeDO
     */
    RecipeDO convert(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeCreateReqVO bean);

    /**
     * RecipeUpdateReqVO 转 RecipeDO
     */
    RecipeDO convert(cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeUpdateReqVO bean);

    /**
     * RecipeDO 转 RecipeUpdateReqVO
     */
    cn.iocoder.yudao.module.cooking.controller.admin.recipe.vo.RecipeUpdateReqVO convertToUpdateReqVO(RecipeDO bean);
// ... existing code ...