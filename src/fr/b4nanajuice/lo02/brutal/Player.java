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
	
	/*
	 * This function allows the players to choose their reservists.
	 * The player need to input 5 different fighter's id.
	 * The function takes as an input the list of fighters.
	 * This function returns nothing.
	 */
	public void chooseReservists(Fighter[] fs) {
		Utils.broadcast("- Choix des réservistes\n" + 
				"- L'entrée doit se faire sous la forme id/id/id/id/id");
		String s;
		String[] i;
		
		do {
			s = Utils.input("Entrez l'id des 5 combattants que vous voulez mettre réservite: ");
			i = s.split("/");
		} while(!Fighter.validFighter(i, 5, fs));
		
		int id;
		for (int v = 0; v < 5; v++) {
			id = Integer.parseInt(i[v]) - 1;
			this.reservists[v] = fs[id];
			fs[id] = null;
		}
		int counter = 0;		
		for (int v = 0; v < 20; v++) {
			if (fs[v] != null) {
				this.fighters[counter] = fs[v];
				counter++;
			}
		}
	}
	
	public void distributeFighters() {
		String s;
		String[] i;
		
		Utils.broadcast("- Distribution des combattants\n" + 
				"- Les entrées se font sous la forme id[/id/...]");
		
		for (Zone z: Main.getGame().getZones()) {
		
			do {
				s = Utils.input("Entrez les id des combattants à mettre dans la zone \"" + z.getName() + "\"");
				i = s.split("/");
			// while 
			// IF the zone isn't the last one AND minimum 1 fighter
			// OR IF the zone is the last AND size of remaining fighters
			} while ((!z.equals(Main.getGame().getZones()[4]) && !Fighter.validFighter(i, 0, this.fighters)) || 
					(z.equals(Main.getGame().getZones()[4]) && !Fighter.validFighter(i, Utils.getSize(this.fighters), this.fighters)));
			
			int id;
			for (int v = 0; v < i.length; v++) {
				id = Integer.parseInt(i[v]);
				z.putFighter(this.fighters, v, Fighter.getFighter(id, this.fighters), this);
			}
			
		}
	}
}
