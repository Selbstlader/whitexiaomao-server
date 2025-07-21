# yudao-module-cooking

## 模块介绍

yudao-module-cooking 是芋道框架的食品烹饪模块，提供了完整的食品烹饪管理功能。

## 主要功能

### 1. 菜谱管理
- ✅ **菜谱 CRUD** - 创建、查询、更新、删除菜谱
- ✅ **菜谱分类** - 按菜系、难度、时间等分类管理
- ✅ **菜谱搜索** - 支持多条件搜索和筛选
- ✅ **菜谱收藏** - 用户收藏喜欢的菜谱
- ✅ **菜谱评分** - 用户对菜谱进行评分和评论

### 2. 食材管理
- ✅ **食材 CRUD** - 创建、查询、更新、删除食材信息
- ✅ **营养成分** - 记录食材的营养成分信息
- ✅ **食材分类** - 按类型分类管理食材
- ✅ **季节性标记** - 标记食材的季节性特征
- ✅ **存储建议** - 提供食材存储和保鲜建议

### 3. 烹饪步骤
- ✅ **步骤管理** - 详细的烹饪步骤记录
- ✅ **时间控制** - 每个步骤的时间要求
- ✅ **温度控制** - 烹饪温度的精确控制
- ✅ **技巧提示** - 烹饪技巧和注意事项
- ✅ **图片说明** - 步骤配图和说明

### 4. 营养分析
- ✅ **营养计算** - 自动计算菜谱营养成分
- ✅ **热量统计** - 精确的热量计算
- ✅ **营养建议** - 基于营养需求的建议
- ✅ **膳食搭配** - 合理的膳食搭配建议

### 5. 用户功能
- ✅ **个人菜谱** - 用户创建和管理个人菜谱
- ✅ **烹饪记录** - 记录用户的烹饪历史
- ✅ **购物清单** - 根据菜谱生成购物清单
- ✅ **饮食偏好** - 记录用户的饮食偏好和禁忌

## 数据库表设计

### 核心表
- `cooking_recipe` - 菜谱主表
- `cooking_ingredient` - 食材表
- `cooking_recipe_ingredient` - 菜谱食材关联表
- `cooking_cooking_step` - 烹饪步骤表
- `cooking_category` - 分类表
- `cooking_nutrition` - 营养成分表
- `cooking_user_recipe` - 用户菜谱关联表
- `cooking_recipe_rating` - 菜谱评分表

## API 接口

### 管理后台接口
- `/admin-api/cooking/recipe/**` - 菜谱管理
- `/admin-api/cooking/ingredient/**` - 食材管理
- `/admin-api/cooking/category/**` - 分类管理
- `/admin-api/cooking/nutrition/**` - 营养管理

### 用户端接口
- `/app-api/cooking/recipe/**` - 菜谱查询
- `/app-api/cooking/user-recipe/**` - 个人菜谱
- `/app-api/cooking/cooking-record/**` - 烹饪记录
- `/app-api/cooking/shopping-list/**` - 购物清单

## 配置说明

在 `application-dev.yaml` 中添加以下配置：

```yaml
yudao:
  cooking:
    enabled: true # 是否启用烹饪功能
    nutrition-api:
      enabled: true # 是否启用营养分析API
      provider: "default" # 营养数据提供商
    image:
      max-size: 10MB # 图片最大大小
      allowed-types: ["jpg", "jpeg", "png", "gif"] # 允许的图片类型
```

## 技术特性

- 基于 Spring Boot 3.x
- 使用 MyBatis Plus 进行数据访问
- 支持 Redis 缓存
- 集成文件上传功能
- 支持多租户
- 完整的权限控制
- 丰富的 API 文档

## 快速开始

### 1. 数据库初始化

执行 `src/main/resources/sql/cooking_tables.sql` 中的SQL脚本来创建相关数据表。

### 2. 启动项目

确保项目已正确编译并启动，然后访问 Swagger 文档：
- 地址：http://localhost:48080/doc.html
- 在分组中选择 "cooking" 查看烹饪模块的API

### 3. 数据同步

使用以下API来同步cook.aiursoft.cn的数据：

1. **同步分类数据**：
   ```
   POST /admin-api/cooking/sync/categories
   ```

2. **同步菜谱数据**：
   ```
   POST /admin-api/cooking/sync/recipes
   ```

3. **测试模块**：
   ```
   GET /admin-api/cooking/test/hello
   ```

### 4. 权限配置

需要在系统管理中配置以下权限：
- `cooking:sync:categories` - 同步分类权限
- `cooking:sync:recipes` - 同步菜谱权限
- `cooking:recipe:query` - 查询菜谱权限
- `cooking:recipe:create` - 创建菜谱权限
- `cooking:recipe:update` - 更新菜谱权限
- `cooking:recipe:delete` - 删除菜谱权限

## 故障排除

### 1. Swagger中看不到cooking模块的API

检查以下几点：
- 确保项目已正确编译
- 确保 `CookingWebConfiguration` 类被Spring扫描到
- 检查控制器类的包路径是否正确
- 重启项目并清除浏览器缓存

### 2. 依赖解析失败

如果出现 "未解析的依赖项: 'cn.iocoder.boot:yudao-module-cooking:jar:2.6.0-SNAPSHOT'" 错误：
- 检查主项目的 `pom.xml` 中是否正确添加了 `<module>yudao-module-cooking</module>`
- 检查 `yudao-server/pom.xml` 中是否正确添加了cooking模块的依赖
- 重新编译整个项目

### 3. 数据同步失败

如果数据同步失败：
- 检查网络连接是否正常
- 检查 cook.aiursoft.cn 是否可访问
- 查看日志中的具体错误信息
