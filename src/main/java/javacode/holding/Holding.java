package javacode.holding;

import jakarta.persistence.*;
import javacode.portfolio.Portfolio;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "holdings")
public class Holding {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(name = "asset_id", nullable = false, length = 100)
    private String assetId;

    @Column(nullable = false, length = 20)
    private String symbol;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 30, scale = 12)
    private BigDecimal quantity;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Holding() {
    }

    public Holding(
            UUID id,
            Portfolio portfolio,
            String assetId,
            String symbol,
            String name,
            BigDecimal quantity,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.portfolio = portfolio;
        this.assetId = assetId;
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
