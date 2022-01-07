package battleship;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	Ocean ocean;
	Ship ship;
  Ship battleship;
	Ship cruiser;
	Ship destroyer;
	Ship emptySea;
	
	@BeforeEach
	void setUp() throws Exception {
		ocean = new Ocean();
    

		cruiser = new Cruiser();
		destroyer = new Destroyer();
		emptySea = new EmptySea();

	}

	@Test
	void testGetLength() {
		ship = new Battleship();
		assertEquals(4, ship.getLength());
		
		//TODO
		//More tests
    //Test for cruiser length
    assertEquals(3, cruiser.getLength());
		
    //Test for destroyer length
		assertEquals(2, destroyer.getLength());
		
    //Test for emptySea legth.
		assertEquals(1, emptySea.getLength());
    
	}

	@Test
	void testGetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		
		//TODO
		//More tests
		
    //Place a cruiser at 7,2, vertically
		cruiser.placeShipAt(7, 2, false, ocean);
		assertEquals(7, cruiser.getBowRow());
		
    //Place a destroyer at 5,6 horizontally
		destroyer.placeShipAt(5, 6, true, ocean);
		assertEquals(5, destroyer.getBowRow());
	}

	@Test
	void testGetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());	
		
		//TODO
		//More tests
		
    //Place a cruiser at 7,2, vertically
		cruiser.placeShipAt(7, 2, false, ocean);
		assertEquals(2, cruiser.getBowColumn());
		
     //Place a destroyer at 5,6 horizontally
		destroyer.placeShipAt(5, 6, true, ocean);
		assertEquals(6, destroyer.getBowColumn());
	}

	@Test
	void testGetHit() {
		ship = new Battleship();
		boolean[] hits = new boolean[4];
		assertArrayEquals(hits, ship.getHit());
		assertFalse(ship.getHit()[0]);
		assertFalse(ship.getHit()[1]);
		
		//TODO
		//More tests
    //Place a cruiser at (7,2) vertically, shoot at [5,2], the cruiser should be hit once
    cruiser.placeShipAt(7, 2, false, ocean);
		ocean.shootAt(5, 2);
		boolean[] cruiserHit = {false, false, true, false};
		assertArrayEquals(cruiserHit, cruiser.getHit());
		
    //Place a destroyer at (5,6) horizontally, shoot twice, the destroyer should be hit twice
		destroyer.placeShipAt(5, 6, true, ocean);
		ocean.shootAt(5, 5);
		ocean.shootAt(5, 6);
		boolean[] destroyerHit = {true, true, false, false};
		assertArrayEquals(destroyerHit, destroyer.getHit());
		
    //Shoot at empty Sea, and the empty Sea should be hit once.
		ocean.shootAt(3, 8);
		boolean[] emptySeaHit = {true, false, false, false};
		assertArrayEquals(emptySeaHit, ocean.getShipArray()[3][8].getHit());
	}
  
  
	@Test
	void testGetShipType() {
		ship = new Battleship();
		assertEquals("battleship", ship.getShipType());
		
		//TODO
		//More tests
    //Get cruiser type
    assertEquals("cruiser", cruiser.getShipType());
		
    //Get destroyer type
		assertEquals("destroyer", destroyer.getShipType());
		
    //Get empty sea type
		assertEquals("empty", emptySea.getShipType());
	}
	
	@Test
	void testIsHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertTrue(battleship.isHorizontal());
		
		//TODO
		//More tests	
     //Place a battleship at 1,4, horizontally.
    battleship.placeShipAt(1, 4, true, ocean);
		assertTrue(battleship.isHorizontal());
		
       //Place a cruiser at 7,2, vertically.
		cruiser.placeShipAt(7, 2, false, ocean);
		assertFalse(cruiser.isHorizontal());
		
       //Place a destroyer at 5,6, horizontally.
		destroyer.placeShipAt(5, 6, true, ocean);
		assertTrue(destroyer.isHorizontal());

	}
	
	@Test
	void testSetBowRow() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowRow(row);
		assertEquals(row, battleship.getBowRow());
		
		//TODO
		//More tests
		
    //Set bow row of the cruiser to 7
		cruiser.setBowRow(7);
		assertEquals(7,cruiser.getBowRow());
		
    //Set destroyer bow row to 5
		destroyer.setBowRow(5);
		assertEquals(5, destroyer.getBowRow());
	}

	@Test
	void testSetBowColumn() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setBowColumn(column);
		assertEquals(column, battleship.getBowColumn());
		
		//TODO
		//More tests
		
    //Set cruiser bow column to 2
		cruiser.setBowColumn(2);
		assertEquals(2, cruiser.getBowColumn());
		
    //Set destroyer bow column to 6
		destroyer.setBowColumn(6);
		assertEquals(6, destroyer.getBowColumn());
	}

	@Test
	void testSetHorizontal() {
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.setHorizontal(horizontal);
		assertTrue(battleship.isHorizontal());
		
		//TODO
		//More tests
		
    //Set cruiser horizontal to be false
		cruiser.setHorizontal(false);
		assertFalse(cruiser.isHorizontal());
		
    //Set destroyer horizontal to be true
		destroyer.setHorizontal(true);
		assertTrue(destroyer.isHorizontal());
	}

	@Test
	void testOkToPlaceShipAt() {
		
		//test when other ships are not in the ocean
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok, "OK to place ship here.");
		
		//TODO
		//More tests
		
    //It's okay to place a destroyer at 6,4, horizontally or vertically
		assertTrue(destroyer.okToPlaceShipAt(3, 4, true, ocean));
		
		assertTrue(destroyer.okToPlaceShipAt(3, 4, false, ocean));
    
    //It's not okay to place a destroyer at (0,4) vertically since it will be out of border
    assertFalse(destroyer.okToPlaceShipAt(0,4,false, ocean));
    
 

	}
	
	@Test
	void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {
		
		//test when other ships are in the ocean
		
		//place first ship
		Battleship battleship1 = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
		assertTrue(ok1, "OK to place ship here.");
		battleship1.placeShipAt(row, column, horizontal, ocean);

		//test second ship
		Battleship battleship2 = new Battleship();
		row = 1;
		column = 4;
		horizontal = true;
		boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
		assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
		
		//TODO
		//More tests
    //It's not okay to place a destroyer at (1,4) horizontally since it's adjacent to the battleship1
    assertFalse(destroyer.okToPlaceShipAt(1,4,true, ocean));
    
    //It's not okay to place a destroyer at (2,5) horizontally since its adjacent diagonally to battleship1
    assertFalse(destroyer.okToPlaceShipAt(1,5,true,ocean));
    
	}

	@Test
	void testPlaceShipAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 4;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		assertEquals(row, battleship.getBowRow());
		assertEquals(column, battleship.getBowColumn());
		assertTrue(battleship.isHorizontal());
		
		assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
		assertEquals(battleship, ocean.getShipArray()[0][1]);
		

		//TODO
		//More tests
    //Place a cruiser at (7,2) vertically
    cruiser.placeShipAt(7, 2, false, ocean);
		assertEquals(cruiser.getLength(), 3);
		assertFalse(ocean.getShipArray()[6][2].isHorizontal());
		
    //Place a destroyer at (5,6) horizontally
		destroyer.placeShipAt(5, 6, true, ocean);
		boolean[] destroyerHit = {false, false, false, false};
		assertArrayEquals(destroyer.getHit(), destroyerHit);
		assertEquals(ocean.getShipArray()[5][5].getShipType(), "destroyer");
	}

	@Test
	void testShootAt() {
		
		Ship battleship = new Battleship();
		int row = 0;
		int column = 9;
		boolean horizontal = true;
		battleship.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(battleship.shootAt(1, 9));
		boolean[] hitArray0 = {false, false, false, false};
		assertArrayEquals(hitArray0, battleship.getHit());
		
		//TODO
		//More tests
    
    //Place a destroyer at (5,6) horizontally
    destroyer.placeShipAt(5, 6, true, this.ocean);
		
		//Init an emptySea
		boolean[] emptySeaHit1 = {false, false, false, false};
		assertArrayEquals(emptySea.getHit(), emptySeaHit1);
		assertArrayEquals(ocean.getShipArray()[0][0].getHit(), emptySeaHit1);
		//shoot emptySea, the first element of emptysea will be hit
		assertFalse(ocean.getShipArray()[0][0].shootAt(0, 0));
		boolean[] emptySeaHit2 = {true, false, false, false};
		assertArrayEquals(ocean.getShipArray()[0][0].getHit(), emptySeaHit2);
		
		//destroyer shoot the bow, the destroyer is hit but not sunk
		assertTrue(destroyer.shootAt(5, 6));
		boolean[] destroyerHit1 = {true, false, false, false};
		assertArrayEquals(destroyer.getHit(), destroyerHit1);
		
		//destroyer shoot the stern, this time it's sunk
		assertTrue(destroyer.shootAt(5, 5));
		//sunk
		assertFalse(ocean.getShipArray()[5][5].shootAt(5, 5));
		boolean[] destroyerHit2 = {true, true, false, false};
		assertArrayEquals(destroyer.getHit(), destroyerHit2);
	}
	
	@Test
	void testIsSunk() {
		
		Ship submarine = new Submarine();
		int row = 3;
		int column = 3;
		boolean horizontal = true;
		submarine.placeShipAt(row, column, horizontal, ocean);
		
		assertFalse(submarine.isSunk());
		assertFalse(submarine.shootAt(5, 2));
		assertFalse(submarine.isSunk());
		
		//TODO
		//More tests
    //emptySea is not sunk even if hit once
		assertFalse(emptySea.isSunk());
		assertFalse(ocean.getShipArray()[0][0].isSunk());
		ocean.getShipArray()[0][0].shootAt(0, 0);
		assertFalse(ocean.getShipArray()[0][0].isSunk());
    
    //Destroyer is sunk when hit twice.
		destroyer.placeShipAt(5, 6, true, ocean);
		assertFalse(destroyer.isSunk());
		assertFalse(ocean.getShipArray()[5][5].isSunk());
		
		//shoot the destroyer once, not sunk
		ocean.getShipArray()[5][6].shootAt(5, 6);
		assertFalse(destroyer.isSunk());
		assertFalse(ocean.getShipArray()[5][5].isSunk());
		
		//shoot twice, sunk
		ocean.getShipArray()[5][5].shootAt(5, 5);
		assertTrue(destroyer.isSunk());
		assertTrue(ocean.getShipArray()[5][5].isSunk());
		
	}

	@Test
	void testToString() {
		
		Ship battleship = new Battleship();
		assertEquals("x", battleship.toString());
		
		int row = 9;
		int column = 1;
		boolean horizontal = false;
		battleship.placeShipAt(row, column, horizontal, ocean);
		battleship.shootAt(9, 1);
		assertEquals("x", battleship.toString());
		
		//TODO
		//More tests
    
    //When noting is shoot at all, the emptySea prints "-"
		assertEquals(ocean.getShipArray()[0][0].toString(), "-");
    
    //Place a destroyer at 5,6, horizontally 
    destroyer.placeShipAt(5, 6, true, ocean);
    
    //shoot destroyer at 5, 6
		ocean.getShipArray()[5][6].shootAt(5, 6);
		assertEquals(ocean.getShipArray()[5][6].toString(), "x");
    assertEquals(ocean.getShipArray()[5][5].toString(), "x");
    
    //shoot again at 5,6
    ocean.getShipArray()[5][6].shootAt(5, 6);
		assertEquals(ocean.getShipArray()[5][6].toString(), "x");
    
    //shoot at 5,5, the destroyer is sunk
    ocean.getShipArray()[5][6].shootAt(5, 5);
		assertEquals(ocean.getShipArray()[5][6].toString(), "s");
    assertEquals(ocean.getShipArray()[5][5].toString(), "s");
    
    
	}

}