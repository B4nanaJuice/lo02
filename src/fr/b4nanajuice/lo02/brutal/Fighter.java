package fr.b4nanajuice.lo02.brutal;

public class Fighter {
	
	private int strength, dexterity, resistance, constitution, initiative, credits, id;
	private String strategy;
	private Role role;
	private static String[] strategies = {"a", "h", "r"}; // Attack / Heal / Random
	
	public Fighter(int s, int d, int r, int c, int i, int id, String st, Role ro) {
		this.strength = s;
		this.dexterity = d;
		this.resistance = r;
		this.constitution = c;
		this.initiative = i;
		this.id = id;
		this.strategy = st;
		this.role = ro;
	}
	
	public int getStrength() { return this.strength; }
	public int getDexterity() { return this.dexterity; }
	public int getResistance() { return this.resistance; }
	public int getConstitution() { return this.constitution; }
	public int getInitiative() { return this.initiative; }
	public int getCredits() { return this.credits; }
	public int getId() { return this.id; }
	public String getStrategy() { return this.strategy; }
	public Role getRole() { return this.role; }
	
	/*
	 * This functions allow to the game to get all points given to the fighter.
	 * It is used to calculate how much points the player can give to each fighter.
	 * The function takes nothing as an input.
	 * This function returns the sum of all points. 
	 */
	public int getPoints() {
		int[] points = {this.strength, this.dexterity, this.resistance, 
				this.constitution, this.initiative};
		return Utils.sum(points) - Utils.sum(this.role.getStats());
	}
	
	/*
	 * This function allows the players to initialize their fighters manually.
	 * It warns the players when the fighter is special (elite or master).
	 * The function takes nothing as an input.
	 * This function returns a Fighter.
	 */
	public static Fighter init(int nb, int pnts) {
		Fighter f;
		String s;
		String[] i;
		
		System.out.println("Points restants: " + pnts);
		
		Role r;
		if (nb == 0) { 
			r = Role.master;
			System.out.println("Ce combattant est un maître du gobi. Il a donc des statistiques améliorées (+2/2/2/10/2).");
		}
		else if (nb < 5) { 
			r = Role.elite; 
			System.out.println("Ce combattant est une élite. Il a donc des statistiques améliorées (+1/1/1/5/1).");
		} 
		else { r = Role.basic; }
		
		do {
			s = Utils.input("Entrez les informations du combattant n°" + (nb+1) + ": ");
			i = s.split("/");
		} while (!isValid(i, r, pnts));
		
		f = new Fighter(Integer.parseInt(i[0]) + r.getStrength(),
				 Integer.parseInt(i[1]) + r.getDexterity(),
				 Integer.parseInt(i[2]) + r.getResistance(),
				 Integer.parseInt(i[3]) + r.getConstitution(),
				 Integer.parseInt(i[4]) + r.getInitiative(),
				 nb+1, i[5], r);
		 
		return f;
	}
	
	/*
	 * This function allows the players to initialize their fighters automatically and randomly.
	 * The function takes nothing as an input.
	 * This function returns a Fighter.
	 */
	public static Fighter random(int nb, int pnts) {
		Fighter f;
		String[] s = {"a", "h", "r"};
		
		Role r;
		if (nb == 0) { r = Role.master; }
		else if (nb < 5) { r = Role.elite; } 
		else { r = Role.basic; }
		
		int[] stats = new int[5];
		for (int i = 0; i < 5; i++) {
			stats[i] = (int) (Math.random() * (10 - r.getStats()[i]));
			if (pnts - stats[i] < 0) {
				stats[i] = 0;
			} else {
				pnts -= stats[i];
			}
		}
		
		f = new Fighter(stats[0] + r.getStrength(), stats[1] + r.getDexterity(), 
				stats[2] + r.getResistance(), stats[3] + r.getConstitution(), 
				stats[4] + r.getInitiative(), nb+1,
				s[(int) (Math.random() * 3)], r);
		
		return f;
	}
	
	/*
	 * This function is used when a player enters fighter's characteristics.
	 * It tests if there is 6 arguments, also if each points given (added to the role's points) are below 10.
	 * It also tests if the given strategy is valid.
	 * The function takes the arguments, the role of the fighter and the points the player has.
	 * This function returns a boolean value.
	 */
	private static boolean isValid(String[] i, Role r, int pnts) {
		boolean resp = true;
		int[] adders = r.getStats();
		if (i.length != 6) return false;
		for (int v = 0; v < 5; v++) {
			if (!(Utils.isInt(i[v]) && Integer.parseInt(i[v]) + adders[v] >= 0
					&& Integer.parseInt(i[v]) + adders[v] <= 10)) {
				resp = false;
				System.out.println("/!\\ L'argument " + (v+1) + " doit être un nombre entre 0 et 10 (en comptant le role du combattant).");
			}
		}
		
		if (!Utils.in(i[5], strategies)) { 
			resp = false;
			System.out.println("/!\\ La stratégie proposée n'est pas valide (a, h ou r).");
		}
		
		if (resp && Integer.parseInt(i[0]) + Integer.parseInt(i[1]) +
				 Integer.parseInt(i[2]) + Integer.parseInt(i[3]) +
				 Integer.parseInt(i[4]) > pnts) {
			resp = false;
			System.out.println("/!\\ Tu n'as pas assez de points pour donner autant à ce combattant.");
		}

		return resp;
	}
	
