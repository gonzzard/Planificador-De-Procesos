package Procesos;

import Utils.Constantes;

/**
 * Clase que representa un proceso en un sistema operativo
 */
public class Proceso implements Comparable<Proceso>
{

    /**
     * Identificador único del proceso.
     */
    public int id;
    
    /**
     * Tiempos del procesador en CPU1, E/S y CPU2.
     */
    public int tiempoCPU1, tiempoCPU2, tiempoES;
    
    /**
     * Prioridad del proceso.
     */
    public int prioridad;
    
    /**
     * Estado actual del proceso.
     */
    public int estado;
    public int tiempoDeLlegada;
    public int tiempoEjecutadoEnCPU1, tiempoEjecutadoEnCPU2, tiempoEjecutadoEnES;
    public int tiempoDeSalida;
    public int ultimaModificacion;
    
    //Estadisticas
    public int tiempoDeEsperaAcum;
    
    public float runTime;


    /**
     * Constructor con parámetros.
     * 
     * @param id Identificador único del proceso.
     * @param tiempoDeLlegada Tiempo de llegada del proceso.
     * @param tiempoCPU1 Tiempo del proceso en CPU1.
     * @param tiempoES Tiempo del proceso en E/S.
     * @param tiempoCPU2 Tiempo del proceso en CPU2.
     * @param prioridad Prioridad del proceso
     */
    public Proceso(int id, int tiempoDeLlegada, int tiempoCPU1, int tiempoES,
            int tiempoCPU2, int prioridad)
    {
        this.id = id;
        this.tiempoDeLlegada = tiempoDeLlegada;
        this.tiempoCPU1 = tiempoCPU1;
        this.tiempoCPU2 = tiempoCPU2;
        this.tiempoES = tiempoES;
        this.prioridad = prioridad;
        
        this.estado = Constantes.PROCESO_EN_CPU1;
        
        this.tiempoEjecutadoEnCPU1 = 0;
        this.tiempoEjecutadoEnCPU2 = 0;
        this.tiempoEjecutadoEnES = 0;
        this.tiempoDeSalida = tiempoCPU1 + tiempoCPU2 + tiempoES;
        
        this.runTime = 0 + (prioridad/100.0f);
        
        this.ultimaModificacion = -1;
        this.tiempoDeEsperaAcum = 0;
    }
    
    /**
     * Devuelve el porcentaje completado del proceso.
     * 
     * @return Devuelve el procejate completado del proceso en formato xxx%
     */
    public String getPorcentajeCompletado()
    {
        int completado = tiempoEjecutadoEnCPU1 + tiempoEjecutadoEnCPU2 + tiempoEjecutadoEnES;
        int total = tiempoCPU1 + tiempoCPU2 + tiempoES;
        
        return (completado * 100) / total + "%"; 
    }

    @Override
    public int compareTo(Proceso o)
    {
        if (this.runTime < o.runTime)
        {
            return -1;
        }
        else if (this.runTime > o.runTime)
        {
            return 0;
        }
        else if(this.runTime == o.runTime)
        {
            if(this.prioridad < o.prioridad)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        return 1;
    }
}
