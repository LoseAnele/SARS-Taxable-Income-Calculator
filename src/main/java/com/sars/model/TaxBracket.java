package com.sars.model;

import java.math.BigDecimal;

public record TaxBracket(
        BigDecimal minIncome,
        BigDecimal maxIncome,
        BigDecimal baseTax,
        double marginalRate
) {}