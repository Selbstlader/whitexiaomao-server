package cn.iocoder.yudao.module.cooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜品難度等級枚舉
 *
 * @author 芋道源碼
 */
@Getter
@AllArgsConstructor
public enum DishDifficultyEnum {

    EASY(1, "簡單"),
    NORMAL(2, "普通"),
    MEDIUM(3, "中等"),
    DIFFICULT(4, "困難"),
    EXPERT(5, "專家");

    /**
     * 難度等級
     */
    private final Integer level;
    /**
     * 難度描述
     */
    private final String desc;

} 