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
    
    Sorpresa(TipoSorpresa tipo, String texto, int valor) {

        this.tipo = tipo;
        this.texto = texto;
        this.valor = valor;
    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        switch (this.tipo){
            
            case PAGARCOBRAR:
                
                this.aplicarAJugador_pagarCobrar(actual, todos);
                break;
                
            case PORCASAHOTEL:
                this.aplicarAJugador_porCasaHotel(actual, todos);
                break;
                
        }
    }

    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        this.informe(actual, todos);
        
        todos.get(actual).modificaSaldo(valor);
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        this.valor = todos.get(actual).cantidadCasasHoteles()*this.valor;
        
        this.informe(actual, todos);
        todos.get(actual).modificaSaldo(valor);             
    }

    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa a: " + todos.get(actual).getNombre() + "\n" + this.toString());
    }

    public String toString() {
        return texto;
    }

}
