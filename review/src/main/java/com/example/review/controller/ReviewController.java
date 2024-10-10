package com.example.review.controller;

import com.example.review.domain.Product;
import com.example.review.domain.User;
import com.example.review.domain.dto.ReviewAddRequestDto;
import com.example.review.domain.dto.ReviewResponseDto;
import com.example.review.domain.dto.ReviewStatsDto;
import com.example.review.service.ProductService;
import com.example.review.service.ReviewService;
import com.example.review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ProductService productService;
    private final ReviewService  reviewService;
    private final UserService    userService;

    @ResponseBody
    @GetMapping("/products/{productId}/reviews")
    public ReviewResponseDto getReviews(@PathVariable Long productId, @RequestParam(required = false) Long cursor, @RequestParam(required = false) int size) {

        return reviewService.getReviews(productId, cursor, size);
    }

    @PostMapping("/products/{productId}/reviews")
    public void addReview(@PathVariable Long productId, @RequestPart(required = false, value = "imageUrl") MultipartFile imageUrl,
                          @Validated @RequestPart(value = "requestDto") ReviewAddRequestDto requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult.getFieldError());
        }

        // Product 하나 가져옴
        Product product = productService.getSingleProduct(productId);
        // User도 하나 가져옴
        User user = userService.getSingleUser(requestDto.getUserId());

        // 파일 구분
        if (imageUrl != null && !imageUrl.isEmpty()) {
            reviewService.addReviewWithFileName(product, user, requestDto, imageUrl);
        } else {
            reviewService.addReview(product, user, requestDto);
        }


        // result 값으로 상품 product 의 count / score 수정
        ReviewStatsDto reviewStatsDto = reviewService.calReviewRateStatus(productId);
        productService.updateProductRating(productId, reviewStatsDto.getTotalCount(), reviewStatsDto.getAverageScore());
    }

}