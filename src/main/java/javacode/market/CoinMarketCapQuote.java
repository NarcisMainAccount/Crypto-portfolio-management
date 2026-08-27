package javacode.market;

import java.math.BigDecimal;

public record CoinMarketCapQuote(
        String symbol,
        BigDecimal price
) {}
