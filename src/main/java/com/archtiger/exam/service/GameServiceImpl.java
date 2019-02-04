package com.archtiger.exam.service;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.GameInformation;
import com.archtiger.exam.contract.PagedGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.GameRepository;
import com.archtiger.exam.model.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private Pageable pageable(final int page, final int limit, final String sort) {
        // default sort is releaseDate in desc order
        boolean desc = "asc".equals(sort) == true ? false : true;
        return PageRequest.of(page, limit, desc == true ?
                Sort.by("releaseDate").descending() : Sort.by("releaseDate").ascending());
    }

    private PagedGamesResponse pagedGamesResponse(PagedGamesResponse response, List<Game> games) {
        for (Game item : games) {
            GameInformation gameInformation = new GameInformation(item.getTitle(),
                    item.getPlatform(),
                    item.getReleaseDate(),
                    item.getRegisterDateTime());
            response.games().add(gameInformation);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGamesResponse getGamesByReleaseDate(final LocalDate start,
                                                    final LocalDate end,
                                                    final int page, final int limit,
                                                    final String sort) throws ExamException {
        if (start.isAfter(end)) {
            logger.error("[getGamesByReleaseDate] Start date must be earlier than end date.");
            throw new ExamException(ExamError.INVALID_PARAMETER, "Start date must be equals or earlier then end");
        }

        // default sort is releaseDate in desc order
        Pageable pageable = pageable(page, limit, sort);
        Page<Game> pagedGames = gameRepository.findAllByReleaseDateBetween(start, end, pageable);
        PagedGamesResponse response = new PagedGamesResponse(pagedGames.getTotalElements(),
                pagedGames.getTotalPages(),
                page,
                null,
                null);
        return pagedGamesResponse(response, pagedGames.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGamesResponse getGames(final String platform,
                                       final String title,
                                       final int page,
                                       final int limit,
                                       final String sort) throws ExamException {
        Pageable pageable = pageable(page, limit, sort);
        Platform platformToFilter = Platform.valueOf(platform);
        String titleToFilter = title;
        Page<Game> pagedGames;
        if (platformToFilter == Platform.ALL) {
            if (titleToFilter != null) {
                pagedGames = gameRepository.findAllByTitle(titleToFilter, pageable);
            } else {
                pagedGames = gameRepository.findAll(pageable);
            }
        } else {
            if (titleToFilter != null) {
                pagedGames = gameRepository.findAllByPlatformAndTitle(platformToFilter, titleToFilter, pageable);
            } else {
                pagedGames = gameRepository.findAllByPlatform(platformToFilter, pageable);
            }
        }

        PagedGamesResponse response = new PagedGamesResponse(pagedGames.getTotalElements(),
                pagedGames.getTotalPages(),
                page,
                platformToFilter,
                titleToFilter);
        return pagedGamesResponse(response, pagedGames.getContent());
    }

}
