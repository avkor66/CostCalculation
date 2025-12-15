package org.calculator.costcalculation.controller;

import org.calculator.costcalculation.dto.*;
import org.calculator.costcalculation.domain.CalculationTask;
import org.calculator.costcalculation.service.CalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calculation")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CalculationController {

    private final CalculationService calculationService;

    // Spring автоматически внедрит бин
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ApiResult> calculate(@RequestBody List<CalculationRequest> requests) {

        System.out.println("--- НАЧАЛО ЗАГОЛОВКОВ ВХОДЯЩЕГО ЗАПРОСА ---");
        System.out.println(requests.toString());
        System.out.println("--- КОНЕЦ ЗАГОЛОВКОВ ---");

        List<TaskResult> tasks = new ArrayList<>();

        for (CalculationRequest request : requests) {

            CalculationTask task = new CalculationTask();
            task.setCartId(request.getCartId());
            task.setHeight(request.getHeight());
            task.setOuterDiameter(request.getOuterDiameter());
            task.setInnerDiameter(request.getInnerDiameter());
            task.setLength(request.getLength());
            task.setDiameter(request.getDiameter());
            task.setSpecies(request.getSpecies());
            task.setSteelGrade(request.getSteelGrade());
            task.setThreadLength(request.getThreadLength());
            task.setStateStandard(request.getStateStandard());
            task.setQuantity(request.getQuantity());
            task.setVolume(request.getWeightKg());

            BlankPriceResponse blankResponse = calculationService.process(task);
            TaskResult taskResult = new TaskResult(
                    request.getId(),
                    blankResponse.getSpecies(),
                    blankResponse.getSteelGrade(),
                    blankResponse.getPricePerKg(),
                    blankResponse.getBlankWeightKg(),
                    blankResponse.getBlankLengthKg(),
                    blankResponse.getTotalPrice()
            );

            tasks.add(taskResult);

        }
        System.out.println(tasks.get(0).getTotalPrice());
        System.out.println(tasks.get(0).getOrderId());
        return ResponseEntity.ok(new ApiResult("Calculation started", tasks));
    }
}
