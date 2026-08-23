package javacode.portfolio;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")

public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
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
}
