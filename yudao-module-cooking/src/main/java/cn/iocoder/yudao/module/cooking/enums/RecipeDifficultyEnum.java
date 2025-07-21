package cn.iocoder.yudao.module.cooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜谱难度枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum RecipeDifficultyEnum {

    EASY(1, "简单"),
    MEDIUM(2, "中等"),
    HARD(3, "困难"),
    EXPERT(4, "专家级");

    /**
     * 难度等级
     */
    private final Integer level;
    /**
     * 难度名称
     */
    private final String name;

    /**
     * 获取难度等级
     */
    public Integer getLevel() {
        return level;
    }

    public static RecipeDifficultyEnum valueOf(Integer level) {
        for (RecipeDifficultyEnum difficulty : values()) {
            if (difficulty.getLevel().equals(level)) {
                return difficulty;
            }
        }
        return null;
    }

}
