package cn.iocoder.yudao.module.cooking.service.tip;

import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipUpdateReqVO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 烹飪小貼士 Service 接口
 *
 * @author 芋道源碼
 */
public interface TipService {

    /**
     * 創建烹飪小貼士
     *
     * @param createReqVO 創建信息
     * @return 編號
     */
    Long createTip(@Valid TipCreateReqVO createReqVO);

    /**
     * 更新烹飪小貼士
     *
     * @param updateReqVO 更新信息
     */
    void updateTip(@Valid TipUpdateReqVO updateReqVO);

    /**
     * 刪除烹飪小貼士
     *
     * @param id 編號
     */
    void deleteTip(Long id);

    /**
     * 批量創建烹飪小貼士
     *
     * @param reqVO 批量創建信息
     * @return 編號列表
     */
    List<Long> batchCreateTip(@Valid TipBatchCreateReqVO reqVO);

    /**
     * 獲得烹飪小貼士
     *
     * @param id 編號
     * @return 烹飪小貼士
     */
    TipDO getTip(Long id);

    /**
     * 獲得指定菜品的烹飪小貼士列表
     *
     * @param dishId 菜品編號
     * @return 烹飪小貼士列表
     */
    List<TipDO> getTipListByDishId(Long dishId);

    /**
     * 校驗小貼士存在於指定菜品下
     *
     * @param id 小貼士編號
     * @param dishId 菜品編號
     */
    void validateTipBelongsToDish(Long id, Long dishId);

}