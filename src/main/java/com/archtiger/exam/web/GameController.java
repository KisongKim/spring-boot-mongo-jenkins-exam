package com.archtiger.exam.web;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.PagedGamesResponse;
import com.archtiger.exam.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "games")
    public PagedGamesResponse getGames(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "start") LocalDate start,
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "end") LocalDate end,
                                       @RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "50") int limit,
                                       @RequestParam(required = false, defaultValue = "desc") String sort) throws ExamException {
        return gameService.getGamesByReleaseDate(start, end, page, limit, sort);
    }

    @GetMapping(path = "games/filter")
    public PagedGamesResponse getGames(@RequestParam(defaultValue = "ALL") String platform,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "50") int limit,
                                       @RequestParam(required = false, defaultValue = "desc") String sort) throws ExamException {
        return gameService.getGames(platform, title, page, limit, sort);
    }

}
