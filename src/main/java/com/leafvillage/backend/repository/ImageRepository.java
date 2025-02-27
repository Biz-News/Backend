package com.leafvillage.backend.repository;

import com.leafvillage.backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    // company_id 기준으로 로고 이미지 업데이트
    @Modifying
    @Query("UPDATE Image i SET i.imageUrl = :imageUrl WHERE i.companyId = :companyId")
    int updateLogoImageByCompanyId(@Param("imageUrl") String imageUrl, @Param("companyId") String companyId);
}
