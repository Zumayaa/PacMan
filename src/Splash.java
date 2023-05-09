import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Splash extends JDialog {
	private ReproductorMusica reproductor = new ReproductorMusica();
	private sounds sonidos = new sounds();
    private JProgressBar barra;
    private JLabel l2;
    public void esperar() {
        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Ventana v1 = new Ventana();


    //PROPIEDADES DIALOOGO
    public Splash() {
        inicio();
        setSize(452, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setUndecorated(true);

        v1.setBounds(11110, 11110, 600, 600);
        inicioHilo();
    }

    //PROPIEDADES ELEMENTOS
    private void inicio() {
        //Imagen de fondo
        ImageIcon imagen = new ImageIcon("imagenes/pantallaDeCarga3.png");
        // Crea el objeto JLabel
        JLabel etiqueta = new JLabel(imagen);

        // Agrega la etiqueta al panel
        etiqueta.setSize(452, 600);
        etiqueta.setLocation(0, 0);

        barra = new JProgressBar();
        barra.setBounds(109, 473, 231, 26);
        barra.setOpaque(true);
        barra.setForeground(Color.decode("#EF8200"));
        getContentPane().add(barra);


        l2 = new JLabel();
        l2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        l2.setBounds(180, 511, 90, 30);
        l2.setForeground(Color.decode("#EF8200"));
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
            super("Instrucciones"); // título de la ventana
            setSize(600, 600); // tamaño de la ventana

            setLocationRelativeTo(null);
            getContentPane().setLayout(null);
            setUndecorated(true);
            getContentPane().setBackground(Color.black);

            JLabel label = new JLabel("INSTRUCCIONES");
            label.setForeground(Color.yellow);
            label.setFont(new Font("Tahoma", Font.PLAIN, 30));
            label.setBounds(180, 30, 500, 100);
            add(label);

            JLabel evadeTitle = new JLabel("Evade a los fantasmas!");
            evadeTitle.setForeground(Color.yellow);
            evadeTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
            evadeTitle.setBounds(50, 50, 300, 300);
            add(evadeTitle);

            JLabel point1 = new JLabel("");
            point1.setSize(20, 20);
            point1.setLocation(20, 190);
            ImageIcon point1img = new ImageIcon("imagenes/rojo.png");
            Icon iconoPoint1 = new ImageIcon(point1img.getImage().getScaledInstance(point1.getWidth(), point1.getHeight(), Image.SCALE_DEFAULT));
            point1.setIcon(iconoPoint1);
            add(point1);

            JLabel consigue = new JLabel("Consigue todos los puntos para ganar!");
            consigue.setForeground(Color.yellow);
            consigue.setFont(new Font("Tahoma", Font.PLAIN, 18));
            consigue.setBounds(50, 100, 500, 300);
            add(consigue);

            JLabel point2 = new JLabel("");
            point2.setSize(23, 23);
            point2.setLocation(19, 240);
            ImageIcon point2img = new ImageIcon("imagenes/rosa.png");
            Icon iconoPoint2 = new ImageIcon(point2img.getImage().getScaledInstance(point2.getWidth(), point2.getHeight(), Image.SCALE_DEFAULT));
            point2.setIcon(iconoPoint2);
            add(point2);

            JLabel energizarte = new JLabel("Come los puntos grandes para poder energizarte!");
            energizarte.setForeground(Color.yellow);
            energizarte.setFont(new Font("Tahoma", Font.PLAIN, 18));
            energizarte.setBounds(50, 150, 500, 300);
            add(energizarte);

            JLabel point3 = new JLabel("");
            point3.setSize(23, 23);
            point3.setLocation(19, 287);
            ImageIcon point3img = new ImageIcon("imagenes/cyan.png");
            Icon iconoPoint3 = new ImageIcon(point3img.getImage().getScaledInstance(point3.getWidth(), point3.getHeight(), Image.SCALE_DEFAULT));
            point3.setIcon(iconoPoint3);
            add(point3);

            JLabel attack = new JLabel("Después de energizarte, ataca!");
            attack.setForeground(Color.yellow);
            attack.setFont(new Font("Tahoma", Font.PLAIN, 18));
            attack.setBounds(50, 200, 500, 300);
            add(attack);

            JLabel point4 = new JLabel("");
            point4.setSize(23, 23);
            point4.setLocation(19, 338);
            ImageIcon point4img = new ImageIcon("imagenes/naranja.png");
            Icon iconoPoint4 = new ImageIcon(point4img.getImage().getScaledInstance(point4.getWidth(), point4.getHeight(), Image.SCALE_DEFAULT));
            point4.setIcon(iconoPoint4);
            add(point4);

            JLabel fruit = new JLabel("Come las frutas para puntos extra!");
            fruit.setForeground(Color.yellow);
            fruit.setFont(new Font("Tahoma", Font.PLAIN, 18));
            fruit.setBounds(50, 250, 500, 300);
            add(fruit);

            JLabel point5 = new JLabel("");
            point5.setSize(23, 23);
            point5.setLocation(19, 390);
            ImageIcon point5img = new ImageIcon("imagenes/fruta.png");
            Icon iconoPoint5 = new ImageIcon(point5img.getImage().getScaledInstance(point5.getWidth(), point5.getHeight(), Image.SCALE_DEFAULT));
            point5.setIcon(iconoPoint5);
            add(point5);

            JLabel eatin = new JLabel("");
            eatin.setSize(50, 50);
            eatin.setLocation(320, 370);
            ImageIcon eatingif = new ImageIcon("imagenes/fruit.gif");
            Icon iconoeatin = new ImageIcon(eatingif.getImage().getScaledInstance(eatin.getWidth(), eatin.getHeight(), Image.SCALE_DEFAULT));
            eatin.setIcon(iconoeatin);
            add(eatin);

            JLabel attackin = new JLabel("");
            attackin.setSize(50, 50);
            attackin.setLocation(290, 319);
            ImageIcon attackingif = new ImageIcon("imagenes/attack.gif");
            Icon iconoattackin = new ImageIcon(attackingif.getImage().getScaledInstance(attackin.getWidth(), attackin.getHeight(), Image.SCALE_DEFAULT));
            attackin.setIcon(iconoattackin);
            add(attackin);

            JLabel evadeDescription = new JLabel("");
            evadeDescription.setSize(200, 100);
            evadeDescription.setLocation(240, 150);
            ImageIcon gif = new ImageIcon("imagenes/evade.gif");
            Icon icono = new ImageIcon(gif.getImage().getScaledInstance(evadeDescription.getWidth(), evadeDescription.getHeight(), Image.SCALE_DEFAULT));
            evadeDescription.setIcon(icono);
            add(evadeDescription);

            ParpadeoBoton atras = new ParpadeoBoton("Regresar al menú");
            atras.setFont(new Font("Tahoma", Font.PLAIN, 18));
            atras.setBounds(150, 500, 300, 40);

            Timer temporizador = new Timer(500, atras);
            temporizador.start();

            atras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // cerrar la ventana actual
                }
            });

            add(atras);

        }
    }

    //clase para la música
    public class ReproductorMusica {
        private Clip clip;

        public void reproducir(String rutaArchivo) {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
                clip.open(inputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        }

        public void detener() {
            clip.stop();
        }
    }
    //clase para sonidos iniciales
	public class sounds {
	    private Clip clip;

	    public void reproducir(String rutaArchivo) {
	        try {
	            if (clip != null && clip.isRunning()) {
	                clip.stop();
	            }
	            clip = AudioSystem.getClip();
	            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(rutaArchivo));
	            clip.open(inputStream);
	            clip.setFramePosition(0);
	            clip.start();
	        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void detener() {
	        if (clip != null) {
	            clip.stop();
	        }
	    }
	}

    public class InformacionVentana extends JFrame {

        public InformacionVentana() {
            super("Controles"); // título de la ventana
            setSize(600, 600); // tamaño de la ventana

            setLocationRelativeTo(null);
            getContentPane().setLayout(null);
            setUndecorated(true);

            getContentPane().setBackground(Color.black);

            JLabel label = new JLabel("CONTROLES");
            label.setFont(new Font("Tahoma", Font.PLAIN, 30));
            label.setForeground(Color.yellow);
            label.setBounds(210, 30, 500, 100);
            add(label);

            JLabel control = new JLabel("Para poder moverte por el mapa utiliza las flechas de tu teclado o WASD!");
            control.setFont(new Font("Tahoma", Font.PLAIN, 15));
            control.setForeground(Color.yellow);
            control.setBounds(60, 90, 600, 100);
            add(control);

            JLabel directions = new JLabel("");
            directions.setSize(270, 170);
            directions.setLocation(10, 190);
            ImageIcon directionsGIF = new ImageIcon("imagenes/directions.png");
            Icon iconoDirections = new ImageIcon(directionsGIF.getImage().getScaledInstance(directions.getWidth(), directions.getHeight(), Image.SCALE_DEFAULT));
            directions.setIcon(iconoDirections);
            add(directions);

            JLabel wasd = new JLabel("");
            wasd.setSize(300, 250);
            wasd.setLocation(300, 140);
            ImageIcon wasdGIF = new ImageIcon("imagenes/wasd.png");
            Icon iconowasd = new ImageIcon(wasdGIF.getImage().getScaledInstance(wasd.getWidth(), wasd.getHeight(), Image.SCALE_DEFAULT));
            wasd.setIcon(iconowasd);
            add(wasd);

            JLabel instructions = new JLabel("");
            instructions.setSize(700, 250);
            instructions.setLocation(0, -90);
            ImageIcon instructionsGIF = new ImageIcon("imagenes/instructions.gif");
            Icon iconoinstructions = new ImageIcon(instructionsGIF.getImage().getScaledInstance(instructions.getWidth(), instructions.getHeight(), Image.SCALE_DEFAULT));
            instructions.setIcon(iconoinstructions);
            add(instructions);

            ParpadeoBoton atras = new ParpadeoBoton("Regresar al menú");
            atras.setFont(new Font("Tahoma", Font.PLAIN, 18));
            atras.setBounds(150, 480, 300, 50);

            Timer temporizador = new Timer(500, atras);
            temporizador.start();

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
            //Cambié el valor porque va demasiado lento
            int x = 0;
            String texto = " ";

            public void run() {
                try {

                    while (x <= 100) {
                        barra.setValue(x);
                        x++;
                        Thread.sleep(50);

                        if (x == 5) {
                            texto = "Cargando...";
                            l2.setText(texto);
                        } else if (x == 50) {
                            texto = "Iniciando...";
                            l2.setText(texto);
                        }
                    }
                    getContentPane().setBackground(Color.black);
                    Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fuentes/Minecraft.ttf")).deriveFont(Font.PLAIN, 13);
                    
                    JLabel title = new JLabel("");
                    title.setSize(470, 160);
                    title.setLocation(-9, 30);
                    ImageIcon titleIMG = new ImageIcon("imagenes/titulo.png");
                    Icon iconoTitle = new ImageIcon(titleIMG.getImage().getScaledInstance(title.getWidth(), title.getHeight(), Image.SCALE_DEFAULT));
                    title.setIcon(iconoTitle);
                    
                    JLabel develop = new JLabel("Developed by Andrea, Luis & Abraham");
                    develop.setSize(350, 160);
                    develop.setLocation(115, 450);
                    develop.setForeground(Color.WHITE);
                    develop.setFont(font);
                    
                    JLabel rights = new JLabel("© 1980 1990 UABCS.");
                    rights.setSize(350, 160);
                    rights.setLocation(167, 470);
                    rights.setForeground(Color.yellow);
                    rights.setFont(font);
                    
                    JLabel rights2 = new JLabel("All rights deserved");
                    rights2.setSize(350, 160);
                    rights2.setLocation(176, 490);
                    rights2.setForeground(Color.yellow);
                    rights2.setFont(font);
                    
                    JLabel evadeDescription = new JLabel("");
                    evadeDescription.setSize(150, 140);
                    evadeDescription.setLocation(150, 145);
                    ImageIcon gif = new ImageIcon("imagenes/pacman-menu.png");
                    Icon icono = new ImageIcon(gif.getImage().getScaledInstance(evadeDescription.getWidth(), evadeDescription.getHeight(), Image.SCALE_DEFAULT));
                    evadeDescription.setIcon(icono);
                    
                    
                    ParpadeoBoton play = new ParpadeoBoton("PLAY     >");
                    play.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    play.setBounds(130, 300, 200, 30);

                    ParpadeoBoton instrucciones = new ParpadeoBoton("INSTRUCTIONS");
                    instrucciones.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    instrucciones.setBounds(130, 345, 200, 30);

                    ParpadeoBoton equipo = new ParpadeoBoton("CONTROLS");
                    equipo.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    equipo.setBounds(130, 390, 200, 30);

                    ParpadeoBoton salir = new ParpadeoBoton("EXIT GAME");
                    salir.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    salir.setBounds(130, 435, 200, 30);

                    getContentPane().removeAll();

                    
                    reproductor.reproducir("musica/music.wav");
                    getContentPane().add(play);
                    getContentPane().add(instrucciones);
                    getContentPane().add(equipo);
                    getContentPane().add(salir);
                    getContentPane().add(title);
                    getContentPane().add(develop);
                    getContentPane().add(rights);
                    getContentPane().add(rights2);
                    getContentPane().add(evadeDescription);

                    revalidate();
                    repaint();

                    Timer temporizador = new Timer(500, play);
                    temporizador.start();

                    play.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            v1.setVisible(true);
                            v1.setLocationRelativeTo(null);
                            reproductor.detener();
                            sonidos.reproducir("musica/SonidoInicio.wav");
                            esperar();
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