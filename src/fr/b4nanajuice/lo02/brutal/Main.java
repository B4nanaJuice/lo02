package fr.b4nanajuice.lo02.brutal;

public class Main {
	
	private static Game game = new Game();
	
	public static void main(String[] args) {
		game.initPlayers();
		Player p1 = game.getFirstPlayer();
		Player p2 = game.getSecondPlayer();
		
		System.out.println("-------------------- Joueur 1 --------------------");
		Fighter[] temp_p1 = Fighter.generate();
		
		System.out.println("-------------------- Joueur 2 --------------------");
		Fighter[] temp_p2 = Fighter.generate();
		
		
		
	}
	
	public static Game getGame() { return game; }

}
