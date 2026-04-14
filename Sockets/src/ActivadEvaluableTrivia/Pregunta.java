package ActivadEvaluableTrivia;

public class Pregunta {
    private String categoria;
    private String textoPregunta;
    private String[] opciones;
    private int indiceCorrecta; // 0 para A, 1 para B, 2 para C, 3 para D

    public Pregunta(String categoria, String textoPregunta, String[] opciones, String respuestaCorrectaLetra) {
        this.categoria = categoria;
        this.textoPregunta = textoPregunta;
        this.opciones = opciones;
        this.indiceCorrecta = letraAIndice(respuestaCorrectaLetra);
    }

    private int letraAIndice(String letra) {
        switch (letra.toLowerCase()) {
            case "a": return 0;
            case "b": return 1;
            case "c": return 2;
            case "d": return 3;
            default: return 0;
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getIndiceCorrecta() {
        return indiceCorrecta;
    }
}
