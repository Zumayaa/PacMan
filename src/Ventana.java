import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{

    public Ventana() {
        //PROPIEDADES VENTANA
        this.setTitle("PacMan");
        this.setSize(600,600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
