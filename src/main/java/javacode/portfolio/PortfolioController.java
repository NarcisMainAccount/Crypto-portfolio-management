package javacode.portfolio;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios")

public class PortfolioController {
    private final PortfolioService portfolioService;
    private final PortfolioValuationService portfolioValuationService;

    public PortfolioController(
            PortfolioService portfolioService,
            PortfolioValuationService portfolioValuationService
    ) {
        this.portfolioService = portfolioService;
        this.portfolioValuationService = portfolioValuationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPortfolio(
            @RequestBody CreatePortfolioRequest request,
            Authentication authentication
    ) {
        portfolioService.createPortfolio(
                authentication.getName(),
                request.name()
        );
    }

    @GetMapping
    public List<Portfolio> getPortfolios(Authentication authentication) {
        return portfolioService.getPortfolios(authentication.getName());
    }

    @GetMapping("/{portfolioId}/summary")
    public PortfolioSummary getPortfolioSummary(
            @PathVariable UUID portfolioId,
            @RequestParam(defaultValue = "EUR") String currency,
            Authentication authentication
    ) {
        return portfolioValuationService.getPortfolioSummary(
                portfolioId,
                authentication.getName(),
                currency
        );
    }
}
