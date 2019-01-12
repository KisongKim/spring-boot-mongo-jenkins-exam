package com.archtiger.exam.web;

import com.archtiger.exam.GamePopulator;
import com.archtiger.exam.contract.GameInformation;
import com.archtiger.exam.contract.ListingGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.Platform;
import com.archtiger.exam.model.Rating;
import com.archtiger.exam.service.GameInformationService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerWebMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameInformationService gameInformationService;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new GameController(gameInformationService)).build();
    }

    private static ListingGamesResponse populate(Platform platform, String title, LocalDate start, LocalDate end) {
        List<Game> games = null;
        if (platform != null) {
            games = GamePopulator.populate().stream().filter(i -> i.getPlatform() == platform).collect(Collectors.toList());
        } else if (title != null) {
            games = GamePopulator.populate().stream().filter(i -> i.getTitle().equals(title)).collect(Collectors.toList());
        } else if (start != null && end != null) {
            games = GamePopulator.populate().stream()
                    .filter(i -> (i.getReleaseDate().equals(start) || i.getReleaseDate().isAfter(start))
                            && (i.getReleaseDate().isEqual(end) || i.getReleaseDate().isBefore(end)))
                    .collect(Collectors.toList());
        }
        Comparator<Game> comparator = (g1, g2) -> g1.getReleaseDate().compareTo(g2.getReleaseDate());
        games.sort(comparator.reversed());

        ListingGamesResponse response = new ListingGamesResponse();
        for (Game game : games) {
            response.getGameInformations().add(new GameInformation(game.getTitle(),
                    game.getPlatform(),
                    game.getReleaseDate(),
                    game.getRegisterDateTime()));
        }
        return response;
    }

    @Test
    public void listingGames_ByPlatform() throws Exception {
        ListingGamesResponse response = populate(Platform.PS_4, null, null, null);
        Mockito.when(gameInformationService.listingGames(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(response);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/games").param("platform", "PS_4");
        mvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[0].platform", Matchers.is("PS_4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.games[1].platform", Matchers.is("PS_4")));
    }

}
