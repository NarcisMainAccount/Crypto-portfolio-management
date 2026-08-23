package javacode.holding;

import javacode.portfolio.Portfolio;
import javacode.portfolio.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;
    private final PortfolioRepository portfolioRepository;

    public HoldingService(
            HoldingRepository holdingRepository,
            PortfolioRepository portfolioRepository
    ) {
        this.holdingRepository = holdingRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public Holding createHolding(
            UUID portfolioId,
            String userEmail,
            String assetId,
            String symbol,
            String name,
            BigDecimal quantity
    ) {
        Portfolio portfolio = portfolioRepository.findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        OffsetDateTime now = OffsetDateTime.now();

        Holding holding = new Holding(
                UUID.randomUUID(),
                portfolio,
                assetId,
                symbol,
                name,
                quantity,
                now,
                now
        );

        return holdingRepository.save(holding);
    }

    public List<Holding> getHoldings(UUID portfolioId, String userEmail) {

        portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));
        return holdingRepository.findByPortfolioId(portfolioId);
    }


}