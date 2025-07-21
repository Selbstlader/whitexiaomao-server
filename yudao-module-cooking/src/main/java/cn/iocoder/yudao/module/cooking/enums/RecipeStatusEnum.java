package cn.iocoder.yudao.module.cooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜谱状态枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RecipeStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    REVIEWING(2, "审核中"),
    REJECTED(3, "已拒绝"),
    DISABLED(4, "已禁用");

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名称
     */
    private final String name;

    /**
     * 获取状态值
     */
    public Integer getStatus() {
        return status;
    }

    public static RecipeStatusEnum valueOf(Integer status) {
        for (RecipeStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }

}
