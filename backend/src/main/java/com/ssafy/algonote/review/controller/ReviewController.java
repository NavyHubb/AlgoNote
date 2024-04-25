package com.ssafy.algonote.review.controller;

import com.ssafy.algonote.review.dto.request.ReviewReqDto;
import com.ssafy.algonote.review.dto.request.ReviewUpdateReqDto;
import com.ssafy.algonote.review.dto.response.ReviewResDto;
import com.ssafy.algonote.review.service.ReviewService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping("/notes/{noteId}/reviews")
    public ResponseEntity<Void> create(@PathVariable Long noteId, @RequestBody ReviewReqDto req) {
        reviewService.create(req, noteId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/notes/{noteId}/reviews")
    public ResponseEntity<List<ReviewResDto>> readList(@PathVariable Long noteId) {
        return ResponseEntity.ok(reviewService.readList(noteId));
    }

    @PatchMapping("/notes/{noteId}/reviews/{reviewId}")
    public ResponseEntity<Void> create(
        @RequestBody ReviewUpdateReqDto req, @PathVariable Long noteId, @PathVariable Long reviewId) {
        reviewService.update(req, noteId, reviewId);
        return ResponseEntity.ok().build();
    }

}
