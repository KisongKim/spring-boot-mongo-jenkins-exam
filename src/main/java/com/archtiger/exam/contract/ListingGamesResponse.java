package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@JsonAutoDetect
public class ListingGamesResponse {

    @JsonProperty(value = "games")
    private List<GameInformation> gameInformations;

    public List<GameInformation> getGameInformations() {
        if (gameInformations == null) {
            gameInformations = new ArrayList<>();
        }
        return gameInformations;
    }

}
