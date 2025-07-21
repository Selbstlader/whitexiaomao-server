package cn.iocoder.yudao.module.cooking.convert.category;

import cn.iocoder.yudao.module.cooking.controller.app.vo.category.CategoryRespVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 分类 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface CategoryConvert {

    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    /**
     * CategoryDO 转 CategoryRespVO
     */
    CategoryRespVO convert(CategoryDO bean);

    /**
     * CategoryDO 列表转 CategoryRespVO 列表
     */
    List<CategoryRespVO> convertList(List<CategoryDO> list);

} 