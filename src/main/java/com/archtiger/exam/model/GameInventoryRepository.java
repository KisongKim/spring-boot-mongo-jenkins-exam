package com.archtiger.exam.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameInventoryRepository extends JpaRepository<GameInventory, Long>, GameInventoryRepositoryCustom {

    Optional<GameInventory> findByGameId(final Long gameId);

}
