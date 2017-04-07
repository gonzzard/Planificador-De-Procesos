/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Gonzalo de las heras
 */
public class Rectangulo extends Rectangle2D.Double
{
    Color colorSeleccionado;
    Stroke anchoTrazoSeleccionado;
    public int id;

    /**
     * Constructor por defecto.
     */
    public Rectangulo(Color color, Stroke stroke, int id)
    {
        super();
        this.colorSeleccionado = color;
        this.anchoTrazoSeleccionado = stroke;
        this.id = id;
    }

    /**
     * Método para pintar el rectángulo en un elemento gráfico.
     *
     * @param g2 Elemento gráfico para saber donde pintar.
     */
    public void pintar(Graphics2D g2, String id)
    {
        g2.setStroke(anchoTrazoSeleccionado);
        g2.setColor(Color.BLACK);
        g2.setPaint(Color.BLACK);
        g2.drawString(id, (int) x, (int) y - 5);
        g2.draw(this);
        g2.fill(this);
    }
}
