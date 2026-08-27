package javacode.portfolio;

import java.math.BigDecimal;

public record HoldingValuation(
        String symbol,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal value
) {
}
