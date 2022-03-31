package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.PlayersModel;
import ca.ubc.cs304.model.TeamsModel;

/**
 * The class is only responsible for handling terminal text inputs.
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}
	
	/**
	 * Sets up the database to have a branch table with two tuples so we can insert/update/delete from it.
	 * Refer to the databaseSetup.sql file to determine what tuples are going to be in the table.
	 */
	public void setupDatabase(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while(choice != 1 && choice != 2) {
			System.out.println("You have 4 tables in your database: Players, Coaches, Teams and Matches.\nIf you want to perform operations on the tables, enter 1; if you want to quit, enter 2.");
			
			choice = readInteger(false);
			
			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:
					delegate.databaseSetup();
					break;
				case 2:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.\n");
					break;
				}
			}
		}
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Players");
			System.out.println("2. Coaches");
			System.out.println("3. Teams");
			System.out.println("4. Matches");
			System.out.println("5. Quit");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:					// Players
					int choice1 = INVALID_INPUT;
					System.out.println("What operation do you want to perform on the table?");
					while (choice1 != 9){
						System.out.println();
						System.out.println("1. Insert player");
						System.out.println("2. Delete player");
						System.out.println("3. Update player name");
						System.out.println("4. Update player age");
						System.out.println("5. Show players");
						System.out.println("6. Show coach name of players");
						System.out.println("7. Show average height of players in each city");
						System.out.println("8. Show players taller than certain height");
						System.out.println("9. Quit");
						System.out.print("Please choose one of the above 9 options: ");

						choice1 = readInteger(false);

						System.out.println(" ");

						if (choice1 != INVALID_INPUT) {
							switch(choice1){
								case 1:
									handleInsertPlayerOption();
									break;
								case 2:
									handleDeletePlayerOption();
									break;
								case 3:
									handleUpdatePlayerNameOption();
									break;
								case 4:
									handleUpdatePlayerAgeOption();
									break;
								case 5:
									delegate.showPlayer();
									break;
								case 6:
									delegate.getAvgWinPercent();
									break;
								case 7:
									handleShowCoachOfPlayer();
									break;
								case 8:
									handleShowTallPlayers();
								case 9:
									handleQuitOption();
									break;
								default:
									System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
									break;
							}
						}
					}

				case 2:  			// Coaches
					int choice2 = INVALID_INPUT;
					System.out.println("What operation do you want to perform on the table?");
					while (choice2 != 6){
						System.out.println();
						System.out.println("1. Insert coach");
						System.out.println("2. Delete coach");
						System.out.println("3. Update coach age");
						System.out.println("4. Show coaches");
						System.out.println("5. Show attribute of coaches");
						System.out.println("6. Quit");
						System.out.print("Please choose one of the above 6 options: ");

						choice2 = readInteger(false);

						System.out.println(" ");

						if (choice2 != INVALID_INPUT) {
							switch(choice2){
								case 1:
									handleInsertCoachOption();
									break;
								case 2:
									handleDeleteCoachOption();
									break;
								case 3:
									handleUpdateCoachAgeOption();
									break;
								case 4:
									delegate.showCoach();
									break;
								case 5:
									int attributeChoice = INVALID_INPUT;
									while (attributeChoice != 5){
										System.out.println();
										System.out.println("1. Coach license numbers");
										System.out.println("2. Coach names");
										System.out.println("3. Coach genders");
										System.out.println("4. Coach ages");
										System.out.println("5. Quit");
										System.out.print("Please choose one of the above 5 options: ");

										attributeChoice = readInteger(false);

										System.out.println(" ");

										if (attributeChoice != INVALID_INPUT) {
											switch (attributeChoice) {
												case 1:
													delegate.showCoachOneAttribute("clicensenumber");
													break;
												case 2:
													delegate.showCoachOneAttribute("cname");
													break;
												case 3:
													delegate.showCoachOneAttribute("gender");
													break;
												case 4:
													delegate.showCoachOneAttribute("age");
													break;
												case 5:
													handleQuitOption();
											}
										}

									}
								case 6:
									handleQuitOption();
									break;
								default:
									System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
									break;
							}
						}
					}
				case 3: 
					handleUpdateOption();
					break;
				case 4:  
					delegate.showBranch(); 
					break;
				case 5:
					handleQuitOption();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}

	// Players
	private void handleInsertPlayerOption(){
		int jerseynumber = INVALID_INPUT;
		while (jerseynumber == INVALID_INPUT) {
			System.out.print("Please enter the jersey number you wish to insert: ");
			jerseynumber = readInteger(false);
		}

		String tname = null;
		while (tname == null || tname.length() <= 0) {
			System.out.print("Please enter the team name you wish to insert: ");
			tname = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city you wish to insert: ");
			city = readLine().trim();
		}

		String pname = null;
		while (pname == null || pname.length() <= 0) {
			System.out.print("Please enter the player name you wish to insert: ");
			pname = readLine().trim();
		}

		// height is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the height you wish to insert (enter 0 if you wish to leave it empty): ");
		int height = readInteger(false);

		// weight is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the weight you wish to insert (enter 0 if you wish to leave it empty): ");
		int weight = readInteger(false);

		// age is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the age you wish to insert (enter -1 if you wish to leave it empty): ");
		int age = readInteger(false);

		int clicensenumber = INVALID_INPUT;
		while (clicensenumber == INVALID_INPUT) {
			System.out.print("Please enter the coach license number you wish to insert: ");
			clicensenumber = readInteger(false);
		}

		PlayersModel model = new PlayersModel(
				jerseynumber,
				tname,
				city,
				pname,
				height,
				weight,
				age,
				clicensenumber);
		delegate.insertPlayer(model);
	}

	private void handleDeletePlayerOption() {
		int jerseynumber = INVALID_INPUT;
		while (jerseynumber == INVALID_INPUT) {
			System.out.print("Please enter the jersey number of the player you wish to delete: ");
			jerseynumber = readInteger(false);
		}

		String tname = null;
		while (tname == null || tname.length() <= 0) {
			System.out.print("Please enter the team name of the player you wish to delete: ");
			tname = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the player you wish to delete: ");
			city = readLine().trim();
		}

		delegate.deletePlayer(jerseynumber, tname, city);
	}

	private void handleUpdatePlayerNameOption() {
		int jerseynumber = INVALID_INPUT;
		while (jerseynumber == INVALID_INPUT) {
			System.out.print("Please enter the jersey number of the player you wish to update: ");
			jerseynumber = readInteger(false);
		}

		String tname = null;
		while (tname == null || tname.length() <= 0) {
			System.out.print("Please enter the team name of the player you wish to update: ");
			tname = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the player you wish to update: ");
			city = readLine().trim();
		}

		String pname = null;
		while (pname == null || pname.length() <= 0) {
			System.out.print("Please enter the new player name: ");
			pname = readLine().trim();
		}

		delegate.updatePlayerName(jerseynumber, tname, city, pname);
	}

	private void handleUpdatePlayerAgeOption() {
		int jerseynumber = INVALID_INPUT;
		while (jerseynumber == INVALID_INPUT) {
			System.out.print("Please enter the jersey number of the player you wish to update: ");
			jerseynumber = readInteger(false);
		}

		String tname = null;
		while (tname == null || tname.length() <= 0) {
			System.out.print("Please enter the team name of the player you wish to update: ");
			tname = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the player you wish to update: ");
			city = readLine().trim();
		}

		// age is allowed to be null if the user plan not to update it
		int age = INVALID_INPUT;
		while (age == INVALID_INPUT){
			System.out.print("Please enter the new age (enter -1 if you wish to leave it empty): ");
			age = readInteger(false);
		}

		delegate.updatePlayerAge(jerseynumber, tname, city, age);
	}

	private void handleShowCoachOfPlayer(){
		int jerseynumber = INVALID_INPUT;
		while (jerseynumber == INVALID_INPUT) {
			System.out.print("Please enter the jersey number of the player: ");
			jerseynumber = readInteger(false);
		}

		String tname = null;
		while (tname == null || tname.length() <= 0) {
			System.out.print("Please enter the team name of the player: ");
			tname = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the city of the player: ");
			city = readLine().trim();
		}

		delegate.getCoachName(jerseynumber, tname, city);
	}

	private void handleShowTallPlayers(){
		int height = INVALID_INPUT;
		while (height == INVALID_INPUT || height <= 0) {
			System.out.print("Please enter a height: ");
			height = readInteger(false);
		}

		delegate.showTallPlayers(height);
	}

	// Coaches
	private void handleInsertCoachOption(){
		int clicensenumber = INVALID_INPUT;
		while (clicensenumber == INVALID_INPUT) {
			System.out.print("Please enter the coach license number you wish to insert: ");
			clicensenumber = readInteger(false);
		}

		String cname = null;
		while (cname == null || cname.length() <= 0) {
			System.out.print("Please enter the coach name you wish to insert: ");
			cname = readLine().trim();
		}

		String gender = null;
		while (gender == null || gender.length() <= 0) {
			System.out.print("Please enter the gender you wish to insert: ");
			gender = readLine().trim();
		}

		// age is allowed to be null so we don't need to repeatedly ask for the address
		System.out.print("Please enter the age you wish to insert (enter -1 if you wish to leave it empty): ");
		int age = readInteger(false);

		CoachesModel model = new CoachesModel(
				clicensenumber,
				cname,
				gender,
				age);
		delegate.insertCoach(model);
	}

	private void handleDeleteCoachOption() {
		int clicensenumber = INVALID_INPUT;
		while (clicensenumber == INVALID_INPUT) {
			System.out.print("Please enter the coach license number of the coach you wish to delete: ");
			clicensenumber = readInteger(false);
		}

		delegate.deleteCoach(clicensenumber);
	}

	private void handleUpdateCoachAgeOption() {
		int clicensenumber = INVALID_INPUT;
		while (clicensenumber == INVALID_INPUT) {
			System.out.print("Please enter the coach license number of the coach you wish to update: ");
			clicensenumber = readInteger(false);
		}

		// age is allowed to be null if the user plan not to update it
		int age = INVALID_INPUT;
		while (age == INVALID_INPUT){
			System.out.print("Please enter the new age (enter -1 if you wish to leave it empty): ");
			age = readInteger(false);
		}

		delegate.updateCoach(clicensenumber, age);
	}

