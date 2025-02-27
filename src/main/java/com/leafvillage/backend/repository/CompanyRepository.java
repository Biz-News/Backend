package com.leafvillage.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leafvillage.backend.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 필요에 따라 추가 쿼리 메서드를 정의할 수 있습니다.
}
