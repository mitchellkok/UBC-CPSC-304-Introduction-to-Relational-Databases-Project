package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.PlayersModel;
import ca.ubc.cs304.model.City;

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
	
	public void deleteBranch(int branchId);
	public void insertBranch(BranchModel model);
	public void showBranch();
	public void updateBranch(int branchId, String name);

	// Players
	public void deletePlayer(int jerseynumber, String tname, String city);
	public void insertPlayer(PlayersModel model);
	public void showPlayer();
	public void updatePlayer(int jerseynumber, String tname, String city);

	// Coaches
	
	public void terminalTransactionsFinished();
}
