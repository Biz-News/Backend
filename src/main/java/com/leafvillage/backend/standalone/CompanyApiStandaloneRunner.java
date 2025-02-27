package com.leafvillage.backend.standalone;

import com.leafvillage.backend.service.CompanyApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyApiStandaloneRunner {
    public static void main(String[] args) {
        CompanyApiService service = new CompanyApiService();
        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);

        System.out.println("조회할 기업명을 입력하세요:");
        String corpNm = scanner.hasNextLine() ? scanner.nextLine() : "기본기업명";

        try {
            // API 호출 후 전체 JSON 응답 받기
            String fullJson = service.getCompanyDataByName(corpNm);
            JsonNode root = mapper.readTree(fullJson);
            JsonNode itemsNode = root.path("response")
                                     .path("body")
                                     .path("items")
                                     .path("item");

            // 기업명 정규화 및 (주) 관련 케이스 구분
            String normalizedInput = corpNm.replaceAll("\\s+", "");
            String variant1 = normalizedInput;
            String variant2 = "(주)" + normalizedInput;
            String variant3 = normalizedInput + "(주)";

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
                if (name.equals(variant1) || name.equals(variant2) || name.equals(variant3)) {
                    exactMatches.add(itemsNode);
                }
            }

            // 결과 출력
            if (exactMatches.isEmpty()) {
                System.out.println(corpNm + " : 정확한 일치 항목 없음");
            } else {
                System.out.println("=== 정확하게 일치하는 항목 (" + exactMatches.size() + "건) ===");
                for (JsonNode item : exactMatches) {
                    String companyName = item.path("corpNm").asText("회사명 없음");
                    String address = item.path("enpBsadr").asText("주소 없음");
                    String phone = item.path("enpTlno").asText("전화번호 없음");
                    String domain = item.path("enpHmpgUrl").asText("도메인 없음");

                    System.out.println("회사명: " + companyName);
                    System.out.println("주소: " + address);
                    System.out.println("전화번호: " + phone);
                    System.out.println("도메인: " + domain);
                    if (!domain.equals("도메인 없음")) {
                        String logoUrl = "https://logo.clearbit.com/" + domain;
                        System.out.println("Clearbit 로고 URL: " + logoUrl);
                    } else {
                        System.out.println("Clearbit 로고 URL: 도메인 정보 없음");
                    }
                    System.out.println("-----");
                }
            }
        } catch (Exception e) {
            System.out.println("API 조회 중 오류 발생: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
