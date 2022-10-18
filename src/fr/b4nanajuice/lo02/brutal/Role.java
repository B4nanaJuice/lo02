package fr.b4nanajuice.lo02.brutal;

public enum Role {
	
	basic (0, 0, 0, 0, 0),
	elite (1, 1, 1, 5, 1),
	master (2, 2, 2, 10, 2);
	
	private int strength, dexterity, resistance, constitution, initiative;

	Role(int s, int d, int r, int c, int i) {
		this.strength = s;
		this.dexterity = d;
		this.resistance = r;
		this.constitution = c;
		this.initiative = i;
	}
	
	public int getStrength() { return this.strength; }
	public int getDexterity() { return this.dexterity; }
	public int getResistance() { return this.resistance; }
	public int getConstitution() { return this.constitution; }
	public int getInitiative() { return this.initiative; }
	
	public int[] getStats() {
		int[] stats = new int[5];
		stats[0] = this.strength;
		stats[1] = this.dexterity;
		stats[2] = this.resistance;
		stats[3] = this.constitution;
		stats[4] = this.initiative;
		
		return stats;
		
	}
}
