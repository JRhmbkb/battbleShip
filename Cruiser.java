package battleship;
//
/*
 * Describe a Cruiser, a ship of length 3.
 */
public class Cruiser extends Ship {

	//static variables
	
	/**
	 * The length of cruiser (3)
	 */
	private static final int LENGTH = 3;
	
	/**
	 * The type of cruiser
	 */
	private static final String SHIPTYPE = "cruiser";

	//constructor
	
	/**
	 * Create a cruiser using super() with default cruiser length.
	 */
	public Cruiser() {
		super(Cruiser.LENGTH);
	}

	/**
	 * Return the cruiser type.
	 */
	@Override
	public String getShipType() {
		return Cruiser.SHIPTYPE;
	}
	
}
