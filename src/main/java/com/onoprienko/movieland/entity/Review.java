package com.onoprienko.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Review {
    @Id
    @SequenceGenerator(name = "review_sequence",
            sequenceName = "review_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "review_sequence")
    private Long id;
    private String text;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
