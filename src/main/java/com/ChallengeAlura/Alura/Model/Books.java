package com.ChallengeAlura.Alura.Model;
import jakarta.persistence.*;


@Entity
@Table(name = "books")
public class Books{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Double numeroDeDescargas;
    @Enumerated(EnumType.STRING)
    private Languages languages;



    @ManyToOne
     @JoinColumn(name="author_id")
    private Authors authors;


    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
       this.titulo = titulo;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }


    @Override
    public String toString() {
        return "Books{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", numeroDeDescargas=" + numeroDeDescargas +
                ", languages=" + languages +
                ", authors=" + authors +
                '}';
    }
}




