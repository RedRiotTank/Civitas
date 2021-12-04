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
public class testp4 {
    
    public static void main(String[] args) {
    
        ArrayList<Jugador> lista = new ArrayList<>();
        
      
        
        lista.add(new Jugador("ISMAEL diaz²"));
        lista.add(new Jugador("Adri²"));
          
        
        CasillaCalle gilena = new CasillaCalle("Gilena",0,0,0);
        lista.get(0).puedeComprarCasilla();
        lista.get(0).comprar(gilena);
        
        SorpresaConvierteme sor = new SorpresaConvierteme("aaa", 11);
        sor.aplicarAJugador(0, lista);
        
        CasillaCalle Mencia = new CasillaCalle("mencia",0,0,0);
        lista.get(1).puedeComprarCasilla();
        lista.get(1).comprar(Mencia);
        
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        lista.get(0).construirCasa(0);
        lista.get(1).construirCasa(0);
        
        System.out.println(lista.get(0).toString());
        System.out.println(lista.get(1).toString());
        
        
    }
    
}
