

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.util.Map;
import java.util.Random;

public class peg {
	
	class Point{
		int x,y;
		public Point(int x,int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private CurveFever game;
	Random randno = new Random();
	int x,y;
	int xo,yo;
	int speed = 2;
	float angle = (float) (randno.nextFloat() * 2 * Math.PI);
	final float omega = (float) 0.08;
	int dx = 1,dy = 1;

	boolean trail = true;
	boolean up_key= false;
	boolean down_key= false;
	int moves = 0;
	
	int DIAMETER=8;
	
	public peg(CurveFever game){
		this.game = game;
		this.x = 200+randno.nextInt(800 - 1);
		this.y = 200+randno.nextInt(200- 1);
	}
	
	public void move(Map<Integer, Boolean> keys){
		
		xo = x;
		yo = y;
		dx = (int)(speed * Math.cos(angle));
		dy = (int)(speed * Math.sin(angle));
		
		if(trail && (moves == 100)){
			trail = false;
			moves = 0;
		}
		else if(!trail && (moves == 10)){
			trail = true;
			moves = 0;
		}
		
		moves += 1;
		
		if(keys.containsKey(KeyEvent.VK_LEFT)){
			angle -= (keys.get(KeyEvent.VK_LEFT)) ? omega : 0 ;	
		}
		
		if(keys.containsKey(KeyEvent.VK_UP)){
			
			speed += (keys.get(KeyEvent.VK_UP)) ? 1 : 0 ;
			keys.put(KeyEvent.VK_UP,false);
					
		}
		
		if(keys.containsKey(KeyEvent.VK_DOWN)){
			
				speed -= (keys.get(KeyEvent.VK_DOWN)) ? 1 : 0 ;
				//speed = speed-1;
				speed = (speed<=1)?1:speed;
				keys.put(KeyEvent.VK_DOWN,false);
			
			
		}
		
		if(keys.containsKey(KeyEvent.VK_RIGHT)){
			angle += (keys.get(KeyEvent.VK_RIGHT)) ? omega : 0 ;	
		}
		
		if(keys.containsKey(KeyEvent.VK_S)){
				if(keys.get(KeyEvent.VK_S)) {
					System.out.println(moves);
					System.out.println(speed);
				}
		}
		
		if (x + dx < 0 || x + dx > game.getWidth() - DIAMETER)
			game.gameOver();
		else 	
			x = x + dx;
		
		if (y + dy < 0 || y + dy > game.getHeight() - DIAMETER)
			game.gameOver();
		else 
			y = y + dy;
		
		for(int i =-DIAMETER/2;i<DIAMETER/2;i++){ 
			for(int j=1; j<speed;j++ ){
		
			if(game.canvas.contains(1000*((int)(x+i * Math.sin(angle) + j*Math.cos(angle)))+(int)(y+j*Math.sin(angle)-i*Math.cos(angle)))){
				System.out.println("Overlap");
				System.out.print(game.canvas);
				game.gameOver();
			}
		}
		}
//		game.canvas.add(1000*x+y);
	}
	
	public void paint(Graphics2D g) {
			
		g.setColor(Color.BLACK);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
		if(trail)
			paintTrail(g);
		else{
			g.setColor(g.getBackground());
			g.fillOval(xo, yo, DIAMETER, DIAMETER);
		}
		
		
	}
	
	public void paintTrail(Graphics2D g){
		g.setColor(Color.BLACK);
//				
		Path2D.Double path = new Path2D.Double();
		path.moveTo(xo + DIAMETER/2 + (Math.sin(angle))*DIAMETER/2, yo +DIAMETER/2 - Math.cos(angle)*DIAMETER/2);
		path.lineTo(xo + DIAMETER/2 - (Math.sin(angle))*DIAMETER/2, yo +DIAMETER/2+ Math.cos(angle)*DIAMETER/2);
		path.lineTo(x +DIAMETER/2- Math.sin(angle)*DIAMETER/2, y +DIAMETER/2+ Math.cos(angle)*DIAMETER/2);
		path.lineTo(x +DIAMETER/2 + Math.sin(angle)*DIAMETER/2, y +DIAMETER/2- Math.cos(angle)*DIAMETER/2);
		
		path.closePath();
		g.fill(path);

		game.canvas.add(1000*x+y);
	}
	
	public void keyPressed(KeyEvent e){
		
		switch(e.getKeyCode()){
		
			case KeyEvent.VK_LEFT : angle -= 0.1; break;
			case KeyEvent.VK_RIGHT : angle += 0.1; break;
			
			case KeyEvent.VK_UP : speed += 1; break;
			case KeyEvent.VK_DOWN : speed -= 1; break;
			
		}
		
		System.out.println(angle);
		
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
//		case KeyEvent.VK_LEFT : dx = 0; break;
//		case KeyEvent.VK_RIGHT : dx = 0; break;
//		case KeyEvent.VK_UP : dy = 0; break;
//		case KeyEvent.VK_DOWN : dy = 0; break;
	}
	}
}
