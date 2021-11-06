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
public class Casilla {

    static private float FACTORALQUILERCALLE = 1.0f;
    static private float FACTORALQUILERCASA = 1.0f;
    static private float FACTORALQUILERHOTEL = 4.0f;

    private TipoCasilla tipo;
    private String Nombre;
    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;

    private Jugador propietario;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;  //no se usa

    public int cantidadCasasHoteles() {
        return this.numCasas + this.numHoteles;
    }

    Casilla(TipoCasilla tipo, String nombre) {  // Descanso     RELLENAR CONSTRUCTORES
        this.init();
        this.tipo = tipo;  
        this.Nombre = nombre;

    }

    Casilla(TipoCasilla tipo, String titulo, float precioEdificar, float precioCompra, float precioBaseAlquiler) {
        init();
        this.tipo = tipo;
        this.Nombre = titulo;
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;
    }

    Casilla(TipoCasilla tipo, String nombre, MazoSorpresas mazo) {
        this.init();
        this.tipo = tipo;
        this.Nombre = nombre;
        this.mazo = mazo;
    }

    boolean comprar(Jugador jugador) {
        this.propietario = jugador;
        
        this.propietario.paga(precioCompra);
        
        return true;
    }

    String getNombre() {
        return Nombre;
    }

    float getPrecioCompra() {
        return precioCompra;
    }

    float getPrecioEdificar() {
        return precioEdificar;
    }

    void informe(int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador: " + todos.get(iactual).getNombre()
                + " ha caido en la casilla: " + this.toString());

    }

    private void init() {
        tipo = null;
        Nombre = null;
        precioCompra = 0;
        precioEdificar = 0;
        precioBaseAlquiler = 0;
        numCasas = 0;
        numHoteles = 0;
        propietario = null;
        mazo = null;
        sorpresa = null;
    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos) {

        /*
        
        //Se puede hacer así más limpio? aunque no sea exactamente lo
        //que se implementa en el diagrama estructurar?
        
        TipoCasilla tipo = this.tipo;
        
        switch(tipo){
            case CALLE:
                recibeJugador_calle(iactual, todos);
            case SORPRESA:
                recibeJugador_sorpresa(iactual, todos);
            case DESCANSO:
                informe(iactual, todos);
        }
        
         */
        TipoCasilla tipo = this.tipo;

        if (tipo == TipoCasilla.CALLE) {
            recibeJugador_calle(iactual, todos);
        }

        if (tipo == TipoCasilla.SORPRESA) {
            recibeJugador_sorpresa(iactual, todos);
        }
        if (tipo == TipoCasilla.DESCANSO) {
            informe(iactual, todos);
        }
    }

    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
        this.informe(iactual, todos);
        
        Jugador jugador = todos.get(iactual);
        
        if(!this.tienePropietario())
            jugador.puedeComprarCasilla();
        
        else
            this.tramitarAlquiler(jugador);
        
    
    }

    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {
    
        Sorpresa sorpresa = this.mazo.siguiente();
        this.informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);
    
    }

    public boolean tienePropietario() {
        boolean resultado = false;
        if (this.propietario != null) {
            resultado = true;
        }
        return resultado;
    }

    public void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && this.esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
        }
        this.propietario.recibe(this.getPrecioAlquilerCompleto());
    }

    int getNumCasas() {
        return numCasas;
    }

    int getNumHoteles() {
        return numHoteles;
    }

    float getPrecioAlquilerCompleto() {
        return precioBaseAlquiler * (FACTORALQUILERCASA + numCasas + FACTORALQUILERHOTEL * numHoteles);
    }

    boolean construirCasa(Jugador jugador) {
        jugador.paga(precioEdificar);
        numCasas += 1;
        return true;
    }

    boolean construirHotel(Jugador jugador) {
        propietario.paga(precioEdificar);
        numHoteles++;
        return true;
    }

    boolean derruirCasas(int numero, Jugador jugador) {
        boolean satisfactorio = false;
        if (this.esEsteElPropietario(jugador) && this.getNumCasas() >= numero) {
            this.numCasas = this.numCasas - numero;
            satisfactorio = true;
        }

        return satisfactorio;
    }

    public boolean esEsteElPropietario(Jugador jugador) {
        boolean resultado = false;
        if (jugador == this.propietario) {
            resultado = true;
        }

        return resultado;

    }

    boolean igualdadIdentidad(Casilla otraParcela) {
        boolean igual = false;

        if (this == otraParcela) {
            igual = true;
        }
        return igual;
    }

    boolean igualdadEstado(Casilla otraParcela) {
        boolean igual = true;

        if (!this.Nombre.equals(otraParcela.getNombre())
                || this.getPrecioCompra() != otraParcela.getPrecioCompra()
                || this.getPrecioEdificar() != otraParcela.getPrecioEdificar()
                || this.precioBaseAlquiler != otraParcela.precioBaseAlquiler
                || this.getNumCasas() != otraParcela.getNumHoteles()
                || this.getNumHoteles() != otraParcela.getNumHoteles()) {
            igual = false;
        }

        return igual;
    }

    public String toString() {
        String Cadena = null;
        if (!this.tienePropietario()) {
            
            if(this.tipo == TipoCasilla.CALLE)
                Cadena = "Calle " + Nombre + ". Precios: Compra: " + precioCompra + ", Edificar: " + precioEdificar + ", Alquiler Base: " + precioBaseAlquiler
                    + ", Casas: " + numCasas + ", Hoteles: " + numHoteles + ".";
            
            if(this.tipo == TipoCasilla.SORPRESA)
                Cadena = "Sorpresa";
            if(this.tipo == TipoCasilla.DESCANSO)
                Cadena = "Descanso";
            
        } else {
            Cadena = "Esta calle es de: " + this.propietario.getNombre();
        }
        return Cadena;
    }
}
