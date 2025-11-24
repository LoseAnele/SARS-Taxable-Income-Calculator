package com.sars.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class TaxConstants {
    public static final List<TaxBracket> TAX_BRACKETS_UNDER_65 = List.of(
            new TaxBracket(new BigDecimal("0.01"), new BigDecimal("237100"), new BigDecimal("0"), 0.18),
            new TaxBracket(new BigDecimal("237101"), new BigDecimal("370500"), new BigDecimal("42678"), 0.26),
            new TaxBracket(new BigDecimal("370501"), new BigDecimal("512800"), new BigDecimal("77362"), 0.31),
            new TaxBracket(new BigDecimal("512801"), new BigDecimal("673000"), new BigDecimal("121475"), 0.36),
            new TaxBracket(new BigDecimal("673001"), new BigDecimal("857900"), new BigDecimal("179147"), 0.39),
            new TaxBracket(new BigDecimal("857901"), new BigDecimal("1817000"), new BigDecimal("251258"), 0.41),
            new TaxBracket(new BigDecimal("1817001"), new BigDecimal("999999999"), new BigDecimal("644489"), 0.45)
    );

    public static final Map<String, BigDecimal> REBATES = Map.of(
            "PRIMARY", new BigDecimal("17235"),
            "SECONDARY", new BigDecimal("9444"),
            "TERTIARY", new BigDecimal("3145")
    );

    public static final BigDecimal RETIREMENT_DEDUCTION_CAP = new BigDecimal("350000");
    public static final double RETIREMENT_DEDUCTION_PERCENT = 0.275;

    private TaxConstants() {}
}