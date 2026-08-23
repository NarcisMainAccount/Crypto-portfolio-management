package javacode.holding;

import java.math.BigDecimal;

public record CreateHoldingRequest(
        String assetId,
        String symbol,
        String name,
        BigDecimal quantity
) {}