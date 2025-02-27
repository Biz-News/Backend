package com.leafvillage.backend.Controller.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test-api/")
public class StatusController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> checkStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "RDS 연결 성공 (Spring Boot)");
        return ResponseEntity.ok(response);
    }

    // 수정된 메서드 - JSON 형식으로 응답
    @GetMapping("/test-db")
    public ResponseEntity<Map<String, String>> testDatabaseConnection() {
        Map<String, String> response = new HashMap<>();
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("message", "RDS 연결 성공 (Spring Boot)");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "RDS 연결 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}