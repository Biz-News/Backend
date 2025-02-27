package com.leafvillage.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.leafvillage.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {

    private final Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    public ImageService() {
        // Cloudinary 계정 정보 (본인 계정의 값으로 변경)
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dncizjyjo");
        config.put("api_key", "451525654937799");
        config.put("api_secret", "HeW5BQcEvIl87MvohARwUPdQO2s");
        this.cloudinary = new Cloudinary(config);
    }

    // MultipartFile을 임시 파일로 변환 후 Cloudinary에 업로드하여 이미지 URL 반환
    public String uploadToCloudinary(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete(); // 임시 파일 삭제
        return (String) uploadResult.get("secure_url");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    // Cloudinary 업로드 후, company_id 기준으로 DB 업데이트 수행
    @Transactional
    public boolean updateCompanyLogoById(String companyId, MultipartFile file) {
        try {
            String imageUrl = uploadToCloudinary(file);
            int updatedCount = imageRepository.updateLogoImageByCompanyId(imageUrl, companyId);
            return updatedCount > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
