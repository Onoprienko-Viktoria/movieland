package com.onoprienko.movieland.repository.jpa;

import com.onoprienko.movieland.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaCountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByIdIn(Collection<Integer> ids);
}
