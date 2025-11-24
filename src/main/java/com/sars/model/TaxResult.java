package com.sars.model;

import java.math.BigDecimal;
import java.util.List;

public record TaxResult(
        BigDecimal taxableIncome,
        BigDecimal taxBeforeRebates,
        BigDecimal totalRebates,
        BigDecimal taxPayable,
        double marginalTaxRate,
        String taxClassification,
        List<String> calculationSteps
) {}