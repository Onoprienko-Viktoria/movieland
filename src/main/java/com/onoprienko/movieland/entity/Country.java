package com.onoprienko.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Country {
    @Id
    @SequenceGenerator(name = "country_sequence",
            sequenceName = "country_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "country_sequence")
    private Integer id;
    private String name;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "country_movies",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "movies_id"))
    private List<Movie> movies = new ArrayList<>();

}