public void handleInsertTeamOption() {
    String tname = null;
    while (tname == null || tname.length() <= 0) {
        System.out.print("Please enter the team name you wish to insert: ");
        tname = readLine().trim();
    }

    String city = null;
    while (city == null || city.length() <= 0) {
        System.out.print("Please enter the city you wish to insert: ");
        city = readLine().trim();
    }

    System.out.print("Please enter the winning percent you wish to insert: ");
    int winpercent = readInteger(false);


    TeamsModel model = new TeamsModel(tname, city, winpercent);
    delegate.insertTeam(model);
}

public void handleDeleteTeamOption() {
    String tname = null;
    while (tname == null || tname.length() <= 0) {
        System.out.print("Please enter the team name of the team you wish to delete: ");
        tname = readLine().trim();
    }

    String city = null;
    while (city == null || city.length() <= 0) {
        System.out.print("Please enter the city of the team you wish to delete: ");
        city = readLine().trim();
    }

    delegate.deleteTeam(tname, city);

}

public void handleUpdateTeamOption() {
    String tname = null;
    while (tname == null || tname.length() <= 0) {
        System.out.print("Please enter the team name you wish to insert: ");
        tname = readLine().trim();
    }

    String city = null;
    while (city == null || city.length() <= 0) {
        System.out.print("Please enter the city you wish to insert: ");
        city = readLine().trim();
    }

    int winpercent = INVALID_INPUT;
    while (winpercent == INVALID_INPUT) {
        System.out.print("Please enter the winning percent you wish to update(enter -1 if you wish to leave it empty): ");
        winpercent = readInteger(false);
    }

    delegate.updateTeam(tname, city, winpercent);

}

