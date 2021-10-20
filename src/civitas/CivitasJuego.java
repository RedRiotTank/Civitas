/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author albertoplaza
 */
public class CivitasJuego {
    
    private int indiceJugadorActual;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private EstadoJuego estado;
    private GestorEstados gestEstados;
    
    private void avanzarJugador(){}
    
    public CivitasJuego(ArrayList<String> nombres, boolean debug){
        for (int i=0; i< nombres.size(); i++){
            Jugador jugador = new Jugador(nombres.get(i));
            this.jugadores.add(jugador);
        }
        
        gestEstados = new GestorEstados();
        gestEstados.estadoInicial();
        
        Dado.getInstance().setDebug(debug);
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        
        mazo = new MazoSorpresas(debug);
        
        tablero = new Tablero();
        this.inicializaTablero(mazo);
        
        this.inicializaMazoSorpresas();
        
    }
    
    public boolean comprar(){return false;}
    
    public boolean contruirCasa(int ip){
        return this.jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    public boolean contruirHotel(int ip){
        return this.jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    private void contabilizarPasosPorSalida(){
        if(this.tablero.computarPasoPorSalida())
            this.jugadores.get(indiceJugadorActual).pasaPorSalida();
    }
    
    public boolean finalDelJuego(){
            boolean finJuego = false;
        for(int i=0; i<this.jugadores.size();i++){
                if(this.jugadores.get(i).enBancarrota())
                    finJuego = true;
            }
        
        return finJuego;
                
    }
   
    public int getIndiceJugadorActual(){
        return this.indiceJugadorActual;
    }
    
    public Jugador getJugadorActual(){
        return this.jugadores.get(indiceJugadorActual);
    }
    
    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }
    public Tablero getTablero(){
        return this.getTablero();
    }
    
    private void inicializaMazoSorpresas(){
        //lo mismo, que quiere que cree todas las sorpresas???
    }
    
    
    private void inicializaTablero(MazoSorpresas mazo){
        //Que quiere que cree que las casillas o que? no entiendo
    }
    
    private void pasarTurno(){
        this.indiceJugadorActual++;
        
        if (this.indiceJugadorActual >= this.jugadores.size()){
            this.indiceJugadorActual = this.indiceJugadorActual % this.jugadores.size();
        }
    }
    
   private ArrayList<Jugador> ranking(){
             Collections.sort(jugadores);
       return this.jugadores;
   }
   
   public OperacionJuego siguientePaso(){return null;}  //operacionesJuego???
   
   public void siguientePasoCompletado(OperacionJuego operacion){
       gestEstados.siguienteEstado(this.jugadores.get(indiceJugadorActual), this.estado, operacion);
   }
    
}
    
    
