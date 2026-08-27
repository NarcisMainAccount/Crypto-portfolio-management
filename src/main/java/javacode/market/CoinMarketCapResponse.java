package javacode.market;

import java.util.List;

public record CoinMarketCapResponse(
        List<CoinMarketCapAsset> data
) {
}
