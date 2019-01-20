package com.archtiger.exam.web;

import com.archtiger.exam.GamePopulator;
import com.archtiger.exam.contract.GameInformation;
import com.archtiger.exam.contract.PagedGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.Platform;
import com.archtiger.exam.service.GameService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerWebMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new GameController(gameService)).build();
    }

    private static PagedGamesResponse populate(LocalDate start, LocalDate end, boolean naturalOrder) {
        List<Game> games = GamePopulator.populate().stream()
                .filter(i -> (i.getReleaseDate().equals(start) || i.getReleaseDate().isAfter(start)))
                .filter(i -> (i.getReleaseDate().isEqual(end) || i.getReleaseDate().isBefore(end)))
                .collect(Collectors.toList());

        Comparator<Game> comparator = (g1, g2) -> g1.getReleaseDate().compareTo(g2.getReleaseDate());
        if (naturalOrder) {
            games.sort(comparator);
        } else {
            games.sort(comparator.reversed());
        }

        PagedGamesResponse response = new PagedGamesResponse();
        response.setTotalElement(games.size());
        response.setTotalPages(1);
        response.setCurrentPage(0);
        for (Game game : games) {
            response.games().add(new GameInformation(game.getTitle(),
                    game.getPlatform(),
                    game.getReleaseDate(),
                    game.getRegisterDateTime()));
        }
        return response;
    }

    private static PagedGamesResponse populate(Platform platform, String title, String sort) {
        List<Game> games = null;
        if (platform != null) {
            games = GamePopulator.populate().stream()
                    .filter(i -> i.getPlatform().equals(platform))
                    .collect(Collectors.toList());
        } else if (title != null) {
            games = GamePopulator.populate().stream()
                    .filter(i -> i.getTitle().equals(title))
                    .collect(Collectors.toList());
        }
        Comparator<Game> comparator = (g1, g2) -> g1.getReleaseDate().compareTo(g2.getReleaseDate());
        if ("asc".equals(sort)) {
            games.sort(comparator);
        } else {
            games.sort(comparator.reversed());
        }

        PagedGamesResponse response = new PagedGamesResponse();
        response.setTotalElement(games.size());
        response.setTotalPages(1);
        response.setCurrentPage(0);
        // to make sure list not null
        response.games();
        for (Game game : games) {
            response.games().add(new GameInformation(game.getTitle(),
                    game.getPlatform(),
                    game.getReleaseDate(),
                    game.getRegisterDateTime()));
        }
        return response;
    }

    @Test
    public void getGames_byReleaseDate() throws Exception {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(3);
        PagedGamesResponse response = populate(start, end, false);
        Mockito.when(gameService.getGamesByReleaseDate(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any())).thenReturn(response);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/games")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("start", start.format(dateTimeFormatter))
                .param("end", end.format(dateTimeFormatter));
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games", Matchers.hasSize(2)));
    }

    @Test
    public void getGames_byPlatform() throws Exception {
        PagedGamesResponse response = populate(Platform.PS_4, null, "desc");
        Mockito.when(gameService.getGames(
                Mockito.any(String.class),
                Mockito.isNull(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/games/filter")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("platform", "PS_4")
                .param("page", "0")
                .param("limit", "50")
                .param("sort", "desc");
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games", Matchers.hasSize(2)));
    }

    @Test
    public void getGames_byTitle() throws Exception {
        PagedGamesResponse response = populate(null, "Destiny 2", "desc");
        Mockito.when(gameService.getGames(
                Mockito.any(String.class),
                Mockito.any(String.class),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/games/filter")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("title", "Destiny 2")
                .param("page", "0")
                .param("limit", "50")
                .param("sort", "desc");
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games", Matchers.hasSize(2)));
    }

    @Test
    public void getGames_expectNoGames() throws Exception {
        PagedGamesResponse response = populate(Platform.PC, null, "desc");
        Mockito.when(gameService.getGames(
                Mockito.any(String.class),
                Mockito.isNull(),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.any(String.class))).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/games/filter")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("platform", "PC")
                .param("page", "0")
                .param("limit", "50")
                .param("sort", "desc");
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games", Matchers.hasSize(0)));
    }

}
