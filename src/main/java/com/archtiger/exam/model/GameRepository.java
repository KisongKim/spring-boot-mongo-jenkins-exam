package com.archtiger.exam.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByPlatformEqualsOrderByReleaseDateDesc(final Platform platform);

    List<Game> findAllByTitleOrderByReleaseDateDesc(final String title);

    List<Game> findAllByReleaseDateBetweenOrderByReleaseDateDesc(final LocalDate start, final LocalDate end);

}
