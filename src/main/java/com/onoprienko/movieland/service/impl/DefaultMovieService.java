package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.common.MoviesRequest;
import com.onoprienko.movieland.common.SortDirectionEnum;
import com.onoprienko.movieland.common.SortTypeEnum;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.mapper.MovieMapper;
import com.onoprienko.movieland.repository.jpa.JpaMovieRepository;
import com.onoprienko.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final JpaMovieRepository jpaMovieRepository;
    private final MovieMapper movieMapper;

    private int defaultPageSize;

    @Override
    public List<MovieDto> findAll(MoviesRequest request) {
        Pageable pageable = getPageableForMovie(request);
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findAll(pageable).getContent());
    }

    @Override
    public List<MovieDto> findByGenre(MoviesRequest request) {
        Pageable pageable = getPageableForMovie(request);
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findByGenreId(request.getGenreId(), pageable));
    }

    @Override
    public List<MovieDto> findRandom() {
        return movieMapper.mapToMovieDtoList(jpaMovieRepository.findRandomMovies());
    }


    private Pageable getPageableForMovie(MoviesRequest request) {
        if (request.getPriceDirection() != null || request.getRatingDirection() != null) {
            return PageRequest.of(request.getPage(),
                    defaultPageSize,
                    getSortForMovie(request));
        }
        return PageRequest.of(request.getPage(), defaultPageSize);
    }

    private Sort getSortForMovie(MoviesRequest request) {
        Sort sort = null;
        if (Objects.equals(request.getRatingDirection(), SortDirectionEnum.ASC)) {
            sort = Sort.by(SortTypeEnum.RATING.name().toLowerCase()).ascending();
        } else if (Objects.equals(request.getPriceDirection(), SortDirectionEnum.ASC)) {
            sort = Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).ascending();
        } else if (Objects.equals(request.getPriceDirection(), SortDirectionEnum.DESC)) {
            sort = Sort.by(SortTypeEnum.PRICE.name().toLowerCase()).descending();
        }
        return sort;
    }

    @Value("${movie.page.size}")
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
