package com.example.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    Optional<CurrencyRate> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
