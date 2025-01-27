package com.example.currency_converter.service;

import com.example.currency_converter.exception.ApiUnavailableException;
import com.example.currency_converter.exception.InvalidCurrencyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Value("${exchange.api.base.url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> getExchangeRates(String baseCurrency) {
        String url = apiBaseUrl + baseCurrency;

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("rates")) {
                throw new ApiUnavailableException("Unable to fetch exchange rates. Please try again later.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> rates = (Map<String, Object>) response.get("rates");

            if (rates == null || rates.isEmpty()) {
                throw new InvalidCurrencyException("Invalid base currency provided: " + baseCurrency);
            }

            return rates.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> ((Number) entry.getValue()).doubleValue()
                    ));
        } catch (RestClientException e) {
            throw new ApiUnavailableException("Failed to connect to the external API. Please check your network.");
        }
    }

    public double convertCurrency(String from, String to, double amount) {
        Map<String, Double> rates = getExchangeRates(from);

        if (!rates.containsKey(to)) {
            throw new InvalidCurrencyException("Invalid target currency: " + to);
        }

        double exchangeRate = rates.get(to);
        return amount * exchangeRate;
    }
}
