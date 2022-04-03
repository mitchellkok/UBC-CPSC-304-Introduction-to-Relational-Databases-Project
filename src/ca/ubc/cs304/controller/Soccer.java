package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;

import java.util.Date;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class Soccer implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public Soccer() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			TerminalTransactions transaction = new TerminalTransactions();
			transaction.setupDatabase(this);
			transaction.showMainMenu(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}
	


	// Player
	public void insertPlayer(PlayersModel model) {
		dbHandler.insertPlayer(model);
	}

	public void deletePlayer(int jerseynumber, String tname, String city) {
		dbHandler.deletePlayer(jerseynumber, tname, city);
	}

	public void updatePlayerName(int jerseynumber, String tname, String city, String pname) {
		dbHandler.updatePlayerName(jerseynumber, tname, city, pname);
	}

	public void updatePlayerAge(int jerseynumber, String tname, String city, int age){
		dbHandler.updatePlayerAge(jerseynumber, tname, city, age);
	}

	public void showPlayer() {
		PlayersModel[] models = dbHandler.getPlayerInfo();

		for (int i = 0; i < models.length; i++) {
			PlayersModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getJerseynumber());
			System.out.printf("%-20.20s", model.getTname());
			System.out.printf("%-20.20s", model.getCity());
			System.out.printf("%-20.20s", model.getPname());
			if (model.getHeight() == 0) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getHeight());
			}
			if (model.getHeight() == 0) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getWeight());
			}
			if (model.getHeight() == -1) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getAge());
			}
			System.out.printf("%-20.20s", model.getClicensenumber());

			System.out.println();
		}
	}


	public void getCoachName(int jerseynumber, String tname, String city){
		dbHandler.getCoachName(jerseynumber, tname, city);
	}

	public void getCityHigherThanAvgHeight(){
		dbHandler.getCityHigherThanAvgHeight();
	}

	public void showTallPlayers(int height){
		dbHandler.showTallPlayers(height);
	}




	// Coach
	public void insertCoach(CoachesModel model) {
		dbHandler.insertCoach(model);
	}

	public void deleteCoach(int clicensenumber) {
		dbHandler.deleteCoach(clicensenumber);
	}

	public void updateCoach(int clicensenumber, int age) {
		dbHandler.updateCoach(clicensenumber, age);
	}

	public void showCoach() {
		CoachesModel[] models = dbHandler.getCoachInfo();

		for (int i = 0; i < models.length; i++) {
			CoachesModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getClicensenumber());
			System.out.printf("%-20.20s", model.getCname());
			if (model.getGender() == " ") {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getGender());
			}
			if (model.getAge() == -1) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getAge());
			}

			System.out.println();
		}
	}

	public void showCoachOneAttribute(String attribute){
		dbHandler.showCoachOneAttribute(attribute);
	}


	// Team
	@Override
	public void deleteTeam(String tname, String city) {
		dbHandler.deleteTeam(tname, city);
	}

	@Override
	public void insertTeam(TeamsModel model) {
		dbHandler.insertTeam(model);
	}

	@Override
	public void showTeam() {
		TeamsModel[] models = dbHandler.getTeamInfo();

		for (int i = 0; i < models.length; i++) {
			TeamsModel model = models[i];

			// simplified output formatting; truncation may occur
			System.out.printf("%-20.20s", model.getTname());
			System.out.printf("%-20.20s", model.getCity());
//			System.out.printf("%-20.20s", model.getWinpercent());

			if (model.getWinpercent() == -1) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getWinpercent());
			}

			System.out.println();
		}
	}

	@Override
	public void updateTeam(String tname, String city, int winpercent) {
		dbHandler.updateTeam(tname, city, winpercent);
	}

	@Override
	public void getAvgWinPercent() {
		dbHandler.getAvgWinPercent();
	}

	@Override
	public void getNumMatchPlayed() {
		dbHandler.getNumMatchPlayed();
	}




	// Match
	public void deleteMatch(String mid) {
		dbHandler.deleteMatch(mid);
	}

	public void insertMatch(MatchesModel model) {
		dbHandler.insertMatch(model);
	}

	public void showMatch() {
		MatchesModel[] models = dbHandler.getMatchInfo();

		for (int i = 0; i < models.length; i++) {
			MatchesModel model = models[i];
			// simplified output formatting; truncation may occur
			System.out.printf("%-10.10s", model.getMid());
			System.out.printf("%-30.30s", model.getOname());
			System.out.printf("%-30.30s", model.getStname());
			System.out.printf("%-20.20s", model.getCityA());
			System.out.printf("%-20.20s", model.getTeamA());
			System.out.printf("%-20.20s", model.getCityB());
			System.out.printf("%-20.20s", model.getTeamB());
			System.out.printf("%-20.20s", model.getRentalFee());

			if (model.getResult() == null) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getResult());
			}

			if (model.getDate() == null) {
				System.out.printf("%-20.20s", " ");
			} else {
				System.out.printf("%-20.20s", model.getDate());
			}

			System.out.println();
		}
	}

	public void updateMatchResult(String mid, String result) {
		dbHandler.updateMatchResult(mid, result);
	}

	public void updateMatchDate(String mid, Date date) {
		dbHandler.updateMatchDate(mid, date);
	}


	/**
	 * TerminalTransactionsDelegate Implementation
	 *
     * The TerminalTransaction instance tells us that it is done with what it's
     * doing so we are cleaning up the connection since it's no longer needed.
     */
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;

    	System.exit(0);
    }

    /**
	 * TerminalTransactionsDelegate Implementation
	 *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
	public void databaseSetup() {
		dbHandler.databaseSetup();;

	}

	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		Soccer soccer = new Soccer();
		soccer.start();
	}
}
