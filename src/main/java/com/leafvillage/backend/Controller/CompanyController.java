package com.leafvillage.backend.Controller;

import com.leafvillage.backend.entity.Company;
import com.leafvillage.backend.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * 기업 리스트 조회 (1~20개 반환)
     */
    @GetMapping("/companies")
    public List<CompanyDto> getCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // 페이징 적용: 최신 등록 순으로 20개만 가져오기
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "companyId"));
        List<Company> companies = companyRepository.findAll(pageable).getContent();

        // 필요한 데이터만 DTO로 변환하여 반환
        return companies.stream()
                .map(company -> new CompanyDto(company.getCompanyId(), company.getCompany()))
                .collect(Collectors.toList());
    }

    // DTO 클래스 정의
    public record CompanyDto(String company_id, String company) {}
}
