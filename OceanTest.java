package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

	Ocean ocean;
	
	static int NUM_BATTLESHIPS = 1;
	static int NUM_CRUISERS = 2;
	static int NUM_DESTROYERS = 3;
	static int NUM_SUBMARINES = 4;
	static int OCEAN_SIZE = 10;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
	}
	
	@Test
	void testEmptyOcean() {
		
		//tests that all locations in the ocean are "empty"
		
		Ship[][] ships = ocean.getShipArray();
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				
				assertEquals("empty", ship.getShipType());
			}
		}
		
		assertEquals(0, ships[0][0].getBowRow());
		assertEquals(0, ships[0][0].getBowColumn());
		
		assertEquals(5, ships[5][5].getBowRow());
		assertEquals(5, ships[5][5].getBowColumn());
		
		assertEquals(9, ships[9][0].getBowRow());
		assertEquals(0, ships[9][0].getBowColumn());
	}
	
	@Test
	void testPlaceAllShipsRandomly() {
		
		//tests that the correct number of each ship type is placed in the ocean
		
		ocean.placeAllShipsRandomly();

		Ship[][] ships = ocean.getShipArray();
		ArrayList<Ship> shipsFound = new ArrayList<Ship>();
		
		int numBattlehips = 0;
		int numCruisers = 0;
		int numDestroyers = 0;
		int numSubmarines = 0;
		int numEmptySeas = 0;
		
		for (int i = 0; i < ships.length; i++) {
			for (int j = 0; j < ships[i].length; j++) {
				Ship ship = ships[i][j];
				if (!shipsFound.contains(ship)) {
					shipsFound.add(ship);
				}
			}
		}
		
		for (Ship ship : shipsFound) {
			if ("battleship".equals(ship.getShipType())) {		
				numBattlehips++;
			} else if ("cruiser".equals(ship.getShipType())) {
				numCruisers++;
			} else if ("destroyer".equals(ship.getShipType())) {
				numDestroyers++;
			} else if ("submarine".equals(ship.getShipType())) {
				numSubmarines++;
			} else if ("empty".equals(ship.getShipType())) {
				numEmptySeas++;
			}
		}
		
		assertEquals(NUM_BATTLESHIPS, numBattlehips);
		assertEquals(NUM_CRUISERS, numCruisers);
		assertEquals(NUM_DESTROYERS, numDestroyers);
		assertEquals(NUM_SUBMARINES, numSubmarines);
		
		//calculate total number of available spaces and occupied spaces
		int totalSpaces = OCEAN_SIZE * OCEAN_SIZE; 
		int occupiedSpaces = (NUM_BATTLESHIPS * 4)
				+ (NUM_CRUISERS * 3)
				+ (NUM_DESTROYERS * 2)
				+ (NUM_SUBMARINES * 1);
		
		//test number of empty seas, each with length of 1
		assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
	}

	@Test
	void testIsOccupied() {

		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.isOccupied(1, 5));
		
		//TODO
		//More tests
    
		//Test when no ship has been placed(empty ocean)
		Ocean ocean = new Ocean();
		//Parse over every slot of the empty ocean
		for (int i = 0; i < ocean.getShipArray().length; i++) {
			for (int j = 0; j < ocean.getShipArray()[0].length; j++) {

				//Check if every slot hasn't been occupied
				assertFalse(ocean.isOccupied(i, j));
			}
		}

		//Place a submarine at (0, 3), check if (0, 3) is occupied.
		Ship submarine1 = new Submarine();
		submarine1.placeShipAt(0, 3, false, ocean);
		assertTrue(ocean.isOccupied(0, 3));

		//Place a battleship at (4, 9), vertically, check if (3, 9)(on the ship) and (4, 8)(not on the ship) is occupied.
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);
		assertTrue(ocean.isOccupied(3, 9));
		assertFalse(ocean.isOccupied(4, 8));
	}

	@Test
	void testShootAt() {
	
		assertFalse(ocean.shootAt(0, 1));
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		
		//TODO
		//More tests
    //When shootAt empty sea, always return false
		Ocean ocean = new Ocean();
		assertFalse(ocean.shootAt(0, 0));
		assertFalse(ocean.shootAt(1, 8));

		//Two shots have been fired.
		assertEquals(2, ocean.getShotsFired());

		//Place a battleship at (4, 9), vertically, shootAt different points
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);

		//Shoot at (4, 8) will return false, hitcount is still 0
		assertFalse(ocean.shootAt(4, 8));
		assertEquals(0, ocean.getHitCount());

		//Shot at (3,9) will return true, hitcount is 1
		assertTrue(ocean.shootAt(3, 9));
		assertEquals(1, ocean.getHitCount());

		//Place a submarine at (0, 3), sink it (with 1 hit)
		Ship submarine = new Submarine();
		submarine.placeShipAt(0, 3, false, ocean);
		//Sink it
		ocean.shootAt(0, 3);

		//If hit a sunk ship, return false
		assertFalse(ocean.shootAt(0, 3));
	}

	@Test
	void testGetShotsFired() {
		
		//should be all false - no ships added yet
		assertFalse(ocean.shootAt(0, 1));
		assertFalse(ocean.shootAt(1, 0));
		assertFalse(ocean.shootAt(3, 3));
		assertFalse(ocean.shootAt(9, 9));
		assertEquals(4, ocean.getShotsFired());
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		Ship submarine = new Submarine();
		row = 0;
		column = 0;
		horizontal = false;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertTrue(ocean.shootAt(0, 5));
		assertTrue(destroyer.isSunk());
		assertEquals(6, ocean.getShotsFired());
		
		//TODO
		//More tests
    //when the sea is empty
		Ocean ocean = new Ocean();
		assertEquals(0, ocean.getShotsFired());

		//When fire a shot but not hitting a ship
		//Place a battleship at (4, 9), vertically
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);
		//Shoot at (5, 9) which is not on the ship
		ocean.shootAt(5, 9);
		assertEquals(1, ocean.getShotsFired());

		//When fired a shot and hitting a ship, shotsfired is still incremented.
		ocean.shootAt(2,  9);
		assertEquals(2, ocean.getShotsFired());
	}

	@Test
	void testGetHitCount() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		
		//TODO
		//More tests
    //when the sea is empty
		Ocean ocean = new Ocean();
		assertEquals(0, ocean.getHitCount());

		//When fire a shot but not hitting a ship
		//Place a battleship at (4, 9), vertically
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);
		//Shoot at (5, 9) which is not on the ship, hitcount isn't incremented.
		ocean.shootAt(5, 9);
		assertEquals(0, ocean.getHitCount());

		//When fired a shot and hitting a ship, hitCount is incremented.
		ocean.shootAt(2,  9);
		assertEquals(1, ocean.getHitCount());
	}
	
	@Test
	void testGetShipsSunk() {
		
		Destroyer destroyer = new Destroyer();
		int row = 1;
		int column = 5;
		boolean horizontal = false;
		destroyer.placeShipAt(row, column, horizontal, ocean);
		
		assertTrue(ocean.shootAt(1, 5));
		assertFalse(destroyer.isSunk());
		assertEquals(1, ocean.getHitCount());
		assertEquals(0, ocean.getShipsSunk());
		
		//TODO
		//More tests
    //when the sea is empty,no ship is sunk
		Ocean ocean = new Ocean();
		assertEquals(0, ocean.getShipsSunk());

		//When fire a shot but not hitting a ship
		//Place a battleship at (4, 9), vertically
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);
		//Shoot at (2, 9) which is  on the ship, yet no ship is sunk.
		ocean.shootAt(2, 9);
		//Update ShipsSunk if the shot hits a ship and the ship is sunk
		if (ocean.shootAt(2, 9) && ocean.getShipArray()[2][9].isSunk()) ocean.incrementShipsSunk();
		assertEquals(0, ocean.getShipsSunk());

		//Shoot at all spots, the ship is sunk
		for (int i = 1; i <= 4; i++) {
			if (ocean.shootAt(i, 9) && ocean.getShipArray()[i][9].isSunk()) ocean.incrementShipsSunk();
		}
		assertEquals(1, ocean.getShipsSunk());

	}

	@Test
	void testGetShipArray() {
		
		Ship[][] shipArray = ocean.getShipArray();
		assertEquals(OCEAN_SIZE, shipArray.length);
		assertEquals(OCEAN_SIZE, shipArray[0].length);
		
		assertEquals("empty", shipArray[0][0].getShipType());
		
		//TODO
		//More tests
    		//when the ocean is just initiated, the ocean is empty, every slot has a type of emptySea
		Ocean ocean = new Ocean();

		//Parse over every spot of the empty ocean.
		for (int i = 0; i < ocean.getShipArray().length; i++) {
			for (int j = 0; j < ocean.getShipArray()[0].length; j++) {

				//Check if every slot has a type "empty"
				assertEquals("empty", ocean.getShipArray()[i][j].getShipType());
			}
		}

		//when there is a battleship at (4,9), vertically, all spots except for ones on the ship are stil empty
		//Place a battleship at (4, 9), vertically
		Ship battleship = new Battleship();
		battleship.placeShipAt(4, 9, false, ocean);

		//Parse over every spot of the empty ocean.
		for (int i = 0; i < ocean.getShipArray().length; i++) {
			for (int j = 0; j < ocean.getShipArray()[0].length; j++) {

				if (j == 9 && i >= 1 && i <= 4) {
					//When the slot is on the ship, the shiptype will be battleship for the given location.
					assertEquals("battleship", ocean.getShipArray()[i][j].getShipType());
				}else{
					//For all other slots, the shiptype is still empty
					assertEquals("empty", ocean.getShipArray()[i][j].getShipType());
				}
			}

		}
		//when there is a ship but it has been sunk. 
		//Place a submarine at (0, 3)
		Ship submarine = new Submarine();
		submarine.placeShipAt(0, 3, true, ocean);

		//Sink the ship with one hit.
		ocean.shootAt(0, 3);

		//The array should still prints the slot as occupied by a submarine
		assertEquals("submarine", ocean.getShipArray()[0][3].getShipType());

		//The hit array has been updated
		assertTrue(ocean.getShipArray()[0][3].getHit()[0]);
	}
  
  @Test
	void testIncrementShipsSunk() {
		//initiate a new empty ocean class, initial value of shipsSunk should be 0.
		Ocean ocean = new Ocean();
		assertTrue(ocean.getShipsSunk() ==  0);

		//increment ShipsSunk by 1
		ocean.incrementShipsSunk();
		assertEquals(1, ocean.getShipsSunk());

		//Place a submarine, and sink it.
		//Place a submarine at (0, 3)
		Ship submarine = new Submarine();
		submarine.placeShipAt(0, 3, true, ocean);

		//Hit the submarine. Check if it can be sunk.
		if (ocean.shootAt(0, 3) && ocean.getShipArray()[0][3].isSunk()) {
			ocean.incrementShipsSunk();
		}
		//Ships sunk should be incremented since the submarine has been sunk
		assertEquals(2, ocean.getShipsSunk());

		//Place another ship and hit at part of it
		Ship destroyer = new Destroyer();
		destroyer.placeShipAt(3, 5, true, ocean);

		//Hit a slot on the ship, the shipsSunk should remain the same.
		if (ocean.shootAt(3, 5) && ocean.getShipArray()[3][5].isSunk()) {
			ocean.incrementShipsSunk();
		}
		//The ships sunk should remain the same.
		assertEquals(0, ocean.getShipsSunk() - 2);

	}


}