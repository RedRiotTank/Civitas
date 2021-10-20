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
public class Sorpresa {

    private String texto;
    private int valor;
    
    private TipoSorpresa tipo;
    private MazoSorpresas mazo;
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        
        if(tipo == TipoSorpresa.PAGARCOBRAR){
            this.aplicarAJugador_pagarCobrar(actual, todos);
        }
        
        else if(tipo == TipoSorpresa.PORCASAHOTEL){
            this.aplicarAJugador_porCasaHotel(actual, todos);
        }
        
    }
    private void  aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
        todos.get(actual).modificaSaldo(valor);
    }
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
        todos.get(actual).modificaSaldo(valor);
    }
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa a: " + todos.get(valor).getNombre());
    }
    Sorpresa(TipoSorpresa tipo, String texto, int valor){}
    public String toString(){
        return texto;
    }
    
    
}
