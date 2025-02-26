package com.leafvillage.backend.Controller.Test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @GetMapping
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("백엔드 서버 정상 작동 중!");
    }
}