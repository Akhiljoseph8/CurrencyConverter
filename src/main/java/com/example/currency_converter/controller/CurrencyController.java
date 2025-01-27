package com.example.currency_converter.controller;

import com.example.currency_converter.exception.ApiUnavailableException;
import com.example.currency_converter.exception.InvalidCurrencyException;
import com.example.currency_converter.service.CurrencyService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/rates")
    public Map<String, Double> getExchangeRates(@RequestParam(defaultValue = "USD") String base) {
        try {
            return currencyService.getExchangeRates(base);
        } catch (ApiUnavailableException | InvalidCurrencyException e) {
            throw e;
        }
    }

    @PostMapping("/convert")
    public Map<String, Object> convertCurrency(@RequestBody Map<String, Object> request) {
        try {
            String from = request.get("from").toString();
            String to = request.get("to").toString();
            double amount = Double.parseDouble(request.get("amount").toString());
            double convertedAmount = currencyService.convertCurrency(from, to, amount);

            return Map.of(
                    "from", from,
                    "to", to,
                    "amount", amount,
                    "convertedAmount", convertedAmount
            );
        } catch (NumberFormatException ex) {
            throw new InvalidCurrencyException("Invalid amount format. Amount must be a valid number.");
        }
    }
}
