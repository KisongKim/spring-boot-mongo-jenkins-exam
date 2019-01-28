package com.archtiger.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long Id;

    @Column(name = "PLATFORM", length = 32, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "RATING", length = 16, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(name = "PUBLISHER", length = 256, nullable = false, updatable = false)
    private String publisher;

    @Column(name = "TITLE", length = 256, nullable = false, updatable = false)
    private String title;

    @Column(name = "RELEASE_DATE", nullable = false, updatable = false)
    private LocalDate releaseDate;

    @Column(name = "REGISTER_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime registerDateTime;

}
