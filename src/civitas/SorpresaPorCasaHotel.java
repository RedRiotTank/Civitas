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
public class SorpresaPorCasaHotel extends Sorpresa {
    
    SorpresaPorCasaHotel(String texto, int valor){
    super(texto,valor);
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {

        this.setValor(todos.get(actual).cantidadCasasHoteles() * this.getValor());

        this.informe(actual, todos);
        todos.get(actual).modificaSaldo(this.getValor());
    }

}
