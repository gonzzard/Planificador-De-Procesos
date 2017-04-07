/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos;

import Utils.Constantes;
import java.util.List;
import Procesos.Proceso;
import Vistas.Rectangulo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gonzalo
 */
public abstract class Algoritmo
{
    protected int tiempoProcesador = 0;
    
    protected JTable tblColaProcesos;

    protected List<Proceso> colaProcesosListos, colaProcesosBloqueados, colaProcesosSinLlegar;

    protected Proceso procesoEnCurso;

    protected List<Rectangulo> elementosDibujados;

    protected int cursorRectanguloGantt;

    protected DefaultTableModel modelTblColaProcesos;

    protected boolean fin, inicio;

    /**
     * Variable de tipo <code>Graphics2D</code> que almacena los elementos
     * gráficos del panel.
     */
    protected Graphics2D graficaPanel, graficaBuffer;

    /**
     * Objeto de tipo <code>BufferImage</code> que almacena el buffer del panel
     * y un buffer del tamaño del panel y de color blanco.
     */
    protected BufferedImage bufferPanelGantt, vacio;

    public Algoritmo(List<Proceso> procesos, javax.swing.JTable tblColaProcesos, 
            Graphics2D graficaPanel, Graphics2D graficaBuffer, BufferedImage bufferPanelGantt)
    {
        this.elementosDibujados = new ArrayList<>();
        this.colaProcesosListos = new ArrayList<>();
        this.colaProcesosSinLlegar = new ArrayList<>();
        this.colaProcesosBloqueados = new ArrayList<>();
        
        this.organizarProcesos(procesos);

        //this.colaProcesosListos = procesos;
        this.procesoEnCurso = null;
        this.tblColaProcesos = tblColaProcesos;
        this.modelTblColaProcesos = (DefaultTableModel) this.tblColaProcesos.getModel();
        this.fin = false;
        this.inicio = false;

        
        this.cursorRectanguloGantt = 5;

        this.bufferPanelGantt = bufferPanelGantt;
        this.graficaPanel = graficaPanel;
        this.graficaBuffer = graficaBuffer;
    }

    public BufferedImage copiarBuffer(BufferedImage buffer)
    {
        ColorModel cm = buffer.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = buffer.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    private void organizarProcesos(List<Proceso> procesos)
    {
        Collections.sort(procesos, (Proceso p1, Proceso p2) ->
        {
            if (p1.tiempoDeLlegada < p2.tiempoDeLlegada)
            {
                return -1;
            } else
            {
                return 1;
            }
        });
        
        for (Proceso current : procesos)
        {
            colaProcesosSinLlegar.add(current);
        }        
    }

    public void añadirCuadrado(Color color, int id, int tiempo)
    {
        int desplazamientoY = 40 + 1 + (40 * (id - 1));
        int desplazamientoX = 40 + 1 + Constantes.ANCHO_CELDA_TIEMPO * tiempo;
             
        rellenarCuadrado(bufferPanelGantt, 
                    new Point(desplazamientoX, desplazamientoY),
                    Color.WHITE, 
                    color);
        
        graficaPanel.drawImage(bufferPanelGantt, 0, 0, null);
    }

    public void añadirProcesoACola(int posicion, int idProceso, int prioridad, Color color)
    {
        modelTblColaProcesos.addRow(new Object[]
        {
            posicion, idProceso, prioridad, color
        });
    }
    
    public void rellenarCuadrado(BufferedImage image, Point node, Color targetColor, Color replacementColor)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor.getRGB();
        int replacement = replacementColor.getRGB();
        if (target != replacement)
        {
            Deque<Point> queue = new LinkedList<>();
            do
            {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getRGB(x - 1, y) == target)
                {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getRGB(x, y) == target)
                {
                    image.setRGB(x, y, replacement);
                    if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target)
                    {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && image.getRGB(x, y - 1) != target)
                    {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1 && image.getRGB(x, y + 1) == target)
                    {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1 && image.getRGB(x, y + 1) != target)
                    {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.pollFirst()) != null);
        }
    }

    public abstract boolean run(int seconds);
    
    public abstract boolean PasoSiguiente();
}
