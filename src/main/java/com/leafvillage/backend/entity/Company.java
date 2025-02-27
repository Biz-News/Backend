package com.leafvillage.backend.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_id", length = 36, nullable = false)
    private String companyId;  // 예: "c000001"

    @Column(name = "company", length = 255, nullable = false)
    private String company;    // 회사 고유 식별자

    @Column(name = "company_name_kr", length = 255)
    private String companyNameKr;  // 회사명(국문)

    @Column(name = "representative_name", length = 100)
    private String representativeName;  // 대표자명

    @Column(name = "business_registration_number", length = 100)
    private String businessRegistrationNumber; // 사업자등록번호

    @Column(name = "address", length = 255)
    private String address;  // 주소

    @Column(name = "phone_number", length = 255)
    private String phoneNumber; // 전화번호

    @Column(name = "fax_number", length = 255)
    private String faxNumber;   // 팩스번호

    @Column(name = "homepage_url", length = 255)
    private String homepageUrl; // 홈페이지 URL

    @Column(name = "standard_industry_classification", length = 255)
    private String standardIndustryClassification; // 표준산업분류

    @Column(name = "main_business", length = 255)
    private String mainBusiness; // 주요사업

    @Column(name = "establishment_date")
    @Temporal(TemporalType.DATE)
    private Date establishmentDate; // 설립일

    @Column(name = "kosdaq_listed_date")
    @Temporal(TemporalType.DATE)
    private Date kosdaqListedDate;  // 코스닥 상장일

    @Column(name = "ticker", length = 20)
    private String ticker; // 티커

    @Column(name = "logo_image", length = 255)
    private String logoImage; // 이미지 링크

    // 기본 생성자
    public Company() {}

    // Getter/Setter

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

    public String getCompanyNameKr() {
        return companyNameKr;
    }
    public void setCompanyNameKr(String companyNameKr) {
        this.companyNameKr = companyNameKr;
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

    public Date getEstablishmentDate() {
        return establishmentDate;
    }
    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public Date getKosdaqListedDate() {
        return kosdaqListedDate;
    }
    public void setKosdaqListedDate(Date kosdaqListedDate) {
        this.kosdaqListedDate = kosdaqListedDate;
    }

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getLogoImage() {
        return logoImage;
    }
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}
