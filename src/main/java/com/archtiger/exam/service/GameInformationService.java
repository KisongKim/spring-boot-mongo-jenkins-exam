package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.ListingGamesResponse;
import com.archtiger.exam.model.Platform;

import java.time.LocalDate;

public interface GameInformationService {

    ListingGamesResponse listingGames(final Platform platform,
                                      final String title,
                                      final LocalDate releaseDateStart,
                                      final LocalDate releaseDateEnd) throws ExamException;

}
