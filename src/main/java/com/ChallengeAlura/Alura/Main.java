package com.ChallengeAlura.Alura;
import com.ChallengeAlura.Alura.Model.Authors;
import com.ChallengeAlura.Alura.Model.Books;
import com.ChallengeAlura.Alura.Model.Languages;
import com.ChallengeAlura.Alura.Record.AuthorsData;
import com.ChallengeAlura.Alura.Record.BooksData;
import com.ChallengeAlura.Alura.Record.Data;
import com.ChallengeAlura.Alura.Repository.BooksRepository;
import com.ChallengeAlura.Alura.Service.AuthorService;
import com.ChallengeAlura.Alura.Service.BookService;
import com.ChallengeAlura.Alura.Service.ConsumoAPI;
import com.ChallengeAlura.Alura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class Main{
@Autowired
private BooksRepository booksRepository;
@Autowired
private BookService bookService;
@Autowired
private AuthorService authorService;
@Autowired
private ConsumoAPI consumoApi;
@Autowired
private ConvierteDatos convierteDatos;
private final String URL_BASE = "https://gutendex.com/books/";




    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();



      public void muestraMenu(){
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    |***********************************************|
                    |*****       BIENVENIDO A LITERALURA      ******|
                    |***********************************************|
                    \s
                     1 - Buscar y mostrar libro por título.
                     2 - Mostrar los libros registrados.
                     3 - Mostrar los autores registrados.
                     4 - Mostrar libros por idiomas.
                     5 - Mostrar autores vivos durante un año determinado.
                     0 - Salir
                    """;
            System.out.println(menu);

            try {
                System.out.print("Ingrese su opción: ");
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                       buscarBookWeb();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarLibrosPorIdioma();
                        break;
                    case 5:
                        listarAutoresLive();
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                teclado.nextLine();
            }

        }
    }
    private void buscarBookWeb() {
        System.out.println("Por favor escribir el Titulo del libro que desea Buscar: ");
        var tituloBooks = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloBooks.replace(" ", "+"));

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {
            var results = convierteDatos.obtenerDatos(json, Data.class);
            Optional<BooksData> libroBuscado = results.datosLibrosList().stream()
                    .findFirst();

            if (libroBuscado.isPresent()) {
                BooksData libro = libroBuscado.get();


                System.out.println("------------------------LIBRO-----------------");
                System.out.println("Titulo: " + libro.titulo());

                String autores = libro.datosAutorList().stream()
                        .map(AuthorsData::nombre)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(", "));
                System.out.println("Autor: " + autores);

                String idiomas = libro.idiomas().stream()
                        .collect(Collectors.joining(", "));
                System.out.println("Idioma: " + idiomas);

                System.out.println("Numero de Descargas: " + libro.numeroDeDescargas());
                System.out.println("------------------------------------------------");

                bookService.guardarLibroEnBaseDeDatos(libro);
            } else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("No existe Libro");
                System.out.println("------------------------------------------------------------------");
            }
        }
    }
    private void listarLibrosRegistrados() {
        List<Books> listaLibros = booksRepository.findAll();
        System.out.println("----------Lista de Libros-----------------------------");
            listaLibros.forEach(System.out::println);
       }

       private void listarAutoresRegistrados(){
            List<Authors> listarAutores = authorService.listarAutores();
            System.out.println(listarAutores);
      }

    private void listarAutoresLive() {
        System.out.println("Ingresar año vivo del Autor:");
        int anio = (int) teclado.nextLong();
        teclado.nextLine();
        List<Authors> listarAutoresVivos = authorService.listarAutoresVivos(anio);
        System.out.println(listarAutoresVivos);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el Idioma que desea buscar: ");
        System.out.println("""
                es - Español
                en - Ingles
                fr - Frances
                pt - Portugues
                """);
        var idioma = teclado.nextLine();

        try {
            Languages idiomaEnum = Languages.fromString(idioma);
            List<Books> languagesList = booksRepository.findByLanguages(idiomaEnum);
            System.out.println(languagesList);
        } catch (IllegalArgumentException e) {
            System.out.println("Advertencia: Idioma no reconocido: " + idioma);
        }
    }
}




