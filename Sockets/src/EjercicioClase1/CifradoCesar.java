package EjercicioClase1;

public class CifradoCesar {

    // El número de posiciones a desplazar. En el cifrado clásico suele ser 3.
    private static final int DESPLAZAMIENTO = 3;

    public static String cifrarCadena(String cadena) {
        StringBuilder resultado = new StringBuilder();

        for (char c : cadena.toCharArray()) {
            // Solo ciframos si es una letra del abecedario
            if (Character.isLetter(c)) {
                // Determinamos si es mayúscula o minúscula para usar la base correcta en ASCII
                char base = Character.isUpperCase(c) ? 'A' : 'a';

                // Aplicamos la fórmula matemática del cifrado César
                c = (char) (((c - base + DESPLAZAMIENTO) % 26) + base);
            }
            resultado.append(c);
        }

        return resultado.toString();
    }

    public static String descifrarCadena(String cadena) {
        StringBuilder resultado = new StringBuilder();

        for (char c : cadena.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';

                // Para descifrar restamos el desplazamiento.
                // Sumamos 26 antes del módulo para evitar que el resultado sea negativo.
                c = (char) (((c - base - DESPLAZAMIENTO + 26) % 26) + base);
            }
            resultado.append(c);
        }

        return resultado.toString();
    }
}
