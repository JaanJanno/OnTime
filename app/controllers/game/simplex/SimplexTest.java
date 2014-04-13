package controllers.game.simplex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimplexTest extends JPanel {
	
	/* Testklass simplex testimiseks.
	 * Võib lihtsalt eclipsis käima panna.
	 */
	
	private static final long serialVersionUID = -3365256410195826554L;
	
	JFrame f;
	
	int xc = 0;
	int yc = 0;
	
	int suurus = 50;
	
	public SimplexTest(JFrame f) {
		this.f = f;
		addKeyListener(new Nuputaja(this));
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, f.getWidth(), f.getHeight());
		g.drawImage(simplex(150, 16).getScaledInstance(f.getWidth(), f.getWidth(), 0), 0, 0, null);
	}
	
	public BufferedImage simplex(int x, double frequency){
		BufferedImage img = new BufferedImage(x, x, BufferedImage.TYPE_INT_ARGB);
		plotSimplex4D(img, x, x, frequency);
		return img;
	}
	
	public void plotSimplex2D(BufferedImage img, int x, int y, double div){
		for (int i = 0; i < x; i++){
			for (int j = 0; j < x; j++){
				int sim  = SimplexStreamer.plotOctave2D(i +xc, j +yc, div, 0,  60);
				sim 	+= SimplexStreamer.plotOctave2D(i +xc, j +yc, div, 3, 195);
				img.setRGB(i, j, SimplexLeveler.levelTransformColor(sim));
			}
		}
	}
	
	public void plotSimplex4D(BufferedImage img, int x, int y, double div){
		for (int i = 0; i < x; i++){
			for (int j = 0; j < x; j++){
				img.setRGB(i, j, SimplexStreamer.getPointColor(i+xc, j+yc, suurus, suurus));
			}
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 1000);
	    f.setLocation(100, 100);
	    f.setTitle("SimplexTest");
	    f.add(new SimplexTest(f));
	    f.setVisible(true);
	}
	
	private class Nuputaja extends KeyAdapter{
		
		private SimplexTest m;
		final private int mul = 5;
		
		public Nuputaja(SimplexTest m) {
			this.m = m;
		}
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_UP){
				m.yc -= mul;
			}
			if (key == KeyEvent.VK_DOWN){
				m.yc += mul;
			}
			if (key == KeyEvent.VK_LEFT){
				m.xc -= mul;
			}
			if (key == KeyEvent.VK_RIGHT){
				m.xc += mul;
			}
			if (key == KeyEvent.VK_PLUS){
				m.suurus += 10;
			}
			m.repaint();
		} 
	}
}
