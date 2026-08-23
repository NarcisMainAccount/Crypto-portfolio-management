CREATE TABLE holdings (
    id           UUID PRIMARY KEY,
    portfolio_id UUID                     NOT NULL,
    asset_id     VARCHAR(100)             NOT NULL,
    symbol       VARCHAR(20)              NOT NULL,
    name         VARCHAR(100)             NOT NULL,
    quantity     NUMERIC(30, 12)          NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_holdings_portfolio
        FOREIGN KEY (portfolio_id)
            REFERENCES portfolios (id)
            ON DELETE CASCADE
);