package com.ChallengeAlura.Alura.Service;
import com.ChallengeAlura.Alura.Model.Authors;
import com.ChallengeAlura.Alura.Model.Books;
import com.ChallengeAlura.Alura.Model.Languages;
import com.ChallengeAlura.Alura.Record.BooksData;
import com.ChallengeAlura.Alura.Repository.AuthorsRepository;
import com.ChallengeAlura.Alura.Repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BookService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    public void guardarLibroEnBaseDeDatos(BooksData libroData) {

        if (booksRepository.findByTituloIgnoreCase(libroData.titulo()).isPresent()) {
            System.out.println("El libro ya existe en la base de datos.");
            return;
        } else {

            Books libro = new Books();
            libro.setTitulo(libroData.titulo());
            libro.setNumeroDeDescargas(libroData.numeroDeDescargas());


            libroData.idiomas().stream()
                    .findFirst()
                    .ifPresentOrElse(idioma -> {
                        try {
                            libro.setLanguages(Languages.fromString(idioma));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Advertencia: Idioma no reconocido: " + idioma);
                            libro.setLanguages(null); // O asignar un idioma predeterminado
                        }
                    }, () -> {
                        System.out.println("Advertencia: No se especificó ningún idioma para el libro.");
                        libro.setLanguages(null); // O asignar un idioma predeterminado
                    });


            libroData.datosAutorList().forEach(authorData -> {
                Authors autor = authorsRepository.findByNombre(authorData.nombre())
                        .orElseGet(() -> {
                            Authors nuevoAutor = new Authors();
                            nuevoAutor.setNombre(authorData.nombre());
                            return authorsRepository.save(nuevoAutor);
                        });
                libro.setAuthors(autor);
            });

            booksRepository.save(libro);
            System.out.println("El libro ha sido guardado en la base de datos.");
        }
    }
}