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
	
	public void removeFighter(Fighter f) {
		int index;
		if (Utils.in(f, this.p1)) {
			index = Fighter.getFighter(f.getId(), this.p1);
			this.p1[index] = null;
			for (int j = index; j < Utils.getSize(this.p1); j++) {
				this.p1[j] = this.p1[j + 1];
			}
			this.p1[Utils.getSize(this.p1)] = null;
		} else {
			index = Fighter.getFighter(f.getId(), this.p2);
			this.p2[index] = null;
			for (int j = index; j < Utils.getSize(this.p2); j++) {
				this.p2[j] = this.p2[j + 1];
			}
			this.p2[Utils.getSize(this.p2)] = null;
		}
	}
	
	public void fight() {
		// Sort the two lists by fighter initiative
		// Have two index (1 for the first player, 1 for the second)
		Fighter.sortByInitiative(this.p1);
		int i_p1 = 0;
		Fighter.sortByInitiative(this.p2);
		int i_p2 = 0;
		
		Utils.broadcast("Joueur 1: ");
		for (Fighter f : this.p1) {
			if (f != null) System.out.println(f);
		}
		Utils.broadcast("Joueur 2: ");
		for (Fighter f : this.p2) {
			if (f != null) System.out.println(f);
		}
		
		while (Utils.getSize(this.p1) != 0 && Utils.getSize(this.p2) != 0) {
			// Test if first consitution > second constitution:
			if (this.p1[i_p1].getInitiative() > this.p2[i_p2].getInitiative()) {
				Utils.broadcast("Le joueur 1 joue");
				// If yes: first do the action and index + 1
				System.out.println("i_p1: " + i_p1);
				System.out.println("Size p1: " + Utils.getSize(this.p1));
				this.p1[i_p1].doAction(Fighter.getWeakest(this.p2), Fighter.getWeakest(this.p1), this);
				// If index > list size: index = 0
				this.p1[i_p1].removeInitiative();
				i_p1++;
				if (i_p1 >= Utils.getSize(this.p1)) i_p1 = 0;
			} else {
				Utils.broadcast("Le joueur 2 joue");
				// else: second do the action and index + 1
				System.out.println("i_p2: " + i_p2);
				System.out.println("Size p2: " + Utils.getSize(this.p2));
				this.p2[i_p2].doAction(Fighter.getWeakest(this.p1), Fighter.getWeakest(this.p2), this);
				// If index > list size: index = 0
				this.p2[i_p2].removeInitiative();
				i_p2++;
				if (i_p2 >= Utils.getSize(this.p2)) i_p2 = 0;
			}
		}
		
		Player w;
		if (Utils.getSize(this.p1) == 0) {
			w = Main.getGame().getFirstPlayer();
			System.out.println(w.getName() + " a conquéri la zone " + this.name);
		} else {
			w = Main.getGame().getSecondPlayer();
			System.out.println(w.getName() + " a conquéri la zone " + this.name);
		}
		this.winner = w;
	}
	
}
