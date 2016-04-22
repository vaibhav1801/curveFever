import java.util.List;

public abstract class power {
	
	int ttl = 4000;
	
	public abstract void effect(peg P);
	
	public abstract peg collision(List<peg> Pegs);

}
