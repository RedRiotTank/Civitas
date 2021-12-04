package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class Jugador implements Comparable<Jugador> {

    protected static final int CasasMax = 4;
    protected static final int CasasPorHotel = 4;
    protected static final int HotelesMax = 4;
    protected static final float PasoPorSalida = 1000;
    private static final float SaldoInicial = 7500;

    private int casillaActual;
    private String nombre;
    private boolean puedeComprar;
    private float saldo;

    private ArrayList<CasillaCalle> propiedades;
    
    Jugador(String nombre) {
        this.casillaActual = 0;
        this.nombre = nombre;
        this.puedeComprar = false;
        this.propiedades = new ArrayList<>();
        this.saldo = SaldoInicial;
    }

    protected Jugador(Jugador otro) {
        this.casillaActual = otro.getCasillaActual();
        this.nombre = otro.getNombre();
        puedeComprar = otro.puedeComprarCasilla();
        propiedades = otro.getPropiedades();
        this.saldo = otro.getSaldo();
    }
    
    int getCasillaActual() {
        return this.casillaActual;
    }

    protected String getNombre() {
        return nombre;
    }

    protected ArrayList<CasillaCalle> getPropiedades() {
        return this.propiedades;
    }

    boolean getPuedeComprar() {
        return this.puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }

    int cantidadCasasHoteles() {
        int sumatorio = 0;
        
        for(int i=0; i<this.propiedades.size(); i++)
            sumatorio += this.propiedades.get(i).cantidadCasasHoteles();
        
        return sumatorio;
    }

    public  int getCasasMax() {
        return CasasMax;
    }

    public static int getCasasPorHotel() {
        return CasasPorHotel;
    }

    public  int getHotelesMax() {
        return HotelesMax;
    }

    public static float getPasoPorSalida() {
        return PasoPorSalida;
    }
    
    //@Override
    public int compareTo(Jugador otro) {
        return Float.compare(otro.saldo, this.saldo);
    }

    boolean comprar(CasillaCalle titulo) {
        boolean result = false;
        
        if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            
            if(puedoGastar(precio)){
                result = titulo.comprar(this);
                this.propiedades.add(titulo);
                
                Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " compra la propiedad " + titulo.getNombre());
                
                this.puedeComprar = false;
            }
            else
                Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " no tiene saldo para comprar la propiedad " + titulo.getNombre());
            
            
        }
        
        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false;
        
        boolean existe = this.existeLaPropiedad(ip);
        
        if(existe){
            CasillaCalle propiedad = this.propiedades.get(ip);
            Boolean puedoEdificar = this.puedoEdificarCasa(propiedad);
            
            if(puedoEdificar){
                result = propiedad.construirCasa(this);
                Diario.getInstance().ocurreEvento("El Jugador " + this.getNombre() + " construye una casa en la calle " + propiedad.getNombre());
            }
        }
        
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;
        
        if(this.existeLaPropiedad(ip)){
           CasillaCalle propiedad = propiedades.get(ip);
           boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
           
           if(puedoEdificarHotel){
               result = propiedad.construirHotel(this);
               propiedad.derruirCasas(CasasPorHotel, this);
               Diario.getInstance().ocurreEvento("El jugador" + this.nombre + " contruye hotel en la calle " + propiedad.getNombre());
           }
        }
        
        return result;
    }

    boolean enBancarrota() {
        boolean bancarrota = false;
        if (this.saldo <= 0) 
            bancarrota = true;
        
        return bancarrota;
    }

    private boolean existeLaPropiedad(int ip) { 
        boolean existe = false;
        for (int i = 0; i < this.propiedades.size(); i++) 
            if (ip == i) 
                existe = true;
          
        return existe;
    }

    boolean modificaSaldo(float cantidad) {
        this.saldo = this.saldo + cantidad;
        
        String operacion = null;
        
        if(cantidad >= 0)
            operacion = "incrementa";
        else
            operacion = "decrementa";
        
        
        Diario.getInstance().ocurreEvento(nombre + " " + operacion + " su saldo en " + cantidad + " $");

        return true;
    }

    boolean moverACasilla(int numCasilla) {
        this.casillaActual = numCasilla;
        this.puedeComprar = false;
        
        Diario.getInstance().ocurreEvento(this.nombre + " se movió a la casilla " + this.getCasillaActual() + "\n");
        
        return true;
    }

    boolean paga(float cantidad) {
        return this.modificaSaldo(-1 * cantidad);
    }

    boolean pagaAlquiler(float cantidad) {
        return this.paga(cantidad);

    }

    boolean pasaPorSalida() {
        this.recibe(Jugador.PasoPorSalida);
        Diario.getInstance().ocurreEvento(this.nombre + " ha pasado por la casilla de salida");

        return true;
    }

    boolean puedeComprarCasilla() {
        this.puedeComprar = true;
        
        return true;
    }

    private boolean puedoEdificarCasa(CasillaCalle propiedad) {
        boolean PuedoEdificar = false;
        
        float precioEdificar = propiedad.getPrecioEdificar();

        if (this.puedoGastar(precioEdificar) && propiedad.getNumCasas() < getCasasMax()) 
            PuedoEdificar = true;
       
        return PuedoEdificar;
    }

    private boolean puedoEdificarHotel(CasillaCalle propiedad) {
        boolean puedoEdificarHotel = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if(puedoGastar(precio) && propiedad.getNumHoteles() < getHotelesMax() && propiedad.getNumCasas() >= Jugador.getCasasPorHotel())
            puedoEdificarHotel = true;
        
        return puedoEdificarHotel;
    }

    private boolean puedoGastar(float precio) {
        boolean puedeGastar = false;

        if (precio <= this.getSaldo()) 
            puedeGastar = true;
        
        return puedeGastar;
    }

    boolean recibe(float cantidad) {
        return this.modificaSaldo(cantidad);
    }

    boolean tieneAlgoQueGestionar() {
        boolean TienePropiedades = false;

        if (this.propiedades.size() != 0) 
            TienePropiedades = true;
        
        return TienePropiedades;
    }
    
    protected Jugador convertir(){
    
        return null;
    }
    
    public void actualizaPropiedadesPorConversion(JugadorEspeculador jug){
        
        for(CasillaCalle prop: this.propiedades){
            prop.actualizaPropietarioPorConversion(jug);
        }
    }
         

    public String toString() {

        String jugadorTS = null;

        jugadorTS = "\n" + this.nombre + " está en la casilla: " + this.getCasillaActual() + "\nSaldo: " + this.getSaldo() + " $\n";

        if (this.propiedades.size() == 0) {
            jugadorTS += "No dispone de ninguna propiedad. \n";
        } else {
            jugadorTS += "Lista de propiedades: \n";

            for (int i = 0; i < this.propiedades.size(); i++) {
                jugadorTS += " " + this.getPropiedades().get(i).getNombre() + " con: " + this.propiedades.get(i).getNumCasas() + " casas y "
                        + this.propiedades.get(i).getNumHoteles() + " hoteles.\n";
            }
        }

        return jugadorTS;
    }
}
