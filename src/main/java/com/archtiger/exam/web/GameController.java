package com.archtiger.exam.web;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.ListingGamesResponse;
import com.archtiger.exam.model.Platform;
import com.archtiger.exam.service.GameInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class GameController {

    private final GameInformationService gameInformationService;

    @Autowired
    public GameController(GameInformationService gameInformationService) {
        this.gameInformationService = gameInformationService;
    }

    @GetMapping(path = "/games")
    @ResponseBody
    public ListingGamesResponse listingGames(@Nullable @RequestParam Platform platform,
                                             @Nullable @RequestParam String title,
                                             @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                 @RequestParam(name = "start") LocalDate releaseDateStart,
                                             @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                 @RequestParam(name = "end") LocalDate releaseDateEnd) throws ExamException {
        return gameInformationService.listingGames(platform, title, releaseDateStart, releaseDateEnd);
    }


}