	/*
	 * This function allow to the player to choose how they want to train the fighters.
	 * The player chooses if they want to train the fighters manually or randomly.
	 * Format: [random|manual]
	 * The function takes nothing as input.
	 * This function returns a list of 20 Fighters (generated by the train() and randomFighter() functions).
	 */
	public static Fighter[] generate() {
		String choice;
		String[] choices = {"random", "manual"};
		
		do {
			choice = Utils.input("Sélectionne une méthode de création de combat: (random/manual)");
		} while (!Utils.in(choice, choices));
		
		Fighter[] resp;
		
		switch (choice) {
			case "random":
				resp = randomFighter();
				break;
			default:
				resp = train();
				break;
		}
		
		return resp;
	}
	
	/*
	 * This function allow to each player to train their fighters.
	 * As an input, the function needs for each fighter a score to give
	 * to each ability and the strategy of the fighter
	 * Format: Str/Dex/Res/Con/Ini/Strat
	 * The function takes nothing as an input.
	 * This function returns a list of 20 Fighters.
	 */
	public static Fighter[] train() {
		Utils.broadcast("- Initialisation des combattants\n" + 
				"- Les entrées doivent être sous la forme force/déxtérité/résistance/constitution/initiative/stratégie");
		int points = 400;
		Fighter[] resp = new Fighter[20];
		Fighter temp;
		
		for (int i = 0; i < 20; i++) {
			temp = Fighter.init(i, points);
			resp[i] = temp;
			points -= temp.getPoints();
		}
		
		return resp;		
	}
	
	/*
	 * This function allow to each player to train their fighters randomly.
	 * They specify firstly if they want the generation to be random.
	 * Each fighter will have random statistics.
	 * The function takes nothing as an input.
	 * This function returns a list of 20 Fighters.
	 */
	public static Fighter[] randomFighter() {
		int points = 400;
		Fighter[] resp = new Fighter[20];
		Fighter temp;
		
		for (int i = 0; i < 20; i++) {
			temp = Fighter.random(i, points);
			resp[i] = temp;
			points -= temp.getPoints();
		}
		
		return resp;
	}
	
	/*
	 * This function allows the user to test if the list of fighter's id is valid or not.
	 * The function takes as an input the list of id, the number of fighters you need to enter, and the list of fighters.
	 * This function return a boolean value.
	 */
	public static boolean validFighter(String[] i, int nb, Fighter[] fs) {
		boolean resp = true;
		Integer[] ids = new Integer[i.length];
		
		if (nb == 0) {
			if (i.length == 0) {
				resp = false;
				System.out.println("/!\\ Il faut donner au moins 1 combattant.");
			}
		} else {
			if (i.length != nb) {
				resp = false;
				System.out.println("/!\\ Il faut donner " + nb + " combattants.");
			}
		}
		
		if (resp) {
			for (int v = 0; v < i.length; v++) {
				if (!Utils.isInt(i[v]) || Integer.parseInt(i[v]) <= 0 || Integer.parseInt(i[v]) > 20) {
					resp = false;
					System.out.println("/!\\ L'argument n°" + (v+1) + " doit être un nombre entre 1 et 20.");
				}
			}
		}
		
		
		if (resp) {
			for (int v = 0; v < i.length; v++) {
				if (Utils.in(Integer.parseInt(i[v]), ids)) {
					resp = false;
					System.out.println("/!\\ Il faut donner des combattants différents.");
				} else {
					ids[v] = Integer.parseInt(i[v]);
				}
			}
		}
		
		if (resp) {
			for (int v : ids) {
				if (getFighter(v, fs) == -1) {
					resp = false;
					System.out.println("/!\\ Le combattant n°" + v + " a déjà été envoyé.");
				}
			}
		}
		
		return resp;
	}
	
	public static int getFighter(int id, Fighter[] fs) {
		int resp = -1;
		for (int v = 0; v < fs.length; v++) {
			if (fs[v] != null && fs[v].getId() == id) {
				resp = v;
			}
		}
		return resp;
	}
	
	@Override
	public String toString() {
		return (this.id + " -> Str: " + this.strength + 
				" / Dex: " + this.dexterity + 
				" / Res: " + this.resistance + 
				" / Con: " + this.constitution + 
				" / Ini: " + this.initiative + 
				" / Strat: " + this.strategy );
	}

}
