package com.sars.model;

import java.math.BigDecimal;

public record TaxInput(
        BigDecimal grossAnnualIncome,
        int age,
        BigDecimal retirementContribution
) {}