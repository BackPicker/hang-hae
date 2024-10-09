package com.example.review.domain.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class ReviewAddRequestDto {
    private Long    userId;
    private Integer score;
    private String  content;

    public ReviewAddRequestDto(Long userId, Integer score, String content) {
        this.userId  = userId;
        this.score   = score;
        this.content = content;
    }
}
