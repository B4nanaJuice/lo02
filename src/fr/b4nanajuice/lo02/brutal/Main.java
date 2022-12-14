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
		p1.chooseReservists(temp_p1);
		p1.distributeFighters();		
		
		Utils.broadcast("-------------------- Joueur 2 --------------------");
		Fighter[] temp_p2 = Fighter.generate();
		p2.chooseReservists(temp_p2);
		p2.distributeFighters();
		
		do {
			Zone z = game.getZone(game.chooseZone());
			z.fight();
		} while (game.getWinner() == null);
		
		System.out.println("Le gagnant est " + game.getWinner().getName());
		
	}
	
	public static Game getGame() { return game; }

}