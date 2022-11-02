package fr.b4nanajuice.lo02.brutal;

public class Game {
	
	private Player[] players = new Player[2];
	private Zone[] zones = new Zone[5];
	
	public Zone[] getZones() { return this.zones; }
	public Zone getZone(int i) { return this.zones[i]; }
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
	
	/*
	 * This function initialize the fives zones found in the game.
	 * The function takes as an input a list of names of the different zones.
	 * this function return nothing.
	 */
	public void initZones(String[] n) {
		for (int i = 0; i < 5; i++) {
			this.zones[i] = new Zone(n[i]);
		}
	};
	
	/*
	 * This function allows the user to pick a random zone of the game.
	 * The zone must be a zone that has for the moment no winner.
	 * The function takes nothing as an input.
	 * This function return the index of the zone.
	 */
	public int chooseZone() {
		int resp;
		do {
			resp = (int) (Math.random() * 5);
		} while (this.zones[resp].getWinner() != null);
		return resp;
	}
	
	/*
	 * This function allows the game to know when a player has 3 zones.
	 * The player is then the winner of the game.
	 * The function takes nothing as an input.
	 * The function return the winning player or null if none of the player wins.
	 */
	public Player getWinner() {
		Player resp = null;
		int p1_c = 0, p2_c = 0;
		for (Zone z : this.zones) {
			if (z.getWinner() == this.getFirstPlayer()) {
				p1_c++;
			} else if (z.getWinner() == this.getSecondPlayer()) {
				p2_c++;
			}
		}
		if (p1_c >= 3) {
			resp = this.getFirstPlayer();
		} else if (p2_c >= 3) {
			resp = this.getSecondPlayer();
		}
		
		return resp;
	}

}
