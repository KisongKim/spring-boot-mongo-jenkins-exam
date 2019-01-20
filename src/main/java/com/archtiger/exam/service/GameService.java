package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.PagedGamesResponse;

import java.time.LocalDate;

public interface GameService {

    PagedGamesResponse getGamesByReleaseDate(final LocalDate start,
                                             final LocalDate end,
                                             final int page,
                                             final int limit,
                                             final String sort) throws ExamException;

    PagedGamesResponse getGames(final String platform,
                                final String title,
                                final int page,
                                final int limit,
                                final String sort) throws ExamException;

}
