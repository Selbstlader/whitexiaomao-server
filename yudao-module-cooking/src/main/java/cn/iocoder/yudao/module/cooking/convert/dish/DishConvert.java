package cn.iocoder.yudao.module.cooking.convert.dish;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishSimpleRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.DishUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 菜品 Convert
 *
 * @author 芋道源碼
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishConvert {

    DishConvert INSTANCE = Mappers.getMapper(DishConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    DishDO convert(DishCreateReqVO bean);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "transMap", ignore = true)
    DishDO convert(DishUpdateReqVO bean);

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
     * @param page        分頁結果
     * @param categoryMap
     * @return 轉換後的分頁結果
     */
    default PageResult<DishRespVO> convertPage(PageResult<DishDO> page, Map<Long, CategoryDO> categoryMap) {
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