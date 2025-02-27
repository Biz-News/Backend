package com.leafvillage.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafvillage.backend.service.CompanyApiService;

import java.util.*;

public class CompanyApiTestRunner {
    public static void main(String[] args) {
        CompanyApiService service = new CompanyApiService();
        List<String> corpNames = Arrays.asList(
                "삼성전자",
                "현대자동차",
                "기아",
                "LG전자",
                "SK하이닉스",
                "네이버",
                "카카오",
                "삼성생명",
                "삼성물산",
                "현대모비스",
                "SK이노베이션",
                "LG화학",
                "포스코홀딩스",
                "한화솔루션",
                "대한항공",
                "아시아나항공",
                "CJ제일제당",
                "롯데쇼핑",
                "삼성SDI",
                "셀트리온",
                "삼성바이오로직스",
                "엔씨소프트",
                "넷마블",
                "하이브",
                "현대건설",
                "두산에너빌리티",
                "에코프로",
                "한국전력공사",
                "LG에너지솔루션",
                "KB금융"

        );

        ObjectMapper mapper = new ObjectMapper();
        Set<String> printedCompanies = new HashSet<>(); // 중복 제거를 위한 Set
        int failCount = 0; // 실패한 기업 수를 셀 변수

        for (String corpNm : corpNames) {
            try {
                // 요청 URL 직접 출력 (한 번만 출력)
                String requestUrl = "https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2?serviceKey=YugI7KFgMPZzeCgNekIYquyzw8WQVtkPJfccnooevCyF5Rh5ZXaL92U26v6PxVdapMNXtf5XfzvajTKbGkfuew==&pageNo=1&numOfRows=5&resultType=json&corpNm=" + corpNm;
                System.out.println("-----");
                //System.out.println("요청 URL: " + requestUrl); // 요청 URL 출력

                // API 호출
                String fullJson = service.getCompanyDataByName(corpNm);
//                System.out.println("-----");
                // API 호출 성공 여부 체크
                if (fullJson == null || fullJson.isEmpty()) {
                    System.out.println(corpNm + " : 기업 정보를 가져오는 데 실패했습니다.");
                    System.out.println("-----"); // 실패 시에도 구분선 추가
                    failCount++; // 실패 시 카운트 증가
                    continue; // 실패 시 다음 기업으로 넘어가기
                }

                JsonNode root = mapper.readTree(fullJson);
                JsonNode itemsNode = root.path("response")
                        .path("body")
                        .path("items")
                        .path("item");

                // 기업명 정규화 (공백 제거)
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

                // 결과 출력 (중복 제거)
                if (exactMatches.isEmpty()) {
                    System.out.println(corpNm + " : 정확한 일치 항목 없음");
                    failCount++;
//                    System.out.println("-----"); // 실패 시에도 구분선 추가
                } else {
                    //System.out.println("=== 정확하게 일치하는 항목 (" + exactMatches.size() + "건) ===");

                    for (JsonNode item : exactMatches) {
                        String companyName = item.path("corpNm").asText("회사명 없음");

                        // 중복 체크: 이미 출력한 기업이면 건너뛰기
                        if (printedCompanies.contains(companyName)) {
                            continue;
                        }
                        printedCompanies.add(companyName); // 출력한 기업 추가

                        String address = item.path("enpBsadr").asText("주소 없음");
                        String phone = item.path("enpTlno").asText("전화번호 없음");
                        String domain = item.path("enpHmpgUrl").asText("도메인 없음");

//                        System.out.println("-----");
                        System.out.println("회사명: " + companyName);
                        System.out.println("주소: " + address);
                        System.out.println("전화번호: " + phone);
                        System.out.println("도메인: " + domain);
                        if (!domain.equals("도메인 없음")) {
                            String logoUrl = "https://logo.clearbit.com/" + domain;
                            System.out.println("Clearbit 로고 URL: " + logoUrl);
//                            System.out.println("-----");
                        } else {
                            System.out.println("Clearbit 로고 URL: 도메인 정보 없음");
                            failCount++;
//                            System.out.println("-----");
                        }
                    }
                }
            } catch (Exception e) {
                // 실패 시에도 구분선과 함께 메시지 출력
                System.out.println("-----");
                System.out.println("[" + corpNm + "] 데이터 조회 중 오류 발생: " + e.getMessage());
//                System.out.println("-----"); // 실패 시에도 구분선 추가
                failCount++; // 실패 시 카운트 증가
            }
        }

        // 실패한 기업 개수 출력
        System.out.println("-----");
        System.out.println("총 정보 가져오기 실패한 기업 개수: " + failCount);
    }
}
