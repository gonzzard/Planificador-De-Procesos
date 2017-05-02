package Algoritmos.Implementacion.RR;

import Algoritmos.Algoritmo;
import Procesos.Proceso;
import Utils.Constantes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 *
 */
public class RR extends Algoritmo
{
    public int quantum;
    public int quantumRestantes;
    public JLabel lblCiclosRestanes;

    public RR(List<Proceso> procesos, JTable tblColaProcesos,
            JTable tblColaProcesosListos, JTable tblColaProcesosBloqueados,
            JTable tblColaProcesosSinLlegar, Graphics2D graficaPanel,
            Graphics2D graficaBuffer, BufferedImage bufferPanelGantt, int quantum, JLabel lblCiclosRestanes)
    {
        super(procesos, tblColaProcesos, tblColaProcesosListos, tblColaProcesosBloqueados,
                tblColaProcesosSinLlegar, graficaPanel, graficaBuffer, bufferPanelGantt);

        this.quantum = quantum;
        this.quantumRestantes = quantum;
        this.lblCiclosRestanes = lblCiclosRestanes;
        this.lblCiclosRestanes.setText("CiclosRestantes : " + this.quantumRestantes);
    }

    @Override
    public boolean run(int seconds)
    {
        while (!fin)
        {
            PasoSiguiente();
        }
        return true;
    }

    @Override
    public boolean PasoSiguiente()
    {
        if (!fin)
        {
            System.out.println("*********************** T = " + tiempoProcesador);

            procesoSaliente = null;

            tratarColaProcesosSinLlegar();

            repintarColaProcesosSinLlegar();

            tratarColaProcesosBloqueados();

            repintarColaProcesosBloqueados();

            tratarColaProcesosListos();

            repintarColaProcesosListos();

            repintarColaProcesosBloqueados();

            repintarColaProcesos();

            if (colaProcesosSinLlegar.isEmpty() && colaProcesosListos.isEmpty()
                    && colaProcesosBloqueados.isEmpty() && procesoEnCurso == null)
            {
                fin = true;
            } else
            {
                tiempoProcesador++;
            }

            System.out.println("********************************************");
            System.out.println("********************************************\n");
        } else
        {
            System.out.println("*** FIN DE LA SIMULACIÓN");
            System.out.println("********************************************");
            System.out.println("********************************************\n");
        }
        return fin;
    }

    private void tratarColaProcesosSinLlegar()
    {
        System.out.println("*** INICIO TRATAMIENTO COLA DE NO LLEGADOS");

        Proceso procesoSinLlegar;

        if (colaProcesosSinLlegar.isEmpty())
        {
            System.out.println("Estado de la cola de no llegados: No hay procesos SIN llegar");
        } else
        {
            String log = "";
            for (Proceso proceso : colaProcesosSinLlegar)
            {
                log = log.concat("#" + proceso.id + " ");
            }
            System.out.println("Estado de la cola de no llegados (" + colaProcesosSinLlegar.size() + "): " + log);
        }

        for (int i = 0; i < colaProcesosSinLlegar.size(); i++)
        {
            procesoSinLlegar = colaProcesosSinLlegar.get(i);

            if (procesoSinLlegar.tiempoDeLlegada == tiempoProcesador)
            {
                procesoSinLlegar.estado = Constantes.PROCESO_EN_CPU1;
                colaProcesosSinLlegar.remove(i);
                i--;
                colaProcesosListos.add(procesoSinLlegar);
                System.out.println("El proceso #" + procesoSinLlegar.id + " SI ha llegado, se mueve a listos");
            } else
            {
                System.out.println("El proceso #" + procesoSinLlegar.id + " todavía NO ha llegado");
            }
        }

        System.out.println("*** FIN TRATAMIENTO COLA DE NO LLEGADOS\n");
    }

    private void tratarColaProcesosBloqueados()
    {
        System.out.println("*** INICIO TRATAMIENTO COLA DE BLOQUEADOS");

        Proceso procesoBloqueado;

        if (colaProcesosBloqueados.isEmpty())
        {
            System.out.println("No hay procesos bloqueados");
        }

        // Tratamos la cola de bloqueados
        for (int i = 0; i < colaProcesosBloqueados.size(); i++)
        {
            procesoBloqueado = colaProcesosBloqueados.get(i);

            procesoBloqueado.tiempoEjecutadoEnES++;
            añadirCuadrado(Color.RED, procesoBloqueado.id, tiempoProcesador);
            System.out.println("El proceso #" + procesoBloqueado.id + " se encuentra bloqueado");

            if (procesoBloqueado.tiempoEjecutadoEnES == procesoBloqueado.tiempoES)
            {
                // Terminó su tiempo bloqueado
                if (procesoBloqueado.tiempoCPU2 == 0)
                {
                    // No tiene ejecución en  CPU2, terminó su ejecución general
                    procesoBloqueado.estado = Constantes.PROCESO_COMPLETADO;
                    procesoBloqueado.ultimaModificacion = tiempoProcesador;
                    System.out.println("El proceso #" + procesoBloqueado.id + " sale a bloqueado y termina su ejecución");
                    this.quantumRestantes = quantum;
                    actualizarLblCiclosRestantes();
                } else
                {
                    // Tiene pendiente su ejecución en CPU2, entra en CPU2
                    procesoBloqueado.estado = Constantes.PROCESO_EN_CPU2;
                    procesoBloqueado.ultimaModificacion = tiempoProcesador;
                    colaProcesosListos.add(procesoBloqueado);
                    System.out.println("El proceso #" + procesoBloqueado.id + " sale a CPU2");
                }
                colaProcesosBloqueados.remove(i);
                i--;
            }
        }

        System.out.println("*** FIN TRATAMIENTO COLA DE BLOQUEADOS\n");
    }

