package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.dto.ReviewDTO;
import com.example.demo.service.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReviewService implements CrudService<ReviewDTO, Long>{

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;


    @Override
    public ReviewDTO getById(Long id) {
        log.debug("Fetching review with id: {}", id);
        return reviewRepository.findById(id)
                .map(reviewMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    @Override
    public List<ReviewDTO> getAll() {
        log.debug("Fetching all reviews");
        return reviewMapper.toDTO(reviewRepository.findAll());
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Saving review: {}", reviewDTO);

        if (reviewDTO == null) {
            throw new IllegalArgumentException("ReviewDTO cannot be null");
        }

        var savedReview = reviewRepository.save(reviewMapper.toEntity(reviewDTO));
        return reviewMapper.toDTO(savedReview);
    }

    @Override
    public ReviewDTO updateAll(Long id, ReviewDTO reviewDTO) {
        log.debug("Full updating review with id: {}", id);

        if (reviewDTO == null) {
            throw new IllegalArgumentException("ReviewDTO cannot be null");
        }

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        reviewMapper.fullUpdate(reviewDTO, review);
        Review updatedReview = reviewRepository.save(review);

        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    public ReviewDTO update(Long id, ReviewDTO reviewDTO) {
        log.debug("Partial updating review with id: {}", id);

        if (reviewDTO == null) {
            throw new IllegalArgumentException("ReviewDTO cannot be null");
        }

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        reviewMapper.partialUpdate(reviewDTO, review);
        Review updatedReview = reviewRepository.save(review);

        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting review with id: {}", id);

        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
        }

        reviewRepository.deleteById(id);
        log.info("Successfully deleted review with id: {}", id);
    }
}
