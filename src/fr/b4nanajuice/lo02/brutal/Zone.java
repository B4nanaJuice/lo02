package fr.b4nanajuice.lo02.brutal;

public enum Zone {

	bu("Bibliothèque"), 
	bde("Bureau des Etudiants"), 
	qa("Quartiers administratifs"), 
	hi("Halle industrielle"), 
	hs("Halle sportive");
	
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
	
	
	
}
