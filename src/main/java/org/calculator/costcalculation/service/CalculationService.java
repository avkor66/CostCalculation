package org.calculator.costcalculation.service;

import org.calculator.costcalculation.domain.CalculationTask;
import org.calculator.costcalculation.dto.BlankPriceBoltRequest;
import org.calculator.costcalculation.dto.BlankPriceNutRequest;
import org.calculator.costcalculation.dto.BlankPriceWasherRequest;
import org.calculator.costcalculation.dto.BlankPriceResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculationService {

    BlankService blankService;

    private static final double SAW_CUT_MM = 3.0;
    // TODO: Вынести эти значения в application.properties или в отдельный сервис
    private static final BigDecimal MACHINE_HOUR_COST = new BigDecimal("1500.00");
    private static final BigDecimal NORM_HOURS_PER_WASHER = new BigDecimal("0.05"); // 3 минуты = 0.05 часа
    private static final BigDecimal NORM_HOURS_PER_BOLT = new BigDecimal("0.15"); // 9 минуты = 0.15 часа
    private static final BigDecimal NORM_HOURS_PER_NUT = new BigDecimal("0.1"); // 6 минуты = 0.1 часа
    private static final BigDecimal NORM_HOURS_PER_EMBEDDED = new BigDecimal("0.25"); // 15 минуты = 0.25 часа
    private static final int CURRENCY_SCALE = 2; // 2 знака после запятой
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public CalculationService(BlankService blankService) {
        this.blankService = blankService;
    }

    BlankPriceResponse finalResult;

    public BlankPriceResponse process(CalculationTask task) {
        String species = task.getSpecies();
        if (species == null) {
            throw new IllegalArgumentException("Species is required");
        }
        System.out.println(species.toLowerCase());
        switch (species.toLowerCase()) {
            case "шайба" -> {
                return calculateWasher(task);
            }
            case "болт" -> {
                return calculateBolt(task);
            }
            case "гайка" -> {
                return calculateNut(task);
            }
            case "закладная" -> {
                return calculateEmbedded(task);
            }
            default -> throw new UnsupportedOperationException("Unsupported species: " + species);
        }
    }

    private BlankPriceResponse calculateWasher(CalculationTask task) {
        if (task.getDiameter() == null || task.getInnerDiameter() == null) {
            throw new IllegalArgumentException("Diameter and innerDiameter required for washer");
        }
        BlankPriceWasherRequest blankRequest = new BlankPriceWasherRequest();
        blankRequest.setSpecies(task.getSpecies());
        blankRequest.setOuterDiameter(task.getOuterDiameter());
        blankRequest.setDiameter(task.getDiameter());
        blankRequest.setSteelGrade(task.getSteelGrade());
        blankRequest.setStateStandard(task.getStateStandard());
        blankRequest.setQuantity(task.getQuantity());
        blankRequest.setHeight(task.getHeight());
        blankRequest.setSawCut(SAW_CUT_MM);
        BlankPriceResponse response = blankService.getBlankWasherPrice(blankRequest);
        if (response == null || response.getTotalPrice() == null) {
            throw new RuntimeException("Blank service returned null or invalid price");
        }
        BigDecimal materialCost = response.getTotalPrice();
        BigDecimal machiningCost = NORM_HOURS_PER_WASHER
                .multiply(MACHINE_HOUR_COST)
                .multiply(BigDecimal.valueOf(((Number) task.getQuantity()).longValue()))
                .setScale(CURRENCY_SCALE, ROUNDING_MODE);

        BigDecimal totalCost = materialCost.add(machiningCost);
        task.setResult(totalCost.doubleValue());
        task.setStatus("COMPLETED");

        System.out.println("Calculation completed:");
        System.out.println(" - Material Cost: " + materialCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Machining Cost: " + machiningCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Total Cost: " + task.getResult());

        return response;
    }

    private BlankPriceResponse calculateBolt(CalculationTask task) {
        if (task.getDiameter() == null || task.getInnerDiameter() == null) {
            throw new IllegalArgumentException("Diameter and innerDiameter required for washer");
        }

        BlankPriceBoltRequest blankRequest = new BlankPriceBoltRequest();
        blankRequest.setSpecies(task.getSpecies());
        blankRequest.setLength(task.getLength());
        blankRequest.setDiameter(task.getDiameter());
        blankRequest.setSteelGrade(task.getSteelGrade());
        blankRequest.setQuantity(task.getQuantity());
        blankRequest.setStateStandard(task.getStateStandard());
        blankRequest.setSawCut(SAW_CUT_MM);
        BlankPriceResponse response = blankService.getBlankBoltPrice(blankRequest);
        if (response == null || response.getTotalPrice() == null) {
            throw new RuntimeException("Blank service returned null or invalid price");
        }
        BigDecimal materialCost = response.getTotalPrice();
        BigDecimal machiningCost = NORM_HOURS_PER_BOLT
                .multiply(MACHINE_HOUR_COST)
                .multiply(BigDecimal.valueOf(((Number) task.getQuantity()).longValue()))
                .setScale(CURRENCY_SCALE, ROUNDING_MODE);

        BigDecimal totalCost = materialCost.add(machiningCost);
        task.setResult(totalCost.doubleValue());
        task.setStatus("COMPLETED");
        System.out.println("Calculation completed:");
        System.out.println(" - Material Cost: " + materialCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Machining Cost: " + machiningCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Total Cost: " + task.getResult());

        return response;
    }

    private BlankPriceResponse calculateNut(CalculationTask task) {
        if (task.getDiameter() == null || task.getInnerDiameter() == null) {
            throw new IllegalArgumentException("Diameter and innerDiameter required for washer");
        }

        BlankPriceNutRequest blankRequest = new BlankPriceNutRequest();
        blankRequest.setSpecies(task.getSpecies());
        blankRequest.setHeight(task.getHeight());
        blankRequest.setDiameter(task.getDiameter());
        blankRequest.setSteelGrade(task.getSteelGrade());
        blankRequest.setQuantity(task.getQuantity());
        blankRequest.setStateStandard(task.getStateStandard());
        blankRequest.setSawCut(SAW_CUT_MM);
        BlankPriceResponse response = blankService.getBlankNutPrice(blankRequest);

        if (response == null || response.getTotalPrice() == null) {
            throw new RuntimeException("Blank service returned null or invalid price");
        }

        BigDecimal materialCost = response.getTotalPrice(); // <-- Это
        BigDecimal machiningCost = NORM_HOURS_PER_NUT
                .multiply(MACHINE_HOUR_COST)
                .multiply(BigDecimal.valueOf(((Number) task.getQuantity()).longValue()))
                .setScale(CURRENCY_SCALE, ROUNDING_MODE);

        BigDecimal totalCost = materialCost.add(machiningCost);
        task.setResult(totalCost.doubleValue());
        task.setStatus("COMPLETED");

        System.out.println("Calculation completed:");
        System.out.println(" - Material Cost: " + materialCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Machining Cost: " + machiningCost.setScale(CURRENCY_SCALE, ROUNDING_MODE));
        System.out.println(" - Total Cost: " + task.getResult());

        System.out.println(response.getSpecies());
        System.out.println(response.getSteelGrade());
        System.out.println(response.getPricePerKg());
        System.out.println(response.getBlankWeightKg());
        System.out.println(response.getBlankLengthKg());
        System.out.println(response.getTotalPrice());

        return response;
    }

    private BlankPriceResponse calculateEmbedded(CalculationTask task) {
        BlankPriceResponse response = new BlankPriceResponse();
        response.setSpecies(task.getSpecies());
        response.setSteelGrade(task.getSteelGrade());
        response.setPricePerKg(120.00);
        response.setPricePerKg(null);
        response.setPricePerKg(null);
        response.setTotalPrice(new BigDecimal(task.getVolume().toString()).multiply(new BigDecimal("120")).multiply(new BigDecimal(task.getQuantity().toString())) );
        return response;
    }
}