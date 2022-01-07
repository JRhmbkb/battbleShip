
package battleship;

import java.util.*;

/**
 * The class representing an ocean and methods to manipulate it.
 */
public class Ocean {

	//instance variables

	/**
	 * Used to quickly determine which ship is in any given location
	 */
	private Ship[][] ship = new Ship[10][10];

	/**
	 * The total number of shots fired by user
	 */
	private int shotsFired;

	/**
	 * the number of times a shot hit a ship
	 */
	private int hitCount;

	/**
	 * the number of ships sunk
	 */
	private int shipsSunk;

	//Constructor
	
	/**
	 * Creates an "empty" ocean with EmptySea objects
	 * Initialize other variables
	 */
	public Ocean() {

		//Initializing int type variables. By default they're 0.
		this.hitCount = 0;
		this.shipsSunk = 0;
		this.shotsFired = 0;

		//parse over the ship array using a double for loop
		for (int i = 0; i < this.getShipArray().length; i++) {
			
			for (int j = 0; j < this.getShipArray()[i].length; j++) {
				
				//Set up a new instance of EmptySea every time
				Ship empty = new EmptySea();
				
				//Place the new instance at the given row and column
				empty.placeShipAt(i, j, false, this);
			}
		}
	}

	//Methods
	
	/**
	 * Place all ten ships randomly on the empty ocean
	 * Larger ships are placed before smaller ones
	 */
	void placeAllShipsRandomly() {

		//Create a random class for generating random int.
		Random rnd = new Random();
		
		//Create an array of all 10 Ships
		Ship[] allShips = new Ship[10];
		allShips[0] = new Battleship();
		allShips[1] = new Cruiser();
		allShips[2] = new Cruiser();
		allShips[3] = new Destroyer();
		allShips[4] = new Destroyer();
		allShips[5] = new Destroyer();
		allShips[6] = new Submarine();
		allShips[7] = new Submarine();
		allShips[8] = new Submarine();
		allShips[9] = new Submarine();
		
		//Parse over the list of all ships staring with a battleship
		for (Ship s: allShips) {
			
			//set the variable for determining whether or not is ok to place
			//the ship. By default is false.
			boolean okToPlaceShip = false;
			
			//Set a while loop to run until is ok to place a ship
			while (okToPlaceShip == false) {
				
				//Set up variables recording the bowRow, bowColumn and horizontal or not
				int bowRow;
				int bowColumn;	
				boolean isHorizontal;
				
				//Generates a random number between 0 and 9 for the row of bow
				bowRow = rnd.nextInt(10);
				
				//Generates a random number between 0 and 9 for the column of bow
				bowColumn = rnd.nextInt(10);
				
				//Generates a random boolean value for horizontal or not.
				isHorizontal = rnd.nextBoolean();
				
				//Determines whether is okay to place the ship with the given parameters
				okToPlaceShip = s.okToPlaceShipAt(bowRow, bowColumn, isHorizontal, this);
				
				//If Okay, place the ship with the given parameters
				if (okToPlaceShip) s.placeShipAt(bowRow, bowColumn, isHorizontal, this);
			}
		}
	}

	/**
	 * Returns true if the given location is occupied, false if empty
	 */
	boolean isOccupied(int row, int column) {
		
         //if the given location has a shipType of emptySea, returns false
		if (this.getShipArray()[row][column].getShipType().equals("empty")) return false;
        
		//else, return false
		return true;
	}

	/**
	 * Returns true if the given location contains a real ship and is afloat
	 * updates shots fired
	 * updates number of hits
	 */
	boolean shootAt(int row, int column) {

		//The shotsFired increments by 1 regardless of hitting or not.
		this.shotsFired = this.shotsFired + 1;

		//If the spot contains a real ship 
		if (this.isOccupied(row, column)) {
			
			//it will only return true if the slot is not sunk and can be shot at
			if (this.getShipArray()[row][column].shootAt(row, column)) {
				
				//Update hit count
				this.hitCount = this.hitCount + 1;
				
				//return true
				return true;
				
			}else {
				
				//when the slot is sunk and can't be shot
				//return false
				return false;
			}
		
		}else {
			
			//when the location is not occupied.
			//directly shoot at the emptySea slot and return false (defined in emptysea)
			return this.getShipArray()[row][column].shootAt(row, column);
		}

	}

