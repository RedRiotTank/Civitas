package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class Casilla {

    static private final float  FACTORALQUILERCALLE = 1.0f;
    static private final float FACTORALQUILERCASA = 1.0f;
    static private final float FACTORALQUILERHOTEL = 4.0f;

    private TipoCasilla tipo;
    private String Nombre;
    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;

    private Jugador propietario;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;  //no se usa

    private void init() {
        this.tipo = null;
        this.Nombre = null;
        this.precioCompra = 0;
        this.precioEdificar = 0;
        this.precioBaseAlquiler = 0;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.propietario = null;
        this.mazo = null;
        this.sorpresa = null;
    }

    Casilla(TipoCasilla tipo, String nombre) {
        this.init();
        this.tipo = tipo;
        this.Nombre = nombre;
    }

    Casilla(TipoCasilla tipo, String titulo, float precioEdificar, float precioCompra, float precioBaseAlquiler) {
        this(tipo, titulo);
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;
    }

    Casilla(TipoCasilla tipo, String nombre, MazoSorpresas mazo) {
        this(tipo, nombre);
        this.mazo = mazo;
    }

    String getNombre() {
        return this.Nombre;
    }

    float getPrecioCompra() {
        return this.precioCompra;
    }

    float getPrecioEdificar() {
        return this.precioEdificar;
    }

    int getNumCasas() {
        return this.numCasas;
    }

    int getNumHoteles() {
        return this.numHoteles;
    }

    float getPrecioAlquilerCompleto() {
        return precioBaseAlquiler * (Casilla.FACTORALQUILERCASA + numCasas + Casilla.FACTORALQUILERHOTEL * numHoteles);
    }

    boolean comprar(Jugador jugador) {
        this.propietario = jugador;
        this.propietario.paga(precioCompra);

        return true;
    }

    public int cantidadCasasHoteles() {
        return this.numCasas + this.numHoteles;
    }

    void informe(int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento(todos.get(iactual).getNombre()
                + " ha caido en la casilla: " + this.toString());

    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        TipoCasilla tipo = this.tipo;

        switch (tipo) {
            case CALLE:
                recibeJugador_calle(iactual, todos);
                break;
            case SORPRESA:
                recibeJugador_sorpresa(iactual, todos);
                break;
            case DESCANSO:
                informe(iactual, todos);
                break;
        }

    }

    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
        this.informe(iactual, todos);
        Jugador jugador = todos.get(iactual);

        if (!this.tienePropietario())
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
        if (this.propietario != null) 
            resultado = true;
        

        return resultado;

    }

    public void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && !this.esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
            this.propietario.recibe(this.getPrecioAlquilerCompleto());
        }
    }

    boolean construirCasa(Jugador jugador) {
        jugador.paga(precioEdificar);
        numCasas++;
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
        if (jugador == this.propietario) 
            resultado = true;
        
        return resultado;

    }

    boolean igualdadIdentidad(Casilla otraParcela) {
        boolean igual = false;

        if (this == otraParcela) 
            igual = true;
        

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

        if (!this.tienePropietario()) 

            switch (this.tipo) {
                case CALLE:
                    Cadena = "Calle " + Nombre + ". \n  Precios: \n     Compra: " + precioCompra + ", Edificar: " + precioEdificar + ", Alquiler Base: " + precioBaseAlquiler
                            + ". \n     Cuenta con: " + numCasas + " casas y " + numHoteles + " hoteles.\n\n";
                    break;
                case SORPRESA:
                    Cadena = "Sorpresa";
                    break;
                case DESCANSO:
                    Cadena = "Descanso";
                    break;
            }

         else 
            Cadena = Nombre + " Esta calle es de: " + this.propietario.getNombre();
        

        return Cadena;
    }
}