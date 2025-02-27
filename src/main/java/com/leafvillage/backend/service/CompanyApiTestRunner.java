package com.leafvillage.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafvillage.backend.service.CompanyApiService;

import java.util.ArrayList;
import java.util.List;

public class CompanyApiTestRunner {
    public static void main(String[] args) {
        CompanyApiService service = new CompanyApiService();
        try {
            // 원하는 회사명 입력 (예: "네이버")
            String corpNm = "삼성전자";
            // API 호출: 전체 JSON 응답을 반환
            String fullJson = service.getCompanyDataByName(corpNm);
            
            // 전체 API 응답 출력
            //System.out.println("=== 전체 API 응답 ===");
            //System.out.println(fullJson);
            
            // ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(fullJson);
            JsonNode itemsNode = root.path("response")
                                     .path("body")
                                     .path("items")
                                     .path("item");
            
            // 입력한 회사명을 정규화 (공백 제거)
            String normalizedInput = corpNm.replaceAll("\\s+", "");
            // 다양한 "(주)" 변형 생성
            String variant1 = normalizedInput;
            String variant2 = "(주)" + normalizedInput;
            String variant3 = normalizedInput + "(주)";
            
            // 정확히 일치하는 항목을 저장할 리스트
            List<JsonNode> exactMatches = new ArrayList<>();
            if (itemsNode.isArray()) {
                for (JsonNode item : itemsNode) {
                    String name = item.path("corpNm").asText("").replaceAll("\\s+", "");
                    if (name.equals(variant1) || name.equals(variant2) || name.equals(variant3)) {
                        exactMatches.add(item);
                    }
                }
            } else if (!itemsNode.isMissingNode()) {
                String name = itemsNode.path("corpNm").asText("").replaceAll("\\s+", "");
                if (name.equals(variant1) || name.equals(variant2) || name.equals(variant3) ) {
                    exactMatches.add(itemsNode);
                }
            }
            
            // 결과 출력
            if (exactMatches.isEmpty()) {
                System.out.println("정확한 일치 항목이 없습니다.");
            } else {
                System.out.println("=== 정확하게 일치하는 항목 (" + exactMatches.size() + "건) ===");
                for (JsonNode item : exactMatches) {
                    String companyName = item.path("corpNm").asText("회사명 없음");
                    String address = item.path("enpBsadr").asText("주소 없음");
                    String phone = item.path("enpTlno").asText("전화번호 없음");
                    String domain = item.path("enpHmpgUrl").asText("도메인 없음");
                    
                    System.out.println("-----");
                    System.out.println("회사명: " + companyName);
                    System.out.println("주소: " + address);
                    System.out.println("전화번호: " + phone);
                    System.out.println("도메인: " + domain);
                    if (!domain.equals("도메인 없음")) {
                        String logoUrl = "https://logo.clearbit.com/" + domain;
                        System.out.println("Clearbit 로고 URL: " + logoUrl);
                    } else {
                        System.out.println("Clearbit 로고 URL: 도메인 정보가 없어 생성할 수 없습니다.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
