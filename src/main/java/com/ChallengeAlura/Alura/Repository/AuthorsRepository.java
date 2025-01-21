package com.ChallengeAlura.Alura.Repository;

import com.ChallengeAlura.Alura.Model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {

    Optional<Authors> findByNombre(String nombre);

    @Query("SELECT a FROM Authors a LEFT JOIN FETCH a.books WHERE (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :ano) AND a.fechaNacimiento <= :ano")
    List<Authors> findAutoresVivos(@Param("ano") int ano);

    @Query("SELECT a FROM Authors a LEFT JOIN FETCH a.books")
    List<Authors> findAllAuthors();
}


