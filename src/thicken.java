import java.util.List;

public class thicken extends power {

	public thicken() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void effect(peg P) {
		// TODO Auto-generated method stub
		if(ttl > 0){
			P.DIAMETER *= 1.5;
		}

	}

	@Override
	public peg collision(List<peg> Pegs) {
		// TODO Auto-generated method stub
		return null;
	}

}
