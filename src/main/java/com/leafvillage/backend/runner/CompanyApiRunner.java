package com.leafvillage.backend.runner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafvillage.backend.entity.Company;
import com.leafvillage.backend.repository.CompanyRepository;
import com.leafvillage.backend.service.CompanyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class CompanyApiRunner implements CommandLineRunner {

    @Autowired
    private CompanyApiService companyApiService;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();

        while (true) {
            System.out.println("조회할 기업명을 입력하세요 (종료하려면 'exit' 입력):");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            try {
                // 1) API 호출
                String fullJson = companyApiService.getCompanyDataByName(input);
                JsonNode root = mapper.readTree(fullJson);
                JsonNode itemsNode = root.path("response")
                                         .path("body")
                                         .path("items")
                                         .path("item");

                // 2) 일치 항목이 있는지 확인
                if (itemsNode.isMissingNode() || (itemsNode.isArray() && itemsNode.size() == 0)) {
                    System.out.println("[API] 일치하는 항목이 없습니다. 건너뜁니다.");
                    continue;
                }

                // 단일 / 배열 구분 (예: 첫 번째만 사용)
                JsonNode firstItem = itemsNode.isArray() ? itemsNode.get(0) : itemsNode;

                // 3) API에서 가져온 회사명
                String apiCompanyName = firstItem.path("corpNm").asText("").trim();
                apiCompanyName = apiCompanyName.replace("(주)", "")
                               .replace("[주]", "")
                               .trim();
                        
                if (apiCompanyName.isEmpty()) {
                    // 회사명이 없다면 업데이트 불가
                    System.out.println("[API] 회사명이 없습니다. 건너뜁니다.");
                    continue;
                }

                // 4) DB에서 레코드 찾기 (company 컬럼 기준)
                Optional<Company> optCompany = companyRepository.findByCompany(apiCompanyName);
                if (optCompany.isPresent()) {
                    // === 이미 존재하는 레코드 => 업데이트 ===
                    Company existing = optCompany.get();

                    // 필요한 컬럼만 업데이트
                    String address = firstItem.path("enpBsadr").asText("").trim();
                    String phone = firstItem.path("enpTlno").asText("").trim();
                    String domain = firstItem.path("enpHmpgUrl").asText("").trim();

                    // 예시로 Null이거나 빈 값일 때만 업데이트
                    if (existing.getAddress() == null || existing.getAddress().isEmpty()) {
                        existing.setAddress(address);
                    }
                    if (existing.getPhoneNumber() == null || existing.getPhoneNumber().isEmpty()) {
                        existing.setPhoneNumber(phone);
                    }
                    if (existing.getHomepageUrl() == null || existing.getHomepageUrl().isEmpty()) {
                        existing.setHomepageUrl(domain);
                    }

                    // 로고 URL
                    String logoUrl = "도메인 정보 없음";
                    if (!domain.isBlank()) {
                        logoUrl = "https://logo.clearbit.com/" + domain;
                    }
                    if (existing.getLogoImage() == null || existing.getLogoImage().isEmpty()) {
                        existing.setLogoImage(logoUrl);
                    }

                    // 실제 DB 업데이트
                    companyRepository.save(existing);
                    System.out.println("[업데이트 완료] 회사명 " + apiCompanyName + " 컬럼이 갱신되었습니다.");

                } else {
                    // === 존재하지 않는 레코드 => 새로 삽입하지 않고 스킵 ===
                    System.out.println("[스킵] DB에 존재하지 않는 회사(" + apiCompanyName + ")입니다.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
