package battleship;

/**
 * Describe a Submarine, a ship of length 1.
 * @author Mengyao Fan & Rui Jiang
 *
 */
public class Submarine extends Ship {

	//static variables
	
	/**
	 * The length of submarine (1)
	 */
	private static final int LENGTH = 1;
	
	/**
	 * The type of submarine
	 */
	private static final String SHIPTYPE = "submarine";
	
	//constructor

	/**
	 * Create a submarine using super() with default submarine length.
	 */
	public Submarine() {
		super(Submarine.LENGTH);
	}

	/**
	 * Return the submarine type.
	 */
	@Override
	public String getShipType() {
		return Submarine.SHIPTYPE;
	}
	
	
}
