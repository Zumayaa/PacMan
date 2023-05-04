import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JDialog {

    private JProgressBar barra;
    private JLabel l2;

    //PROPIEDADES DIALOOGO
    public Splash() {
        inicio();
        setSize(600,600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setUndecorated(true);

        inicioHilo();
    }

    //PROPIEDADES ELEMENTOS
    private void inicio() {

        //Imagen de fondo
        ImageIcon imagen = new ImageIcon("");
        // Crea el objeto JLabel
        JLabel etiqueta = new JLabel(imagen);

        // Agrega la etiqueta al panel
        etiqueta.setSize(600, 600);
        etiqueta.setLocation(0, 0);

        barra = new JProgressBar();
        barra.setBounds(154,397,294,22);
        barra.setOpaque(true);
        barra.setForeground(Color.decode("#3E4532"));
        getContentPane().add(barra);

        l2 = new JLabel();
        l2.setFont(new Font("Tahoma", Font.PLAIN,18));
        l2.setBounds(260,310,300,300);
        getContentPane().add(l2);

        getContentPane().add(etiqueta);
    }

    public class ParpadeoBoton extends JButton implements ActionListener {
        private boolean parpadeo;

        public ParpadeoBoton(String text) {
            super(text);
            parpadeo = false;
            setBackground(Color.RED);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Botón presionado!");
                }
            });
        }

        public void actionPerformed(ActionEvent e) {
            if (parpadeo) {
                setBackground(Color.RED);
                setForeground(Color.WHITE);
                parpadeo = false;
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                parpadeo = true;
            }
        }
    }

    public class InstruccionesVentana extends JFrame {

        public InstruccionesVentana() {
            super("Controls"); // título de la ventana
            setSize(600, 600); // tamaño de la ventana

            setLocationRelativeTo(null);
            getContentPane().setLayout(null);
            setUndecorated(true);

            JLabel label = new JLabel("Aquí van las instrucciones");
            label.setFont(new Font("Tahoma", Font.PLAIN,18));
            label.setBounds(50,50,300,100);
            add(label);

            JButton atras = new JButton("Regresar al menú");
            atras.setFont(new Font("Tahoma", Font.PLAIN,18));
            atras.setBounds(200,310,200,200);
            atras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // cerrar la ventana actual
                }
            });

            add(atras);
        }
    }

    public class InformacionVentana extends JFrame {

        public InformacionVentana() {
            super("About us"); // título de la ventana
            setSize(600, 600); // tamaño de la ventana

            setLocationRelativeTo(null);
            getContentPane().setLayout(null);
            setUndecorated(true);

            JLabel label = new JLabel("Aquí va la información");
            label.setFont(new Font("Tahoma", Font.PLAIN,18));
            label.setBounds(50,50,300,100);
            add(label);

            JButton atras = new JButton("Regresar al menú");
            atras.setFont(new Font("Tahoma", Font.PLAIN,18));
            atras.setBounds(200,310,200,200);
            atras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // cerrar la ventana actual
                }
            });

            add(atras);
        }
    }

    private void inicioHilo() {
        Thread hilo = new Thread(new Runnable() {
            int x = 0;
            String texto = " ";

            public void run() {
                try {

                    while (x <= 100) {
                        barra.setValue(x);
                        x++;
                        Thread.sleep(10);

                        if (x == 5) {
                            texto = "Cargando...";
                            l2.setText(texto);
                        } else if (x == 50) {
                            texto = "Iniciando...";
                            l2.setText(texto);
                        }
                    }



                    ParpadeoBoton play = new ParpadeoBoton("PLAY     >");
                    play.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    play.setBounds(240, 420, 140, 25);

                    ParpadeoBoton instrucciones = new ParpadeoBoton("CONTROLS");
                    instrucciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    instrucciones.setBounds(240, 455, 140, 25);

                    ParpadeoBoton equipo = new ParpadeoBoton("ABOUT US");
                    equipo.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    equipo.setBounds(240, 490, 140, 25);

                    ParpadeoBoton salir = new ParpadeoBoton("EXIT GAME");
                    salir.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    salir.setBounds(240, 525, 140, 25);

                    getContentPane().removeAll();

                    getContentPane().add(play);
                    getContentPane().add(instrucciones);
                    getContentPane().add(equipo);
                    getContentPane().add(salir);

                    revalidate();
                    repaint();

                    Timer temporizador = new Timer(500, play);
                    temporizador.start();

                    play.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            Ventana v1 = new Ventana();
                            v1.setVisible(true);
                        }
                    });

                    instrucciones.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            InstruccionesVentana v1 = new InstruccionesVentana();
                            v1.setVisible(true);
                        }
                    });

                    equipo.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            InformacionVentana v1 = new InformacionVentana();
                            v1.setVisible(true);
                        }
                    });

                    salir.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            System.exit(0);
                        }
                    });

                } catch (Exception e) {
                    System.out.println("Excepcion: " + e.getMessage());
                }
            }
        });
        hilo.start();
    }

}