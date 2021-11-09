package civitas;

import java.util.ArrayList;
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

    public CivitasJuego(ArrayList<String> nombres, boolean debug) {

        this.jugadores = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            Jugador jugador = new Jugador(nombres.get(i));
            this.jugadores.add(jugador);
        }

        gestEstados = new GestorEstados();
        estado = gestEstados.estadoInicial();

        Dado.getInstance().setDebug(debug);
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        mazo = new MazoSorpresas(debug);

        tablero = new Tablero();
        this.inicializaTablero(mazo);

        this.inicializaMazoSorpresas();

    }

    public int getIndiceJugadorActual() {
        return this.indiceJugadorActual;
    }

    public Jugador getJugadorActual() {
        return this.jugadores.get(indiceJugadorActual);
    }

    public ArrayList<Jugador> getJugadores() {
        return this.jugadores;
    }

    public Tablero getTablero() {
        return this.tablero;
    }
    
     public int getNumPropiedadesJugadorActual() {
        return this.jugadores.get(indiceJugadorActual).getPropiedades().size();
    }

    private void avanzarJugador() {
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual = jugadorActual.getCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida();
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
    }

    public boolean comprar() {
        Jugador jugadorActual = this.getJugadorActual();
        int numCasillaActual = jugadorActual.getCasillaActual();
        
        Casilla casilla = this.getTablero().getCasilla(numCasillaActual);
        boolean res = jugadorActual.comprar(casilla);

        return res;
    }

    public boolean contruirCasa(int ip) {
        return this.jugadores.get(indiceJugadorActual).construirCasa(ip);
    }

    public boolean contruirHotel(int ip) {
        return this.jugadores.get(indiceJugadorActual).construirHotel(ip);
    }

    private void contabilizarPasosPorSalida() {
        if (this.tablero.computarPasoPorSalida()) 
            this.jugadores.get(indiceJugadorActual).pasaPorSalida();
        
    }

    public boolean finalDelJuego() {
        boolean finJuego = false;
        
        for (int i = 0; i < this.jugadores.size(); i++) 
            if (this.jugadores.get(i).enBancarrota()) 
                finJuego = true;
                
        if (finJuego == true) 
            this.ranking();
        
        return finJuego;

    }
    
    private void pasarTurno() {
        this.indiceJugadorActual++;

        if (this.indiceJugadorActual >= this.jugadores.size()) 
            this.indiceJugadorActual = this.indiceJugadorActual % this.jugadores.size();
        
    }

    private ArrayList<Jugador> ranking() {
        Collections.sort(this.jugadores);
        return this.jugadores;
    }

    public OperacionJuego siguientePaso() {
        Jugador jugadorActual = this.getJugadorActual();

        OperacionJuego operacion = this.gestEstados.siguienteOperacion(jugadorActual, estado);
        Diario.getInstance().eventosPendientes();
        switch(operacion){
            case PASAR_TURNO:
                this.pasarTurno();
                this.siguientePasoCompletado(operacion);
                break;
                
            case AVANZAR:
                this.avanzarJugador();
                this.siguientePasoCompletado(operacion);
                break;
        }
        
        Diario.getInstance().eventosPendientes();

        return operacion;
        
    }

    public void siguientePasoCompletado(OperacionJuego operacion) {
        this.estado = this.gestEstados.siguienteEstado(this.jugadores.get(this.indiceJugadorActual), this.estado, operacion);
    }

    private void inicializaMazoSorpresas() {
        
        /*
        Lo hago así en lugar de crearlas directamente dentro de los parámetros de mazo.alMazo para
        recalcar el hecho de que el diagrama especifica que es una agregación y no una composición.
        */
        
        Sorpresa Positiva1PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Recibe 500$ tras ganar concurso de belleza", 500);
        Sorpresa Positiva2PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Recibe 100$ tras ganar concurso de ajedrez", 100);
        Sorpresa Positiva3PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Recibe 50$ tras ganar partida de poker", 50);
        Sorpresa Negativa1PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Te han pillado blanqueando capitales, multa de 500$ ", -500);
        Sorpresa Negativa2PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Tu primo te pide 100$", -100);
        Sorpresa Negativa3PAGARCOBRAR = new Sorpresa(TipoSorpresa.PAGARCOBRAR, "Multa de 50$ por exceo de velocidad", -50);

        Sorpresa Positiva1PORCASAHOTEL = new Sorpresa(TipoSorpresa.PORCASAHOTEL, "Tus edificaciones reportan beneficios, reciba 75$ por cada una", 75);
        Sorpresa Positiva2PORCASAHOTEL = new Sorpresa(TipoSorpresa.PORCASAHOTEL, "Tus edificaciones reportan beneficios, reciba 25$ por cada una", 25);
        Sorpresa Negativa1PORCASAHOTEL = new Sorpresa(TipoSorpresa.PORCASAHOTEL, "Reparaciones generales en tus propiedades", -75);
        Sorpresa Negativa2PORCASAHOTEL = new Sorpresa(TipoSorpresa.PORCASAHOTEL, "Reparaciones generales en tus propiedades", -25);

        this.mazo.alMazo(Positiva1PAGARCOBRAR);
        this.mazo.alMazo(Positiva2PAGARCOBRAR);
        this.mazo.alMazo(Positiva3PAGARCOBRAR);
        this.mazo.alMazo(Negativa1PAGARCOBRAR);
        this.mazo.alMazo(Negativa2PAGARCOBRAR);
        this.mazo.alMazo(Negativa3PAGARCOBRAR);
        
        this.mazo.alMazo(Positiva1PORCASAHOTEL);
        this.mazo.alMazo(Positiva2PORCASAHOTEL);
        this.mazo.alMazo(Negativa1PORCASAHOTEL);
        this.mazo.alMazo(Negativa2PORCASAHOTEL);

    }

    private void inicializaTablero(MazoSorpresas mazo) {

        /*
        Lo hago así en lugar de crearlas directamente dentro de los parámetros de tablero.aniadeCasilla para
        recalcar el hecho de que el diagrama especifica que es una agregación y no una composición.
         */
        
        Casilla calle1 = new Casilla(TipoCasilla.CALLE, "El Carril", 100, 500, 25);
        Casilla calle2 = new Casilla(TipoCasilla.CALLE, "La bodega", 100, 500, 25);
        Casilla calle3 = new Casilla(TipoCasilla.CALLE, "Buena Vista", 100, 500, 25);
        Casilla calle4 = new Casilla(TipoCasilla.CALLE, "Avenida El laderón", 100, 500, 25);
        Casilla calle5 = new Casilla(TipoCasilla.CALLE, "Plaza de Andalucía", 100, 500, 25);
        Casilla calle6 = new Casilla(TipoCasilla.CALLE, "El barrio", 100, 500, 25);
        Casilla calle7 = new Casilla(TipoCasilla.CALLE, "Pilar de arriba", 100, 500, 25);
        Casilla calle8 = new Casilla(TipoCasilla.CALLE, "El genasar", 100, 500, 25);
        Casilla calle9 = new Casilla(TipoCasilla.CALLE, "Alcalá Galiano", 100, 500, 25);
        Casilla calle10 = new Casilla(TipoCasilla.CALLE, "Federico García Lorca", 100, 500, 25);
        Casilla calle11 = new Casilla(TipoCasilla.CALLE, "La viña", 100, 500, 25);
        Casilla calle12 = new Casilla(TipoCasilla.CALLE, "Higueras", 100, 500, 25);
        Casilla calle13 = new Casilla(TipoCasilla.CALLE, "Hospital", 100, 500, 25);
        Casilla calle14 = new Casilla(TipoCasilla.CALLE, "Baena", 100, 500, 25);

        Casilla Sorpresa1 = new Casilla(TipoCasilla.SORPRESA, "Sorpresa1", this.mazo);
        Casilla Sorpresa2 = new Casilla(TipoCasilla.SORPRESA, "Sorpresa2", this.mazo);
        Casilla Sorpresa3 = new Casilla(TipoCasilla.SORPRESA, "Sorpresa3", this.mazo);
        Casilla Sorpresa4 = new Casilla(TipoCasilla.SORPRESA, "Sorpresa4", this.mazo);
        Casilla Parking = new Casilla(TipoCasilla.DESCANSO, "Parking");

        this.tablero.aniadeCasilla(calle1);
        this.tablero.aniadeCasilla(calle2);
        this.tablero.aniadeCasilla(calle3);
        this.tablero.aniadeCasilla(calle4);
        this.tablero.aniadeCasilla(Sorpresa1);
        this.tablero.aniadeCasilla(calle5);
        this.tablero.aniadeCasilla(calle6);
        this.tablero.aniadeCasilla(calle7);
        this.tablero.aniadeCasilla(calle8);
        this.tablero.aniadeCasilla(Sorpresa2);
        this.tablero.aniadeCasilla(calle9);
        this.tablero.aniadeCasilla(calle10);
        this.tablero.aniadeCasilla(calle11);
        this.tablero.aniadeCasilla(calle12);
        this.tablero.aniadeCasilla(Sorpresa3);
        this.tablero.aniadeCasilla(Parking);
        this.tablero.aniadeCasilla(calle13);
        this.tablero.aniadeCasilla(calle14);
        this.tablero.aniadeCasilla(Sorpresa4);
        
    }

}
