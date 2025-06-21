package com.ws.screenmatch.repository;

import com.ws.screenmatch.model.Categoria;
import com.ws.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findByTotalTemporadasAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(Integer totalTemporadas, Double avaliacao);
}
