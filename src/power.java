import java.util.List;

public abstract class power {
	
	int ttl = 100;
	int x,y;
	peg P;
	boolean isActive = false;
	
	public abstract void effect();
	public abstract void removeEffect();
	
	public power(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void collision(peg Pegs){
		if(!isActive){
			this.P = Pegs;
			isActive=true;
			effect();
		}
	}
	
	public void decay(){ 
		if(isActive){
			ttl--;
			if(ttl == 0)
				removeEffect();
		}
	}
}
