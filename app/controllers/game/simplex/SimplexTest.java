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
	
	int suurus = 70;
	int mode = 0;
	
	public SimplexTest(JFrame f) {
		this.f = f;
		addKeyListener(new Nuputaja(this));
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, f.getWidth(), f.getHeight());
		g.drawImage(simplex(150, 16, mode).getScaledInstance(f.getWidth(), f.getWidth(), 0), 0, 0, null);
	}
	
	public BufferedImage simplex(int x, double frequency, int mode){
		BufferedImage img;
		if (mode == 2){
			img = new BufferedImage(768, 768, BufferedImage.TYPE_INT_ARGB);
			plotSimplex4DAdvanced(img, 768, 768, frequency);
		}else if (mode == 1){
			img = new BufferedImage(x, x, BufferedImage.TYPE_INT_ARGB);
			plotSimplex4D(img, x, x, frequency);
		} else{
			img = new BufferedImage(x, x, BufferedImage.TYPE_INT_ARGB);
			plotSimplex2D(img, x, x, frequency);
		}
		return img;
	}
	
	public void plotSimplex2D(BufferedImage img, int x, int y, double div){
		for (int i = 0; i < x; i++){
			for (int j = 0; j < x; j++){
				double sim  = SimplexStreamer.plotOctave2D(i +xc, j +yc, div*2, 0,  60);
				sim 	+= SimplexStreamer.plotOctave2D(i +xc, j +yc, div*2, 1, 195);
				img.setRGB(i, j, SimplexLeveler.levelAsRgb((int)sim));
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
	
	public void plotSimplex4DAdvanced(BufferedImage img, int x, int y, double div){
		for (int i = 0; i < x; i++){
			for (int j = 0; j < x; j++){
				img.setRGB(i, j, SimplexTest.getPointColorAdvanced(i+xc*25, j+yc*25, suurus, suurus));
			}
		}
	}
	
	public static int getPointColorAdvanced(double x, double y, int w, int h) {
		double frequency = 1024;
		w = 1024;
		h = 1024;
		x += 364;
		y += 264;
		int sim  = SimplexStreamer.plotOctave4D(w, h, x, y, frequency, 0, 155);
		sim 	+= SimplexStreamer.plotOctave4D(w, h, x, y, frequency, 1, 70);
		sim 	+= SimplexStreamer.plotOctave4D(w, h, x, y, frequency, 2, 30);
		return SimplexLeveler.levelTransformColorAdvanced(sim);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(768, 768);
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
			if (key == KeyEvent.VK_ENTER){
				m.xc = 0;
				m.yc = 0;
				m.mode = (m.mode + 1) % 3;
			}
			m.repaint();
		} 
	}
}
