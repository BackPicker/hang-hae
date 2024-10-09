package com.example.review.service;

import com.example.review.domain.Product;
import com.example.review.domain.Review;
import com.example.review.domain.User;
import com.example.review.domain.dto.ReviewAddRequestDto;
import com.example.review.domain.dto.ReviewResponseDto;
import com.example.review.domain.dto.ReviewStatsDto;
import com.example.review.exception.ReviewNotFoundException;
import com.example.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository            reviewRepository;
    private final ValidationAutoConfiguration validationAutoConfiguration;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public ReviewResponseDto getReviews(Long productId, Integer cursor, Integer size) {
        int pageSize   = size != null ? size : 10;
        int pageNumber = cursor != null ? cursor / pageSize : 0;

        // 리뷰 조회
        List<Review> reviews = reviewRepository.findByProductId(productId, PageRequest.of(pageNumber, pageSize));
        log.info("reviews = {}", reviews);

        ReviewStatsDto reviewStats = calReviewRateStatus(productId);

        int    totalCount   = (int) reviewStats.getTotalCount(); // 총 리뷰 수
        double averageScore = roundToOneDecimal(reviewStats.getAverageScore());

        // 커서 계산
        int nextCursor = (pageNumber + 1) * pageSize < totalCount ? (pageNumber + 1) * pageSize : -1;

        // DTO 변환
        List<ReviewResponseDto.ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewResponseDto.ReviewDto(review.getId(), review.getUser()
                        .getId(), review.getScore(), review.getContent(), review.getImageUrl(), review.getCreatedAt()))
                .toList();

        return new ReviewResponseDto(totalCount, averageScore, nextCursor, reviewDtos);
    }

    private double roundToOneDecimal(double value) {
        return Math.floor(value * 10) / 10;
    }

    public ReviewStatsDto calReviewRateStatus(Long productId) {
        // 총 리뷰 수와 평균 점수 조회
        ReviewStatsDto reviewStats = reviewRepository.findReviewCountAndAverageScoreByProductId(productId);
        return reviewStats;
    }

    @Transactional
    public void addReview(Product product, User user, ReviewAddRequestDto requestDto) {
        try {
            Review review = new Review(product, user, requestDto.getScore(), requestDto.getContent());
            reviewRepository.save(review);
        } catch (Exception e) {
            log.error("리뷰 등록 실패 : {}", e.getMessage());
            throw new ReviewNotFoundException(e);
        }
    }

    @Transactional
    public void addReviewWithFileName(Product product, User user, ReviewAddRequestDto requestDto, MultipartFile multipartFile) {
        try {
            String storedFile = storeFile(multipartFile);
            Review review     = new Review(product, user, requestDto.getScore(), requestDto.getContent(), storedFile);
            reviewRepository.save(review);
        } catch (Exception e) {
            log.error("파일 리뷰 등록 실패 : {}", e.getMessage());
            throw new ReviewNotFoundException(e);
        }
    }

    private String storeFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName    = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID()
                .toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

