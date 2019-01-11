package com.archtiger.exam.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByPlatformEqualsOrderByReleaseDateTimeDesc(final Platform platform);

}
