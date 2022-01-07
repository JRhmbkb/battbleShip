package battleship;

/**
 * Describe a Battleship, a ship of length 4.
 */

public class Battleship extends Ship {
	
	//static variables
	
	/**
	 * The length of battleship (4)
	 */
	private static final int LENGTH = 4;
	
	/**
	 * The type of battleship
	 */
	private static final String SHIPTYPE = "battleship";
	
	//constructor
	
	/**
	 * Create a battleship using super() with default battleship length.
	 */
	public Battleship() {
		super(Battleship.LENGTH);
	}

	/**
	 * Return the battleship type.
	 */
	@Override
	public String getShipType() {	
		return Battleship.SHIPTYPE;
	}
	
}
