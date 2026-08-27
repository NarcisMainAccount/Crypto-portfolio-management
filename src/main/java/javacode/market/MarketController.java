package javacode.market;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketDataProvider marketDataProvider;

    public MarketController(MarketDataProvider marketDataProvider) {
        this.marketDataProvider = marketDataProvider;
    }

    @GetMapping("/price/{assetId}")
    public BigDecimal getPrice(
            @PathVariable String assetId,
            @RequestParam(defaultValue = "EUR") String currency
    ) {
        return marketDataProvider.getPrice(assetId, currency);
    }
}
