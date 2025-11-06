package org.calculator.costcalculation.service;

import org.calculator.costcalculation.dto.BlankPriceRequest;
import org.calculator.costcalculation.dto.BlankPriceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlankService {

    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    public BlankService(RestTemplate restTemplate, ServiceConfig serviceConfig) {
        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
    }

    public BlankPriceResponse getBlankPrice(BlankPriceRequest request) {

        return restTemplate.postForObject(
                serviceConfig.getMaterialPrice() + "/hardware/washers/price",
                request,
                BlankPriceResponse.class
        );
    }
}
