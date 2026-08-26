package javacode.holding;

import javacode.exception.ResourceNotFoundException;
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

    public Holding updateHoldingQuantity (
            UUID portfolioId,
            UUID holdingId,
            String userEmail,
            BigDecimal quantity
    ) {
        portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new ResourceNotFoundException("Holding not found"));

        if(!holding.getPortfolio().getId().equals(portfolioId)) {
            throw new ResourceNotFoundException("Holding not found");
        }

        holding.setQuantity(quantity);
        holding.setUpdatedAt(OffsetDateTime.now());

        return holdingRepository.save(holding);
    }

    public void deleteHolding(
            UUID portfolioId,
            UUID holdingId,
            String userEmail
    ) {
        portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new ResourceNotFoundException("Holding not found"));

        if (!holding.getPortfolio().getId().equals(portfolioId)) {
            throw new ResourceNotFoundException("Holding not found");
        }

        holdingRepository.delete(holding);
    }

}