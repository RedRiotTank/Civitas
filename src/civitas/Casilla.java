package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class Casilla {

    private String Nombre;

    Casilla(String nombre) {
        this.Nombre = nombre;
    }

    String getNombre() {
        return this.Nombre;
    }

    void informe(int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento(todos.get(iactual).getNombre()
                + " ha caido en la casilla: " + this.toString());

    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        informe(iactual, todos);
    }

    public String toString() {
        return "Descanso";
    }
}
