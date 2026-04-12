package com.excellia.wallet.controller;

import com.excellia.wallet.dto.RecommendationDto;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.RecommendationService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final UserService userService;

    public RecommendationController(RecommendationService recommendationService, UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<RecommendationDto> getRecommendations() {
        return recommendationService.getUserRecommendations(getCurrentUser())
                .stream().map(r -> {
                    RecommendationDto dto = new RecommendationDto();
                    dto.setId(r.getId());
                    dto.setType(r.getType());
                    dto.setTitle(r.getTitle());
                    dto.setMessage(r.getMessage());
                    dto.setPotentialSavings(r.getPotentialSavings());
                    dto.setPriority(r.getPriority());
                    dto.setStatus(r.getStatus());
                    dto.setCreatedAt(r.getCreatedAt());
                    return dto;
                }).collect(Collectors.toList());
    }

    @PutMapping("/{id}/apply")
    public void applyRecommendation(@PathVariable String id) {
        recommendationService.markAsApplied(id, getCurrentUser());
    }

    @PutMapping("/{id}/dismiss")
    public void dismissRecommendation(@PathVariable String id) {
        recommendationService.dismiss(id, getCurrentUser());
    }
}