package org.calculator.costcalculation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CostController {
    record HealthResponse(String status) {}

    @GetMapping("/health_check")
    public ResponseEntity<HealthResponse> healthCheck() {
        return ResponseEntity.ok(new HealthResponse("CostCalculation Check OK"));
    }
}
