package fr.b4nanajuice.lo02.brutal;

public class Main {
	
	private static Game game = new Game();
	
	public static void main(String[] args) {
		game.initZones(new String[] {"Bibliothèque", "Bureau des Etudiants", 
				"Quartier administratif", "Halle indistrielle", "Halle sportive"});
		
		game.initPlayers();
		Player p1 = game.getFirstPlayer();
		Player p2 = game.getSecondPlayer();
		
		Utils.broadcast("-------------------- Joueur 1 --------------------");
		Fighter[] temp_p1 = Fighter.generate();
		
		System.out.println();
		System.out.println("Fighters p1");
		for (Fighter f : temp_p1) {
			System.out.println(f);
		}
		
		p1.chooseReservists(temp_p1);
		
		/*
		System.out.println("-------------------- Joueur 2 --------------------");
		Fighter[] temp_p2 = Fighter.generate();
		p2.chooseReservists(temp_p2);
		*/
		
		System.out.println();
		System.out.println("Réservistes p1");
		for (Fighter c : p1.getReservists()) {
			System.out.println(c);
		}
		
		System.out.println();
		System.out.println("disribution p1");
		p1.distributeFighters();
		
		for (Zone z : game.getZones()) {
			Utils.broadcast(z.getName());
			for (Fighter f : z.getP1()) {
				if (f != null) {
					System.out.println(f);
				}
			}
		}
		
	}
	
	public static Game getGame() { return game; }

}