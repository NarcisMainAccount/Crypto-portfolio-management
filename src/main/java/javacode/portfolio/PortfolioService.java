package javacode.portfolio;

import javacode.exception.ResourceNotFoundException;
import javacode.user.User;
import javacode.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public PortfolioService(
            PortfolioRepository portfolioRepository,
            UserRepository userRepository
    ) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    public Portfolio createPortfolio(String userEmail, String name){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OffsetDateTime now = OffsetDateTime.now();

        Portfolio portfolio = new Portfolio(
                UUID.randomUUID(),
                user,
                name,
                now,
                now
        );

        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getPortfolios(String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return portfolioRepository.findByUserId(user.getId());
    }

    public Portfolio updatePortfolioName(
            UUID portfolioId,
            String userEmail,
            String name
    ) {
        Portfolio portfolio = portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        portfolio.setName(name);
        portfolio.setUpdatedAt(OffsetDateTime.now());

        return portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(
            UUID portfolioId,
            String userEmail
    ) {
        Portfolio portfolio = portfolioRepository
                .findByIdAndUserEmail(portfolioId, userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        portfolioRepository.delete(portfolio);
    }
}
