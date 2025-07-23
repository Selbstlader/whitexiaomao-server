package cn.iocoder.yudao.module.cooking.service.dish;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.cooking.controller.admin.dish.vo.*;
import cn.iocoder.yudao.module.cooking.dal.dataobject.DishDO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 菜品 Service 接口
 *
 * @author 芋道源碼
 */
public interface DishService {

    /**
     * 創建菜品
     *
     * @param createReqVO 創建信息
     * @return 編號
     */
    Long createDish(@Valid DishCreateReqVO createReqVO);

    /**
     * 更新菜品
     *
     * @param updateReqVO 更新信息
     */
    void updateDish(@Valid DishUpdateReqVO updateReqVO);

    /**
     * 刪除菜品
     *
     * @param id 編號
     */
    void deleteDish(Long id);

    /**
     * 獲得菜品
     *
     * @param id 編號
     * @return 菜品
     */
    DishDO getDish(Long id);

    /**
     * 獲得菜品詳情，包括配料、步驟和小貼士
     *
     * @param id 編號
     * @return 菜品詳情
     */
    DishDetailRespVO getDishDetail(Long id);

    /**
     * 獲得菜品列表
     *
     * @param ids 編號列表
     * @return 菜品列表
     */
    List<DishDO> getDishList(Collection<Long> ids);

    /**
     * 獲得菜品分頁
     *
     * @param pageReqVO 分頁查詢
     * @return 菜品分頁
     */
    PageResult<DishDO> getDishPage(DishPageReqVO pageReqVO);

    /**
     * 獲得指定名稱的菜品
     *
     * @param name 名稱
     * @return 菜品
     */
    DishDO getDishByName(String name);

    /**
     * 處理菜品圖片
     *
     * @param imageFile 圖片文件
     * @return 圖片名稱
     */
    String processImage(MultipartFile imageFile);

    /**
     * 獲得菜品 Map
     *
     * @param ids 菜品編號數組
     * @return 菜品 Map
     */
    Map<Long, DishDO> getDishMap(Collection<Long> ids);

} 