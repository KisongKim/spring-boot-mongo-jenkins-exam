package com.archtiger.exam.service;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.GameInformation;
import com.archtiger.exam.contract.ListingGamesResponse;
import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.GameRepository;
import com.archtiger.exam.model.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameInformationServiceImpl implements GameInformationService {

    private final GameRepository gameRepository;

    private static final Logger logger = LoggerFactory.getLogger(GameInformationServiceImpl.class);

    @Autowired
    public GameInformationServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ListingGamesResponse listingGames(final Platform platform,
                                             final String title,
                                             final LocalDate releaseDateStart,
                                             final LocalDate releaseDateEnd) throws ExamException {
        List<Game> games = null;
        if (platform != null) {
            games = gameRepository.findAllByPlatformEqualsOrderByReleaseDateDesc(platform);
        } else if (title != null) {
            games = gameRepository.findAllByTitleOrderByReleaseDateDesc(title);
        } else if (releaseDateStart != null && releaseDateEnd != null) {
            if (releaseDateEnd.isBefore(releaseDateStart)) {
                logger.error("[listingGames] releaseDateStart must be after releaseDateEnd");
                throw new ExamException(ExamError.INVALID_PARAMETER, new IllegalArgumentException());
            }
            games = gameRepository.findAllByReleaseDateBetweenOrderByReleaseDateDesc(releaseDateStart, releaseDateEnd);
        }

        if (games == null) {
            logger.error("[listingGames] Repository error");
            throw new ExamException(ExamError.INTERNAL_SERVER_ERROR, "Error Occurred.");
        }

        ListingGamesResponse response = new ListingGamesResponse();
        for (Game item : games) {
            GameInformation gameInformation = new GameInformation(item.getTitle(),
                    item.getPlatform(),
                    item.getReleaseDate(),
                    item.getRegisterDateTime());
            response.getGameInformations().add(gameInformation);
        }
        return response;
    }

}
