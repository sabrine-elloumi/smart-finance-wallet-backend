package com.excellia.wallet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
public class AiClientService {

    @Value("${ai.service.url:http://localhost:5000}")
    private String aiServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Version réelle (à décommenter quand le service Python sera prêt)
    /*
    public AiResponse categorize(String description, BigDecimal amount, String userId) {
        String url = aiServiceUrl + "/api/ai/categorize";
        AiRequest request = new AiRequest(description, amount, userId);
        return restTemplate.postForObject(url, request, AiResponse.class);
    }
    */

    // Simulation pour les tests (catégorisation basique)
    public AiResponse simulateCategorize(String description) {
        AiResponse response = new AiResponse();
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("carrefour") || lowerDesc.contains("monoprix") ||
            lowerDesc.contains("magasin") || lowerDesc.contains("boulangerie")) {
            response.setCategory("Alimentation");
            response.setConfidence(0.95);
        } else if (lowerDesc.contains("uber") || lowerDesc.contains("taxi") ||
                   lowerDesc.contains("essence") || lowerDesc.contains("station")) {
            response.setCategory("Transport");
            response.setConfidence(0.92);
        } else if (lowerDesc.contains("netflix") || lowerDesc.contains("spotify") ||
                   lowerDesc.contains("cinema")) {
            response.setCategory("Loisirs");
            response.setConfidence(0.98);
        } else if (lowerDesc.contains("pharmacie") || lowerDesc.contains("clinique") ||
                   lowerDesc.contains("medecin")) {
            response.setCategory("Santé");
            response.setConfidence(0.93);
        } else if (lowerDesc.contains("steg") || lowerDesc.contains("sonede") ||
                   lowerDesc.contains("orange") || lowerDesc.contains("ooredoo")) {
            response.setCategory("Factures");
            response.setConfidence(0.97);
        } else if (lowerDesc.contains("salaire") || lowerDesc.contains("virement")) {
            response.setCategory("Revenu");
            response.setConfidence(0.99);
        } else {
            response.setCategory("Autre");
            response.setConfidence(0.70);
        }
        return response;
    }

    // DTOs internes
    public static class AiRequest {
        private String description;
        private BigDecimal amount;
        private String userId;
        public AiRequest(String description, BigDecimal amount, String userId) {
            this.description = description;
            this.amount = amount;
            this.userId = userId;
        }
        // getters et setters (optionnels pour la sérialisation Jackson)
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }

    public static class AiResponse {
        private String category;
        private Double confidence;
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public Double getConfidence() { return confidence; }
        public void setConfidence(Double confidence) { this.confidence = confidence; }
    }
}
