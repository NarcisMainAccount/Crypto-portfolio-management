package javacode.portfolio;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioSummary(
        String currency,
        BigDecimal totalValue,
        List<HoldingValuation> holdings
) {


}

