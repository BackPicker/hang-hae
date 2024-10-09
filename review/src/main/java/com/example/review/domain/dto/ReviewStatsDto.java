package com.example.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewStatsDto {
    private long   totalCount; // 총 리뷰 수
    private double averageScore; // 평균 점수
}