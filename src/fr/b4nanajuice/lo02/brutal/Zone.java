package fr.b4nanajuice.lo02.brutal;

public class Zone {
	
	private String name;
	private Player winner;
	private Fighter[] p1, p2;
	
	Zone(String n) {
		this.name = n;
		this.winner = null;
		this.p1 = new Fighter[20];
		this.p2 = new Fighter[20];
	}
	
	public String getName() { return this.name; }
	public Player getWinner() { return this.winner; }
	public Fighter[] getP1() { return this.p1; }
	public Fighter[] getP2() { return this.p2; }
	
	/*
	 * This function allows the user to put a fighter in the zone.
	 * The fighter will be put in the fighter list depending on which player it is (first or second).
	 * The functions takes as an input a list of fighters (the player's list), 
	 * 		the position where the fighter will be put, the index of the fighter in the given list and the player.
	 * This function returns nothing.
	 */
	public void putFighter(Fighter[] fs, int pos, int index, Player p) {
		if (p.equals(Main.getGame().getFirstPlayer())) {
			this.p1[pos] = fs[index];
		} else {
			this.p2[pos] = fs[index];
		}
		fs[index] = null;
	}
	
	public void fight() {
		// Sort the two lists by fighter initiative
		Fighter.sortByInitiative(this.p1);
		Fighter.sortByInitiative(this.p2);
		
		// Have two index (1 for the first player, 1 for the second)
		// Test if first consitution > second constitution: 
		// If yes: first do the action and index + 1
		// If index > list size: index = 0
	}
	
}
