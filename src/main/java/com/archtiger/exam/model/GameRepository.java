package com.archtiger.exam.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findAllByReleaseDateBetween(final LocalDate start, final LocalDate end, Pageable pageable);

    Page<Game> findAllByPlatform(Platform platform, Pageable pageable);

    Page<Game> findAllByTitle(String title, Pageable pageable);

    Page<Game> findAllByPlatformAndTitle(Platform platform, String title, Pageable pageable);

}
