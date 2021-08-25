package edu.espol.grupo_12;

public enum Mark {
    X('X'),
    O('O'),
    BLANK(' ');

    private final char mark;

    Mark(char MarcaInicial) {
        this.mark = MarcaInicial;
    }

    public boolean estaMarcado() {
        return this != BLANK;
    }

    public char getMark() {
        return this.mark;
    }

    @Override
    public String toString() {
        return String.valueOf(mark);
    }
}