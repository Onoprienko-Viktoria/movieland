package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.dto.ReviewDto;
import com.onoprienko.movieland.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    ReviewDto mapToReviewDto(Review review);

    List<ReviewDto> mapToReviewDtoList(List<Review> reviews);
}
