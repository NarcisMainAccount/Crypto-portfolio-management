package javacode.holding;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios/{portfolioId}/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createHolding(
            @PathVariable UUID portfolioId,
            @Valid @RequestBody CreateHoldingRequest request,
            Authentication authentication
    ) {
        holdingService.createHolding(
                portfolioId,
                authentication.getName(),
                request.assetId(),
                request.symbol(),
                request.name(),
                request.quantity()
        );
    }

    @GetMapping
    public List<Holding> getHoldings(
            @PathVariable UUID portfolioId,
            Authentication authentication
    ) {
        return holdingService.getHoldings(
                portfolioId,
                authentication.getName()
        );
    }
}
