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
public class Tablero {

    private ArrayList<Casilla> casillas;
    private boolean porSalida;

    public Tablero() {
        Casilla salida = new Casilla(TipoCasilla.DESCANSO, "Salida", 0, 0, 0);
        casillas = new ArrayList<Casilla>();
        casillas.add(salida);
        porSalida = false;
    }

    private boolean correcto(int numCasilla) {
        boolean resultado = true;

        if (numCasilla > casillas.size() || numCasilla < 0) {
            resultado = false;
        }
        return true;
    }

     boolean computarPasoPorSalida() {
        boolean PasoSalida = porSalida;
        porSalida = false;
        return PasoSalida;
    }

     void aniadeCasilla(Casilla casilla) {
        casillas.add(casilla);
    }

    public Casilla getCasilla(int numCasilla) {
        Casilla Devuelve = null;
        if (correcto(numCasilla)) {
            Devuelve = casillas.get(numCasilla);
        }
        return Devuelve;
    }
    
    public ArrayList<Casilla> getCasillas(){
        return null;
    }

     int nuevaPosicion(int actual, int tirada) {
        int newPos = (actual + tirada) % casillas.size();

        if (newPos != (actual + tirada)) {
            porSalida = true;
        }
        return newPos;
    }

}
