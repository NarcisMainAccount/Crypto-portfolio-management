package javacode.market;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CoinMarketCapMarketDataProvider implements MarketDataProvider {

    private final RestClient restClient;
    private final String apiKey;

    public CoinMarketCapMarketDataProvider(
            @Value("${coinmarketcap.api.url}") String apiUrl,
            @Value("${coinmarketcap.api.key}") String apiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();

        this.apiKey = apiKey;
    }

    @Override
    public BigDecimal getPrice(String assetId, String currency) {

        CoinMarketCapResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/cryptocurrency/quotes/latest")
                        .queryParam("id", assetId)
                        .queryParam("convert", currency)
                        .build())
                .header("X-CMC_PRO_API_KEY", apiKey)
                .retrieve()
                .body(CoinMarketCapResponse.class);

        if (response == null || response.data().isEmpty()) {
            throw new IllegalStateException("No market data returned");
        }

        CoinMarketCapAsset asset = response.data().getFirst();

        return asset.quote().stream()
                .filter(quote -> quote.symbol().equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Currency quote not found"))
                .price();
    }

}
