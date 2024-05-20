package org.conversor.model;

public record CurrenciesData(
        String[][] supported_codes,
        String base_code,
        String target_code,
        double conversion_rate
) { }
