package com.example.review.service;

import com.example.review.domain.Product;
import com.example.review.domain.Review;
import com.example.review.domain.User;
import com.example.review.domain.dto.ReviewAddRequestDto;
import com.example.review.domain.dto.ReviewResponseDto;
import com.example.review.domain.dto.ReviewStatsDto;
import com.example.review.exception.ReviewRegistrationException;
import com.example.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    private final ReviewRepository reviewRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Cacheable(cacheNames = "getReviews", key = "'reviews:page:' + (#cursor != null ? #cursor : 'null') + ':size:' + #size", cacheManager = "reviewCacheManager")
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size 는 0보다 커야 합니다");
        }
        // 커서 기반 리뷰 조회
        List<Review> reviews = reviewRepository.findByProductId(productId, cursor, PageRequest.of(0, size));
        log.info("reviews = {}", reviews);

        if (reviews.isEmpty()) {
            return new ReviewResponseDto(0, 0.0, null, List.of());
        }

        ReviewStatsDto reviewStats  = calReviewRateStatus(productId);
        int            totalCount   = (int) reviewStats.getTotalCount(); // 총 리뷰 수
        double         averageScore = roundToOneDecimal(reviewStats.getAverageScore());

        // DTO 변환
        List<ReviewResponseDto.ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewResponseDto.ReviewDto(review.getId(), review.getUser()
                        .getId(), review.getScore(), review.getContent(), review.getImageUrl(), review.getCreatedAt()))
                .toList();

        // 다음 커서 설정
        Long nextCursor = reviews.get(reviews.size() - 1)
                .getId();

        return new ReviewResponseDto(totalCount, averageScore, nextCursor, reviewDtos);
    }

    private double roundToOneDecimal(double value) {
        return Math.floor(value * 10) / 10;
    }

    // 총 리뷰 수와 평균 점수 조회
    public ReviewStatsDto calReviewRateStatus(Long productId) {
        return reviewRepository.findReviewCountAndAverageScoreByProductId(productId);
    }

    @Transactional
    public void addReview(Product product, User user, ReviewAddRequestDto requestDto) {
        if (reviewRepository.existsByProductAndUser(product, user)) {
            throw new ReviewRegistrationException("이미 작성된 리뷰가 있습니다.");
        }
        try {
            // 중복 리뷰 체크 로직 추가 가능
            Review review = new Review(product, user, requestDto.getScore(), requestDto.getContent());
            reviewRepository.save(review);
        } catch (Exception e) {
            log.error("리뷰 등록 실패 : {}", e.getMessage());
            throw new ReviewRegistrationException(e); // 예외 이름 변경
        }
    }

    @Transactional
    public void addReviewWithFileName(Product product, User user, ReviewAddRequestDto requestDto, MultipartFile multipartFile) {
        if (reviewRepository.existsByProductAndUser(product, user)) {
            throw new ReviewRegistrationException("이미 작성된 리뷰가 있습니다.");
        }
        try {
            String storedFile = storeFile(multipartFile);
            Review review     = new Review(product, user, requestDto.getScore(), requestDto.getContent(), storedFile);
            reviewRepository.save(review);
        } catch (Exception e) {
            log.error("파일 리뷰 등록 실패: {}", e.getMessage());
            throw new ReviewRegistrationException("리뷰 등록에 실패했습니다.");
        }
    }


    private String storeFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName    = createStoreFileName(originalFilename);
        File   fileToStore      = new File(getFullPath(storeFileName));

        try {
            multipartFile.transferTo(fileToStore);
        } catch (IOException e) {
            log.error("파일 저장 실패: {}", e.getMessage());
            throw new IOException("파일 저장 중 오류가 발생했습니다.");
        }
        return storeFileName;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID()
                .toString();
        // UUID로 파일 이름 생성하여 덮어쓰기 방지
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        // 확장자 없는 경우 처리
        return (pos == -1) ? "" : originalFilename.substring(pos + 1);
    }
}

