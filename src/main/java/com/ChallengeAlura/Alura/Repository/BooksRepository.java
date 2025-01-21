package com.ChallengeAlura.Alura.Repository;
import com.ChallengeAlura.Alura.Model.Books;
import com.ChallengeAlura.Alura.Model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

    public interface BooksRepository extends JpaRepository<Books,Long> {

    Optional<Books> findByTituloIgnoreCase(String titulo);
    @Query("SELECT b FROM Books b WHERE b.languages = :languages")
    List<Books> findByLanguages(@Param("languages") Languages languages);







}

