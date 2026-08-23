package javacode.market;

import java.util.List;

public record CoinMarketCapAsset(
        long id,
        String name,
        String symbol,
        List<CoinMarketCapQuote> quote
) {
}
