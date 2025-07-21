package cn.iocoder.yudao.module.cooking.controller.admin.test;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.cooking.service.sync.CookingSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 烹饪模块测试")
@RestController
@RequestMapping("/cooking/test")
public class CookingTestController {

    @Resource
    private CookingSyncService cookingSyncService;

    @GetMapping("/hello")
    @Operation(summary = "测试接口")
    public CommonResult<Map<String, Object>> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "烹饪模块运行正常！");
        result.put("module", "yudao-module-cooking");
        result.put("version", "1.0.0");
        result.put("description", "基于cook.aiursoft.cn的食品烹饪管理模块");
        return success(result);
    }

    @GetMapping("/check-source")
    @Operation(summary = "检查数据源连接")
    public CommonResult<Map<String, Object>> checkSource() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 尝试获取菜谱链接来测试连接
            java.util.List<String> urls = cookingSyncService.getAllRecipeUrls();
            result.put("status", "success");
            result.put("message", "数据源连接正常");
            result.put("recipeCount", urls.size());
            result.put("sampleUrls", urls.stream().limit(5).toList());
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "数据源连接失败: " + e.getMessage());
            result.put("recipeCount", 0);
        }
        
        return success(result);
    }

}
