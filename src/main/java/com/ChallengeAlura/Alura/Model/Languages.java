package com.ChallengeAlura.Alura.Model;


public enum Languages {
    ES("es"),
    EN("en"),
    FR("fr"),
    PT("pt"),
    DE("de");

    private final String idiomas;

    Languages(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getIdioma() {
        return idiomas;
    }

    public static Languages fromString(String text) {
        for (Languages idioma : Languages.values()) {
            if (idioma.idiomas.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún idioma correspondiente para: " + text);
    }
}
