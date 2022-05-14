package pelotasrebotando;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Lienzo extends Canvas {

    //Constantes
    final int cajaTitulo = 37;
    final int numPelotas = 2;
    final double velocidad = 1;

    //Declaración de variables
    Graphics2D g2;
    float x;
    float y;
    float r;

    //Angulo es igual a los grados en radianes (angulo en grados * PI/180)
    double angulo;
    //boolean derecha, izquierda, abajo, arriba;
    JFrame ventana;
    Pelota[] pelotas;
    Pelota pelota1, pelota2;
    //Para poder pintar los objetos sin parpadeos
    private static BufferStrategy strategy;

    public Lienzo(JFrame vent) {
        ventana = vent;
        //Asigno el tamaño del lienzo a toda la ventana
        this.setPreferredSize(ventana.getSize());

        //Evito que el sistema llame a la funcion Repaint() por si mismo 
        this.setIgnoreRepaint(true);

        //Crea las pelotas (posicion inicial en x, posicion inicial en y, diametro, angulo inicial, color)
        //Math.rondom()*360 devuelve un numero entre 0.0 y 359.99
        pelota1 = new Pelota(100, 100, 50, Math.random() * 360, Color.green);
        pelota2 = new Pelota(200, 200, 50, Math.random() * 360, Color.white);
        pelotas = new Pelota[numPelotas];
        pelotas[0] = pelota1;
        pelotas[1] = pelota2;
    }

    public void play() {

        while (true) {
            //DIBUJA CADA PASADA LA POSICIÓN DE CADA OBJETO
            pinta();

            //RELENTIZA EL MOVIMIENTO PARA HACERLO FLUIDO
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }

    public void pinta() {
        try {
            if (strategy == null || strategy.contentsLost()) {
                // Crea BufferStrategy para el renderizado
                createBufferStrategy(2);
                strategy = getBufferStrategy();
                Graphics g = strategy.getDrawGraphics();
                g2 = (Graphics2D) g;
            }

        } catch (Exception e) {}

        // ANTIALIASING
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibuja el fondo
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        //Comprueba los rebotes con las paredes
        compruebaRebotes();

        //Dibuja la bolas
        for (int i = 0; i < numPelotas; i++) {
            pelotas[i].pinta(g2);
        }

        //Si todo funciona correctamente muestra por pantalla el dibujo
        if (!strategy.contentsLost()) {
            strategy.show();
        }
    }

    public void compruebaRebotes() {
        //Creo un bucle para que compruebe los rebotes de todas las pelotas
        for (int i = 0; i < numPelotas; i++) {
            //Si la pelota se sale por arriba o por abajo cambio el angulo
            if ((pelotas[i].y + pelotas[i].r * 2 >= ventana.getHeight() - cajaTitulo) || (pelotas[i].y <= 0)) {
                pelotas[i].angulo = (360 * Math.PI / 180) - pelotas[i].angulo;
            }
            //Si la pelota se sale por la derecha o por la izquierda cambio el angulo
            if ((pelotas[i].x + pelotas[i].r * 2 >= getWidth()) || (pelotas[i].x <= 0)) {
                pelotas[i].angulo = (180 * Math.PI / 180) - pelotas[i].angulo;
            }
            pelotas[i].x += velocidad * Math.cos(pelotas[i].angulo);
            pelotas[i].y -= velocidad * Math.sin(pelotas[i].angulo);
        }
        compruebaChoqueEntreBolas();
    }

    public void compruebaChoqueEntreBolas() {

        //Obtengo la distancia entre las pelotas mediante el teorema de Pitagoras
        double distancia = Math.sqrt(((pelotas[1].dameCentroX() - pelotas[0].dameCentroX()) * (pelotas[1].dameCentroX() - pelotas[0].dameCentroX()))
                + ((pelotas[1].dameCentroY() - pelotas[0].dameCentroY()) * (pelotas[1].dameCentroY() - pelotas[0].dameCentroY())));

        //Si la distancia entre las pelotas es menor la suma de los radios habrá choque
        if (distancia < pelota1.r + pelota2.r) {
            /*System.out.println("Choque!");

            System.out.println("Pelota 1");
            System.out.println("x: " + pelotas[0].x);
            System.out.println("y: " + pelotas[0].y);
            System.out.println("angulo: " + pelotas[0].angulo);
            System.out.println(" ");
            System.out.println("Pelota 2");
            System.out.println("x: " + pelotas[1].x);
            System.out.println("y: " + pelotas[1].y);
            System.out.println("angulo: " + pelotas[1].angulo);
            System.out.println(" ");*/

            //Cambio el angulo cuando las pelotas chocan entre si
            if (pelota2.x > pelota1.x) {
                pelotas[1].angulo = (2 * Math.PI) - (Math.sin((pelota2.y - pelota1.y) / (pelota1.r + pelota2.r)));
                pelotas[0].angulo = (Math.PI) - (Math.sin((pelota2.y - pelota1.y) / (pelota1.r + pelota2.r)));

            } else {
                pelotas[0].angulo = (2 * Math.PI) - (Math.sin((pelota1.y - pelota2.y) / (pelota1.r + pelota2.r)));
                pelotas[1].angulo = (Math.PI) - (Math.sin((pelota1.y - pelota2.y) / (pelota1.r + pelota2.r)));
            }
        }
    }
}
