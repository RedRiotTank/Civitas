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
public class Jugador implements Comparable<Jugador> {

    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    private static float SaldoInicial = 7500;

    private int casillaActual;
    private String nombre;
    private boolean puedeComprar;
    private float saldo;

    private ArrayList<Casilla> propiedades;

    int cantidadCasasHoteles() {
        return this.propiedades.size();
    }

    //@Override
    public int compareTo(Jugador otro) {
        return Float.compare(otro.saldo, this.saldo);
    }

    boolean comprar(Casilla titulo) {
        boolean result = false;
        
        if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            if(puedoGastar(precio)){
                result = titulo.comprar(this);
                this.propiedades.add(titulo);
                Diario.getInstance().ocurreEvento("El jugador " + this + " compra la propiedad" + "título");
                this.puedeComprar = false;
            }
            else
                Diario.getInstance().ocurreEvento("El jugador " + this + " no tiene saldo para comprar la propiedad " + titulo);
            
            
        }
        
        
        return result;
    }

    boolean construirCasa(int ip) {
        boolean result = false;
        
        boolean existe = this.existeLaPropiedad(ip);
        
        if(existe){
            Casilla propiedad = this.propiedades.get(ip);
            Boolean puedoEdificar = this.puedoEdificarCasa(propiedad);
            if(puedoEdificar){
                result = propiedad.construirCasa(this);
                Diario.getInstance().ocurreEvento("El Jugador " + this.nombre + " construye una casa en la propiedad " + ip);
            }
        }
        
        
        return result;
    }

    boolean construirHotel(int ip) {
        boolean result = false;
        
        if(this.existeLaPropiedad(ip)){
           Casilla propiedad = propiedades.get(ip);
           boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
           
           if(puedoEdificarHotel){
               result = propiedad.construirHotel(this);
               propiedad.derruirCasas(CasasPorHotel, this);
               Diario.getInstance().ocurreEvento("El jugador" + this.nombre + " contruye hotel en la propiedad " + ip);
           }
        }
        
        return result;
    }

    boolean enBancarrota() {
        boolean bancarrota = false;
        if (this.saldo <= 0) {
            bancarrota = true;
        }

        return bancarrota;

    }

    private boolean existeLaPropiedad(int ip) { //?????
        boolean existe = false;
        for (int i = 0; i < this.propiedades.size(); i++) {
            if (ip == i) {
                existe = true;
            }
        }

        return existe;
    }

    private int getCasasMax() {
        return this.CasasMax;
    }

    int getCasasPorHotel() {
        return CasasPorHotel;
    }

    int getCasillaActual() {
        return this.casillaActual;
    }

    private int getHotelesMax() {
        return HotelesMax;
    }

    protected String getNombre() {
        return nombre;
    }

    private float getPremioPasoSalida() {
        return PasoPorSalida;
    }

    protected ArrayList<Casilla> getPropiedades() {
        return this.propiedades;
    }

    boolean getPuedeComprar() {
        return this.puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }

    Jugador(String nombre) {
        casillaActual = 0;
        this.nombre = nombre;
        puedeComprar = false;
        propiedades = new ArrayList<>();
        this.saldo = SaldoInicial;
    }

    protected Jugador(Jugador otro) {
        casillaActual = otro.getCasillaActual();
        this.nombre = otro.getNombre();
        puedeComprar = otro.puedeComprarCasilla();
        propiedades = otro.getPropiedades();
        this.saldo = otro.saldo;
    }

    boolean modificaSaldo(float cantidad) {

        this.saldo = this.saldo + cantidad;
        Diario.getInstance().ocurreEvento(nombre + " modifica " + cantidad);

        return true;
    }

    boolean moverACasilla(int numCasilla) {
        this.casillaActual = numCasilla;
        this.puedeComprar = false;
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " se movió a la casilla " + this.getCasillaActual());
        return true;
    }

    boolean paga(float cantidad) {
        return this.modificaSaldo(-1 * cantidad);
    }

    boolean pagaAlquiler(float cantidad) {
        return this.paga(cantidad);

    }

    boolean pasaPorSalida() {
        this.recibe(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " ha pasado por la casilla de salida");

        return true;
    }

    boolean puedeComprarCasilla() {
        this.puedeComprar = true;
        return true;
    }

    private boolean puedoEdificarCasa(Casilla propiedad) {
        boolean PuedoEdificar = false;
        
        float precioEdificar = propiedad.getPrecioEdificar();

        if (this.puedoGastar(precioEdificar) && propiedad.getNumCasas() < getCasasMax()) 
            PuedoEdificar = true;
        

        return PuedoEdificar;
    }

    private boolean puedoEdificarHotel(Casilla propiedad) {
        boolean puedoEdificarHotel = false;
        
        float precio = propiedad.getPrecioEdificar();
        
        if(puedoGastar(precio) && propiedad.getNumHoteles() < this.getHotelesMax() && propiedad.getNumCasas() >= this.getCasasPorHotel())
            puedoEdificarHotel = true;
        
        return puedoEdificarHotel;
    }

    private boolean puedoGastar(float precio) {
        boolean puedeGastar = false;

        if (precio <= this.getSaldo()) {
            puedeGastar = true;
        }
        return puedeGastar;
    }

    boolean recibe(float cantidad) {
        return this.modificaSaldo(cantidad);
    }

    boolean tieneAlgoQueGestionar() {
        boolean TienePropiedades = false;

        if (this.propiedades.size() != 0) {
            TienePropiedades = true;
        }

        return TienePropiedades;
    }

 public String toString() {     //REVISAR (código de isma)
        
     String salida = null;
     salida = "\n" + this.nombre + " en casilla: " + this.casillaActual + "\n" + "Saldo: " + this.getSaldo() + " $\n";
     if (this.propiedades.size() == 0)
         salida += "No dispone de ninguna propiedad. \n" ;
        for(int i = 0; i < this.propiedades.size(); i++)
            salida += "Propiedad: " + (i + 1) + ": " + this.propiedades.get(i).getNumCasas() + " casas y "
                    + this.propiedades.get(i).getNumHoteles() + " hoteles.\n";
        
        return salida;
    }
}
