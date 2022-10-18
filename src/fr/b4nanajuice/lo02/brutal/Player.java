package fr.b4nanajuice.lo02.brutal;

public class Player {
	
	private String name, spe;
	private Fighter[] fighters = new Fighter[15];
	private Fighter[] reservists = new Fighter[5];
	
	Player(String n, String s) {
		this.name = n;
		this.spe = s;
	}
	
	public String getName() { return this.name; }
	public String getSpe() { return this.spe; }
	public Fighter[] getFIghters() { return this.fighters; }
	public Fighter[] getReservists() { return this.reservists; }
	
	/*
	 * This function allows the player to present themselves.
	 * They give as an input their name and their speciality.
	 * Format: Name/Spe
	 * The function takes the number of the player (first player or second player).
	 * This function returns a Player.
	 */
	public static Player init(int nb) {
		Player p;
		String s;
		String[] i;
		
		do {
			s = Utils.input("Entrer le nom et la spécialité du joueur " + nb + ": ");
			i = s.split("/");
		} while (!isValid(i));
		
		p = new Player(i[0], i[1]);
		
		return p;
	}
	
	/*
	 * This function is used when a player enters his name and speciality.
	 * It tests if there is two arguments (name and speciality).
	 * It also tests if the given speciality is valid.
	 * The function takes the arguments of the console input.
	 * This function return a boolean value.
	 */
	private static boolean isValid(String[] i) {
		boolean resp = true;
		String[] spes = {"ISI", "RT"};
		
		if (i.length != 2) { resp = false; }
		if (resp && !Utils.in(i[1], spes)) { 
			resp = false; 
			System.out.println("La filière entrée est inconnue.");
		}
		
		return resp;
	}
}
