package com.leafvillage.backend.Controller;

import com.leafvillage.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // 회사 로고 업데이트 API – company_id를 기준으로 업데이트
    // 예: POST /images/upload?companyId=c000018 와 함께 file 전송
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("companyId") String companyId,
                                              @RequestParam("file") MultipartFile file) {
        boolean updated = imageService.updateCompanyLogoById(companyId, file);
        if (updated) {
            return ResponseEntity.ok("회사 로고 업데이트 성공");
        } else {
            return ResponseEntity.badRequest().body("회사 로고 업데이트 실패");
        }
    }
}
