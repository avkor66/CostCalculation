package org.calculator.costcalculation.service;

import org.calculator.costcalculation.domain.CalculationTask;
import org.calculator.costcalculation.dto.BlankPriceRequest;
import org.calculator.costcalculation.dto.BlankPriceResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    private final BlankService blankService;

    public CalculationService(BlankService blankService) {
        this.blankService = blankService;
    }
    public void process(CalculationTask task) {
        String species = task.getSpecies();
        if (species == null) {
            throw new IllegalArgumentException("Species is required");
        }
        switch (species.toLowerCase()) {
            case "шайба" -> calculateWasher(task);
//            case "болт" -> calculateBolt(task);
//            case "гайка" -> calculateNut(task);
            default -> throw new UnsupportedOperationException("Unsupported species: " + species);
        }

        // После расчёта — можно сохранить в БД
        // ... сохранение, логирование и т.д.
        // calculationRepository.save(task);
    }

    private void calculateWasher(CalculationTask task) {

        if (task.getDiameter() == null || task.getInnerDiameter() == null) {
            throw new IllegalArgumentException("Diameter and innerDiameter required for washer");
        }

        BlankPriceRequest blankRequest = new BlankPriceRequest();
        blankRequest.setSpecies("шайба");
        blankRequest.setOuterDiameter(task.getOuterDiameter());
        blankRequest.setDiameter(task.getDiameter());
        blankRequest.setSteelGrade(task.getSteelGrade());
        blankRequest.setQuantity(task.getQuantity());

        BlankPriceResponse response = blankService.getBlankPrice(blankRequest);

        if (response == null || response.getTotalPrice() == null) {
            throw new RuntimeException("Blank service returned null");
        }

        double total = response.getTotalPrice() * 1.25; // +25% за обработку
        task.setResult(total);
        task.setStatus("COMPLETED");
        System.out.println("Calculation completed, result: " + total);
    }
}
