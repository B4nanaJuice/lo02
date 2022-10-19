package fr.b4nanajuice.lo02.brutal;

public class Game {
	
	private Player[] players = new Player[2];
	private Zone[] zones = new Zone[5];
	
	public Zone[] getZones() { return this.zones; }
	public Player getFirstPlayer() { return this.players[0]; }
	public Player getSecondPlayer() { return this.players[1]; }
	
	/*
	 * This function allow the game to initialize the players of the game.
	 * To do so, each player must give as an input in the console their name and their speciality.
	 * The functions takes nothing as an input.
	 * This function does not return anything.
	 */
	public void initPlayers() {
		System.out.println("");
		System.out.println("- Initialisation des joueurs");
		System.out.println("- Les entrées doivent être sous la forme nom/spécialité");
		System.out.println("");
		
		Player p1 = Player.init(1);
		Player p2 = Player.init(2);
		
		this.players[0] = p1;
		this.players[1] = p2;
	}
	
	public void initZones() {};

}
