package com.archtiger.exam.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "GAME_INVENTORY")
public class GameInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long Id;

    @Column(name = "GAME_ID", nullable = false, updatable = false)
    private Long gameId;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    // To use optimistic lock
    @Version
    @Column(name = "VERSION")
    private long version;

    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private LocalDateTime updateDateTime;

    @Column(name = "REGISTER_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime registerDateTime;

}
