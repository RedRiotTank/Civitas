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
public class SorpresaConvierteme extends Sorpresa {
    
    public SorpresaConvierteme(String texto,int valor) {
        super(texto, valor);
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        JugadorEspeculador espec = new JugadorEspeculador(todos.get(actual));
        todos.set(actual,espec);

    }
    
}
