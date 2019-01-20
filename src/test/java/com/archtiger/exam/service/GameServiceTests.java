package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.ExamExceptionMatcher;
import com.archtiger.exam.GamePopulator;
import com.archtiger.exam.contract.PagedGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.GameRepository;
import com.archtiger.exam.model.Platform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class GameServiceTests {

    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.gameService = new GameServiceImpl(gameRepository);
    }

    /**
     * Pageable request with default argument unit test
     */
    @Test
    public void getGames_expectedPageableArgumentIsDefault() {
        List<Game> expected = GamePopulator.populate();
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAll(Mockito.any(Pageable.class))).thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames("ALL",
                null, 0, 50, "desc");

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(gameRepository, Mockito.times(1)).findAll(pageableArgument.capture());
        Mockito.verifyNoMoreInteractions(gameRepository);

        Pageable pageSpecification = pageableArgument.getValue();
        Assert.assertEquals(0, pageSpecification.getPageNumber());
        Assert.assertEquals(50, pageSpecification.getPageSize());
        Assert.assertEquals(Sort.Direction.DESC, pageSpecification.getSort().getOrderFor("releaseDate").getDirection());

        Assert.assertEquals(expected.size(), response.games().size());
    }

    /**
     * Pageable request arguments provided by user unit test.
     */
    @Test
    public void getGames_expectedPageableArgumentIsNotDefault() {
        List<Game> expected = GamePopulator.populate();
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAll(Mockito.any(Pageable.class))).thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames("ALL",
                null, 1, 10, "asc");

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(gameRepository, Mockito.times(1)).findAll(pageableArgument.capture());
        Mockito.verifyNoMoreInteractions(gameRepository);

        Pageable pageSpecification = pageableArgument.getValue();
        Assert.assertEquals(1, pageSpecification.getPageNumber());
        Assert.assertEquals(10, pageSpecification.getPageSize());
        Assert.assertEquals(Sort.Direction.ASC, pageSpecification.getSort().getOrderFor("releaseDate").getDirection());

        Assert.assertEquals(expected.size(), response.games().size());
    }

    @Test
    public void getGames_expectedInvocationIsFindAllByReleaseDateBetween() {
        LocalDate end = LocalDate.now().plusDays(10);
        LocalDate start = end.minusMonths(3);
        List<Game> expected = GamePopulator.populate().stream()
                .filter(i -> (i.getReleaseDate().isAfter(start) || i.getReleaseDate().equals(start)))
                .filter(i -> (i.getReleaseDate().isBefore(end) || i.getReleaseDate().equals(end)))
                .collect(Collectors.toList());
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAllByReleaseDateBetween(Mockito.any(),
                Mockito.any(),
                Mockito.any(Pageable.class))).thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGamesByReleaseDate(start, end, 0, 50, "desc");

        ArgumentCaptor<LocalDate> startArgument = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> endArgument = ArgumentCaptor.forClass(LocalDate.class);
        Mockito.verify(gameRepository, Mockito.times(1))
                .findAllByReleaseDateBetween(startArgument.capture(), endArgument.capture(), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gameRepository);

        LocalDate capturedStart = startArgument.getValue();
        LocalDate capturedEnd = endArgument.getValue();
        Assert.assertEquals(start, capturedStart);
        Assert.assertEquals(end, capturedEnd);
        Assert.assertEquals(expected.size(), response.games().size());
    }

    @Test
    public void getGames_expectedExamException() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(40000));

        LocalDate end = LocalDate.now();
        LocalDate start = end.plusDays(1);
        gameService.getGamesByReleaseDate(start, end, 0, 50, "desc");
    }

    @Test
    public void getGames_expectedInvocationsIsFindAllByTitle() {
        final String title = "Destiny 2";
        List<Game> expected = GamePopulator.populate().stream()
                .filter(i -> title.equals(i.getTitle()))
                .collect(Collectors.toList());
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAllByTitle(Mockito.any(), Mockito.any(Pageable.class))).thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames("ALL",
                title,
                0,
                50,
                "desc");

        ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(gameRepository, Mockito.times(1))
                .findAllByTitle(stringArgument.capture(), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gameRepository);

        String capturedTitle = stringArgument.getValue();
        Assert.assertEquals(title, capturedTitle);
        Assert.assertEquals(expected.size(), response.games().size());
    }

    @Test
    public void getGames_expectedInvocationsIsFindAll() {
        List<Game> expected = GamePopulator.populate();
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAll(Mockito.any(Pageable.class))).thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames(
                "ALL",
                null,
                0,
                50,
                "desc");

        Mockito.verify(gameRepository, Mockito.times(1)).findAll(Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gameRepository);

        Assert.assertEquals(expected.size(), response.games().size());
    }

    @Test
    public void getGames_expectedInvocationsIsFindAllByPlatformAndTitle() {
        Platform platform = Platform.XBOX_ONE;
        String title = "Destiny 2";
        List<Game> expected = GamePopulator.populate().stream()
                .filter(i -> platform.equals(i.getPlatform()))
                .filter(i -> title.equals(i.getTitle()))
                .collect(Collectors.toList());
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAllByPlatformAndTitle(Mockito.any(), Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames("XBOX_ONE",
                title,
                0,
                50,
                "desc");

        ArgumentCaptor<Platform> enumArgument = ArgumentCaptor.forClass(Platform.class);
        ArgumentCaptor<String> stringArgument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(gameRepository, Mockito.times(1))
                .findAllByPlatformAndTitle(enumArgument.capture(),
                        stringArgument.capture(),
                        Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gameRepository);

        Platform capturedPlatform = enumArgument.getValue();
        String capturedTitle = stringArgument.getValue();
        Assert.assertEquals(platform, capturedPlatform);
        Assert.assertEquals(title, capturedTitle);
        Assert.assertEquals(expected.size(), response.games().size());
    }

    @Test
    public void getGames_expectedInvocationsIsFindAllByPlatform() {
        Platform platform = Platform.SWITCH;
        List<Game> expected = GamePopulator.populate().stream()
                .filter(i -> platform.equals(i.getPlatform()))
                .collect(Collectors.toList());
        Page<Game> foundPage = new PageImpl<>(expected);
        Mockito.when(gameRepository.findAllByPlatform(Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(foundPage);

        PagedGamesResponse response = gameService.getGames("SWITCH",
                null,
                0,
                50,
                "desc");

        ArgumentCaptor<Platform> enumArgument = ArgumentCaptor.forClass(Platform.class);
        Mockito.verify(gameRepository, Mockito.times(1))
                .findAllByPlatform(enumArgument.capture(), Mockito.any(Pageable.class));
        Mockito.verifyNoMoreInteractions(gameRepository);

        Platform capturedPlatform = enumArgument.getValue();
        Assert.assertEquals(platform, capturedPlatform);
        Assert.assertEquals(expected.size(), response.games().size());
    }

}
