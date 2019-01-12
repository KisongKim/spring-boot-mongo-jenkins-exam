package com.archtiger.exam;

import com.archtiger.exam.model.Game;
import com.archtiger.exam.model.Platform;
import com.archtiger.exam.model.Rating;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class GamePopulator {

    public static List<Game> populate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate weekAgo = LocalDate.now().minusWeeks(1);
        LocalDate monthAgo = LocalDate.now().minusMonths(1);
        LocalDate yearAgo = LocalDate.now().minusYears(1);
        Game g1 = new Game(null, Platform.PS_4, Rating.PG_18, "Activision", "Destiny 2", yearAgo, now);
        Game g2 = new Game(null, Platform.XBOX_ONE, Rating.PG_18, "Activision", "Destiny 2", yearAgo, now);
        Game g3 = new Game(null, Platform.SWITCH, Rating.PG_18, "Nintendo", "Super Mario", monthAgo, now);
        Game g4 = new Game(null, Platform.PS_4, Rating.PG_12, "Sony", "Horizon Zero Dawn", weekAgo, now);
        return Arrays.asList(g1, g2, g3, g4);
    }

}
