package fr.b4nanajuice.lo02.brutal;

public interface Strategy {
	
	public void doAction(Fighter e, Fighter a, Zone z);
	
	public void attack(Fighter f, Zone z);
	public void heal(Fighter f);
	public void randomAction(Fighter e, Fighter a, Zone z);

}
