package com.ws.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(@JsonAlias("Title") String title,
                        @JsonAlias("Rated") String avaliacao,
                        @JsonAlias("totalSeasons") Integer totalTemps,
                        @JsonAlias("Year") Integer ano) {

}
