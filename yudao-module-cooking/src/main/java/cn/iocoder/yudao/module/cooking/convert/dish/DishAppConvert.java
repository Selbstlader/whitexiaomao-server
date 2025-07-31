package cn.iocoder.yudao.module.cooking.convert.dish;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.app.dish.vo.DishRespVO;
import cn.iocoder.yudao.module.cooking.controller.app.dish.vo.DishSimpleRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 菜品 App Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishAppConvert {

    DishAppConvert INSTANCE = Mappers.getMapper(DishAppConvert.class);

    @Named("convert")
    @Mapping(target = "categoryName", ignore = true)
    DishRespVO convert(DishDO bean);

    @Named("convertWithCategory")
    @Mapping(source = "bean.id", target = "id")
    @Mapping(source = "bean.name", target = "name")
    @Mapping(source = "bean.createTime", target = "createTime")
    @Mapping(source = "category.name", target = "categoryName")
    DishRespVO convert(DishDO bean, CategoryDO category);

    @IterableMapping(qualifiedByName = "convert")
    List<DishRespVO> convertList(List<DishDO> list);

    /**
     * 分頁結果轉換
     *
     * @param page 分頁結果
     * @return 轉換後的分頁結果
     */
    default PageResult<DishRespVO> convertPage(PageResult<DishDO> page) {
        if (page == null) {
            return null;
        }
        List<DishRespVO> respList = convertList(page.getList());
        return new PageResult<>(respList, page.getTotal());
    }

    @Named("convertToSimple")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "difficulty", target = "difficulty")
    DishSimpleRespVO convertToSimple(DishDO bean);

    @IterableMapping(qualifiedByName = "convertToSimple")
    List<DishSimpleRespVO> convertList02(List<DishDO> list);
}