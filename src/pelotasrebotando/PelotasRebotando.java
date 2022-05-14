package pelotasrebotando;

import java.awt.Color;
import javax.swing.JFrame;

public class PelotasRebotando {

    static Lienzo lienzo;
    static JFrame ventana;
    
    public static void main(String[] args) /*throws InterruptedException*/ {
        //Creo una ventana
        ventana = new JFrame("Bolas Rebotando");
        
        //Ajustamos el tamaño de la ventana
        ventana.setSize(300, 300);
        
        
        //La coloco en el centro de la pantalla
        ventana.setLocationRelativeTo(null);
        
        //Le digo que se cierre al pulsar la "X"
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Lo configuramos para que cualquier añadido (getContentPane().add();) 
        //lo añada en el eje de las Y empezando por arriba y añadiendo después.
        //getContentPane().add(xxx) añade el objeto xxx a la clase que lo llama
        //ventana.getContentPane().setLayout(new BoxLayout(ventana.getContentPane(),BoxLayout.Y_AXIS));
        
        //Creamos un objeto lienzo (canvas) donde vamos poner el bucle que se va 
        //a repetir todo el rato dibujando constantemente.
        lienzo = new Lienzo(ventana);
        lienzo.setBackground(Color.black);
        
        
        //Se lo añadimos a la ventana
        ventana.getContentPane().add(lienzo);
        
        //Hacemos visible la ventana
        ventana.setVisible(true);
        
        //Inicia el programa
        lienzo.play();
    }
}
