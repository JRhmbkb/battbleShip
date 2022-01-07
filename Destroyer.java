package battleship;

/**
 * Describe a Destroyer, a ship of length 2.
 */ 
public class Destroyer extends Ship {

	//static variables
	
	/**
	 * The length of destroyer (2)
	 */
	private static final int LENGTH = 2;
	
	/**
	 * The type of destroyer
	 */
	private static final String SHIPTYPE = "destroyer";
	
	//constructor

	/**
	 * Create a destroyer using super() with default destroyer length.
	 */
	public Destroyer() {
		super(Destroyer.LENGTH);
	}

	/**
	 * Return the destroyer type.
	 */
	@Override
	public String getShipType() {
		return Destroyer.SHIPTYPE;
	}
	
}
