package com.leafvillage.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.*;
@Service
public class CompanyApiService {

    // 이미 인코딩된 서비스키를 사용 (브라우저에서 테스트한 URL과 동일)
    private static final String SERVICE_KEY = "YugI7KFgMPZzeCgNekIYquyzw8WQVtkPJfccnooevCyF5Rh5ZXaL92U26v6PxVdapMNXtf5XfzvajTKbGkfuew==";
    private static final String BASE_URL = "https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CompanyApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 프론트엔드에서 한글 회사명을 전달받아, 공공데이터포털 API를 호출한 후 JSON 응답을 반환합니다.
     */
    public String getCompanyDataByName(String corpNm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("serviceKey", SERVICE_KEY)  // 대소문자도 브라우저와 동일하게
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 5) // 아마 이 부분에서 "=== 정확하게 일치하는 항목 (5건) ===" 문구가 반복적 출력되는 듯
                .queryParam("resultType", "json")
                .queryParam("corpNm", corpNm);

        // build(false)를 사용하여 이미 인코딩된 파라미터가 재인코딩되지 않도록 합니다.
        String url = builder.build(false).toUriString();
        System.out.println("요청 URL: " + url);  // 디버그용 로그

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // JSON 파싱
            String json = response.getBody();
            System.out.println("===============================================================");
            System.out.println(json);
            System.out.println("===============================================================");
            try{
                JsonNode root = objectMapper.readTree(json);
                JsonNode itemsNode = root.path("response")
                                  .path("body")
                                  .path("items")
                                  .path("item");

            // 정확한 매칭을 위한 리스트
            String normalizedInput = corpNm.replaceAll("\\s+", "").trim();
            String variant1 = normalizedInput;
            String variant2 = "(주)" + normalizedInput;
            String variant3 = normalizedInput + "(주)";

                List<JsonNode> exactMatches = new ArrayList<>();
                if (itemsNode.isArray()) {
                    for (JsonNode item : itemsNode) {
                        String name = item.path("corpNm").asText("").replaceAll("\\s+", "");
                        if (name.equals(normalizedInput) || name.equals(variant1) || name.equals(variant2) || name.equals(variant3)) {
                            exactMatches.add(item);
                         }               
                    }
                }else if (!itemsNode.isMissingNode()) {
            // 단일 객체인 경우에도 일치 여부 확인
                    String name = itemsNode.path("corpNm").asText("").replaceAll("\\s+", "").trim();
                    if (name.equals(variant1) || name.equals(variant2) || name.equals(variant3)) {
                        exactMatches.add(itemsNode);
                     } 
                }

            if (exactMatches.isEmpty()) {
                System.out.println("정확한 일치 항목이 없습니다.");
                return "{}";
            } else {
                System.out.println("=== 정확하게 일치하는 항목 (" + exactMatches.size() + "건) ===");
                for (JsonNode item : exactMatches) {
                    String address = item.path("enpBsadr").asText("주소 없음");
                    String phone = item.path("enpTlno").asText("전화번호 없음");
                    String domain = item.path("enpDmn").asText("도메인 없음");
                
                    //System.out.println("-----");
                    //System.out.println("회사명: " + item.path("corpNm").asText("회사명 없음"));
                    //System.out.println("주소: " + address);
                    //System.out.println("전화번호: " + phone);
                    //System.out.println("도메인: " + domain);
                    //if (!domain.equals("도메인 없음")) {
                    //    String logoUrl = "https://logo.clearbit.com/" + domain;
                    //    System.out.println("Clearbit 로고 URL: " + logoUrl);
                    //} else {
                     //   System.out.println("Clearbit 로고 URL: 도메인 정보가 없어 생성할 수 없습니다.");
                //}
            }
        }

    }catch(Exception e){
        throw new RuntimeException("JSON 파싱 오류: " + e.getMessage());
    }
    return json;
        } else {
            throw new RuntimeException("API 호출 실패: " + response.getStatusCodeValue());
        }
    }
}
