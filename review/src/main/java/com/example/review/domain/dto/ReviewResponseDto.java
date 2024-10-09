package com.example.review.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class ReviewResponseDto {
    private int             totalCount; // 총 리뷰 수
    private double          score;    // 평균 점수
    private int             cursor;      // 다음 커서
    private List<ReviewDto> reviews; // 리뷰 목록

    @Data
    @Setter(AccessLevel.NONE)
    @AllArgsConstructor
    public static class ReviewDto {
        private Long          id;           // 리뷰 ID
        private Long          userId;       // 작성자 유저 ID
        private int           score;         // 리뷰 점수
        private String        content;     // 리뷰 내용
        private String        imageUrl;    // 이미지 URL
        @CreatedDate
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime createdAt; // 생성 날짜
    }
}