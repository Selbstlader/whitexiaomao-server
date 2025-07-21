package cn.iocoder.yudao.module.cooking.service.sync;

import cn.hutool.http.HttpUtil;
import cn.iocoder.yudao.module.cooking.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.module.cooking.dal.dataobject.recipe.RecipeDO;
import cn.iocoder.yudao.module.cooking.dal.mysql.category.CategoryMapper;
import cn.iocoder.yudao.module.cooking.dal.mysql.recipe.RecipeMapper;
import cn.iocoder.yudao.module.cooking.enums.RecipeDifficultyEnum;
import cn.iocoder.yudao.module.cooking.enums.RecipeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 烹饪数据同步 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class CookingSyncServiceImpl implements CookingSyncService {

    private static final String BASE_URL = "https://cook.aiursoft.cn";
    private static final String DISHES_URL = BASE_URL + "/dishes/";
    
    @Resource
    private RecipeMapper recipeMapper;
    
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public void syncCategories() {
        log.info("开始同步菜谱分类数据...");
        
        try {
            // 获取主页内容
            String htmlContent = HttpUtil.get(BASE_URL);
            Document doc = Jsoup.parse(htmlContent);
            
            // 解析分类信息
            Elements categoryElements = doc.select("nav ul li a");
            
            for (Element categoryElement : categoryElements) {
                String categoryName = categoryElement.text().trim();
                String categoryUrl = categoryElement.attr("href");
                
                if (categoryName.isEmpty() || categoryUrl.isEmpty()) {
                    continue;
                }
                
                // 检查分类是否已存在
                CategoryDO existingCategory = categoryMapper.selectOne("name", categoryName);
                if (existingCategory == null) {
                    CategoryDO category = new CategoryDO();
                    category.setName(categoryName);
                    category.setDescription("来自HowToCook项目的" + categoryName + "分类");
                    category.setType(1); // 菜谱分类
                    category.setParentId(0L);
                    category.setSort(0);
                    category.setStatus(1);
                    categoryMapper.insert(category);
                    log.info("新增分类: {}", categoryName);
                }
            }
            
            log.info("菜谱分类数据同步完成");
        } catch (Exception e) {
            log.error("同步菜谱分类数据失败", e);
            throw new RuntimeException("同步菜谱分类数据失败", e);
        }
    }

    @Override
    @Transactional
    public void syncRecipes() {
        log.info("开始同步菜谱数据...");
        
        try {
            List<String> recipeUrls = getAllRecipeUrls();
            log.info("找到 {} 个菜谱链接", recipeUrls.size());
            
            int successCount = 0;
            int skipCount = 0;
            int errorCount = 0;
            
            for (String recipeUrl : recipeUrls) {
                try {
                    if (isRecipeExists(recipeUrl)) {
                        skipCount++;
                        continue;
                    }
                    
                    RecipeDO recipe = syncSingleRecipe(recipeUrl);
                    if (recipe != null) {
                        successCount++;
                        log.info("成功同步菜谱: {}", recipe.getName());
                    }
                    
                    // 添加延迟，避免请求过于频繁
                    Thread.sleep(1000);
                    
                } catch (Exception e) {
                    errorCount++;
                    log.error("同步菜谱失败: {}", recipeUrl, e);
                }
            }
            
            log.info("菜谱数据同步完成 - 成功: {}, 跳过: {}, 失败: {}", successCount, skipCount, errorCount);
        } catch (Exception e) {
            log.error("同步菜谱数据失败", e);
            throw new RuntimeException("同步菜谱数据失败", e);
        }
    }

    @Override
    public RecipeDO syncSingleRecipe(String recipeUrl) {
        try {
            String fullUrl = recipeUrl.startsWith("http") ? recipeUrl : BASE_URL + recipeUrl;
            String htmlContent = HttpUtil.get(fullUrl);
            
            RecipeDO recipe = parseRecipeContent(fullUrl, htmlContent);
            if (recipe != null) {
                recipeMapper.insert(recipe);
                return recipe;
            }
        } catch (Exception e) {
            log.error("同步单个菜谱失败: {}", recipeUrl, e);
        }
        return null;
    }

    @Override
    public List<String> getAllRecipeUrls() {
        List<String> recipeUrls = new ArrayList<>();
        
        try {
            // 获取主页内容
            String htmlContent = HttpUtil.get(BASE_URL);
            Document doc = Jsoup.parse(htmlContent);
            
            // 查找所有菜谱链接
            Elements recipeLinks = doc.select("a[href*='/dishes/']");
            
            for (Element link : recipeLinks) {
                String href = link.attr("href");
                if (href.contains("/dishes/") && !href.endsWith("/dishes/")) {
                    recipeUrls.add(href);
                }
            }
            
            // 去重
            return new ArrayList<>(new LinkedHashSet<>(recipeUrls));
            
        } catch (Exception e) {
            log.error("获取菜谱链接失败", e);
            return Collections.emptyList();
        }
    }

    @Override
    public RecipeDO parseRecipeContent(String recipeUrl, String htmlContent) {
        try {
            Document doc = Jsoup.parse(htmlContent);
            
            // 解析菜谱名称
            String recipeName = doc.select("h1").first().text().trim();
            if (recipeName.isEmpty()) {
                return null;
            }
            
            // 解析菜谱描述
            String description = "";
            Element descElement = doc.select("p").first();
            if (descElement != null) {
                description = descElement.text().trim();
            }
            
            // 解析难度等级（从URL或内容中推断）
            Integer difficulty = parseDifficulty(doc);
            
            // 解析分类
            Long categoryId = parseCategoryFromUrl(recipeUrl);
            
            // 构建菜谱对象
            RecipeDO recipe = new RecipeDO();
            recipe.setName(recipeName);
            recipe.setDescription(description);
            recipe.setSourceUrl(recipeUrl);
            recipe.setSource("HowToCook");
            recipe.setCategoryId(categoryId);
            recipe.setDifficulty(difficulty);
            recipe.setStatus(RecipeStatusEnum.PUBLISHED.getStatus());
            recipe.setCreatorId(1L); // 系统用户
            recipe.setViewCount(0);
            recipe.setFavoriteCount(0);
            recipe.setRating(BigDecimal.ZERO);
            recipe.setRatingCount(0);
            
            return recipe;
            
        } catch (Exception e) {
            log.error("解析菜谱内容失败: {}", recipeUrl, e);
            return null;
        }
    }

    @Override
    public boolean isRecipeExists(String sourceUrl) {
        return recipeMapper.selectOne("source_url", sourceUrl) != null;
    }

    /**
     * 从URL中解析分类ID
     */
    private Long parseCategoryFromUrl(String recipeUrl) {
        try {
            // 从URL中提取分类名称，如 /dishes/meat_dish/宫保鸡丁/
            Pattern pattern = Pattern.compile("/dishes/([^/]+)/");
            Matcher matcher = pattern.matcher(recipeUrl);
            
            if (matcher.find()) {
                String categoryPath = matcher.group(1);
                String categoryName = mapCategoryPathToName(categoryPath);
                
                CategoryDO category = categoryMapper.selectOne("name", categoryName);
                if (category != null) {
                    return category.getId();
                }
            }
        } catch (Exception e) {
            log.warn("解析分类失败: {}", recipeUrl, e);
        }
        
        return 1L; // 默认分类
    }

    /**
     * 将URL路径映射为分类名称
     */
    private String mapCategoryPathToName(String categoryPath) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("meat_dish", "荤菜");
        categoryMap.put("vegetable_dish", "素菜");
        categoryMap.put("aquatic", "水产");
        categoryMap.put("breakfast", "早餐");
        categoryMap.put("staple", "主食");
        categoryMap.put("soup", "汤与粥");
        categoryMap.put("drink", "饮料");
        categoryMap.put("dessert", "甜品");
        categoryMap.put("semi-finished", "半成品加工");
        categoryMap.put("condiment", "酱料和其它材料");
        
        return categoryMap.getOrDefault(categoryPath, "其他");
    }

    /**
     * 解析难度等级
     */
    private Integer parseDifficulty(Document doc) {
        // 查找难度标识，如果找到星级标识则解析
        Elements difficultyElements = doc.select("*:contains(★)");
        
        for (Element element : difficultyElements) {
            String text = element.text();
            long starCount = text.chars().filter(ch -> ch == '★').count();
            if (starCount > 0 && starCount <= 4) {
                return (int) starCount;
            }
        }
        
        // 默认中等难度
        return RecipeDifficultyEnum.MEDIUM.getLevel();
    }
}