	//Getters
	
	/**
	 * returns the number of shots fired
	 */
	int getShotsFired() {

		return this.shotsFired;
	}

	/**
	 * returns the number of hits recorded
	 */
	int getHitCount() {

		return this.hitCount;
	}

	/**
	 * returns the number of ships sunk
	 */
	int getShipsSunk() {

		return this.shipsSunk;
	}

	/**
	 * determines whether the game is over
	 * returns true if all ships have been sunk
	 */
	boolean isGameOver() {
		
		int sunkShips = this.getShipsSunk();

		//returns true if all ships(10) are sunk
		if (sunkShips == 10) return true;

		//else return false
		return false;
	}

	/**
	 * 
	 * @return the 10x10 array of ship
	 */
	Ship[][] getShipArray(){

		return this.ship;
	}
	
	/**
	 * Setter. Use to update shipsSunk
	 */
	void incrementShipsSunk() {
		
		this.shipsSunk += 1;
	}
	
	/**
	 * Prints the ocean
	 * "x" indicates a location that was fired at and contains a real ship
	 * "-" indicates a location that was fired at but no ships contained
	 * 's' indicates a location with a sunken ship
	 * '.' indicates a location that was never fired at.
	 */
	void print() {
		//Print the first row of the ocean which is the number
		System.out.println("   0  1  2  3  4  5  6  7  8  9  ");

		//parse over each row of the ocean
		for (int i = 0; i < this.getShipArray().length; i++) {

			//Print out the row number
			System.out.print(i + "  ");
			
			//parse over every column of the ocean.
			for (int j = 0; j < this.getShipArray()[0].length; j++) {
				

				//Print "." if the type is empty and it's not hit
				if (this.getShipArray()[i][j].getShipType() == "empty" && 
            this.getShipArray()[i][j].getHit()[0] == false) {
					
					//prints "."
					System.out.print(".  ");
					
				}else {
					
					//If the location is occupied by a ship
					if (this.getShipArray()[i][j].toString().equals("x")) {
						
						//get the location of the ship bow
						int bowRow = this.getShipArray()[i][j].getBowRow();
						int bowColumn = this.getShipArray()[i][j].getBowColumn();
						
						//the index of the slot will be given by the larger value
						//if we compare the difference between slot indexes.
						int indexOnShip = Math.max(bowRow - i, bowColumn - j);
						
						//if in the hit array of the slot corresponds to false, print "."
						if (this.getShipArray()[i][j].getHit()[indexOnShip] == false) {
							
							System.out.print(".  ");
						
              //If the location is occupied by emptySea and hit, use the toString method
						}else{
              System.out.print(this.getShipArray()[i][j].toString() + "  ");
            }
						
					}else {
						
						//otherwise just use toString method of ship class
						System.out.print(this.getShipArray()[i][j].toString() + "  ");
					}
				}
			}
			
			System.out.println();
		}
	}
  
  /**
	 * Prints the ocean
	 * "b" indicates a location that was occupied by a battleship
	 * "c" indicates a location that was occupied by a cruiser
	 * 'd' indicates a location that was occupied by destroyer
	 * 's' indicates a location that was occupied by submarine
	 * " " indicates empty sea
	 */
	void printWithShips() {
		//Print the first row of the ocean which is the number
		System.out.println("   0  1  2  3  4  5  6  7  8  9  ");

		//parse over each row of the ocean
		for (int i = 0; i < this.getShipArray().length; i++) {

			//Print out the row number
			System.out.print(i + "  ");
			
			//parse over every column of the ocean.
			for (int j = 0; j < this.getShipArray()[0].length; j++) {
								//if the toString method of the ship at this location returns nothing
				if (this.getShipArray()[i][j].getShipType() == "battleship") {
					
					//prints "b"
					System.out.print("b  ");
				
			}else if(this.getShipArray()[i][j].getShipType() == "cruiser"){
          System.out.print("c  ");
        }else if(this.getShipArray()[i][j].getShipType() == "destroyer"){
          System.out.print("d  ");
        }else if(this.getShipArray()[i][j].getShipType() == "submarine"){
          System.out.print("s  ");
        }else{
          System.out.print("   ");
        }
			

		}
      			System.out.println();
	}
}
}
					
