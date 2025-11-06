package org.calculator.costcalculation.controller;

import org.calculator.costcalculation.dto.ApiResponse;
import org.calculator.costcalculation.domain.CalculationTask;
import org.calculator.costcalculation.dto.CalculationRequest;
import org.calculator.costcalculation.service.CalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculation")
public class CalculationController {

    private final CalculationService calculationService;

    // Spring автоматически внедрит бин
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse> calculate(@RequestBody CalculationRequest request) {

        CalculationTask task = new CalculationTask();
        task.setCartId(request.getCartId());
        task.setHeight(request.getHeight());
        task.setOuterDiameter(request.getOuterDiameter());
        task.setInnerDiameter(request.getInnerDiameter());
        task.setSpecies(request.getSpecies());
        task.setStateStandard(request.getStateStandard());
        task.setDiameter(request.getDiameter());
        task.setLength(request.getLength());
        task.setThreadLength(request.getThreadLength());
        task.setStateStandard(request.getStateStandard());
        task.setExecution(request.getExecution());
        task.setQuantity(request.getQuantity());
        task.setDelivery(request.getDelivery());
        task.setVolume(request.getVolume());
        task.setComment(request.getComment());

        calculationService.process(task);

        return ResponseEntity.ok(new ApiResponse("Calculation started", task.getCartId()));
    }
}
