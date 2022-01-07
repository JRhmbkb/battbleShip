package battleship;

/**
 * Describe the characteristics common to all ships.
 */
public abstract class Ship {

	//instance variables
	
	/**
	 * The row that contains the bow (front part of the ship)
	 */
	private int bowRow;
	
	/**
	 * The column that contains the bow (front part of the ship)
	 */
	private int bowColumn;
	
	/**
	 * The length of the ship
	 */
	private int length;
	
	/**
	 * A boolean that represents whether the ship is going to be placed horizontally or vertically
	 */
	private boolean horizontal;
	
	/**
	 * An array of 4 booleans that indicate whether that part of the ship has been hit or not
	 */
	private boolean[] hit;
	
	//constructor
	
	/**
	 * Create a ship and set the length property of the particular ship and initializes the hit array with all values as false
	 * @param length
	 */
	public Ship(int length) {
		
        //set length of this ship to be given length, and initialize the 
		this.length = length; 
		this.hit = new boolean[4];
	}

	//methods
	
	//getters and setters
	
	public int getLength() {
		return length;
	}
	
	public int getBowRow() {
		return bowRow;
	}

	public int getBowColumn() {
		return bowColumn;
	}

	public boolean[] getHit() {
		return hit;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}

	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	//abstract method
	
	/**
	 * Returns the type of ship as a String. 
	 * Every specific type of Ship overrides and implements this method and return the corresponding ship type.
	 * @return the type of ship
	 */
	public abstract String getShipType();	
	
	//other methods
	
	/**
	 * Based on the given row, column, and orientation, returns true if it is okay to put a specific ship with its bow in this location; false otherwise. 
	 * The ship must not overlap another ship, or touch another ship (vertically, horizontally, or diagonally), and it must not �stick out� beyond the array. 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 * @return true if it is okay to put a specific ship with its bow in this location; false otherwise. 
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//set bowRow, bowColumn to be given row, column by default
		int bowRow = row;
		int bowColumn = column;
		//set sternRow, sternColumn to be given row, column by default
		int sternRow = row;
		int sternColumn = column;
		
		//if given ship is horizontal
		if (horizontal) {
			//set sternColumn to be (bowColumn - shipLength + 1)
			sternColumn = bowColumn - this.getLength() + 1;		
			
		} else {	
			//if given ship is vertical
			//set sternRow to be (bowColumn - shipLength + 1)
			sternRow = bowRow - this.getLength() + 1;
		}
		
		//set verticalBorder and horizontalBorder of ocean to be (oceanLength - 1) and (oceanWidth - 1)
		int verticalBorder = ocean.getShipArray().length - 1;
		int horizontalBorder = ocean.getShipArray()[0].length - 1;
		
		//set okToPlace to be true by default
		boolean okToPlace = true;

		if (sternRow >= 0 && sternRow <= verticalBorder && sternColumn >= 0 && sternColumn <= horizontalBorder) {
					//check if each of given expected ship slot is occupied
		//iterate over each row of expected ship place inside the ocean
		for (int i = Math.max(0, sternRow - 1); i <= Math.min(horizontalBorder, bowRow + 1); i++) {
			
			////iterate over each column of expected ship place inside the ocean
			for(int j = Math.max(0, sternColumn - 1); j <= Math.min(verticalBorder, bowColumn + 1); j++) {
				
				//if one slot is occupied
				if (ocean.isOccupied(i, j)) {
					
					//set okToPlace to be false
					okToPlace = false;
					//break the j for loop
					break;
				}
				
			}		
			//if not okToPlace, break the i for loop
			if (okToPlace == false) break;
		}
      return okToPlace;
		}else{
      //When the stern is out of border, return false
      return false;
    }

	}
	
	/**
	 * "Puts" the ship in the ocean. 
	 * Give values to the bowRow, bowColumn, and horizontal instance variables in the ship,
	 * put a reference to the ship in each of 1 or more locations (up to 4) in the ships array in the Ocean object.
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		//set bowRow, bowColumn, horizontal of given ship to be given row, column, horizontal
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);

		//set sternRow, sternColumn to be given row, column by default
		int sternRow = row;
		int sternColumn = column;
		
		//if given ship is horizontal
		if (horizontal) {
			//set sternColumn to be (bowColumn - shipLength + 1)
			sternColumn = this.getBowColumn() - this.getLength() + 1;		
			
		} else {	
			//if given ship is vertical
			//set sternRow to be (bowRow - shipLength + 1)
			sternRow = this.getBowRow() - this.getLength() + 1;
		}
		
		//iterate each row of expected ship place
		for (int i = sternRow; i <= this.getBowRow(); i++) {
			
			//iterate each column of expected ship place
			for(int j = sternColumn; j <= this.getBowColumn(); j++) {
				
				//set each slot of expected ship place as this ship
				ocean.getShipArray()[i][j] = this;
				}
			}
		}	
	
	/**
	 * If a part of the ship occupies the given row and column, and the ship hasn�t been sunk, mark that part of the ship as �hit� and return true; otherwise return false
	 * @param row
	 * @param column
	 * @return true if that part of the ship was hit, otherwise false
	 */
	boolean shootAt(int row, int column) {
		
		//return false if this ship is sunk
		if (isSunk()) return false;
		
		//if this ship is horizontal
		if (this.isHorizontal()) {
			
			//iterate each part (column) of this ship from bow
			for (int i = 0; i < this.getLength(); i++) {	
				
				//if any part (column) of this ship in the given row an column.
				if (this.getBowRow() == row && (this.getBowColumn() - i) == column) {
					
					//mark this part as hit in the hit[]
					this.getHit()[i] = true;
					//return true
					return true;
				}
			}	
		//if this ship is vertical
		} else {

			//iterate each part (row) of this ship from bow
			for (int i = 0; i < this.getLength(); i++) {
				
				//if any part (row) of this ship in the given row an column.
				if ((this.getBowRow() - i) == row && this.getBowColumn() == column) {
					
					//mark this part as hit in the hit[]
					this.getHit()[i] = true;
					//return true
					return true;				
				}
			}
		}
		//return false if no part of this ship was hit
		return false;
	}

	
	/**
	 * Return true if every part of the ship has been hit, false otherwise
	 * @return true if every part of the ship has been hit, false otherwise
	 */
	boolean isSunk() {
//iterate each part of this ship
		for (int i = 0; i < this.getLength(); i++) {
			
			//if any part of this ship is not marked as hit
			if (this.getHit()[i] == false) {
				
				//return false
				return false;
			}
		}
		
		//otherwise return true as this ship is sunk
		return true;
		
	}
	
	@Override
	/**
	 * Return "s" if the ship has been sunk and "x" if it has not been sunk to use in the Ocean�s print method.
	 */
	public String toString() {
    //Return s if the ship is sunk, else return x meaning it has been hit
		if (this.isSunk()) return "s";
		
    return "x";
	}
	
	
}
