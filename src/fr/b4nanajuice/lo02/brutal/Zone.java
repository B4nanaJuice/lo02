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
	
	public void putFighter(Fighter[] fs, int pos, int index, Player p) {
		if (p.equals(Main.getGame().getFirstPlayer())) {
			this.p1[pos] = fs[index];
		} else {
			this.p2[pos] = fs[index];
		}
		fs[index] = null;
	}
	
	
}
