package com.ChallengeAlura.Alura.Service;
import com.ChallengeAlura.Alura.Model.Authors;
import com.ChallengeAlura.Alura.Repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorsRepository authorsRepository;

    public List<Authors> listarAutores() {
        return authorsRepository.findAllAuthors();
    }

    public List<Authors> listarAutoresVivos(int ano) {
        return authorsRepository.findAutoresVivos(ano);
    }

    public Authors crearAutor(Authors authors) {
        return authorsRepository.save(authors);
    }

    public Optional<Authors> authorsByID(long id) {
        return authorsRepository.findById(id);
    }

    public Optional<Authors> authorByNombre(String nombre) {
        return authorsRepository.findByNombre(nombre);
    }

    public Authors actualizarAutor(Long id, Authors autorNuevo) {
        Authors authors = authorsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
        authors.setNombre(autorNuevo.getNombre());
        authors.setFechaNacimiento(autorNuevo.getFechaNacimiento());
        authors.setFechaFallecimiento(autorNuevo.getFechaFallecimiento());
        return authorsRepository.save(authors);
    }
public void eliminarAutor(Long id){
        authorsRepository.deleteById(id);
}
}

