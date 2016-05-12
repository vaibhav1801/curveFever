import java.util.List;

public class thicken extends power {

	final double THICKNESS_FACTOR = 2.5;
	
	public thicken(int x,int y) {
		// TODO Auto-generated constructor stub
		super(x,y);
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		if(ttl > 0){
			P.DIAMETER *= THICKNESS_FACTOR;
		}
	}

	@Override
	public void removeEffect() {
		// TODO Auto-generated method stub
		P.DIAMETER = (int)(P.DIAMETER /THICKNESS_FACTOR);
		isActive=false;
	}
	
	
}
