/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class CasillaSorpresa extends Casilla {

    private MazoSorpresas mazo;

    CasillaSorpresa(String nombre, MazoSorpresas mazo) {
        super(nombre);
        this.mazo = mazo;
    }

    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {

        Sorpresa sorpresa = this.mazo.siguiente();
        this.informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);

    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        recibeJugador_sorpresa(iactual, todos);
    }

    public String toString() {
        return "Sorpresa: ";
    }
}
