package com.leafvillage.backend.dto;

public class CompanyPartialDto {
    private String corpNm;        // 회사명
    private String englishName;   // 영문명
    private String address;       // 주소

    public CompanyPartialDto() {
    }

    public CompanyPartialDto(String corpNm, String englishName, String address) {
        this.corpNm = corpNm;
        this.englishName = englishName;
        this.address = address;
    }

    public String getCorpNm() {
        return corpNm;
    }
    public void setCorpNm(String corpNm) {
        this.corpNm = corpNm;
    }

    public String getEnglishName() {
        return englishName;
    }
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