    private void tratarColaProcesosListos()
    {
        System.out.println("*** INICIO TRATAMIENTO NUEVO PROCESO DE COLA DE LISTOS");

        // Saco un proceso y le hago ejecutarse
        if (procesoEnCurso == null && colaProcesosListos.size() > 0
                && colaProcesosListos.get(0).ultimaModificacion != tiempoProcesador)
        {
            procesoEnCurso = colaProcesosListos.get(0);
            colaProcesosListos.remove(0);

            System.out.println("Sacando Proceso de la cola de listo, #" + procesoEnCurso.id);
        } 
        else if (procesoEnCurso != null && quantumRestantes != 0)
        {
            System.out.println("El proceso #" + procesoEnCurso.id + " va a continuar ejecutándose");
        } 
        else if (quantumRestantes == 0)
        {
            System.out.println("Al proceso #" + procesoEnCurso.id + " se le ha terminado el tiempo, expulsandolo de procesador");
            System.out.println("Añadiendo proceso #" + procesoEnCurso.id + " a la cola de listos");
            colaProcesosListos.add(procesoEnCurso);
            System.out.println("Sacando el proceso #" + procesoEnCurso.id + " de cola de listos");
            procesoEnCurso = colaProcesosListos.get(0);
            colaProcesosListos.remove(0);
            System.out.println("Reseteando quantum");
            this.quantumRestantes = this.quantum;
            actualizarLblCiclosRestantes();
        } 
        else
        {
            System.out.println("No hay procesos listos");
        }

        System.out.println("****** INICIO TRATAMIENTO PROCESOS EN ESPERA");

        for (Proceso procesoEnEspera : colaProcesosListos)
        {
            System.out.println("El proceso #" + procesoEnEspera.id + " está en espera");
            procesoEnEspera.tiempoDeEsperaAcum++;
            añadirCuadrado(Color.ORANGE, procesoEnEspera.id, tiempoProcesador);
        }

        System.out.println("****** FIN TRATAMIENTO PROCESOS EN ESPERA");

        if (procesoEnCurso != null)
        {
            if (procesoEnCurso.estado == Constantes.PROCESO_EN_CPU1)
            {
                System.out.println("El proceso #" + procesoEnCurso.id + " está en CPU1");
                procesoEnCurso.tiempoEjecutadoEnCPU1++;
                actualizarLblCiclosRestantes();
                quantumRestantes--;
                añadirCuadrado(Color.GREEN, procesoEnCurso.id, tiempoProcesador);

                if (procesoEnCurso.tiempoEjecutadoEnCPU1 == procesoEnCurso.tiempoCPU1)
                {
                    // Ha terminado su ejecución en CPU1
                    if (procesoEnCurso.tiempoEjecutadoEnES == procesoEnCurso.tiempoES)
                    {
                        // No tiene pendiente ejecución en ES
                        if (procesoEnCurso.tiempoCPU2 == 0)
                        {
                            // No tiene ejecución en  CPU2, terminó su ejecución general
                            procesoEnCurso.estado = Constantes.PROCESO_COMPLETADO;
                            System.out.println("El proceso #" + procesoEnCurso.id + " ha terminado");
                            procesoEnCurso = null;
                            this.quantumRestantes = quantum;
                            actualizarLblCiclosRestantes();
                        } else
                        {
                            // Tiene pendiente su ejecución en CPU2, entra en CPU2
                            procesoEnCurso.estado = Constantes.PROCESO_EN_CPU2;
                            colaProcesosListos.add(procesoEnCurso);
                            System.out.println("El proceso #" + procesoEnCurso.id + " entra en CPU2");
                            procesoEnCurso = null;
                            this.quantumRestantes = quantum;
                            actualizarLblCiclosRestantes();
                        }
                    } else
                    {
                        // Tiene ejecución pendiente en ES
                        procesoEnCurso.estado = Constantes.PROCESO_EN_ES;
                        colaProcesosBloqueados.add(procesoEnCurso);
                        procesoSaliente = procesoEnCurso;
                        System.out.println("El proceso #" + procesoEnCurso.id + " entra en E/S");
                        procesoEnCurso = null;
                        this.quantumRestantes = quantum;
                        actualizarLblCiclosRestantes();
                    }
                } else
                {
                    System.out.println("El proceso #" + procesoEnCurso.id + " seguirá en CPU1");
                }
            } else if (procesoEnCurso.estado == Constantes.PROCESO_EN_CPU2)
            {
                System.out.println("El proceso #" + procesoEnCurso.id + " está en CPU2");

                procesoEnCurso.tiempoEjecutadoEnCPU2++;
                actualizarLblCiclosRestantes();
                quantumRestantes--;
                añadirCuadrado(Color.GREEN, procesoEnCurso.id, tiempoProcesador);

                if (procesoEnCurso.tiempoEjecutadoEnCPU2 == procesoEnCurso.tiempoCPU2)
                {
                    // Terminó su ejecución en CPU2 y su ejecución en general
                    System.out.println("El proceso #" + procesoEnCurso.id + " ha terminado");
                    procesoEnCurso.estado = Constantes.PROCESO_COMPLETADO;
                    procesoEnCurso = null;
                    this.quantumRestantes = quantum;
                    actualizarLblCiclosRestantes();
                }
            }
        }

        System.out.println("*** FIN TRATAMIENTO NUEVO PROCESO DE COLA DE LISTOS \n");
    }

    private void actualizarLblCiclosRestantes()
    {
        this.lblCiclosRestanes.setText("CiclosRestantes : " + this.quantumRestantes);
    }
}
