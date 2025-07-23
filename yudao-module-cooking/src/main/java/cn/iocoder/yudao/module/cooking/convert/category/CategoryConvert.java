package cn.iocoder.yudao.module.cooking.convert.category;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategorySimpleRespVO;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 菜品分類 Convert
 *
 * @author 芋道源碼
 */
@Mapper
public interface CategoryConvert {

    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    CategoryDO convert(CategoryCreateReqVO bean);

    CategoryDO convert(CategoryUpdateReqVO bean);

    CategoryRespVO convert(CategoryDO bean);

    List<CategoryRespVO> convertList(List<CategoryDO> list);

    PageResult<CategoryRespVO> convertPage(PageResult<CategoryDO> page);

    CategorySimpleRespVO convertToSimple(CategoryDO bean);

    List<CategorySimpleRespVO> convertList02(List<CategoryDO> list);

}