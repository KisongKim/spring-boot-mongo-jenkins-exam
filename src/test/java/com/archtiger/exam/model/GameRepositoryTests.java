package com.archtiger.exam.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Before
    public void populate() {
        Game g1 = new Game(null, Platform.PS_4, Rating.PG_18, "Activision", "Destiny 2", LocalDateTime.now(), LocalDateTime.now());
        Game g2 = new Game(null, Platform.XBOX_ONE, Rating.PG_18, "Activision", "Destiny 2", LocalDateTime.now(), LocalDateTime.now());
        Game g3 = new Game(null, Platform.SWITCH, Rating.PG_18, "Nintendo", "Super Mario", LocalDateTime.now(), LocalDateTime.now());
        Game g4 = new Game(null, Platform.PS_4, Rating.PG_12, "Sony", "Horizon Zero Dawn", LocalDateTime.now(), LocalDateTime.now());
        gameRepository.saveAll(Arrays.asList(g1, g2, g3, g4));
    }

    @Test
    public void findAllByPlatformEqualsOrderByReleaseDateTimeDesc() {
        List<Game> results = gameRepository.findAllByPlatformEqualsOrderByReleaseDateTimeDesc(Platform.PS_4);
        Assert.assertTrue(results.size() == 2);
        results.forEach(i -> System.out.println(i.toString()));
    }

}
