import javax.imageio.ImageIO;
import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;

public class Ventana extends JFrame {

	public int px = 275;//Posicion X del persona
	public int py = 260;
	long tiempoPastilla;
	boolean pastillaActiva = true;
	private sounds reproductor = new sounds();

	int anteriorPx, anteriorPy;

	private JLabel etiqueta;
	private Image pacman;
	private ArrayList<Rect> paredes = new ArrayList<Rect>();
	private ArrayList<Rect> comidas = new ArrayList<>();
	ArrayList<Rect> fruta = new ArrayList<>();
	private ArrayList<Fantasma> fantasmas = new ArrayList<Fantasma>();

	public int puntos = 0;
	private ArrayList<Rect> punto = new ArrayList<Rect>();
	private JPanel panel = new JPanel();
	private JPanel juego = new JPanel();
	private HashMap<String, Image> imagenes = new HashMap<String, Image>();

	private boolean juegoIniciado = false;

	int vidas = 3;
	int verFruta = 1;


	//LO CAMBIE A STRING PORQUE ME DI CUENTA QUE SE OCUPABAN MUCHOS DISEÑOS DE PAREDES :'V
	private String[][] laberinto = {

			{"E", "E", "E", "E", "E", "a", "2", "2", "2", "2", "2", "2", "2", "2", "7", "2", "2", "2", "2", "2", "2", "2", "2", "b"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "P", "e", "f", "0", "e", "i", "f", "0", "1", "0", "e", "i", "f", "0", "e", "f", "P", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "g", "h", "0", "g", "j", "h", "0", "u", "0", "g", "j", "h", "0", "g", "h", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "r", "t", "0", "s", "0", "r", "l", "y", "l", "t", "0", "s", "0", "r", "t", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "k", "0", "0", "0", "k", "0", "0", "0", "k", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "c", "2", "2", "b", "0", "x", "l", "t", "0", "v", "0", "r", "l", "z", "0", "a", "2", "2", "d"},
			{"E", "E", "E", "E", "E", "E", "E", "E", "1", "0", "k", "0", "0", "0", "0", "0", "0", "0", "k", "0", "1", "E", "E", "E"},
			{"E", "E", "E", "E", "E", "E", "E", "E", "1", "0", "k", "0", "a", "4", "E", "3", "b", "0", "k", "0", "1", "E", "E", "E"},
			{"E", "E", "E", "E", "E", "3", "2", "2", "d", "0", "v", "0", "1", "E", "E", "E", "1", "0", "v", "0", "c", "2", "2", "4"},
			{"E", "E", "E", "E", "E", "X", "0", "0", "0", "0", "0", "0", "1", "E", "E", "E", "1", "0", "0", "0", "0", "0", "0", "X"},
			{"E", "E", "E", "E", "E", "3", "2", "2", "b", "0", "s", "0", "c", "2", "2", "2", "d", "0", "s", "0", "a", "2", "2", "4"},
			{"E", "E", "E", "E", "E", "E", "E", "E", "1", "0", "k", "0", "0", "0", "0", "0", "0", "0", "k", "0", "1", "E", "E", "E"},
			{"E", "E", "E", "E", "E", "E", "E", "E", "1", "0", "k", "0", "r", "l", "y", "l", "t", "0", "k", "0", "1", "E", "E", "E"},
			{"E", "E", "E", "E", "E", "a", "2", "2", "d", "0", "v", "0", "0", "0", "k", "0", "0", "0", "v", "0", "c", "2", "2", "b"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "0", "0", "s", "0", "k", "0", "s", "0", "0", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "r", "o", "0", "r", "l", "q", "0", "v", "0", "p", "l", "t", "0", "m", "t", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "P", "0", "k", "0", "0", "0", "0", "0", "F", "0", "0", "0", "0", "0", "k", "0", "P", "1"},
			{"E", "E", "E", "E", "E", "5", "4", "0", "v", "0", "s", "0", "r", "l", "y", "l", "t", "0", "s", "0", "v", "0", "3", "6"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "k", "0", "0", "0", "k", "0", "0", "0", "k", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "r", "l", "l", "w", "l", "t", "0", "v", "0", "r", "l", "w", "l", "l", "t", "0", "1"},
			{"E", "E", "E", "E", "E", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1"},
			{"E", "E", "E", "E", "E", "c", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "d"},

			{"E", "E", "E", "E", "E", "A", "B", "C", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "D", "E", "F", "G"},
			{"E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E"},
	};

	public Ventana() {

		pacman = new ImageIcon("imagenes/pacman.png").getImage();

		imagenes.put("1", cargarImagen("imagenes/paredParada1.png"));
		imagenes.put("2", cargarImagen("imagenes/paredAcostada1.png"));

		imagenes.put("a", cargarImagen("imagenes/esquinaArribaIzq.png"));
		imagenes.put("b", cargarImagen("imagenes/esquinaArribaDer.png"));
		imagenes.put("c", cargarImagen("imagenes/esquinaAbajoIzq.png"));
		imagenes.put("d", cargarImagen("imagenes/esquinaAbajoDer.png"));

		imagenes.put("e", cargarImagen("imagenes/esquinaInterior1.png"));
		imagenes.put("f", cargarImagen("imagenes/esquinaInterior2.png"));
		imagenes.put("g", cargarImagen("imagenes/esquinaInterior3.png"));
		imagenes.put("h", cargarImagen("imagenes/esquinaInterior4.png"));

		imagenes.put("i", cargarImagen("imagenes/lineaArriba.png"));
		imagenes.put("j", cargarImagen("imagenes/lineaAbajo.png"));

		imagenes.put("k", cargarImagen("imagenes/paredParadaInterior.png"));
		imagenes.put("l", cargarImagen("imagenes/paredAcostadaInterior.png"));

		imagenes.put("m", cargarImagen("imagenes/esquinaInteriorDoble1.png"));
		imagenes.put("o", cargarImagen("imagenes/esquinaInteriorDoble2.png"));
		imagenes.put("p", cargarImagen("imagenes/esquinaInteriorDoble3.png"));
		imagenes.put("q", cargarImagen("imagenes/esquinaInteriorDoble4.png"));

		imagenes.put("r", cargarImagen("imagenes/redondoInterior1.png"));
		imagenes.put("s", cargarImagen("imagenes/redondoInterior2.png"));
		imagenes.put("t", cargarImagen("imagenes/redondoInterior3.png"));
		imagenes.put("v", cargarImagen("imagenes/redondoInterior4.png"));

		imagenes.put("w", cargarImagen("imagenes/tripleInterior1.png"));
		imagenes.put("x", cargarImagen("imagenes/tripleInterior2.png"));
		imagenes.put("y", cargarImagen("imagenes/tripleInterior3.png"));
		imagenes.put("z", cargarImagen("imagenes/tripleInterior4.png"));

		imagenes.put("u", cargarImagen("imagenes/u.png"));
		imagenes.put("n", cargarImagen("imagenes/n.png"));
		imagenes.put("3", cargarImagen("imagenes/derecha.png"));
		imagenes.put("4", cargarImagen("imagenes/izquierda.png"));

		imagenes.put("5", cargarImagen("imagenes/doble1.png"));
		imagenes.put("6", cargarImagen("imagenes/doble2.png"));
		imagenes.put("7", cargarImagen("imagenes/doble3.png"));
		imagenes.put("8", cargarImagen("imagenes/doble4.png"));

		imagenes.put("F", cargarImagen("imagenes/fruta.png"));

		imagenes.put("0", cargarImagen("imagenes/comida.png"));
		imagenes.put("P", cargarImagen("imagenes/pastilla.png"));


		//PROPIEDADES VENTANA
		this.setTitle("Pacman");
		this.setSize(600, 600);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		JPanel panelSuperior = new JPanel();
		panelSuperior.setPreferredSize(new Dimension(getWidth(), 50));
		panelSuperior.setLayout(new GridBagLayout());

		JButton boton = new JButton("Salir");
		boton.setPreferredSize(new Dimension(70, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panelSuperior.add(boton, gbc);
		boton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		etiqueta = new JLabel("SCORE: ");
		etiqueta.setPreferredSize(new Dimension(100, 20));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 100, 0, 100);
		panelSuperior.add(etiqueta, gbc);

		this.add(panelSuperior, BorderLayout.NORTH);

		juego = new JPanel(new BorderLayout());
		//juego.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		juego.setOpaque(true);
		juego.setBackground(Color.decode("#eeeeee"));
		juego.add(panel, BorderLayout.CENTER);

		juego.add(new dibujar());

		this.add(juego, BorderLayout.CENTER);
		this.repaint();
		this.revalidate();

		juego.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getKeyCode());

				anteriorPx = px;
				anteriorPy = py;
				if (e.getKeyCode() == 87 && py > 0) {
					py = py - 5;
				}
				if (e.getKeyCode() == 83 && py < 500) {
					py = py + 5;
				}
				if (e.getKeyCode() == 65 && px > 0) {
					px = px - 5;
				}
				if (e.getKeyCode() == 68 && px < 500) {
					px = px + 5;

				}
				
				if (verFruta == 0) {
					imagenes.put("F", cargarImagen("imagenes/nada.png"));
					revalidate();
					repaint();

				}

				if (vidas == 3) {
					imagenes.put("A", cargarImagen("imagenes/pacman.png"));
					imagenes.put("B", cargarImagen("imagenes/pacman.png"));
					imagenes.put("C", cargarImagen("imagenes/pacman.png"));
					revalidate();
					repaint();

				} else if (vidas == 2) {
					imagenes.put("A", cargarImagen("imagenes/pacman.png"));
					imagenes.put("B", cargarImagen("imagenes/pacman.png"));
					imagenes.put("C", cargarImagen("imagenes/nada.png"));
					revalidate();
					repaint();

				} else if (vidas == 1) {
					imagenes.put("A", cargarImagen("imagenes/pacman.png"));
					imagenes.put("B", cargarImagen("imagenes/nada.png"));
					imagenes.put("C", cargarImagen("imagenes/nada.png"));
					revalidate();
					repaint();

				} else if (vidas == 0) {
					imagenes.put("A", cargarImagen("imagenes/nada.png"));
					imagenes.put("B", cargarImagen("imagenes/nada.png"));
					imagenes.put("C", cargarImagen("imagenes/nada.png"));
					reproductor.reproducir("musica/SonidoMuerte.wav");
					JOptionPane.showMessageDialog(null, "PERDISTESSSS", "GAY", JOptionPane.INFORMATION_MESSAGE);

					revalidate();
					repaint();

					System.exit(0);

				}

				colision();
				score();
				atajo();

				juego.repaint();
				juego.revalidate();
			}


			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		juego.setFocusable(true);
		juego.requestFocus();


		//Se crean los fantasmas y se añaden
		Fantasma fantasma1 = new Fantasma(280, 200, 20, 20, Color.red, paredes);
		Fantasma fantasma2 = new Fantasma(280, 220, 20, 20, Color.pink, paredes);
		Fantasma fantasma3 = new Fantasma(280, 210, 20, 20, Color.cyan, paredes);
		Fantasma fantasma4 = new Fantasma(280, 200, 20, 20, Color.orange, paredes);

		fantasmas.add(fantasma1);
		fantasmas.add(fantasma2);
		fantasmas.add(fantasma3);
		fantasmas.add(fantasma4);

		//Hilo para mover los fantasmas aunque el jugador no este haciendo nada
		Object bloqueo = new Object(); // Crear un objeto de bloqueo para cada instancia de Fantasma
		for (Fantasma f : fantasmas) {
			Thread t = new Thread(() -> {
				while (true) {
					synchronized (bloqueo) { // Obtener el bloqueo correspondiente a la instancia actual de Fantasma
						f.mover();
					}
					juego.repaint();
					try {
						Thread.sleep(70); // Espera 70 milisegundos antes de mover el fantasma nuevamente
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			t.start();
		}

	}
	
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

	public void colision() {

		Rect r = new Rect(px, py, 20, 20, Color.yellow);

		for (Rect pared : paredes) {
			if (r.colision(pared)) {

				px = anteriorPx;
				py = anteriorPy;
				break;
			}
		}

		for (int i = 0; i < punto.size(); i++) {
			Rect p = punto.get(i);
			if (r.colision(p)) {
				puntos++;
				System.out.println("PUNTOSSSSSSS" + puntos);
				punto.remove(p);


				juego.repaint();
				break;
			}
		}
		//Colison del pacman con los fantasmas
		for (Fantasma fantasma : fantasmas) {
			Rect rectFantasma = new Rect(fantasma.x, fantasma.y, fantasma.w, fantasma.h, fantasma.c);
			if (r.colision(rectFantasma) || (anteriorPx == fantasma.x && anteriorPy == fantasma.y)) {
				px = 275;
				py = 260;
				vidas = vidas - 1;
				revalidate();
				repaint();
				break;
			}
		}
	}


	public void atajo() {

		System.out.println(px + "," + py);
		if (px == 105 && py == 220) {
			px = 455;
			py = 220;
		} else if (px == 455 && py == 220) {
			px = 105;
			py = 220;
		}
	}

	public void vidasPacman() {

		if (vidas == 3) {
			imagenes.put("A", cargarImagen("imagenes/pacman.png"));
			imagenes.put("B", cargarImagen("imagenes/pacman.png"));
			imagenes.put("C", cargarImagen("imagenes/pacman.png"));
			revalidate();
			repaint();

		} else if (vidas == 2) {
			imagenes.put("A", cargarImagen("imagenes/pacman.png"));
			imagenes.put("B", cargarImagen("imagenes/pacman.png"));
			imagenes.put("C", cargarImagen("imagenes/nada.png"));
			revalidate();
			repaint();

		} else if (vidas == 1) {
			imagenes.put("A", cargarImagen("imagenes/pacman.png"));
			imagenes.put("B", cargarImagen("imagenes/nada.png"));
			imagenes.put("C", cargarImagen("imagenes/nada.png"));
			revalidate();
			repaint();

		} else if (vidas == 0) {
			imagenes.put("A", cargarImagen("imagenes/nada.png"));
			imagenes.put("B", cargarImagen("imagenes/nada.png"));
			imagenes.put("C", cargarImagen("imagenes/nada.png"));
			revalidate();
			repaint();
		}
	}

	public void aumentarPuntaje() {
		puntos += 1;
		etiqueta.setText("SCORE: " + puntos);
	}


	public void score() {
		boolean[][] visitado = new boolean[26][24];

		Rect r = new Rect(px, py, 20, 20, Color.yellow);

		for (int i = 0; i < laberinto.length; i++) {
			for (int j = 0; j < laberinto[0].length; j++) {
				if (laberinto[i][j].equals("0") && r.colision(new Rect(j * 20, i * 20, 20, 20,Color.black))) {

					if (!visitado[i][j]) {
						visitado[i][j] = true;
						laberinto[i][j] = "-";
						reproductor.reproducir("musica/pacmanComiendo.wav");
						aumentarPuntaje();
					}
					break;
				} else if (laberinto[i][j].equals("P") && r.colision(new Rect(j * 20, i * 20, 20, 20, Color.black))) {
					if (!visitado[i][j]) {
						visitado[i][j] = true;
						laberinto[i][j] = "-";
						aumentarPuntaje();
						pastillaActiva = true;
						repaint();

						// Esperar tiempoPastilla milisegundos
						try {
							Thread.sleep(tiempoPastilla);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						pastillaActiva = false;
						repaint();
					}
					break;
			}
		}}

		for (int i = 0; i < laberinto.length; i++) {
			for (int j = 0; j < laberinto[0].length; j++) {
				if (laberinto[i][j].equals("F") && r.colision(new Rect(j * 20, i * 20, 20, 20,Color.black))) {
					if (!visitado[i][j]) {
						visitado[i][j] = true;
						laberinto[i][j] = "-";
						puntos = puntos + 100;
						verFruta = 0;
						repaint();
					}
					break;
				}
			}
		}
		
		boolean quedaAlgun0 = false;
		for (int i = 0; i < laberinto.length; i++) {
			for (int j = 0; j < laberinto[0].length; j++) {
				if (laberinto[i][j].equals("0")) {
					quedaAlgun0 = true;
					break;
				}
			}
			if (quedaAlgun0) {
				break;
			}
		}

		if (!quedaAlgun0) {
			JOptionPane.showMessageDialog(null, "Ganasteeeeeee amiiix!");
			System.exit(0);
		}
	}


	class dibujar extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			setBackground(Color.BLACK);

			//PAREDES
			for(int i = 0; i < laberinto.length; i++) {
				for(int j = 0; j < laberinto[i].length; j++) {

					String letra = laberinto[i][j];

					// Si la letra tiene una imagen asociada, la dibuja
					if (imagenes.containsKey(letra)) {
						Image imagen = imagenes.get(letra);
						g.drawImage(imagen, j * 20, i * 20, this);

					}

					if(letra.equals("1") || letra.equals("2") || letra.equals("3") || letra.equals("4") || letra.equals("5") || letra.equals("6") || letra.equals("7") || letra.equals("8") ||
							letra.equals("a") || letra.equals("b") || letra.equals("c") || letra.equals("d") || letra.equals("e") || letra.equals("f") || letra.equals("g") || letra.equals("h") ||
							letra.equals("i") || letra.equals("j") || letra.equals("u") || letra.equals("n") || letra.equals("k") || letra.equals("l") || letra.equals("m") || letra.equals("n") ||
							letra.equals("o") || letra.equals("p") || letra.equals("q") || letra.equals("r") || letra.equals("s") || letra.equals("t") || letra.equals("u") ||
							letra.equals("v") || letra.equals("w") || letra.equals("x") || letra.equals("y") || letra.equals("z") || letra.equals("V")) {
						Rect pared = new Rect(j * 20, i * 20, 20, 20, Colores.colorParedes);
						paredes.add(pared);
					}

					if(letra.equals("0")) {
						Rect comida = new Rect(j *20,i *20, 20, 20, Colores.colorParedes);
						comidas.add(comida);

					}
					if(letra.equals("F")) {
						Rect frutas = new Rect(j *20,i *20, 20, 20, Colores.colorParedes);
						fruta.add(frutas);

					}

				}
			}

			Image imagenRect = null;
			Image pastillaEfecto = null;
			try {
				imagenRect = ImageIO.read(new File("imagenes/pacman.png"));
				pastillaEfecto = ImageIO.read(new File("imagenes/nada.png"));
			} catch (IOException e) {
				System.out.println("No se pudo cargar la imagen");
				e.printStackTrace();
			}

			//JUGADOR
			Rect r = new Rect(px, py, 20, 20, null);
			g.setColor(r.c);
			g.fillRect(r.x, r.y, r.w, r.h);
			g.drawImage(imagenRect, r.x, r.y, r.w, r.h, null);

			if (pastillaActiva == false) {
				g.drawImage(pastillaEfecto, r.x, r.y, r.w, r.h, null);
			} else {
				// Dibujar la imagen por defecto
				g.drawImage(imagenRect, px, py, 20, 20, null);
			}

			//COMIDA
	        /*for (Rect c : comida) {
	            g.setColor(c.c);
	            g.fillRect(c.x, c.y, c.w, c.h);
	        }*/


			//Esto de aqui muestra a los fantasmas y los mueve
			if (fantasmas != null) {
				for (Fantasma f : fantasmas) {
					f.mover();
					g.drawImage(f.imagen, f.x, f.y, f.w, f.h, null);
				}
			}
		}
	}

	//CARGARÁ CADA IMAGEN INDEPENDIENTE PARA CADA PARED
	private Image cargarImagen(String archivo) {
		Image imagen = null;
		try {
			imagen = ImageIO.read(new File(archivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imagen;
	}

	public class Colores {
		public static Color colorParedes = Color.black;

		public static void cambiarColores() {
			Random random = new Random();
			colorParedes = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		}
	}

	public class Rect{
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		Color c = Color.black;

		Rect(int x, int y, int w, int h, Color c){
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.c = c;
		}

		public Boolean colision(Rect target) {
			if(this.x < target.x + target.w &&
					this.x + this.w > target.x &&
					this.y < target.y + target.h &&
					this.y + this.h > target.y) {
				return true;
			}
			return false;
		}
	}

	//Se crea una nueva clase exclusiva para los fantasmas
	public class Fantasma {
		int x, y, w, h;
		Color c;
		Random rnd = new Random();
		int dirX, dirY;

		List<Rect> paredes;
		BufferedImage imagen;

		public Fantasma(int x, int y, int w, int h, Color c, List<Rect> paredes) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.c = c;
			this.paredes = paredes;

			do {
				dirX = rnd.nextInt(3) - 1;
				dirY = rnd.nextInt(3) - 1;
			} while (dirX == 0 && dirY == 0); // se asegura de que ambas direcciones no sean cero al mismo tiempo

			//Se añaden las imagenes de los fantasmas comparando si es igual al color del fantasma
			try {
				if (c.equals(Color.red)) {
					imagen = ImageIO.read(new File("imagenes/rojo.png"));
				} else if (c.equals(Color.pink)) {
					imagen = ImageIO.read(new File("imagenes/rosa.png"));
				} else if (c.equals(Color.cyan)) {
					imagen = ImageIO.read(new File("imagenes/cyan.png"));
				} else if (c.equals(Color.orange)) {
					imagen = ImageIO.read(new File("imagenes/naranja.png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void dibujar(Graphics g) {
			g.setColor(c);
			g.fillRect(x, y, 20, 20);
		}
		public void mover() {
			synchronized (this) { // Obtener el bloqueo del objeto
				int newX = x + dirX;
				int newY = y + dirY;

				if (!hayColision(dirX, dirY)) {
					x = newX;
					y = newY;

				} else {
					int intentos = 0;
					while (intentos < 20) { // intenta varias veces encontrar una dirección sin colisión
						int rand = (int) (Math.random() * 4); // elige una dirección aleatoria
						switch (rand) {
							case 0:
								dirX = -1;
								dirY = 0;
								break;
							case 1:
								dirX = 1;
								dirY = 0;
								break;
							case 2:
								dirX = 0;
								dirY = -1;
								break;
							case 3:
								dirX = 0;
								dirY = 1;
								break;
						}
						if (!hayColision(dirX, dirY)) { // verifica si la nueva dirección no tiene colisión
							x += dirX;
							y += dirY;
							break;
						}
						intentos++;
					}
				}
				//Colision de los fantasmas con el pacman
				Rect r = new Rect(px, py, 20, 20, Color.yellow);
				Rect rectFantasma = new Rect(x, y, w, h, c);
				if (r.colision(rectFantasma)) {
					px = 275;
					py = 260;
					vidas = vidas - 1;
					vidasPacman();
					revalidate();
					repaint();

				}
			}
		}
		//Se crea un metodo que verifique si hay una colision con las paredes con el método de la colision ya antes escrita
		public boolean hayColision(int dirX, int dirY) {
			List<Rect> copiaParedes = new ArrayList<>(paredes);
			for (Rect pared : copiaParedes) {
				Rect futuro = new Rect(x + dirX, y + dirY, w, h, null);
				if (futuro.colision(pared)) {
					return true;
				}
			}
			return false;
		}
	}
}


