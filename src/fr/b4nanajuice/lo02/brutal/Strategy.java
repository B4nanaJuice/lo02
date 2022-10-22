package fr.b4nanajuice.lo02.brutal;

public interface Strategy {
	
	public void attack(Fighter f);
	public void heal(Fighter f);
	public void randomAction(Fighter e, Fighter a);

}
