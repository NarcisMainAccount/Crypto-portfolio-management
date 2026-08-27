package javacode.market;

import java.math.BigDecimal;
public interface MarketDataProvider {

    BigDecimal getPrice(String assetId, String currency);
}
