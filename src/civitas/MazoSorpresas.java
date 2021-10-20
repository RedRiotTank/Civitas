/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author albertoplaza
 */
public class MazoSorpresas {

    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
      
    private void init() {
        sorpresas = new ArrayList<Sorpresa>();
        barajada = false;
        usadas = 0;
    }

    MazoSorpresas(boolean debug) {
        init();
        this.debug = debug;
        if (debug) {
            Diario.getInstance().ocurreEvento("cambio a modo debugger");
        }

    }

    MazoSorpresas() {
        init();
        this.debug = false;
    }

    public void alMazo(Sorpresa s) {
        if (!barajada) {
            sorpresas.add(s);
        }

    }

    Sorpresa siguiente() {   //Revisar.
        Sorpresa haSalido;
        if (!barajada || usadas == sorpresas.size()) {
            if (!debug) {
                Collections.shuffle(sorpresas);
            }
            usadas = 0;
            barajada = true;
        }

        usadas++;
        haSalido = sorpresas.get(0);    //0 es lel principio del mazo
        sorpresas.add(haSalido);
        sorpresas.remove(0);

        return haSalido;

    }
}
