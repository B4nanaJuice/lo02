package fr.b4nanajuice.lo02.brutal;

public class Fighter implements Strategy {
	
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
		this.credits = 30 + c;
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
	
	public void removeCredits(int c) { this.credits -= c; }
	public void addCredits(int c) { this.credits += c; }
	public void removeInitiative() { this.initiative--; }
	
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
			System.out.println("Ce combattant est un ma??tre du gobi. Il a donc des statistiques am??lior??es (+2/2/2/10/2).");
		}
		else if (nb < 5) { 
			r = Role.elite; 
			System.out.println("Ce combattant est une ??lite. Il a donc des statistiques am??lior??es (+1/1/1/5/1).");
		} 
		else { r = Role.basic; }
		
		do {
			s = Utils.input("Entrez les informations du combattant n??" + (nb+1) + ": ");
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
				System.out.println("/!\\ L'argument " + (v+1) + " doit ??tre un nombre entre 0 et 10 (en comptant le role du combattant).");
			}
		}
		
		if (!Utils.in(i[5], strategies)) { 
			resp = false;
			System.out.println("/!\\ La strat??gie propos??e n'est pas valide (a, h ou r).");
		}
		
		if (resp && Integer.parseInt(i[0]) + Integer.parseInt(i[1]) +
				 Integer.parseInt(i[2]) + Integer.parseInt(i[3]) +
				 Integer.parseInt(i[4]) > pnts) {
			resp = false;
			System.out.println("/!\\ Tu n'as pas assez de points pour donner autant ?? ce combattant.");
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
			choice = Utils.input("S??lectionne une m??thode de cr??ation de combat: (random/manual)");
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
				"- Les entr??es doivent ??tre sous la forme force/d??xt??rit??/r??sistance/constitution/initiative/strat??gie");
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
					System.out.println("/!\\ L'argument n??" + (v+1) + " doit ??tre un nombre entre 1 et 20.");
				}
			}
		}
		
		
		if (resp) {
			for (int v = 0; v < i.length; v++) {
				if (Utils.in(Integer.parseInt(i[v]), ids)) {
					resp = false;
					System.out.println("/!\\ Il faut donner des combattants diff??rents.");
				} else {
					ids[v] = Integer.parseInt(i[v]);
				}
			}
		}
		
		if (resp) {
			for (int v : ids) {
				if (getFighter(v, fs) == -1) {
					resp = false;
					System.out.println("/!\\ Le combattant n??" + v + " a d??j?? ??t?? envoy??.");
				}
			}
		}
		
		return resp;
	}
	
	/*
	 * This function allow the user to get a fighter in a given list by its id.
	 * The function takes the id and the fighter list as an input.
	 * This function returns the index of the fighter.
	 */
	public static int getFighter(int id, Fighter[] fs) {
		int resp = -1;
		for (int v = 0; v < fs.length; v++) {
			if (fs[v] != null && fs[v].getId() == id) {
				resp = v;
			}
		}
		return resp;
	}
	
	/*
	 * This function allows the user to sort a fighter list by their initiative.
	 * The function takes as an input the list of fighters.
	 * This function return nothing.
	 */
	public static void sortByInitiative(Fighter[] fs) {
		Fighter temp;
		for (int i = 0; i < Utils.getSize(fs); i++) {
			for (int j = 0; j < Utils.getSize(fs) - 1; j++) {
				if (fs[j].getInitiative() < fs[j+1].getInitiative()) {
					temp = fs[j];
					fs[j] = fs[j+1];
					fs[j+1] = temp;
				}
			}
		}
	}
	
	/*
	 * This function allows the user to get the weakest player (in terms of credits).
	 * This will be used when a fighter attacks or heals someone else.
	 * The function takes as an input a list of fighters.
	 * This function returns the weakest fighter.
	 */
	public static Fighter getWeakest(Fighter[] fs) {
		Fighter resp = null;
		int c = 40;
		for (int i = 0; i < Utils.getSize(fs); i++) {
			if (fs[i].getCredits() < c) {
				resp = fs[i];
				c = resp.getCredits();
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

	@Override
	public void attack(Fighter f, Zone z) {
		/*
		 * Attaquer frontalement. Sur sa zone de combat, l?????tudiant lance son gobi ?? l???ennemi qui a le moins de
		 * cr??dits ECTS.
		 * Un tirage al??atoire permet de savoir si l???attaque est r??ussie. Soit x ??? [0,100] un nombre tir??
		 * al??atoirement. Si x ??? [0 , 40 + 3 ??? dext??rit?? de l???attaquant] alors l???attaque est r??ussie. Dans un tel
		 * cas, l???attaqu?? perd E(y ??? (1 + coefficient d??g??t) ??? d??g??t de r??f??rence) cr??dits ECTS avec y ???]0, 1]
		 * un nombre tir?? al??atoirement et E(z) la partie enti??re de z. 
		 * Le d??g??t de r??f??rence vaut 10 et
		 * coefficient d??g??t = max(0, min(100, 10 ??? force de l'attaquant ??? 5 ??? r??sistance de l'attaqu??))
		 */
		if (Math.random()*100 <= 40 + 3 * this.dexterity) {
			int coef = Math.max(0, Math.min(100, 10 * this.strength - 5 * f.getResistance()));
			int ref = 1;
			int c = (int) (Math.random() * (1*coef) * ref);
			if (c > f.getCredits()) {
				c = f.getCredits();
			}
			f.removeCredits(c);
			System.out.println("Le combattant " + f.getId() + " a perdu " + c + " cr??dits.");
			System.out.println("Il lui reste " + f.getCredits() + " cr??dits.");
			if (f.getCredits() <= 0) {
				z.removeFighter(f);
			}
		} else {
			System.out.println("L'attaquant a loup?? sa cible...");
		}
	}

	@Override
	public void heal(Fighter f) {
		/*
		 * Soigner un combattant alli??. Cela consiste ?? choisir un alli?? sur la m??me zone de combat ayant le moins
		 * de cr??dits ECTS. Pour le soign??, le nombre de cr??dits gagn??s d??pend de la dext??rit?? du soignant et de la
		 * constitution du soign??.
		 * Un tirage al??atoire permet de savoir si le soin est r??ussi. Soit x ??? [0,100] un nombre tir?? al??atoirement.
		 * Si x ??? [0 , 20 + 6 ??? dext??rit?? du soignant] alors le soin est r??ussi. Dans un tel cas, le soign?? engrange
		 * E(y ??? (10 + consitution du soign??)) cr??dits ECTS avec y ???]0, 0.6] un nombre tir?? al??atoirement et
		 * E(z) la partie enti??re de z. Le gain obtenu ne peut en aucun cas d??passer la valeur (30 +
		 * constitution du soign??)
		 */
		if (Math.random() * 100 <= 20 + 6 * this.dexterity) {
			int c = (int) (Math.random() * 0.6 * (10 + f.getConstitution()));
			if (f.getCredits() + c > 30 + f.getConstitution()) {
				c = 30 + f.getConstitution() - f.getCredits();
			}
			f.addCredits(c);
			System.out.println("Le combattant " + f.getId() + " a gagn?? " + c + " cr??dits.");
			System.out.println("Il a maintenant " + f.getCredits() + " cr??dits.");
		} else {
			System.out.println("Le soignant a rat?? son sort.");
		}
	}

	@Override
	public void randomAction(Fighter e, Fighter a, Zone z) {
		if (Math.random() > .5) {
			this.attack(e, z);
		} else {
			this.heal(a);
		}
	}
	
	@Override
	public void doAction(Fighter e, Fighter	a, Zone z) {
		System.out.println("Le combattant "+  this.id + " effectue son action (" + this.strategy + ")");
		switch (this.strategy) {
		case "a":
			this.attack(e, z);
			break;
		case "h":
			this.heal(a);
			break;
		default:
			this.randomAction(e, a, z);
			break;
		}
	}

}
