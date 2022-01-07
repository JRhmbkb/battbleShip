package battleship;
// * Describe a part of the ocean that doesn’t have a ship in it.
public class EmptySea extends Ship {

	//static variables
	
	/**
	 * The length of emptySea (1)
	 */
	private static final int LENGTH = 1;
	
	/**
	 * The type of emptySea
	 */
	private static final String SHIPTYPE = "empty";

	//constructor

	/**
	 * Create a emptySea using super() with default emptySea length.
	 */
	public EmptySea() {
		super(EmptySea.LENGTH);
	}

	//methods
	
	/**
	 * Always returns false to indicate that nothing was hit.
	 */
	@Override 
	boolean shootAt(int row, int column) {
		
		//mark the emptySea as hit if be shoot at and return false
		this.getHit()[0] = true;
		return false;
	}
	
	/**
	 * Always returns false to indicate that you didn’t sink anything.
	 */
	@Override 
	boolean isSunk(){
		return false;
	}
	
	/**
	 * Returns “-“ if a shot has been fired to emptySea (nothing has been hit).
	 */
	@Override 
	public String toString() {
		
		//return "-" if a shot has been fired to emptySea
		return "-";
		

	}
	
	/**
	 * Return the emptySea type.
	 */

	@Override
	public String getShipType() {
		return EmptySea.SHIPTYPE;
	}
	
}
