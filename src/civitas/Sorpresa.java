package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public abstract class Sorpresa {
    private String texto;
    private int valor;

    
    Sorpresa(String texto, int valor) {
        this.texto = texto;
        this.valor = valor;
    }

    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
     

     void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa a: " + todos.get(actual).getNombre() + "\n" + this.toString());
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
     
     
    
    public String toString() {
        return texto;
    }

}
