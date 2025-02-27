package com.leafvillage.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company", schema = "news_db")
public class Company {

    // 회사 고유 식별자(PK) - VARCHAR(36)으로 UUID 등 수동 저장을 가정
    @Id
    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;

    // 회사명(한글)
    @Column(name = "company", length = 255)
    private String company;

    // 회사명(영문)
    @Column(name = "company_name_en", length = 255)
    private String companyNameEn;

    // 대표자명
    @Column(name = "representative_name", length = 100)
    private String representativeName;

    // 사업자등록번호
    @Column(name = "business_registration_number", length = 50)
    private String businessRegistrationNumber;

    // 주소
    @Column(name = "address", length = 255)
    private String address;

    // 전화번호
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    // 팩스번호
    @Column(name = "fax_number", length = 50)
    private String faxNumber;

    // 홈페이지 URL
    @Column(name = "homepage_url", length = 255)
    private String homepageUrl;

    // 표준산업분류명
    @Column(name = "standard_industry_classification", length = 255)
    private String standardIndustryClassification;

    // 주요사업
    @Column(name = "main_business", length = 255)
    private String mainBusiness;

    // 설립일자 (DATE)
    @Column(name = "establishment_date")
    private String establishmentDate; 
    // 실제로는 LocalDate로 매핑할 수도 있음
    // @Temporal(TemporalType.DATE)는 jakarta.persistence에서 사용 가능

    // 코스닥 상장일자 (DATE)
    @Column(name = "kosdaq_listed_date")
    private String kosdaqListedDate;
    // 마찬가지로 LocalDate를 사용할 수도 있음

    // 종업원 수 (INT)
    @Column(name = "employee_count")
    private Integer employeeCount;

    // 이미지 URL
    @Column(name = "logo_image", length = 255)
    private String logoImage;

    // 기본 생성자
    public Company() {
    }

    // 필요한 경우 모든 필드를 받는 생성자
    public Company(String companyId, String company, String companyNameEn, String representativeName,
                   String businessRegistrationNumber, String address, String phoneNumber,
                   String faxNumber, String homepageUrl, String standardIndustryClassification,
                   String mainBusiness, String establishmentDate, String kosdaqListedDate,
                   Integer employeeCount, String logoImage) {
        this.companyId = companyId;
        this.company = company;
        this.companyNameEn = companyNameEn;
        this.representativeName = representativeName;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.homepageUrl = homepageUrl;
        this.standardIndustryClassification = standardIndustryClassification;
        this.mainBusiness = mainBusiness;
        this.establishmentDate = establishmentDate;
        this.kosdaqListedDate = kosdaqListedDate;
        this.employeeCount = employeeCount;
        this.logoImage = logoImage;
    }

    // Getter & Setter

    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }
    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getRepresentativeName() {
        return representativeName;
    }
    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }
    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }
    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getStandardIndustryClassification() {
        return standardIndustryClassification;
    }
    public void setStandardIndustryClassification(String standardIndustryClassification) {
        this.standardIndustryClassification = standardIndustryClassification;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }
    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }
    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getKosdaqListedDate() {
        return kosdaqListedDate;
    }
    public void setKosdaqListedDate(String kosdaqListedDate) {
        this.kosdaqListedDate = kosdaqListedDate;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }
    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getLogoImage() {
        return logoImage;
    }
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}