private void handleDeleteOption() {
    int branchId = INVALID_INPUT;
    while (branchId == INVALID_INPUT) {
        System.out.print("Please enter the branch ID you wish to delete: ");
        branchId = readInteger(false);
        if (branchId != INVALID_INPUT) {
            delegate.deleteBranch(branchId);
        }
    }
}

private void handleInsertOption() {
    int id = INVALID_INPUT;
    while (id == INVALID_INPUT) {
        System.out.print("Please enter the branch ID you wish to insert: ");
        id = readInteger(false);
    }

    String name = null;
    while (name == null || name.length() <= 0) {
        System.out.print("Please enter the branch name you wish to insert: ");
        name = readLine().trim();
    }

    // branch address is allowed to be null so we don't need to repeatedly ask for the address
    System.out.print("Please enter the branch address you wish to insert: ");
    String address = readLine().trim();
    if (address.length() == 0) {
        address = null;
    }

    String city = null;
    while (city == null || city.length() <= 0) {
        System.out.print("Please enter the branch city you wish to insert: ");
        city = readLine().trim();
    }

    int phoneNumber = INVALID_INPUT;
    while (phoneNumber == INVALID_INPUT) {
        System.out.print("Please enter the branch phone number you wish to insert: ");
        phoneNumber = readInteger(true);
    }

    BranchModel model = new BranchModel(address,
            city,
            id,
            name,
            phoneNumber);
    delegate.insertBranch(model);
}

private void handleQuitOption() {
    System.out.println("Good Bye!");

    if (bufferedReader != null) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("IOException!");
        }
    }

    delegate.terminalTransactionsFinished();
}

private void handleUpdateOption() {
    int id = INVALID_INPUT;
    while (id == INVALID_INPUT) {
        System.out.print("Please enter the branch ID you wish to update: ");
        id = readInteger(false);
    }

    String name = null;
    while (name == null || name.length() <= 0) {
        System.out.print("Please enter the branch name you wish to update: ");
        name = readLine().trim();
    }

    delegate.updateBranch(id, name);
}

private int readInteger(boolean allowEmpty) {
    String line = null;
    int input = INVALID_INPUT;
    try {
        line = bufferedReader.readLine();
        input = Integer.parseInt(line);
    } catch (IOException e) {
        System.out.println(EXCEPTION_TAG + " " + e.getMessage());
    } catch (NumberFormatException e) {
        if (allowEmpty && line.length() == 0) {
            input = EMPTY_INPUT;
        } else {
            System.out.println(WARNING_TAG + " Your input was not an integer");
        }
    }
    return input;
}

private String readLine() {
    String result = null;
    try {
        result = bufferedReader.readLine();
    } catch (IOException e) {
        System.out.println(EXCEPTION_TAG + " " + e.getMessage());
    }
    return result;
}
}
