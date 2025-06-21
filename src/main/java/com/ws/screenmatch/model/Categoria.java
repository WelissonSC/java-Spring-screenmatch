package com.ws.screenmatch.model;

public enum Categoria {

    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Românce"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPt;

    Categoria(String categoriaOmdb, String categoriaPt) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPt = categoriaPt;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("anyone category has been found for string disponq" +
                "" +
                "ibilized" + text);
    }

    public static Categoria fromStringPt(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPt.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("anyone category has been found for string disponibilized" + text);
    }

}
