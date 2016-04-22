import java.util.List;

public abstract class power {
	
	int ttl = 4000;
	int x,y;
	peg P;
	boolean isActive = false;
	
	public abstract void effect();
	
	public power(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void collision(peg Pegs){
		this.P = Pegs;
		isActive=true;
		effect();
	}
	
	public void decay(){ 
		ttl--;
	}
}
