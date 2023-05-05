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
import java.util.List;
import java.util.Random;

public class Ventana extends JFrame{
	public int super_x = 2;
	public int super_y = 2;
	public int puntos = 0;
	private Boolean lleno = true;
	public int tecla = 0;
    ArrayList<Rect> paredes = new ArrayList<Rect>();
    ArrayList<Fantasma> fantasmas = new ArrayList<Fantasma>();
    private JPanel contentPane;
    
    public Ventana() {
        //PROPIEDADES VENTANA
        this.setTitle("PacMan");
        this.setSize(600,600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
        
		JPanel juego = new JPanel();
		juego.setBackground(Color.black);
		contentPane.add(juego, BorderLayout.CENTER);
		juego.add(new MyGraphics());
		juego.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println(e.getKeyCode());
				
				if((e.getKeyCode() == 87 || e.getKeyCode() == 38) && super_y>0) {
					super_y -= 2;
					tecla = e.getKeyCode();
				}
				if((e.getKeyCode() == 83 || e.getKeyCode() == 40) && super_y <430) {
					super_y += 2;
					tecla = e.getKeyCode();
				}
				if((e.getKeyCode() == 65 || e.getKeyCode() == 37) && super_x > 0) {
					super_x -= 2;
					tecla = e.getKeyCode();
				}
				if((e.getKeyCode() == 68 || e.getKeyCode() == 39) && super_x < 490) {
					super_x += 2;
					tecla = e.getKeyCode();
				}
				juego.repaint();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		juego.setFocusable(true);
		juego.requestFocus();
		
		//Se crean los fantasmas y se añaden
		Fantasma fantasma1 = new Fantasma(55, 200, 20, 20, Color.red, paredes);
		Fantasma fantasma2 = new Fantasma(55, 200, 20, 20, Color.pink, paredes);
		Fantasma fantasma3 = new Fantasma(55, 200, 20, 20, Color.cyan, paredes);
		Fantasma fantasma4 = new Fantasma(55, 200, 20, 20, Color.orange, paredes);

		fantasmas.add(fantasma1);
		fantasmas.add(fantasma2);
		fantasmas.add(fantasma3);
		fantasmas.add(fantasma4);
        
		//Hilo para mover los fantasmas aunque el jugador no este haciendo nada
        for (Fantasma f : fantasmas) {
            Thread t = new Thread(() -> {
                while (true) {
                    f.mover();
                    juego.repaint();
                    try {
                        Thread.sleep(100); // Espera 100 milisegundos antes de mover el fantasma nuevamente
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
		
    }
    
	public class MyGraphics extends JComponent{
		private static final long serialVersionUID = 1L;
		
		MyGraphics(){
			setPreferredSize(new Dimension(500,500));
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(Color.gray);
	        g.drawRect(0, 0, 499, 440);
			
			Rect player = new Rect(super_x,super_y,10,10, Color.yellow);
			g.setColor(player.c);
			g.fillRect(player.x, player.y, player.w, player.h);
			
			//					  x   y w  h
			Rect pared = new Rect(13, 0,3,50,Color.decode("#00ffff"));
			g.setColor(pared.c);
			g.fillRect(pared.x, pared.y, pared.w, pared.h);
			
			Rect pared2 = new Rect(13, 50,20,3,Color.decode("#00ffff"));
			g.setColor(pared2.c);
			g.fillRect(pared2.x, pared2.y, pared2.w, pared2.h);
			
			Rect pared3 = new Rect(30, 30,3,60,Color.decode("#00ffff"));
			g.setColor(pared3.c);
			g.fillRect(pared3.x, pared3.y, pared3.w, pared3.h);
			
			Rect pared4 = new Rect(30, 90,20,3,Color.decode("#00ffff"));
			g.setColor(pared4.c);
			g.fillRect(pared4.x, pared4.y, pared4.w, pared4.h);
			
			Rect pared5 = new Rect(30, 130,20,3,Color.decode("#00ffff"));
			g.setColor(pared5.c);
			g.fillRect(pared5.x, pared5.y, pared5.w, pared5.h);
			
			Rect pared6 = new Rect(50, 90,3,300,Color.decode("#00ffff"));
			g.setColor(pared6.c);
			g.fillRect(pared6.x, pared6.y, pared6.w, pared6.h);
			
			Rect pared7 = new Rect(20, 160,3,260,Color.decode("#00ffff"));
			g.setColor(pared7.c);
			g.fillRect(pared7.x, pared7.y, pared7.w, pared7.h);
			
			Rect pared8 = new Rect(20, 420,90,3,Color.decode("#00ffff"));
			g.setColor(pared8.c);
			g.fillRect(pared8.x, pared8.y, pared8.w, pared8.h);
			
			Rect pared9 = new Rect(110, 420,3,20,Color.decode("#00ffff"));
			g.setColor(pared9.c);
			g.fillRect(pared9.x, pared9.y, pared9.w, pared9.h);
			
			Rect pared10 = new Rect(50, 390,60,3,Color.decode("#00ffff"));
			g.setColor(pared10.c);
			g.fillRect(pared10.x, pared10.y, pared10.w, pared10.h);
			
			Rect pared11 = new Rect(110, 363,3,30,Color.decode("#00ffff"));
			g.setColor(pared11.c);
			g.fillRect(pared11.x, pared11.y, pared11.w, pared11.h);
			
			Rect pared12 = new Rect(80, 220, 3, 150, Color.decode("#00ffff"));
			g.setColor(pared12.c);
			g.fillRect(pared12.x, pared12.y, pared12.w, pared12.h);
			
			Rect pared13 = new Rect(80, 220, 60, 3, Color.decode("#00ffff"));
			g.setColor(pared13.c);
			g.fillRect(pared13.x, pared13.y, pared13.w, pared13.h);
			
			Rect pared14 = new Rect(110, 150, 3, 70, Color.decode("#00ffff"));
			g.setColor(pared14.c);
			g.fillRect(pared14.x, pared14.y, pared14.w, pared14.h);
			
			Rect pared15 = new Rect(80, 70, 3, 130, Color.decode("#00ffff"));
			g.setColor(pared15.c);
			g.fillRect(pared15.x, pared15.y, pared15.w, pared15.h);
			
			Rect pared16 = new Rect(50, 70,60,3, Color.decode("#00ffff"));
			g.setColor(pared16.c);
			g.fillRect(pared16.x, pared16.y, pared16.w, pared16.h);
			
			Rect pared17 = new Rect(50, 70,60,3, Color.decode("#00ffff"));
			g.setColor(pared17.c);
			g.fillRect(pared17.x, pared17.y, pared17.w, pared17.h);
			
			Rect pared18 = new Rect(110, 180, 3, 70, Color.decode("#00ffff"));
			g.setColor(pared18.c);
			g.fillRect(pared18.x, pared18.y, pared18.w, pared18.h);
			
			Rect pared19 = new Rect(110, 280, 3, 70, Color.decode("#00ffff"));
			g.setColor(pared19.c);
			g.fillRect(pared19.x, pared19.y, pared19.w, pared19.h);
			
			Rect pared20 = new Rect(140, 220, 60, 3, Color.decode("#00ffff"));
			g.setColor(pared20.c);
			g.fillRect(pared20.x, pared20.y, pared20.w, pared20.h);
	
			//Este if mete todos los objetos dentro del array, se pone en un if porque sin fuese asi, este se llenaría infinitamente ya que la clase se ejecuta todo el tiempo :p
			if(lleno) {
				paredes.add(pared);
				paredes.add(pared2);
				paredes.add(pared3);
				paredes.add(pared4);
				paredes.add(pared5);
				paredes.add(pared6);
				paredes.add(pared7);
				paredes.add(pared8);
				paredes.add(pared9);
				paredes.add(pared10);
				paredes.add(pared11);
				paredes.add(pared12);
				paredes.add(pared13);
				paredes.add(pared14);
				paredes.add(pared15);
				paredes.add(pared16);
				paredes.add(pared17);
				paredes.add(pared18);
				paredes.add(pared19);
				paredes.add(pared20);
			    
			    lleno = false;
			}
			/*
			 * Para mis compañeros!! De esta manera funciona la colisión, basicamente hace lo mismo que el keyListener pero si se dan cuenta es detectar
			 * la ultima tecla que se presionó y en vez de hacer la función que hace normalmente que puede ser avanzar a la derecha, este retrocedera la misma cantidad de px a la izq
			 * Por lo tanto no se podrá mover en la dirección que se presionó
			 */
			for(int i = 0; i< paredes.size();i++) {
				if(player.colision(paredes.get(i))) {
					if((tecla == 87 || tecla == 38)) {
						super_y += 2;	
					}
					if((tecla == 83 || tecla == 40)) {
						super_y -= 2;
					}
					if((tecla == 65 || tecla == 37)) {
						super_x += 2;
					}
					if((tecla == 68 || tecla == 39)) {
						super_x -= 2;
					}
				}
			}
			
			//#FF0000
			Rect ganar = new Rect(490, 0, 10, 10, Color.decode("#23c423"));
			g.setColor(ganar.c);
			g.fillRect(ganar.x, ganar.y, ganar.w, ganar.h);
			
			
			if(player.colision(ganar)) {
				super_x = 2;
				super_y = 2;
				
				String tiempo = "";
				
				JOptionPane.showMessageDialog(null, "Tu tiempo fue: " + tiempo,"FELICIDADES!",JOptionPane.INFORMATION_MESSAGE);
				
				repaint();
				revalidate();
				
			}
			
			System.out.println(player.colision(pared));
			
			//Esto de aqui muestra a los fantasmas y los mueve
			if (fantasmas != null) {
			    for (Fantasma f : fantasmas) {
			        f.mover();
			        g.drawImage(f.imagen, f.x, f.y, f.w, f.h, null);
			    }
			}
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
	        int newX = x + dirX;
	        int newY = y + dirY;

	        if (!hayColision(dirX, dirY)) {
	            x = newX;
	            y = newY;
	        } else {
	            int intentos = 0;
	            while (intentos < 10) { // intenta varias veces encontrar una dirección sin colisión
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
	    //Se crea un metodo que verifique si hay una colision con las paredes con el método de la colision ya antes escrita
	    public boolean hayColision(int dirX, int dirY) {
	        for (Rect pared : paredes) {
	            Rect futuro = new Rect(x + dirX, y + dirY, w, h, null);
	            if (futuro.colision(pared)) {
	                return true;
	            }
	        }
	        return false;
	    }
	}
}


