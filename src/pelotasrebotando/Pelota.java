package pelotasrebotando;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pelota {

    //Declaración de variables
    float x, y, r;
    boolean derecha, izquierda, abajo, arriba;
    double angulo;
    Color color;

    //Constructor
    public Pelota(float posX, float posY, float diametro, double ang, Color col) {
        x = posX;
        y = posY;
        r = diametro / 2;
        angulo = ang * Math.PI / 180;
        color = col;
    }

    public void pinta(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int) x, (int) y, (int) r * 2, (int) r * 2);
    }

    public void ponColor(Color col) {
        color = col;
    }

    public float dameCentroX() {
        float centroX;
        centroX = x + r;
        return centroX;
    }

    public float dameCentroY() {
        float centroY;
        centroY = y + r;
        return centroY;
    }
}
