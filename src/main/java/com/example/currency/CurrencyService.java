package com.example.currency;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRateRepository rateRepository;

    CurrencyService(CurrencyRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Transactional(readOnly = true)
    public double convert(String from, String to, double amount) {
        Optional<CurrencyRate> rateOpt = rateRepository.findByFromCurrencyAndToCurrency(from, to);
        if (rateOpt.isEmpty()) {
            throw new IllegalArgumentException("No rate found for " + from + " → " + to);
        }
        return amount * rateOpt.get().getRate();
    }

    @Transactional
    public void addOrUpdateRate(String from, String to, double rate) {
        var rateEntity = rateRepository.findByFromCurrencyAndToCurrency(from, to)
                .orElse(new CurrencyRate(from, to, rate));
        rateEntity.setRate(rate);
        rateRepository.save(rateEntity);
    }
}
