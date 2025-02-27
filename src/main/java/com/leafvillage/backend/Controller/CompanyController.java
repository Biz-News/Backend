package com.leafvillage.backend.Controller;

import com.leafvillage.backend.service.CompanyApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CompanyController {
    private final CompanyApiService companyApiService;
    public CompanyController(CompanyApiService companyApiService) {
        this.companyApiService = companyApiService;
    }

    /**
     * 예: GET /api/company?corpNm=넥슨
     * - API 응답 JSON 전체를 반환
     * - 터미널에는 주소/전화번호만 출력
     */
    @GetMapping("/company")
    public ResponseEntity<String> getCompanyInfo(@RequestParam String corpNm) {
        try {
            // 1) 주소/전화번호 콘솔 출력 + 전체 JSON 반환
            String json = companyApiService.getCompanyDataByName(corpNm);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("오류 발생: " + e.getMessage());
        }
    }
}
