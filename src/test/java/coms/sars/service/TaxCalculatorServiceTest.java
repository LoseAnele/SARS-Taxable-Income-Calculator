package coms.sars.service;

import com.sars.model.TaxInput;
import com.sars.model.TaxResult;
import com.sars.service.TaxCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Robust test cases for the SARS Taxable Income Calculator (2025/2026 tax year).
 */
class TaxCalculatorServiceTest {

    private TaxCalculatorService taxService;

    private static final int SCALE = 2;

    @BeforeEach
    void setUp() {
        taxService = new TaxCalculatorService();
    }

    /**
     * Test Case 1: Low Income (18% Bracket, Below Primary Threshold)
     * Taxable Income: R 90,000.00
     * Expected Tax Before Rebate: R 90,000 * 18% = R 16,200.00
     * Expected Rebates: R 17,235.00
     * Expected Tax Payable: R 0.00 (Tax is fully covered by Primary Rebate)
     */
    @Test
    void testCase1_belowTaxThreshold() {
        // GAI: 90000, Age: 40, TRC: 0
        TaxInput input = new TaxInput(
                new BigDecimal("90000.00"),
                40,
                BigDecimal.ZERO
        );

        TaxResult result = taxService.calculateTax(input);

        // Verification checks
        assertEquals(90000.00, result.taxableIncome().doubleValue(), "Taxable Income should be 90,000.00");

        // Expected Tax Before Rebates: 16200.00
        assertEquals(new BigDecimal("16200.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxBeforeRebates(), "Tax Before Rebates should be 16,200.00 (18%)");

        // Expected Rebates: 17235.00
        assertEquals(new BigDecimal("17235.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.totalRebates(), "Total Rebates should be 17,235.00 (Primary)");

        // Expected Tax Payable: 0.00
        assertEquals(BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP),
                result.taxPayable(), "Final Tax Payable should be 0.00");

        assertEquals(18.0, result.marginalTaxRate(), "Marginal Rate should be 18%");
        assertTrue(result.taxClassification().startsWith("18%"), "Classification check failed");
    }


    /**
     * Test Case 2: Middle Income (26% Bracket, No Deductions)
     * Taxable Income: R 352,792.80
     * Expected Base Tax: R 42,678.00 (up to R237,100)
     * Expected Excess Tax: (352792.80 - 237100) * 26% = R 30,080.13
     * Expected Tax Before Rebate: R 72,758.13
     * Expected Tax Payable: 72758.13 - 17235.00 = R 55,523.13
     */
    @Test
    void testCase2_middleIncome26Percent() {
        // GAI: 352792.80, Age: 26, TRC: 0
        TaxInput input = new TaxInput(
                new BigDecimal("352792.80"),
                26,
                BigDecimal.ZERO
        );

        TaxResult result = taxService.calculateTax(input);

        // Verification checks
        assertEquals(new BigDecimal("352792.80").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxableIncome(), "Taxable Income should match GAI");

        // Expected Tax Before Rebates: 72758.13
        assertEquals(new BigDecimal("72758.13").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxBeforeRebates(), "Tax Before Rebates check failed (R42678 + R30080.13)");

        // Expected Tax Payable: 55523.13
        assertEquals(new BigDecimal("55523.13").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxPayable(), "Final Tax Payable check failed");

        assertEquals(26.0, result.marginalTaxRate(), "Marginal Rate should be 26%");
    }


    /**
     * Test Case 3: High Income (39% Bracket) with Full Retirement Deduction
     * GAI: R 800,000.00, TRC: R 200,000.00
     * Max Deduction: Min(200000, 800000 * 27.5%) = R 200,000.00 (actual contribution is lower than cap)
     * Taxable Income: 800000 - 200000 = R 600,000.00
     * Bracket: 512 801 – 673 000 (36%)
     * Expected Tax Before Rebates: 121475 + 36% * (600000 - 512800) = R 121475 + R 31392.00 = R 152,867.00
     * Expected Tax Payable: 152867.00 - 17235.00 = R 135,632.00
     */
    @Test
    void testCase3_highIncomeWithDeduction() {
        // GAI: 800000, Age: 50, TRC: 200000
        TaxInput input = new TaxInput(
                new BigDecimal("800000.00"),
                50,
                new BigDecimal("200000.00")
        );

        TaxResult result = taxService.calculateTax(input);

        // Verification checks
        // Taxable Income: 800000 - 200000 = 600000.00
        assertEquals(new BigDecimal("600000.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxableIncome(), "Taxable Income should be 600,000.00 after deduction");

        // Expected Tax Before Rebates: 152867.00
        assertEquals(new BigDecimal("152867.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxBeforeRebates(), "Tax Before Rebates check failed (36% bracket)");

        // Expected Tax Payable: 135632.00
        assertEquals(new BigDecimal("135632.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxPayable(), "Final Tax Payable check failed");

        assertEquals(36.0, result.marginalTaxRate(), "Marginal Rate should be 36%");
    }

    /**
     * Test Case 4: Senior Taxpayer (Age 75) to Test Tertiary Rebate
     * Taxable Income: R 520,000.00
     * Bracket: 512 801 – 673 000 (36%) -> Note: Since it is R520,000.00 it should fall in 36% bracket
     * Expected Tax Before Rebate (36% bracket): 121475 + 36% * (520000 - 512800) = 121475 + 2592 = R 124067.00
     * Rebates: Primary (17235) + Secondary (9444) + Tertiary (3145) = R 29,824.00
     * Expected Tax Payable: 124067 - 29824.00 = R 94,243.00
     */
    @Test
    void testCase4_seniorTertiaryRebate() {
        // GAI: 520000, Age: 75, TRC: 0
        TaxInput input = new TaxInput(
                new BigDecimal("520000.00"),
                75,
                BigDecimal.ZERO
        );

        TaxResult result = taxService.calculateTax(input);

        // Verification checks
        // Expected Rebates: 17235 + 9444 + 3145 = 29824.00
        assertEquals(new BigDecimal("29824.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.totalRebates(), "Total Rebates should include Primary, Secondary, and Tertiary");

        // Expected Tax Before Rebates: R 124067.00
        assertEquals(new BigDecimal("124067.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxBeforeRebates(), "Tax Before Rebates check failed (31% bracket)");

        // Expected Tax Payable: 94243.00
        assertEquals(new BigDecimal("94243.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxPayable(), "Final Tax Payable check failed");

        assertEquals(36.0, result.marginalTaxRate(), "Marginal Rate should be 31%");
    }

    /**
     * Test Case 5: Maximum Retirement Deduction Cap
     * GAI: R 2,000,000.00, TRC: R 400,000.00
     * 27.5% of GAI: 2000000 * 0.275 = 550,000.00
     * Max Cap: R 350,000.00
     * Actual TRC: R 400,000.00
     * Max Allowable Deduction: Min(400k, 550k, 350k) = R 350,000.00
     * Taxable Income: 2000000 - 350000 = R 1,650,000.00
     */
    @Test
    void testCase5_deductionCap() {
        // GAI: 2000000, Age: 40, TRC: 400000
        TaxInput input = new TaxInput(
                new BigDecimal("2000000.00"),
                40,
                new BigDecimal("400000.00")
        );

        TaxResult result = taxService.calculateTax(input);

        // Verification checks
        // Taxable Income: 2000000 - 350000 (cap) = 1650000.00
        assertEquals(new BigDecimal("1650000.00").setScale(SCALE, RoundingMode.HALF_UP),
                result.taxableIncome(), "Deduction should be capped at R350,000");

        // 1,650,000.00 falls into the 41% bracket
        assertEquals(41.0, result.marginalTaxRate(), "Marginal Rate should be 41%");
    }
}