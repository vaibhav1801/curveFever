import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Printable;
import java.util.Map;
import java.util.Random;

import org.omg.PortableInterceptor.ACTIVE;

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
	int thick_power =0;
	boolean thick_power_active = false;
	
	int DIAMETER=8;
	
	Path2D.Double path_new = new Path2D.Double();
	//Path2D.Double path = new Path2D.Double();
	
	
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
		
		thick_power +=1;
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
		
/*		for(int i =-DIAMETER/2;i<DIAMETER/2;i++){ 
			for(int j=1; j<speed;j++ ){
		
				if(game.canvas.contains(1000*((int)(x+i * Math.sin(angle) + j*Math.cos(angle)))+(int)(y+j*Math.sin(angle)-i*Math.cos(angle)))){
					System.out.println("Overlap");
					System.out.print(game.canvas);
					game.gameOver();
				}
			}
		}*/
		
		if(path_new.contains(x, y)){
			System.out.println("Overlap");
			game.gameOver();
		}
		
		/*if(path.contains(x, y)){
			System.out.println("Overlap");
			game.gameOver();
		}*/
		
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
		if(thick_power % 60 ==0 && !thick_power_active  ){
			
			thick_power_active = true;
			//System.out.println("thick_power_active");
			int x1 = 200+randno.nextInt(800 - 1);
			int y1 = 200+randno.nextInt(200- 1);
			while (game.canvas.contains((1000*x1)+y1)){
				x1 = 200+randno.nextInt(800 - 1);
				y1 = 200+randno.nextInt(200- 1);
			}
			thicken thick_power = new thicken(x1,y1);
			for(int i =-DIAMETER;i<DIAMETER;i++){ 
				for(int j=1; j<speed;j++ ){
					game.power_canvas.add(1000*((int)(x1+i * Math.sin(angle) + j*Math.cos(angle)))+(int)(y1+j*Math.sin(angle)-i*Math.cos(angle)));
				}
			}
			game.powerList.add(thick_power);
			
						
		} 
		if(thick_power_active ){
			thicken temp = (thicken)game.powerList.get(0);
			if(!temp.isActive){
				if(game.power_canvas.contains((1000*x)+y)){
					temp.collision(this);
					g.setColor(Color.BLACK);
					g.fillOval(temp.x, temp.y, DIAMETER, DIAMETER);
					game.power_canvas.clear();
					thick_power=0;
				}
				else{
					g.setColor(Color.BLUE);
				    g.fillOval(temp.x , temp.y ,DIAMETER, DIAMETER);
				}
			}
			else{
				
				if(temp.ttl==1){
					game.powerList.clear();
					thick_power_active = false;
					temp.decay();
				}else{
					temp.decay();
				}
				
			}
			
			//System.out.print("temp.isActive :");
			//System.out.println(temp.isActive);
			temp.decay();
			//System.out.print("thick_power :");
			//System.out.println(thick_power);
			
			
		}
		//System.out.print("thick_power_active");
		//System.out.println(thick_power_active);
	}
	
	public void paintTrail(Graphics2D g){
		g.setColor(Color.BLACK);
//		
		//int[] x_cord = {(int) (xo + DIAMETER/2 + (Math.sin(angle))*DIAMETER/2),(int) (xo + DIAMETER/2 - (Math.sin(angle))*DIAMETER/2),(int) (x +DIAMETER/2- Math.sin(angle)*DIAMETER/2),(int) (x +DIAMETER/2 + Math.sin(angle)*DIAMETER/2)};
		//int[] y_cord ={(int) (yo +DIAMETER/2 - Math.cos(angle)*DIAMETER/2),(int) (yo +DIAMETER/2+ Math.cos(angle)*DIAMETER/2),(int) (y +DIAMETER/2+ Math.cos(angle)*DIAMETER/2),(int) (y +DIAMETER/2- Math.cos(angle)*DIAMETER/2)};
		
		Path2D.Double path = new Path2D.Double();
		path.moveTo(xo + DIAMETER/2 + (Math.sin(angle))*DIAMETER/2, yo +DIAMETER/2 - Math.cos(angle)*DIAMETER/2);
		path.lineTo(xo + DIAMETER/2 - (Math.sin(angle))*DIAMETER/2, yo +DIAMETER/2+ Math.cos(angle)*DIAMETER/2);
		path.lineTo(x +DIAMETER/2- Math.sin(angle)*DIAMETER/2, y +DIAMETER/2+ Math.cos(angle)*DIAMETER/2);
		path.lineTo(x +DIAMETER/2 + Math.sin(angle)*DIAMETER/2, y +DIAMETER/2- Math.cos(angle)*DIAMETER/2);
		
		/*path.moveTo(x_cord[0], y_cord[0]);
		path.lineTo(x_cord[1], y_cord[1]);
		path.lineTo(x_cord[2], y_cord[2]);
		path.lineTo(x_cord[3],y_cord[3]);*/			
		path.closePath();
		
		Rectangle2D bound_rect = path.getBounds2D();
		
		g.fill(path);
		
		//Polygon curr_poly = new Polygon(x_cord,y_cord, 4);
		//Rectangle2D bound_rect = curr_poly.getBounds2D();
		path_new.append(bound_rect, false);
		//g.fill(path_new);
		game.canvas.add(1000*x+y);
	}
	
	public void keyPressed(KeyEvent e){
		
		switch(e.getKeyCode()){
		
			case KeyEvent.VK_LEFT : angle -= 0.1; break;
			case KeyEvent.VK_RIGHT : angle += 0.1; break;
			
			case KeyEvent.VK_UP : speed += 1; break;
			case KeyEvent.VK_DOWN : speed -= 1; break;
			
		}
		
		//System.out.println(angle);
		
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
