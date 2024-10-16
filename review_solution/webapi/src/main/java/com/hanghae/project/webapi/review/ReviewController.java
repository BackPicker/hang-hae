package com.hanghae.project.webapi.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.project.application.review.ReviewApplicationService;
import com.hanghae.project.application.review.dto.GetReviewsResponse;
import com.hanghae.project.domain.image.FileInfo;
import com.hanghae.project.webapi.common.MultipartUtil;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/products/{productId}/reviews")
public class ReviewController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ReviewApplicationService service;

    ReviewController(ReviewApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public void review(
        @PathVariable("productId") long productId,
        @RequestPart MultipartFile image,
        @RequestPart @NotNull String request
    ) throws IOException {
        // 이미지 확장자 검사
        MultipartUtil.validateImageFile(image);

        SaveReviewRequest saveReviewRequest = objectMapper.convertValue(request, SaveReviewRequest.class);
        FileInfo fileInfo = toFileInfo(image);
        service.add(productId, saveReviewRequest.userId, saveReviewRequest.score, saveReviewRequest.content, fileInfo);
    }

    @GetMapping
    public GetReviewsResponse getReviews(
        @PathVariable("productId") long productId,
        @RequestParam(required = false) String cursor,
        @RequestParam(defaultValue = "10") int size
    ) {
        return service.getReviews(productId, size, cursor);
    }

    public record SaveReviewRequest(
        long userId,
        int score,
        @NotNull String content
    ) {}

    @Nullable
    private FileInfo toFileInfo(@Nullable MultipartFile file) throws IOException {
        if (file == null) return null;
        return new FileInfo(
            Objects.requireNonNull(file.getOriginalFilename()),
            MultipartUtil.toByteArray(file)
        );
    }
}
