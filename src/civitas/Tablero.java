package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class Tablero {

    private ArrayList<Casilla> casillas;
    private boolean porSalida;

    public Tablero() {
        Casilla salida = new Casilla(TipoCasilla.DESCANSO, "Salida", 0, 0, 0);
        this.casillas = new ArrayList<Casilla>();
        this.casillas.add(salida);
        this.porSalida = false;
    }
    
    public ArrayList<Casilla> getCasillas() {
        return this.casillas;
    }

    private boolean correcto(int numCasilla) {
        boolean resultado = true;

        if (numCasilla > casillas.size() || numCasilla < 0) 
            resultado = false;
        
        return resultado;
    }

    boolean computarPasoPorSalida() {
        boolean PasoSalida = this.porSalida;
        this.porSalida = false;
        
        return PasoSalida;
    }

    void aniadeCasilla(Casilla casilla) {
        this.casillas.add(casilla);
    }

    public Casilla getCasilla(int numCasilla) {
        Casilla Devuelve = null;
        
        if (correcto(numCasilla)) 
            Devuelve = this.casillas.get(numCasilla);
        
        return Devuelve;
    }

    int nuevaPosicion(int actual, int tirada) {
        int newPos = (actual + tirada) % this.casillas.size();

        if (newPos != (actual + tirada)) 
            this.porSalida = true;
        
        return newPos;
    }

}
