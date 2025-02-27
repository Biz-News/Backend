package com.leafvillage.backend.Controller;

import com.leafvillage.backend.entity.Company;
import com.leafvillage.backend.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies") // ✅ 모든 API를 /companies 하위로 통합
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * ✅ 기업 리스트 조회 (페이징)
     * GET /companies?page=0&size=20
     */
    @GetMapping
    public List<CompanyDto> getCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // 페이징 적용: companyId 기준 오름차순 정렬하여 20개씩 반환
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "companyId"));
        List<Company> companies = companyRepository.findAll(pageable).getContent();

        // 필요한 데이터만 DTO로 변환하여 반환
        return companies.stream()
                .map(company -> new CompanyDto(company.getCompanyId(), company.getCompany()))
                .collect(Collectors.toList());
    }

    /**
     * ✅ 기업 ID로 상세 조회
     * GET /companies/{company-id}
     */
    @GetMapping("/{companyId}")
public ResponseEntity<?> getCompanyById(@PathVariable String companyId) {
    Optional<Company> company = companyRepository.findById(companyId);

    return company
        .<ResponseEntity<?>>map(c -> ResponseEntity.ok(new CompanyDetailDto(
            c.getCompanyId(),
            c.getCompany(),
            c.getCompanyNameEn(),
            c.getRepresentativeName(),
            c.getBusinessRegistrationNumber(),
            c.getAddress(),
            c.getPhoneNumber(),
            c.getFaxNumber(),
            c.getHomepageUrl(),
            c.getStandardIndustryClassification(),
            c.getMainBusiness(),
            c.getEstablishmentDate(),
            c.getKosdaqListedDate(),
            c.getEmployeeCount(),
            c.getLogoImage()
        )))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("{ \"error\": \"해당 ID의 기업을 찾을 수 없습니다.\" }"));
}
    // ✅ 기업 리스트 조회용 DTO
    public record CompanyDto(String company_id, String company) {}

    // ✅ 기업 상세 정보 DTO
    public record CompanyDetailDto(
            String company_id,
            String company,
            String company_name_en,
            String representative_name,
            String business_registration_number,
            String address,
            String phone_number,
            String fax_number,
            String homepage_url,
            String standard_industry_classification,
            String main_business,
            String establishment_date,
            String kosdaq_listed_date,
            Integer employee_count,
            String logo_image
    ) {}
}
