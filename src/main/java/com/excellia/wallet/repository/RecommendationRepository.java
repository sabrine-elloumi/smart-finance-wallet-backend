package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Recommendation;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    List<Recommendation> findByUserOrderByCreatedAtDesc(User user);
}
