package com.archtiger.exam.model;

import com.archtiger.exam.GamePopulator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void findAllByPlatform_expectTotalPagesIsOne() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("releaseDate").descending());
        Page<Game> page = gameRepository.findAllByPlatform(Platform.PS_4, pageable);
        Assert.assertFalse(page.isEmpty());
        Assert.assertTrue(page.getTotalElements() == 2);
        Assert.assertTrue(page.getTotalPages() == 1);
        List<Game> content = page.getContent();
        Assert.assertTrue(content.size() == 2);
        content.forEach(item -> System.out.println(item));
    }

    @Test
    public void findAllByPlatform_expectTotalPagesIsTwo() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("releaseDate").descending());
        Page<Game> page = gameRepository.findAllByPlatform(Platform.PS_4, pageable);
        Assert.assertFalse(page.isEmpty());
        Assert.assertTrue(page.getTotalElements() == 2);
        Assert.assertTrue(page.getTotalPages() == 2);
        List<Game> content = page.getContent();
        content.forEach(item -> System.out.println(item));
    }

    @Test
    public void findAllByPlatform_expectNoPage() {
        Pageable pageable = PageRequest.of(10, 50, Sort.by("releaseDate").descending());
        Page<Game> page = gameRepository.findAllByPlatform(Platform.PS_4, pageable);
        Assert.assertFalse(page.hasContent());
    }

    @Test
    public void findAllByTitle() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("releaseDate").descending());
        Page<Game> page = gameRepository.findAllByTitle("Destiny 2", pageable);
        Assert.assertFalse(page.isEmpty());
        Assert.assertTrue(page.getTotalElements() == 2);
        Assert.assertTrue(page.getTotalPages() == 1);
        List<Game> content = page.getContent();
        content.forEach(item -> System.out.println(item));
    }

    @Test
    public void findAllByPlatform_shouldAscendingOrder() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("releaseDate").ascending());
        Page<Game> page = gameRepository.findAllByPlatform(Platform.PS_4, pageable);
        Assert.assertFalse(page.isEmpty());
        Assert.assertTrue(page.getTotalElements() == 2);
        Assert.assertTrue(page.getTotalPages() == 1);
        List<Game> content = page.getContent();
        content.forEach(item -> System.out.println(item));
        Game game1 = content.get(0);
        Game game2 = content.get(1);
        Assert.assertTrue(game1.getReleaseDate().isBefore(game2.getReleaseDate()));
    }

    @Test
    public void findAllByReleaseDateBetween() {
        LocalDate start = LocalDate.now().minusWeeks(2);
        LocalDate end = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 50, Sort.by("releaseDate").descending());
        Page<Game> page = gameRepository.findAllByReleaseDateBetween(start, end, pageable);
        Assert.assertTrue(page.hasContent());
        page.getContent().forEach(i -> System.out.println(i.toString()));
    }

}
