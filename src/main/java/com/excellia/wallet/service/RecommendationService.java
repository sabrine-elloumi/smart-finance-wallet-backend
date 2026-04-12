package com.excellia.wallet.service;

import com.excellia.wallet.entity.Recommendation;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.RecommendationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public List<Recommendation> getUserRecommendations(User user) {
        return recommendationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public void markAsApplied(String id, User user) {
        Recommendation rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found"));
        if (rec.getUser().getId().equals(user.getId())) {
            rec.setStatus("APPLIED");
            recommendationRepository.save(rec);
        }
    }

    public void dismiss(String id, User user) {
        Recommendation rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found"));
        if (rec.getUser().getId().equals(user.getId())) {
            rec.setStatus("DISMISSED");
            recommendationRepository.save(rec);
        }
    }
}