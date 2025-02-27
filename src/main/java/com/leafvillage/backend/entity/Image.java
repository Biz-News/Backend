package com.leafvillage.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company", schema = "news_db")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회사 ID (예: "c000018")
    @Column(name = "company_id", unique = true)
    private String companyId;

    // (옵션) 회사명 – 필요에 따라 유지하거나 제거할 수 있습니다.
    @Column(name = "company")
    private String company;

    // 로고 이미지 URL
    @Column(name = "logo_image")
    private String imageUrl;

    public Image() {}

    // 생성자나 getter/setter는 필요에 따라 추가
    public Long getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompany() {
        return company;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
