package battleship;

//
import java.util.Scanner;

/**
 * The main class for a Human v.s. Computer version of the battleship
 * Create a single instance of Ocean. Get user input for interacting with and play against Computer.
 *
 */
public class BattleshipGame {
	
	/**
	 * Print game rules and welcoming message.
	 */
	private void printGameRules() {
		
		System.out.println("Welcome to the battleship game!\n");
		
		//Prompt ship types
		System.out.println("There are four types of ships: \n" 
				+ "1 Battleship occupying 4 slots;\n" 
				+ "2 Cruisers occupying 3 slots each;\n"
				+ "3 Destroyers occupying 2 slots each;\n"
				+ "4 Submarines occupiying 1 slot each.\n");
		
		//Indicate game rules.
		System.out.println("Initially all the ships will be placed on the ocean and hided.\n"
				+ "You can indicate a slot on the ocean that you would like to fire on each turn.\n"
				+ "A \".\" represents a slot that hasn't been fired at.\n"
				+ "A \"-\" represents a slot that has been fired but no ships exists.\n"
				+ "An \"x\" represents a slot that has been fired upon and a ship is hit.\n" 
				+ "An \"s\" represents a slot with a sunk ship.\n");

		System.out.println("Your will play against the computer.\n" 
				+ "Your goal is to sink all 10 ships with as few shots as possible.\n"
				+ "Enjoy the game!\n");
	}
	
	/**
	 * This function fires a shot at the given location,
	 * and determine whether or not the user has hit a ship.
	 * It prints a message telling user whether or not the hit is successful.
	 * If a ship is sunk, it will print the ship type.
	 * @param int row, 
	 * @param int column
	 * @param Ocean ocean
	 */
	private void fireShotAndUpdate(int row, int column, Ocean ocean) {
		
		//fire a shot at the given location and acquire the boolean value.
		boolean hit = ocean.shootAt(row, column);
		
		if (!hit) {
			//If the hit was missed.
			System.out.println("Oops. Your shot was missed.\n");

		}else {
			//if the shot hits a ship
			//if the ship then has been sunk, prompt user of the ship type sunk
			if (ocean.getShipArray()[row][column].isSunk()) {
				
				//Update the shipSunk in ocean class.
				ocean.incrementShipsSunk();
				
				//Printout the sunk shipType
				System.out.println("Congratulations! You just sunk a " 
						+ ocean.getShipArray()[row][column].getShipType() + ".\n");
				System.out.println();		
				
			}else {
				//if the ship hasn't been sunk
				System.out.println("You hit a ship.\n");
			}
		}		
	}
	
	/**
	 * Prints out final results of the game.
	 * Prompt user of shots fired, shots hit the ship
	 * @param Ocean ocean
	 */
	private void printGameresults(Ocean ocean) {
		
		System.out.println("Congratulations! You have sunk all 10 ships!");
		System.out.println();
		
		//Prompt user of total shots fired.
		System.out.println("You fired " + ocean.getShotsFired() + " shots in total.");
		
		//Prompt user of how many shots hit a ship.
		System.out.println("Out of which " + ocean.getHitCount() + " shots hit a ship.");
		
		//Prompt user of minimal shots needed
		System.out.println("A minimum of 20 shots are required to sink all ship.");
		System.out.println();
		
		//Print the current ocean.
		System.out.println("The current ocean is: ");
		ocean.print();
	}

	public static void main(String[] args) {
		
		//create a new game class
		BattleshipGame game = new BattleshipGame();
		
		Scanner sc = new Scanner(System.in);

		//set up a boolean value for recording whether or not to start the game.
		boolean toPlay = true;

		//put the game run in a while loop which keeps running until toPlay is false.
		while (toPlay) {
			
			//Print out a welcome message and game rules.
			game.printGameRules();
			
			//set up an empty ocean.
			Ocean ocean = new Ocean();

			//place ship at the ocean.
			ocean.placeAllShipsRandomly();

			//print the  view of sea.
			System.out.println("=================================================================");
			System.out.println("The current sea is: ");
      ocean.printWithShips();
			ocean.print();
			
			//ocean.printSea();
			
			//set up a game loop running until the game is over.
			while(!ocean.isGameOver()) {
				
				//Find out the borders of the ocean.
				int verticalBorder = ocean.getShipArray().length - 1;
				int horizontalBorder = ocean.getShipArray()[0].length - 1;
				
				//Set up value for the column and row. By default is 10
				int row = verticalBorder + 1;
				int column = horizontalBorder + 1;

				//set up a while loop that keeps running until the column and row have been modified
				while (column > verticalBorder && row > horizontalBorder) {

					//Print guidance to the user.
					System.out.println("Enter the location you would like to shoot as row, column: ");

					//ask for user input
					String answer = sc.nextLine().trim();

					//if the answer contains a comma
					if (answer.contains(",")){

						//split the answer based on comma.
						String[] answerSplited = answer.split("\\s*,\\s*");

						//when there're only two elements in the array
						if (answerSplited.length == 2) {
							
							try {
								//try to cast elements into integers
								int rowEntered = Integer.parseInt(answerSplited[0].trim());
								int columnEntered = Integer.parseInt(answerSplited[1].trim());

								//Only updates row and column when it's in range.
								if (rowEntered <= verticalBorder && rowEntered >= 0 
										&& columnEntered <= horizontalBorder && columnEntered >= 0) {
									
									//update row and column
									row = rowEntered;
									column = columnEntered;
									
								}else {
									//Otherwise prompt user that he/she has entered a number out of the range.
									System.out.println("Please enter row/column number in the range of [0, 9], both inclusive.\n");
								}
								
							}catch(NumberFormatException e){
								//prompt user that he/she has not entered a valid answer.
								System.out.println(e.getStackTrace() + "Please enter in the format of \"row, column\".\n");
							}	
							
						}else {
							//prompt user that he/she entered with wrong format.
							System.out.println("Please enter in the format of \"row, column\".\n");
						}	
						
					} else {
						//prompt user that he/she entered with wrong format.
						System.out.println("Please enter in the format of \"row, column\".\n");
					}
				}
				
				//Check if the ship has been sunk and if this shot hits a ship. If both yes, update ships sunk
				game.fireShotAndUpdate(row, column, ocean);
	
				//Print the ocean to the player
				System.out.println("===================================================");
				System.out.println("The current sea is: ");
				ocean.print();
				System.out.println();
			}
			
			//When the game round is over, print the final results.
			game.printGameresults(ocean);
			
			//Set up a variable recording answer from user.
			String answer = "";
			
			//Set up a while loop that will run until the input answer starts with designated characters.
			while (!answer.startsWith("Y") && !answer.startsWith("y") 
					&& !answer.startsWith("n") && !answer.startsWith("N")) {
				
				//Ask the user if he/she wants to play again
				System.out.println("Do you want to play again? Enter y or Y for yes, n or N for no");
				answer = sc.next().trim();
			}
				
			//when the answer starts with N or n. toPlay is false.
			if (answer.startsWith("N") || answer.startsWith("n")) toPlay = false;
		}
		
		sc.close();
	}
}


