import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Ventana extends JFrame{
	public int px=20;
	public int py=20;
	int anteriorPx, anteriorPy;
	private Image pacman;
	ArrayList<Rect> paredes = new ArrayList<Rect>();
	ArrayList<Rect> comidas = new ArrayList<>();
	ArrayList<Fantasma> fantasmas = new ArrayList<Fantasma>();

	private HashMap<String, Image> imagenes = new HashMap<String, Image>();

	//LO CAMBIE A STRING PORQUE ME DI CUENTA QUE SE OCUPABAN MUCHOS DISEÑOS DE PAREDES :'V
	private String[][] laberinto = {
			{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"},
			{"1","0","0","0","0","0","0","0","0","1","0","0","0","0","0","0","0","0","1"},
			{"1","0","1","1","0","1","1","1","0","1","0","1","1","1","0","1","1","0","1"},
			{"1","0","1","1","0","1","1","1","0","1","0","1","1","1","0","1","1","0","1"},
			{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
			{"1","0","1","1","0","1","0","1","1","1","1","1","0","1","0","1","1","0","1"},
			{"1","0","0","0","0","1","0","0","0","1","0","0","0","1","0","0","0","0","1"},
			{"1","1","1","1","0","1","1","1","0","1","0","1","1","1","0","1","1","1","1"},
			{"2","2","2","1","0","1","0","0","0","0","0","0","0","1","0","1","2","2","2"},
			{"2","2","2","1","0","1","0","1","1","2","1","1","0","1","0","1","2","2","2"},
			{"1","1","1","1","0","1","0","1","2","2","2","1","0","1","0","1","1","1","1"},
			{"0","0","0","0","0","0","0","1","2","2","2","1","0","0","0","0","0","0","0"},
			{"1","1","1","1","0","1","0","1","1","1","1","1","0","1","0","1","1","1","1"},
			{"2","2","2","1","0","1","0","0","0","0","0","0","0","1","0","1","2","2","2"},
			{"2","2","2","1","0","1","0","1","1","1","1","1","0","1","0","1","2","2","2"},
			{"1","1","1","1","0","1","0","0","0","1","0","0","0","1","0","1","1","1","1"},
			{"1","0","0","0","0","0","0","1","0","1","0","1","0","0","0","0","0","0","1"},
			{"1","0","1","1","0","1","1","1","0","1","0","1","1","1","0","1","1","0","1"},
			{"1","0","0","1","0","0","0","0","0","0","0","0","0","0","0","1","0","0","1"},
			{"1","1","0","1","0","1","0","1","1","1","1","1","0","1","0","1","0","1","1"},
			{"1","0","0","0","0","1","0","0","0","1","0","0","0","1","0","0","0","0","1"},
			{"1","0","1","1","1","1","1","1","0","1","0","1","1","1","1","1","1","0","1"},
			{"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
			{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"},
	};
	public Ventana() {
		pacman = new ImageIcon("imagenes/pacman.png").getImage();
		imagenes.put("1", cargarImagen("imagenes/pared.png"));


		//PROPIEDADES VENTANA
		this.setTitle("Pacman");
		this.setSize(600,600);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		JPanel panel = new JPanel();
		panel.setBackground(Color.black);

		JPanel juego = new JPanel(new BorderLayout());
		juego.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		juego.setBackground(Color.decode("#c6dee9"));
		juego.add(panel, BorderLayout.CENTER);

		juego.add(new MiPanel());

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
				System.out.println(e.getKeyCode());

				anteriorPx = px;
				anteriorPy = py;

				if(e.getKeyCode() == 87 && py > 0) {
					py = py - 5;
					colision();
				}
				if(e.getKeyCode() == 83 && py < 460) {
					py = py + 5;
					colision();
				}
				if(e.getKeyCode() == 65 && px > 0) {
					px = px - 5;
					colision();
				}
				if(e.getKeyCode() == 68 && px < 460) {
					px = px + 5;
					colision();
				}

				juego.repaint();
				juego.revalidate();
				//juego.repaint();
			}


			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		juego.setFocusable(true);
		juego.requestFocus();


		//Se crean los fantasmas y se añaden
		Fantasma fantasma1 = new Fantasma(160, 160, 20, 20, Color.red, paredes);
		Fantasma fantasma2 = new Fantasma(180, 180, 20, 20, Color.pink, paredes);
		Fantasma fantasma3 = new Fantasma(160, 220, 20, 20, Color.cyan, paredes);
		Fantasma fantasma4 = new Fantasma(180, 200, 20, 20, Color.orange, paredes);

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

	public void colision(){

		Rect r = new Rect(px, py, 20, 20, Color.yellow);

		for (Rect pared : paredes) {
			if (r.colision(pared)) {

				px = anteriorPx;
				py = anteriorPy;
				break;
			}
		}
	}

	class MiPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			//JUGADOR
			Rect r = new Rect(px, py, 20, 20, Color.yellow);
			g.setColor(r.c);
			g.fillRect(r.x, r.y, r.w, r.h);
			g.drawImage(pacman, px, py, this);

			//PAREDES
			for(int i = 0; i < laberinto.length; i++) {
				for(int j = 0; j < laberinto[i].length; j++) {

					String letra = laberinto[i][j];

					// Si la letra tiene una imagen asociada, la dibuja
					if (imagenes.containsKey(letra)) {
						Image imagen = imagenes.get(letra);
						g.drawImage(imagen, j * 20, i * 20, this);

					}

					if(letra.equals("1")) {
						Rect pared = new Rect(j * 20, i * 20, 20, 20, Colores.colorParedes);
						paredes.add(pared);
					}
				}
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

			dirX = rnd.nextInt(3) - 1;
			dirY = rnd.nextInt(3) - 1;

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


