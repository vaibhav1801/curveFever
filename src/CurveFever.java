

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class CurveFever extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	//Key Map for key listener
	Map<Integer, Boolean> keys = new HashMap<Integer, Boolean>();
	// Hashset for keeping track of visited nodes
	Set<Integer> canvas = new HashSet<Integer>();
	//List of active unused powers
	List<power> powerList = new ArrayList<power>();
	
	peg Peg1 = new peg(this);
		
	public CurveFever(){
		this.addKeyListener(new KeyListener(){
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
					
					keys.put(e.getKeyCode(), false);
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					
					keys.put(e.getKeyCode(), true);
				}
	
		});
		
		setFocusable(true);
	}

	private void move(){
		Peg1.move(keys);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Peg1.paint(g2d);
		
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("CurveFever");
		CurveFever game = new CurveFever();
		frame.add(game);
		frame.setSize(1200, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Graphics2D graphic = (Graphics2D) game.getGraphics();
		
		while (true) {
			game.move();
			game.Peg1.paint(graphic);
			Thread.sleep(30);
		}
	}
}
