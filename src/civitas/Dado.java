package civitas;

import java.util.Random;

/**
 *
 * @author albertoplaza
 */
public class Dado {

    static private final int VALORDEBUG = 1;
    static private final int VALORESDADO = 6;
    static final private Dado instance = new Dado();
    
    private Random random;
    private int ultimoResultado;
    boolean debug;

    Dado() {
        this.random = new Random();
        this.ultimoResultado = -1;
        debug = false;
    }

    public static Dado getInstance() {
        return instance;
    }
    
    int getUltimoResultado() {
        return ultimoResultado;
    }

    int tirar() {
        int devuelve = Dado.VALORDEBUG;
        
        if (!debug) {
            devuelve = random.nextInt(Dado.VALORESDADO);
            devuelve++;                                //+1 para que salga el 6 también y no salga el 0.
        }
        
        ultimoResultado = devuelve;
        
        return devuelve;
    }

    int quienEmpieza(int n) {
        return random.nextInt(n);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        
        if(debug)
            Diario.getInstance().ocurreEvento("---Civitas en modo debug---");
    }

}
