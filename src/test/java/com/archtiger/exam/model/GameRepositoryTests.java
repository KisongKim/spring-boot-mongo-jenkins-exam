package com.archtiger.exam.model;

import com.archtiger.exam.GamePopulator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Before
    public void populate() {
        gameRepository.saveAll(GamePopulator.populate());
    }

    @After
    public void cleanUp() {
        gameRepository.deleteAll();
    }

    @Test
    public void findAllByPlatformEqualsOrderByReleaseDateTimeDesc_ExpectMultipleItems() {
        List<Game> results = gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(Platform.PS_4);
        Assert.assertTrue(results.size() == 2);
        results.forEach(i -> System.out.println(i.toString()));
    }

    @Test
    public void findAllByPlatformEqualsOrderByReleaseDateTimeDesc_ExpectOneItem() {
        List<Game> results = gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(Platform.SWITCH);
        Assert.assertTrue(results.size() == 1);
        results.forEach(i -> System.out.println(i.toString()));
    }

    @Test
    public void findAllByPlatformEqualsOrderByReleaseDateTimeDesc_ExpectNoItem() {
        List<Game> results = gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(Platform.PC);
        Assert.assertTrue(results.size() == 0);
    }

    @Test
    public void findAllByTitleOrderByReleaseDateTimeDesc_ExpectMultipleResults() {
        List<Game> results = gameRepository.findAllByTitleOrderByReleaseDateDesc("Destiny 2");
        Assert.assertTrue(results.size() == 2);
        results.forEach(i -> System.out.println(i.toString()));
    }

    @Test
    public void findAllByTitleOrderByReleaseDateTimeDesc_ExpectOneResult() {
        List<Game> results = gameRepository.findAllByTitleOrderByReleaseDateDesc("Super Mario");
        Assert.assertTrue(results.size() == 1);
        results.forEach(i -> System.out.println(i.toString()));
    }

    @Test
    public void findAllByTitleOrderByReleaseDateTimeDesc_ExpectNoItem() {
        List<Game> results = gameRepository.findAllByTitleOrderByReleaseDateDesc("Wonder Boy");
        Assert.assertTrue(results.size() == 0);
    }

    @Test
    public void findAllByReleaseDateBetweenOrderByReleaseDateDesc_ExpectMultipleItems() {
        LocalDate start = LocalDate.now().minusYears(2);
        LocalDate end = LocalDate.now();
        List<Game> results = gameRepository.findAllByReleaseDateBetweenOrderByReleaseDateDesc(start, end);
        Assert.assertTrue(results.size() == 4);
        results.forEach(i -> System.out.println(i.toString()));
    }

    @Test
    public void findAllByReleaseDateBetweenOrderByReleaseDateDesc_ExpectOneItem() {
        LocalDate start = LocalDate.now().minusWeeks(2);
        LocalDate end = LocalDate.now();
        List<Game> results = gameRepository.findAllByReleaseDateBetweenOrderByReleaseDateDesc(start, end);
        Assert.assertTrue(results.size() == 1);
        results.forEach(i -> System.out.println(i.toString()));
    }

}
