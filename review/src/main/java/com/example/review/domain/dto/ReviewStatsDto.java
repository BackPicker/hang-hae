package com.example.review.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewStatsDto {
    private long   totalCount; // 총 리뷰 수
    private double averageScore; // 평균 점수
}