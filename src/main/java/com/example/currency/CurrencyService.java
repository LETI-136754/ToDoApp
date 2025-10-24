package com.example.currency;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRateRepository rateRepository;

    CurrencyService(CurrencyRateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @PostConstruct
    public void initDefaultRates() {
        addIfMissing("EUR", "USD", 1.10);
        addIfMissing("USD", "EUR", 0.91);
        addIfMissing("EUR", "GBP", 0.85);
        addIfMissing("GBP", "EUR", 1.18);
        addIfMissing("USD", "GBP", 0.77);
        addIfMissing("GBP", "USD", 1.30);
    }

    private void addIfMissing(String from, String to, double rate) {
        rateRepository.findByFromCurrencyAndToCurrency(from, to)
                .orElseGet(() -> rateRepository.save(new CurrencyRate(from, to, rate)));
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
