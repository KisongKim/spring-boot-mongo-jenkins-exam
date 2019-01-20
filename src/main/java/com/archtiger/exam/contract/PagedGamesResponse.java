package com.archtiger.exam.contract;

import com.archtiger.exam.model.Platform;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
@Data
@ToString
public class PagedGamesResponse {

    @JsonProperty(required = true)
    private long totalElement;

    @JsonProperty(required = true)
    private long totalPages;

    @JsonProperty(required = true)
    private long currentPage;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "requestedPlatform")
    private Platform platform;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "requestedTitle")
    private String title;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonProperty(value = "games")
    private List<GameInformation> games;

    public PagedGamesResponse(long totalElement, long totalPages, long currentPage, Platform platform, String title) {
        this.totalElement = totalElement;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.platform = platform;
        this.title = title;
    }

    public List<GameInformation> games() {
        if (games == null) {
            games = new ArrayList<>();
        }
        return games;
    }

}
