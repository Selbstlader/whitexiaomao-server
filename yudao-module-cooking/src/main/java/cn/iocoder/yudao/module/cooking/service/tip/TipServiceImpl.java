package cn.iocoder.yudao.module.cooking.service.tip;

import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipBatchCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipCreateReqVO;
import cn.iocoder.yudao.module.cooking.controller.admin.tip.vo.TipUpdateReqVO;
import cn.iocoder.yudao.module.cooking.convert.tip.TipConvert;
import cn.iocoder.yudao.module.cooking.dal.dataobject.TipDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.TipMapper;
import cn.iocoder.yudao.module.cooking.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.cooking.service.dish.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 烹飪小貼士 Service 實現類
 *
 * @author 芋道源碼
 */
@Service
@Validated
public class TipServiceImpl implements TipService {

    @Resource
    private TipMapper tipMapper;

    @Resource
    private DishService dishService;

    @Override
    public Long createTip(TipCreateReqVO createReqVO) {
        // 校驗菜品是否存在
        validateDishExists(createReqVO.getDishId());

        // 插入
        TipDO tip = TipConvert.INSTANCE.convert(createReqVO);
        tipMapper.insert(tip);
        return tip.getId();
    }

    @Override
    public void updateTip(TipUpdateReqVO updateReqVO) {
        // 校驗存在
        validateTipExists(updateReqVO.getId());
        // 校驗菜品是否存在
        validateDishExists(updateReqVO.getDishId());

        // 更新
        TipDO updateObj = TipConvert.INSTANCE.convert(updateReqVO);
        tipMapper.updateById(updateObj);
    }

    @Override
    public void deleteTip(Long id) {
        // 校驗存在
        validateTipExists(id);

        // 刪除
        tipMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchCreateTip(TipBatchCreateReqVO reqVO) {
        // 校驗菜品是否存在
        validateDishExists(reqVO.getDishId());

        // 轉換並插入
        List<TipDO> tipList = TipConvert.INSTANCE.convertList(reqVO);
        List<Long> ids = new ArrayList<>(tipList.size());
        for (TipDO tip : tipList) {
            tipMapper.insert(tip);
            ids.add(tip.getId());
        }
        return ids;
    }

    private void validateTipExists(Long id) {
        if (tipMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.TIP_NOT_EXISTS);
        }
    }

    private void validateDishExists(Long dishId) {
        if (dishService.getDish(dishId) == null) {
            throw exception(ErrorCodeConstants.DISH_NOT_EXISTS);
        }
    }

    @Override
    public TipDO getTip(Long id) {
        return tipMapper.selectById(id);
    }

    @Override
    public List<TipDO> getTipListByDishId(Long dishId) {
        return tipMapper.selectListByDishId(dishId);
    }

    @Override
    public void validateTipBelongsToDish(Long id, Long dishId) {
        TipDO tip = tipMapper.selectById(id);
        if (tip == null) {
            throw exception(ErrorCodeConstants.TIP_NOT_EXISTS);
        }
        if (!tip.getDishId().equals(dishId)) {
            throw exception(ErrorCodeConstants.TIP_DISH_NOT_MATCH);
        }
    }
} 