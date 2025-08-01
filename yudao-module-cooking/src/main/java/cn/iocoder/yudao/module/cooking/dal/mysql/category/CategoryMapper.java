package cn.iocoder.yudao.module.cooking.dal.mysql.category;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.cooking.controller.admin.category.vo.CategoryPageReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

/**
 * 菜品分類 Mapper
 *
 * @author 芋道源碼
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<CategoryDO> {

    /**
     * 根據名稱查詢分類
     * 
     * @param name 名稱
     * @return 分類
     */
    @Select("SELECT id, name FROM cooking_category WHERE name = #{name} LIMIT 1")
    CategoryDO selectByName(String name);

    /**
     * 分頁查詢
     * 
     * @param reqVO 查詢條件
     * @return 分頁結果
     */
    default PageResult<CategoryDO> selectPage(CategoryPageReqVO reqVO) {
        // 創建查詢條件
        LambdaQueryWrapper<CategoryDO> wrapper = Wrappers.<CategoryDO>lambdaQuery()
                .select(CategoryDO::getId, CategoryDO::getName)
                .like(reqVO.getName() != null, CategoryDO::getName, reqVO.getName())
                .orderByDesc(CategoryDO::getId);
        
        // 執行查詢
        Page<CategoryDO> page = selectPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), wrapper);
        
        // 返回結果
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 查詢所有分類，按ID排序
     * 
     * @return 分類列表
     */
    @Select("SELECT id, name FROM cooking_category ORDER BY id ASC")
    List<CategoryDO> selectList();
    
    /**
     * 根據ID批量查詢
     * 
     * @param ids ID列表
     * @return 分類列表
     */
    default List<CategoryDO> selectBatchByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return selectList(Wrappers.<CategoryDO>lambdaQuery()
                .select(CategoryDO::getId, CategoryDO::getName)
                .in(CategoryDO::getId, ids));
    }
}