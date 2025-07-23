package cn.iocoder.yudao.module.cooking.controller.admin.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 管理後台 - 烹飪相關文件
 */
@Tag(name = "管理後台 - 烹飪相關文件")
@RestController
@RequestMapping("/api/cooking/file")
@Validated
@Slf4j
public class CookingFileController {

    private static final String DISH_IMAGE_PATH = "cooking_images/dishes/";
    private static final String STEP_IMAGE_PATH = "cooking_images/steps/";

    @GetMapping("/dish/{imageName}")
    @Operation(summary = "獲取菜品圖片")
    @Parameter(name = "imageName", description = "圖片名稱", required = true)
    public ResponseEntity<Resource> getDishImage(@PathVariable String imageName) {
        return getImageResponse(DISH_IMAGE_PATH + imageName);
    }

    @GetMapping("/step/{imageName}")
    @Operation(summary = "獲取步驟圖片")
    @Parameter(name = "imageName", description = "圖片名稱", required = true)
    public ResponseEntity<Resource> getStepImage(@PathVariable String imageName) {
        return getImageResponse(STEP_IMAGE_PATH + imageName);
    }

    private ResponseEntity<Resource> getImageResponse(String imagePath) {
        try {
            File file = new File(imagePath);
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            String contentType = determineContentType(file.getName());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            log.error("獲取圖片失敗: {}", imagePath, e);
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String filename) {
        String lowerCaseFilename = filename.toLowerCase();
        if (lowerCaseFilename.endsWith(".jpg") || lowerCaseFilename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerCaseFilename.endsWith(".png")) {
            return "image/png";
        } else if (lowerCaseFilename.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

} 