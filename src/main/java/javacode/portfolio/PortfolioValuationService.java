package javacode.portfolio;


import javacode.holding.Holding;
import javacode.holding.HoldingRepository;
import javacode.market.MarketDataProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PortfolioValuationService {

    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final MarketDataProvider marketDataProvider;

    public PortfolioValuationService(
            PortfolioRepository portfolioRepository,
            HoldingRepository holdingRepository,
            MarketDataProvider marketDataProvider
    ) {
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
        this.marketDataProvider = marketDataProvider;
    }

    public BigDecimal getPortfolioValue(
            UUID portfolioId,
            String userEmail,
            String currnecy
    ) {
        portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new IllegalThreadStateException("Portfolio not found!"));

        List<Holding> holdings =
                holdingRepository.findByPortfolioId(portfolioId);

        return holdings.stream()
                .map(holding -> {
                    BigDecimal price = marketDataProvider.getPrice(
                            holding.getAssetId(),
                            currnecy
                    );

                    return holding.getQuantity().multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}























