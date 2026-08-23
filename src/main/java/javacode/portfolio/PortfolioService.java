package javacode.portfolio;

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
}
