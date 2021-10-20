/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.Random;

/**
 *
 * @author albertoplaza
 */
public class Dado {

    static private int VALORDEBUG = 1;
    static private int VALORESDADO = 6;
    static final private Dado instance = new Dado();
    private Random random = new Random();
    private int ultimoResultado;
    boolean debug = false;  //creo ue hay que quitar el = false

    Dado(){}
   
    public static Dado getInstance() {
        return instance;
    }

    int tirar() {
        int devuelve = 1;
        if (!debug) {
            devuelve = random.nextInt(6) + 1;  //+1 para que salga el 6 también y no salga el 0.
        }
        ultimoResultado = devuelve;
        return devuelve;
    }

    int quienEmpieza(int n) {
        return random.nextInt(n);
    }

    public void setDebug(boolean debug) {
        String Debug = "Debugger", jugar = "jugar";
        String modo;

        if (debug) {
            modo = Debug;
        } else {
            modo = jugar;
        }

        debug = debug;

        Diario.getInstance().ocurreEvento("cambio a modo " + modo);
    }

     int getUltimoResultado() {
        return ultimoResultado;
    }

}
