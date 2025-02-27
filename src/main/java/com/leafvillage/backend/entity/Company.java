package com.leafvillage.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corp_nm")
    private String corpNm;
    private String address;
    private String phone;
    private String domain;

    @Column(name = "logo_url")
    private String logoUrl;

    // 기본 생성자 및 getter/setter
    public Company() {}

    public Company(String corpNm, String address, String phone, String domain, String logoUrl) {
        this.corpNm = corpNm;
        this.address = address;
        this.phone = phone;
        this.domain = domain;
        this.logoUrl = logoUrl;
    }

    public Long getId() {
        return id;
    }
    public String getCorpNm() {
        return corpNm;
    }
    public void setCorpNm(String corpNm) {
        this.corpNm = corpNm;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getLogoUrl() {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
