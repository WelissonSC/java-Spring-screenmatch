package com.ws.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(@JsonAlias("Title") String title,
                        @JsonAlias("Rated") String avaliacao,
                        @JsonAlias("totalSeasons") Integer totalTemps,
                        @JsonAlias("Year") String ano,
                        @JsonAlias("Genre") String genero,
                        @JsonAlias("Actors") String atores,
                        @JsonAlias("Poster") String poster,
                        @JsonAlias("Plot") String sinopse) {
}
