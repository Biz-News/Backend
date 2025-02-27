package com.leafvillage.backend.repository;

import com.leafvillage.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    // 이미 있는 'company'(회사명)으로 레코드를 찾는 메서드
    Optional<Company> findByCompany(String company);
}
