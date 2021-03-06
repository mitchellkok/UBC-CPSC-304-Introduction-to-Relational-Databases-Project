package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.PlayersModel;
import ca.ubc.cs304.model.CoachesModel;
import ca.ubc.cs304.model.MatchesModel;
import ca.ubc.cs304.model.TeamsModel;

import java.util.Date;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void databaseSetup();

	// Players
	public void deletePlayer(int jerseynumber, String tname, String city);
	public void insertPlayer(PlayersModel model);
	public void showPlayer();
	public void updatePlayerName(int jerseynumber, String tname, String city, String pname);
	public void updatePlayerAge(int jerseynumber, String tname, String city, int age);
	public void getCoachName(int jerseynumber, String tname, String city);
	public void getCityHigherThanAvgHeight();
	public void showTallPlayers(int height);

	// Coaches
	public void deleteCoach(int clicensenumber);
	public void insertCoach(CoachesModel model);
	public void showCoach();
	public void updateCoach(int clicensenumber, int age);
	public void showCoachOneAttribute(String attribute);


	// Teams
	public void deleteTeam(String tname, String city);
	public void insertTeam(TeamsModel model);
	public void showTeam();
	public void updateTeam(String tname, String city, int winpercent);
	public void getAvgWinPercent();


	// Matches
	public void deleteMatch(String mid);
	public void insertMatch(MatchesModel model);
	public void showMatch();
	public void updateMatchResult(String mid, String result);
	public void updateMatchDate(String mid, Date date);
	public void getNumMatchPlayed();


	public void terminalTransactionsFinished();
}
