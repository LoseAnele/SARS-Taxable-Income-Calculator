package com.sars.model;

import java.math.BigDecimal;

public record TaxBeforeRebatesResult(
        BigDecimal taxBeforeRebates,
        double marginalRate,
        String taxClassification)
{ }
