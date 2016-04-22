import java.util.List;

public class thicken extends power {

	public thicken(int x,int y) {
		// TODO Auto-generated constructor stub
		super(x,y);
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		if(ttl > 0){
			P.DIAMETER *= 1.5;
		}
	}
	
	
}
