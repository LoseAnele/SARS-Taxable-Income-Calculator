package com.sars.service;

import com.sars.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TaxCalculatorService {

    private static final int DECIMAL_PLACES = 2;

    public TaxResult calculateTax(TaxInput input) {
        List<String> steps = new ArrayList<>();
        // 1. Calculate Taxable Income
        BigDecimal taxableIncome = calculateTaxableIncome(input, steps);

        // 2. Calculate Tax before Rebates
        TaxBeforeRebatesResult taxBeforeRebatesResult = calculateTaxBeforeRebates(taxableIncome, steps);

        // 3. Apply Rebates
        BigDecimal totalRebates = applyTaxRebates(input, steps);

        // 4. Calculate Tax Payable
        BigDecimal taxPayable = calculateTaxPayable(taxBeforeRebatesResult.taxBeforeRebates(), totalRebates, steps);

        return new TaxResult(
                taxableIncome,
                taxBeforeRebatesResult.taxBeforeRebates(),
                totalRebates,
                taxPayable,
                taxBeforeRebatesResult.marginalRate() * 100,
                taxBeforeRebatesResult.taxClassification(),
                steps
        );
    }

    private TaxBeforeRebatesResult calculateTaxBeforeRebates(BigDecimal taxableIncome, List<String> steps) {
        BigDecimal taxBeforeRebates = BigDecimal.ZERO;
        double marginalRate = 0.0;
        String taxClassification = "N/A";

        TaxBracket applicableBracket = determineApplicableBracket(taxableIncome);

        if (applicableBracket != null) {
            return applyTaxBracketNotNull(taxableIncome, applicableBracket, steps);
        } else {
            taxClassification = "0% - Below Taxable Threshold";
            steps.add("2. Tax before Rebates: Taxable income is below the minimum threshold (R237,100). Tax liability is R0.");
        }

        return new TaxBeforeRebatesResult(taxBeforeRebates, marginalRate, taxClassification);
    }

    private TaxBeforeRebatesResult applyTaxBracketNotNull(BigDecimal taxableIncome, TaxBracket applicableBracket, List<String> steps) {
        BigDecimal incomeAboveThreshold = taxableIncome.subtract(applicableBracket.minIncome().subtract(new BigDecimal("1")));
        BigDecimal marginalTaxAmount = incomeAboveThreshold.multiply(new BigDecimal(applicableBracket.marginalRate()));

        BigDecimal taxBeforeRebates = applicableBracket.baseTax().add(marginalTaxAmount).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        double marginalRate = applicableBracket.marginalRate();
        String taxClassification = String.format("%.0f%% (R%,.2f to R%,.2f)",
                (marginalRate * 100), applicableBracket.minIncome(), applicableBracket.maxIncome());

        steps.add(String.format("2. Tax before Rebates: Based on the income of R%,.2f, you fall into the %.0f%% marginal tax bracket.", taxableIncome, marginalRate * 100));
        steps.add(String.format("   - Base Tax for the bracket: R%,.2f", applicableBracket.baseTax()));
        steps.add(String.format("   - Income Taxed at Marginal Rate: R%,.2f", incomeAboveThreshold));
        steps.add(String.format("   - Marginal Tax (%.0f%%): R%,.2f", marginalRate * 100, marginalTaxAmount));
        steps.add(String.format("   - Total Tax Before Rebates: R%,.2f", taxBeforeRebates));

        return new TaxBeforeRebatesResult(taxBeforeRebates, marginalRate, taxClassification);
    }

    private BigDecimal calculateTaxPayable(BigDecimal taxBeforeRebates, BigDecimal totalRebates, List<String> steps) {
        BigDecimal taxPayable = taxBeforeRebates.subtract(totalRebates)
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);

        if (taxPayable.compareTo(BigDecimal.ZERO) < 0) {
            taxPayable = BigDecimal.ZERO;
        }

        steps.add(String.format("4. Final Tax Payable: Tax Before Rebates (R%,.2f) - Total Rebates (R%,.2f) = R%,.2f",
                taxBeforeRebates, totalRebates, taxPayable));

        return  taxPayable;
    }

    private BigDecimal applyTaxRebates(TaxInput input, List<String> steps) {
        BigDecimal primaryRebate = TaxConstants.REBATES.get("PRIMARY");
        BigDecimal secondaryRebate = BigDecimal.ZERO;
        BigDecimal tertiaryRebate = BigDecimal.ZERO;

        if (input.age() >= 65) {
            secondaryRebate = TaxConstants.REBATES.get("SECONDARY");
        }
        if (input.age() >= 75) {
            tertiaryRebate = TaxConstants.REBATES.get("TERTIARY");
        }

        BigDecimal totalRebates = primaryRebate.add(secondaryRebate).add(tertiaryRebate);
        steps.add(String.format("3. Apply Rebates: Primary (R%,.2f) + Secondary (R%,.2f) + Tertiary (R%,.2f) = R%,.2f",
                primaryRebate, secondaryRebate, tertiaryRebate, totalRebates));

        return totalRebates;
    }

    private TaxBracket determineApplicableBracket(BigDecimal taxableIncome) {
        for (TaxBracket bracket : TaxConstants.TAX_BRACKETS_UNDER_65) {
            if (taxableIncome.compareTo(bracket.minIncome()) >= 0) {
                return bracket;
            } else {
                break;
            }
        }
        return null;
    }

    private BigDecimal calculateTaxableIncome(TaxInput input, List<String> steps) {
        BigDecimal maxDeductibleRetirement = input.grossAnnualIncome()
                .multiply(new BigDecimal(TaxConstants.RETIREMENT_DEDUCTION_PERCENT))
                .min(TaxConstants.RETIREMENT_DEDUCTION_CAP)
                .min(input.retirementContribution())
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);

        BigDecimal taxableIncome = input.grossAnnualIncome()
                .subtract(maxDeductibleRetirement)
                .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);

        steps.add(String.format("1. Taxable Income Calculation: Gross Income (R%,.2f) - Retirement Deduction (R%,.2f) = R%,.2f",
                input.grossAnnualIncome(), maxDeductibleRetirement, taxableIncome));

        return taxableIncome;
    }
}