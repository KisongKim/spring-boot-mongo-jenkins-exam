package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.ExamExceptionMatcher;
import com.archtiger.exam.GamePopulator;
import com.archtiger.exam.contract.ListingGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.GameRepository;
import com.archtiger.exam.model.Platform;
import com.archtiger.exam.model.Rating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GameInformationService unit tests.
 */
@RunWith(SpringRunner.class)
public class GameInformationServiceTests {

    private GameInformationService gameInformationService;

    @MockBean
    private GameRepository gameRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.gameInformationService = new GameInformationServiceImpl(gameRepository);
    }

    @Test
    public void listingGames_ByPlatform() {
        List<Game> games = GamePopulator.populate().stream().filter(i -> i.getPlatform() == Platform.PS_4).collect(Collectors.toList());
        Mockito.when(gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(Mockito.any())).thenReturn(games);

        ListingGamesResponse response = gameInformationService.listingGames(Platform.PS_4, null, null, null);

        Assert.assertTrue(response.getGameInformations().size() == 2);
    }

    @Test
    public void listingGames_ExpectException50001() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(50001));

        Mockito.when(gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(Mockito.any())).thenReturn(null);

        gameInformationService.listingGames(Platform.PS_4, null, null, null);
    }

    @Test
    public void listingGames_ByTitle() {
        List<Game> games = GamePopulator.populate().stream().filter(i -> "Destiny 2".equals(i.getTitle())).collect(Collectors.toList());
        Mockito.when(gameRepository.findAllByTitleOrderByReleaseDateDesc(Mockito.any())).thenReturn(games);

        ListingGamesResponse response = gameInformationService.listingGames(null, "Destiny 2", null, null);

        Assert.assertTrue(response.getGameInformations().size() == 2);
    }

    @Test
    public void listingGames_ByReleaseDates() {
        List<Game> games = GamePopulator.populate();
        Mockito.when(gameRepository.findAllByReleaseDateBetweenOrderByReleaseDateDesc(Mockito.any(), Mockito.any())).thenReturn(games);

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(1);
        ListingGamesResponse response = gameInformationService.listingGames(null, null, start, end);

        Assert.assertTrue(response.getGameInformations().size() == 4);
    }

    @Test
    public void listingGames_ExpectException40000() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(40000));

        LocalDate end = LocalDate.now();
        LocalDate start = end.plusDays(1);
        gameInformationService.listingGames(null, null, start, end);
    }

}
