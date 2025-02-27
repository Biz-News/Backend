package com.leafvillage.backend.repository;
import java.util.Optional;
import com.leafvillage.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    // 기본 제공되는 findById(companyId) 사용 (JPA에서 자동 제공)
    Optional<Company> findByCompany(String company);
}
