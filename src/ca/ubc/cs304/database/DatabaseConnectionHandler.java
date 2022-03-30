package ca.ubc.cs304.database;

import java.sql.*;
import java.util.ArrayList;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.City;
import ca.ubc.cs304.model.PlayersModel;
import ca.ubc.cs304.model.TeamsModel;
import ca.ubc.cs304.model.CoachesModel;
import ca.ubc.cs304.model.MatchesModel;
import ca.ubc.cs304.model.TVModel;
import ca.ubc.cs304.util.PrintablePreparedStatement;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteBranch(int branchId) {
		try {
			String query = "DELETE FROM branch WHERE branch_id = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, branchId);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertBranch(BranchModel model) {
		try {
			String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, model.getId());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setString(4, model.getCity());
			if (model.getPhoneNumber() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getPhoneNumber());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();

		try {
			String query = "SELECT * FROM branch";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				BranchModel model = new BranchModel(rs.getString("branch_addr"),
						rs.getString("branch_city"),
						rs.getInt("branch_id"),
						rs.getString("branch_name"),
						rs.getInt("branch_phone"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new BranchModel[result.size()]);
	}

	public void updateBranch(int id, String name) {
		try {
			String query = "UPDATE branch SET branch_name = ? WHERE branch_id = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, name);
			ps.setInt(2, id);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Player
	public void deletePlayer(int jerseynumber, String tname, String city) {
		try {
			String query = "DELETE FROM branch WHERE jerseynumber = ? and tname = ? and city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, jerseynumber);
			ps.setString(2, tname);
			ps.setString(3, city);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Player " + jerseynumber + " in team " + tname + " in " + city + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertPlayer(PlayersModel model) {
		try {
			String query = "INSERT INTO Players VALUES (?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, model.getJerseynumber());
			ps.setString(2, model.getTname());
			ps.setString(3, model.getCity());
			ps.setString(4, model.getPname());
			ps.setInt(8, model.getClicensenumber());

			if (model.getHeight() == 0) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, model.getHeight());
			}

			if (model.getWeight() == 0) {
				ps.setNull(6, java.sql.Types.INTEGER);
			} else {
				ps.setInt(6, model.getWeight());
			}

			if (model.getAge() == -1) {
				ps.setNull(7, java.sql.Types.INTEGER);
			} else {
				ps.setInt(7, model.getAge());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public PlayersModel[] getPlayerInfo() {
		ArrayList<PlayersModel> result = new ArrayList<PlayersModel>();

		try {
			String query = "SELECT * FROM Players";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				PlayersModel model = new PlayersModel(
						rs.getInt("player_jerseynumber"),
						rs.getString("player_tname"),
						rs.getString("player_city"),
						rs.getString("player_pname"),
						rs.getInt("player_height"),
						rs.getInt("player_weight"),
						rs.getInt("player_age"),
						rs.getInt("player_clicencenumber"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new PlayersModel[result.size()]);
	}

	public void updatePlayerName(int jerseynumber, String tname, String city, String pname) {
		try {
			String query = "UPDATE branch SET pname = ? WHERE jerseynumber = ? and tname = ? and city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, pname);
			ps.setInt(2, jerseynumber);
			ps.setString(3, tname);
			ps.setString(4, city);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Player " + jerseynumber + " in team " + tname + " in " + city + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updatePlayerAge(int jerseynumber, String tname, String city, int age) {
		try {
			String query = "UPDATE branch SET age = ? WHERE jerseynumber = ? and tname = ? and city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(2, jerseynumber);
			ps.setString(3, tname);
			ps.setString(4, city);

			if (age == -1) {
				ps.setNull(1, java.sql.Types.INTEGER);
			} else {
				ps.setInt(1, age);
			}

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Player " + jerseynumber + " in team " + tname + " in " + city + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void getCoachName(int jerseynumber, String tname, String city){
		try{
			String query = "SELECT Cname FROM Players, Coaches WHERE jerseynumber = ? AND tname = ? and city = ? AND Players.clicensenumber = Coach.clicensenumber";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, jerseynumber);
			ps.setString(2, tname);
			ps.setString(3, city);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Player " + jerseynumber + " in team " + tname + " in " + city + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void getAvgHeightInCity(){
		try{
			String query = "SELECT AVG(height) FROM Players GROUP BY city";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " No city exists!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Coach
	public void deleteCoach(int clicensenumber) {
		try {
			String query = "DELETE FROM branch WHERE clicensenumber = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, clicensenumber);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Coach " + clicensenumber + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertCoach(CoachesModel model){
		try {
			String query = "INSERT INTO Coaches VALUES (?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, model.getClicensenumber());
			ps.setString(2, model.getCname());
			ps.setString(3, model.getGender());

			if (model.getAge() == 0) {
				ps.setNull(4, java.sql.Types.INTEGER);
			} else {
				ps.setInt(4, model.getAge());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateCoach(int clicensenumber, int age){
		try {
			String query = "UPDATE branch SET age = ? WHERE clicensenumber";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(2, clicensenumber);

			if (age == -1) {
				ps.setNull(1, java.sql.Types.INTEGER);
			} else {
				ps.setInt(1, age);
			}

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Coach " + clicensenumber + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public CoachesModel[] getCoachInfo() {
		ArrayList<CoachesModel> result = new ArrayList<>();

		try {
			String query = "SELECT * FROM Coaches";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				CoachesModel model = new CoachesModel(
						rs.getInt("coach_clicensenumber"),
						rs.getString("coach_cname"),
						rs.getString("coach_gender"),
						rs.getInt("coach_age"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new CoachesModel[result.size()]);
	}





	// Teams
	// TODO
	public void deleteTeam(String tname, String city){
		try {
			String query = "DELETE FROM team WHERE tname = ? AND city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tname);
			ps.setString(2, city);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Team " + tname + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertTeam(TeamsModel model){
		try {
			String query = "INSERT INTO team VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getTname());
			ps.setString(2, model.getCity());
			ps.setInt(3, model.getWinpercent());
			if (model.getTname() == null) {
				ps.setNull(1, java.sql.Types.INTEGER);
			} else {
				ps.setString(1, model.getTname());
			}

			if (model.getCity() == null) {
				ps.setNull(2, java.sql.Types.INTEGER);
			} else {
				ps.setString(2, model.getCity());
			}

			if (model.getWinpercent() == -1) {
				ps.setNull(3, java.sql.Types.INTEGER);
			} else {
				ps.setInt(3, model.getWinpercent());
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public void updateTeam(String tname, String city, int winpercent){
		try {
			String query = "UPDATE team SET winpercent = ? WHERE tname = ? AND city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tname);
			ps.setString(2, city);
			ps.setInt(3, winpercent);


			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Team " + tname + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}



	public TeamsModel[] getTeamInfo(){
		ArrayList<TeamsModel> result = new ArrayList<TeamsModel>();

		try {
			String query = "SELECT * FROM teams";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				TeamsModel model = new TeamsModel(
						rs.getString("tname"),
						rs.getString("city"),
						rs.getInt("winpercent"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new TeamsModel[result.size()]);
	}




	// Average winpercent of all teams
	public void getAvgWinPercent() {
		try{
			String query = "SELECT AVG(winpercent) FROM Teams";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " No city exists!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}



	// Number of matches a team has played
	public void getNumMatchPlayed() {
		try{
			String query = "SELECT COUNT(teamA) + COUNT(teamB) FROM Matches GROUP BY (teamA UNION teamB)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " No city exists!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}


	// Matches
	// TODO
	public void deleteMatch(String mid){
		try {
			String query = "DELETE FROM matches WHERE mid = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, mid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Match " + mid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertMatch(MatchesModel model){
		try {
			String query = "INSERT INTO matches VALUES (?,?,?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getMid());
			ps.setString(3, model.getOname());
			ps.setString(4, model.getStname());
			ps.setString(5, model.getCityA());
			ps.setString(6, model.getTeamA());
			ps.setString(7, model.getCityB());
			ps.setString(8, model.getTeamB());
			ps.setInt(9, model.getRentalfee());

			if (model.getResult() == null) {
				ps.setNull(2, java.sql.Types.INTEGER);
			} else {
				ps.setString(2, model.getResult());
			}

			if (model.getDate() == null) {
				ps.setNull(10, java.sql.Types.INTEGER);
			} else {
				long dateLong = model.getDate().getTime();
				ps.setDate(10, new java.sql.Date(dateLong));
			}

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateMatchResult(String mid, String result){
		try {
			String query = "UPDATE matches SET result = ? WHERE mid = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(2, mid);

			if (result == null) {
				ps.setNull(1, java.sql.Types.INTEGER);
			} else {
				ps.setString(1, result);
			}

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Match " + mid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateMatchDate(String mid, Date date){
		try {
			String query = "UPDATE matches SET date = ? WHERE mid = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(2, mid);

			if (date == null) {
				ps.setNull(1, java.sql.Types.INTEGER);
			} else {
				ps.setDate(1, date);
			}

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Match " + mid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public MatchesModel[] getMatchInfo(){
		ArrayList<MatchesModel> result = new ArrayList<MatchesModel>();

		try {
			String query = "SELECT * FROM matches";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				MatchesModel model = new MatchesModel(
						rs.getString("mid"),
						rs.getString("oname"),
						rs.getString("stname"),
						rs.getString("cityA"),
						rs.getString("teamA"),
						rs.getString("cityB"),
						rs.getString("teamB"),
						rs.getInt("rentalfee"),
						rs.getDate("date"),
						rs.getString("result"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new MatchesModel[result.size()]);
	}

	// Number of matches a team has played
	public void getAllTVWithAllMatches() {
		try{
			String query = "SELECT bname FROM TV T WHERE NOT EXISTS((SELECT M.mid FROM Matches M) EXCEPT (SELECT L.mid FROM Livestreams L WHERE L.bname = T.bname))";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " No city exists!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}

	}



	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}



	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void databaseSetup() {
		dropBranchTableIfExists();

		try {
			String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
		insertBranch(branch1);

		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
		insertBranch(branch2);
	}

	private void dropBranchTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("branch")) {
					ps.execute("DROP TABLE branch");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}
}
