package ca.ubc.cs304.database;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import ca.ubc.cs304.model.*;
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
			String query = "DELETE FROM Players WHERE (jerseynumber = ? and tname = ? and city = ?)";
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
						rs.getInt("jerseynumber"),
						rs.getString("tname"),
						rs.getString("city"),
						rs.getString("pname"),
						rs.getInt("height"),
						rs.getInt("weight"),
						rs.getInt("age"),
						rs.getInt("clicensenumber"));
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
			String query = "UPDATE Players SET pname = ? WHERE (jerseynumber = ? AND tname = ? AND city = ?)";
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
			String query = "UPDATE Players SET age = ? WHERE (jerseynumber = ? AND tname = ? AND city = ?)";
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
			String query = "SELECT cname FROM Players, Coaches WHERE jerseynumber = ? AND tname = ? AND city = ? AND Players.clicensenumber = Coaches.clicensenumber";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, jerseynumber);
			ps.setString(2, tname);
			ps.setString(3, city);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Player " + jerseynumber + " in team " + tname + " in " + city + " does not exist!");
			}
			else {
				ArrayList<String> result = new ArrayList<String>();
				ResultSet rs = ps.executeQuery();
				System.out.println();
				while (rs.next()) {
					result.add(rs.getString("cname"));
				}

				for (int i = 0; i < result.size(); i++) {
					System.out.printf("%-10.10s", result.get(i));
					System.out.println();
				}
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void getCityHigherThanAvgHeight(){
		try{
			String query = "WITH temp(city, avgHeight) AS (SELECT city, avg(height) AS avgHeight FROM Players GROUP BY city) SELECT city from temp WHERE avgHeight > (SELECT avg(avgHeight) from temp)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if(rowCount == 0){
				System.out.println(WARNING_TAG + " No city exists!");
			}
			else{
				ArrayList<String> result = new ArrayList<String>();
				ResultSet rs = ps.executeQuery();
				System.out.println();
				while(rs.next()) {
					result.add(rs.getString("city"));
				}

				for (int i = 0; i < result.size(); i++) {
					System.out.printf("%-10.10s", result.get(i));
					System.out.println();
				}
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void showTallPlayers(int height){
		try{
			String query = "SELECT pname FROM Players WHERE height > ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, height);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Players taller than " + height + " does not exist!");
			}
			else{
				ArrayList<String> result = new ArrayList<String>();
				ResultSet rs = ps.executeQuery();
				System.out.println();
				while(rs.next()) {
					result.add(rs.getString("pname"));
				}

				for (int i = 0; i < result.size(); i++) {
					System.out.printf("%-10.10s", result.get(i));
					System.out.println();
				}
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
			String query = "DELETE FROM Coaches WHERE clicensenumber = ?";
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
			String query = "UPDATE Coaches SET age = ? WHERE clicensenumber = ?";
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
						rs.getInt("clicensenumber"),
						rs.getString("cname"),
						rs.getString("gender"),
						rs.getInt("age"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new CoachesModel[result.size()]);
	}

	public void showCoachOneAttribute(String attribute){
		try{
			String query = "";
			if (attribute == "clicensenumber")
				query = "SELECT clicensenumber FROM Coaches";
			else if (attribute == "cname")
				query = "SELECT cname FROM Coaches";
			else if (attribute == "gender")
				query = "SELECT gender FROM Coaches";
			else if (attribute == "age")
				query = "SELECT age FROM Coaches";

			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + attribute + " does not exist!");
			}
			else{
				ArrayList<String> result = new ArrayList<String>();
				ResultSet rs = ps.executeQuery();
				System.out.println();
				while(rs.next()) {
					if (attribute == "clicensenumber")
						result.add(rs.getString("clicensenumber"));
					else if (attribute == "cname")
						result.add(rs.getString("cname"));
					else if (attribute == "gender")
						result.add(rs.getString("gender"));
					else if (attribute == "age")
						result.add(rs.getString("age"));
				}

				for (int i = 0; i < result.size(); i++) {
					System.out.printf("%-10.10s", result.get(i));
					System.out.println();
				}
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
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
			String query = "INSERT INTO Teams VALUES (?,?,?)";
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
			String query = "UPDATE Teams SET winpercent = ? WHERE tname = ? AND city = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, tname);
			ps.setString(2, city);
			ps.setInt(3, winpercent);


			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Team " + tname + " in " + city + " does not exist!");
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
			String query = "SELECT * FROM Teams";
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
			String query = "SELECT AVG(winpercent) AS avgWinpercent FROM Teams";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " No city exists!");
			}
			else{
				ArrayList<String> result = new ArrayList<String>();
				ResultSet rs = ps.executeQuery();
				System.out.println();
				while(rs.next()) {
					result.add(rs.getString("avgWinpercent"));
				}

				for (int i = 0; i < result.size(); i++) {
					System.out.printf("%-10.10s", result.get(i));
					System.out.println();
				}
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
			String query = "SELECT COUNT(team) " +
					"FROM ((SELECT teamA AS team FROM Matches) UNION (SELECT teamB AS team FROM Matches))" +
					"GROUP BY (team)";
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
			String query = "INSERT INTO Matches VALUES (?,?,?,?,?,?,?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getMid());
			ps.setString(2, model.getOname());
			ps.setString(3, model.getStname());
			ps.setString(4, model.getCityA());
			ps.setString(5, model.getTeamA());
			ps.setString(6, model.getCityB());
			ps.setString(7, model.getTeamB());
			ps.setInt(8, model.getRentalFee());

			if (model.getDate() == null) {
				ps.setNull(9, java.sql.Types.INTEGER);
			} else {
				long dateLong = model.getDate().getTime();
				ps.setDate(9, new java.sql.Date(dateLong));
			}

			if (model.getResult() == null) {
				ps.setNull(10, java.sql.Types.INTEGER);
			} else {
				ps.setString(10, model.getResult());
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
				ps.setDate(1, (java.sql.Date) date);
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
						rs.getDate("matchdate"),
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

	// TV
	public void insertTV(TVModel model){
		try {
			String query = "INSERT INTO TV VALUES (?,?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getBname());
			ps.setString(2, model.getCountry());
			ps.setInt(3, model.getContact());
			ps.setInt(4, model.getChannelnumber());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Livestream
	public void insertLivestream(LivestreamsModel model){
		try {
			String query = "INSERT INTO Livestreams VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, model.getBname());
			ps.setString(2, model.getCountry());
			ps.setString(3, model.getMid());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	// Number of matches a team has played
	public void getAllTVWithAllMatches() {
		try{
			String query = "SELECT T.bname FROM TV T WHERE NOT EXISTS((SELECT M.mid FROM Matches M) MINUS (SELECT L.mid FROM Livestreams L WHERE L.bname = T.bname))";
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
		dropLivestreamsTableIfExists();
		dropMatchesTableIfExists();
		dropPlayersTableIfExists();
		dropTVTableIfExists();
		dropTeamsTableIfExists();
		dropCoachesTableIfExists();
		dropOrganizersTableIfExists();
		dropStadiumsTableIfExists();
		dropLocationsTableIfExists();
		dropCitiesTableIfExists();

		try {
			String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE TV (" +
					"bname char(40), " +
					"country char(40), " +
					"contact integer NOT NULL, " +
					"channelnumber integer NOT NULL, " +
					"PRIMARY KEY (bname, country), " +
					"UNIQUE (contact)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Cities (" +
					"city varchar(40), " +
					"country char(40) NOT NULL, " +
					"PRIMARY KEY (city)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Teams (" +
					"tname varchar(40), " +
					"city varchar(40), " +
					"winpercent integer, " +
					"PRIMARY KEY (tname, city), " +
					"FOREIGN KEY (city) REFERENCES Cities" +
					" ON DELETE CASCADE" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Coaches (" +
					"clicensenumber integer, " +
					"cname char(40) NOT NULL, " +
					"gender char(10), " +
					"age integer, " +
					"PRIMARY KEY (clicensenumber)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Players (" +
					"jerseynumber integer, " +
					"tname varchar(40), " +
					"city varchar(40), " +
					"pname char(40) NOT NULL, " +
					"height integer, " +
					"weight integer, " +
					"age integer, " +
					"clicensenumber integer NOT NULL, " +
					"PRIMARY KEY (tname, city, jerseynumber), " +
					"FOREIGN KEY (tname, city) REFERENCES Teams " +
					" ON DELETE CASCADE, " +
					"FOREIGN KEY (clicensenumber) REFERENCES Coaches" +
                    " ON DELETE CASCADE)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Locations (" +
					"address char(40) NOT NULL, " +
					"postalcode char(6) NOT NULL, " +
					"PRIMARY KEY (address)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}


		try {
			String query = "CREATE TABLE Stadiums (" +
					"stname char(40), " +
					"address char(40), " +
					"capacity integer, " +
					"PRIMARY KEY (stname)" +
//					"FOREIGN KEY (address) REFERENCES Locations," +
//					"UNIQUE (address)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Organizers(" +
					"oname char(40), " +
					"PRIMARY KEY (oname)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Matches (" +
					"mid char(10), " +
					"oname char(40) NOT NULL, " +
					"stname char(40) NOT NULL, " +
					"cityA varchar(40) NOT NULL, " +
					"teamA varchar(40) NOT NULL, " +
					"cityB varchar(40) NOT NULL, " +
					"teamB varchar(40) NOT NULL, " +
					"rentalfee integer, " +
					"matchdate date, " +
					"result char(10), " +
					"PRIMARY KEY (mid), " +
					"FOREIGN KEY (oname) REFERENCES Organizers, " +
					"FOREIGN KEY (stname) REFERENCES Stadiums, " +
					"FOREIGN KEY (teamA, cityA) REFERENCES Teams (tname, city), " +
					"FOREIGN KEY (teamB, cityB) REFERENCES Teams (tname, city)" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "CREATE TABLE Livestreams (" +
					"bname char(40), " +
					"country char(40), " +
					"mid char(10), " +
					"FOREIGN KEY (bname, country) REFERENCES TV" +
					" ON DELETE CASCADE, " +
					"FOREIGN KEY (mid) REFERENCES Matches" +
					" ON DELETE CASCADE" +
					")";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		// MISC INSERT STATEMENTS
		try {
			String query = "INSERT INTO Organizers VALUES (?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "FIFA");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Organizers VALUES (?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Junior Football League");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Organizers VALUES (?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Senior Football League");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Stadiums VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "BC Place");
			ps.setString(2, "123 BC Place");
			ps.setInt(3, 12345);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Stadiums VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Thunderbird Stadium");
			ps.setString(2, "456 Thunderbird Stadium");
			ps.setInt(3, 34567);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Stadiums VALUES (?,?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Tokyo Dome");
			ps.setString(2, "789 Tokyo Dome");
			ps.setInt(3, 56789);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Cities VALUES (?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Manchester");
			ps.setString(2, "UK");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Cities VALUES (?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Liverpool");
			ps.setString(2, "UK");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Cities VALUES (?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Chelsea");
			ps.setString(2, "UK");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		try {
			String query = "INSERT INTO Cities VALUES (?,?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setString(1, "Madrid");
			ps.setString(2, "Spain");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
		insertBranch(branch1);

		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
		insertBranch(branch2);

		// Insert Teams
		TeamsModel team1 = new TeamsModel("Liverpool", "Liverpool", 77);
		insertTeam(team1);

		TeamsModel team2 = new TeamsModel("Manchester United", "Manchester", 66);
		insertTeam(team2);

		TeamsModel team3 = new TeamsModel("Real Madrid", "Madrid", 22);
		insertTeam(team3);

		TeamsModel team4 = new TeamsModel("Chelsea", "Chelsea", 11);
		insertTeam(team4);

		// Insert Coaches
		CoachesModel coach1 = new CoachesModel(12345, "Jim Slim", "Male", 55);
		insertCoach(coach1);

		CoachesModel coach2 = new CoachesModel(67890, "Ben Ten", "Male", 44);
		insertCoach(coach2);

		// Insert Matches
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01");
			MatchesModel match1 = new MatchesModel("ASD432", "FIFA", "Thunderbird Stadium", "Manchester", "Manchester United", "Liverpool", "Liverpool", 10000, date, "5-6");
			insertMatch(match1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1997-12-05");
			MatchesModel match3 = new MatchesModel("ZXC098", "Senior Football League", "BC Place", "Chelsea", "Chelsea", "Liverpool", "Liverpool", 5000, date, "2-3");
			insertMatch(match3);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2007-03-25");
			MatchesModel match2 = new MatchesModel("QWE765", "Junior Football League", "Tokyo Dome", "Madrid", "Real Madrid", "Liverpool", "Liverpool", 25000, date, "6-7");
			insertMatch(match2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2027-09-15");
			MatchesModel match4 = new MatchesModel("FGH135", "FIFA", "Tokyo Dome", "Madrid", "Real Madrid", "Manchester", "Manchester United", 15000, date, "");
			insertMatch(match4);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Insert TV
		TVModel tv1 = new TVModel("ABC", "USA", 93461996, 101);
		insertTV(tv1);

		TVModel tv2 = new TVModel("CNN", "USA", 94866588, 202);
		insertTV(tv2);

		TVModel tv3 = new TVModel("Sportsnet", "Canada", 75054932, 303);
		insertTV(tv3);

		// Insert Livestream
		LivestreamsModel livestream1 = new LivestreamsModel("ABC", "USA", "ASD432");
		insertLivestream(livestream1);

		LivestreamsModel livestream2 = new LivestreamsModel("ABC", "USA", "QWE765");
		insertLivestream(livestream2);

		LivestreamsModel livestream3 = new LivestreamsModel("ABC", "USA", "ZXC098");
		insertLivestream(livestream3);

		LivestreamsModel livestream4 = new LivestreamsModel("ABC", "USA", "FGH135");
		insertLivestream(livestream4);

		LivestreamsModel livestream5 = new LivestreamsModel("CNN", "USA", "QWE765");
		insertLivestream(livestream5);

		LivestreamsModel livestream6 = new LivestreamsModel("CNN", "USA", "ZXC098");
		insertLivestream(livestream6);

		LivestreamsModel livestream7 = new LivestreamsModel("CNN", "USA", "FGH135");
		insertLivestream(livestream7);

		LivestreamsModel livestream8 = new LivestreamsModel("Sportsnet", "Canada", "ZXC098");
		insertLivestream(livestream8);

		LivestreamsModel livestream9 = new LivestreamsModel("Sportsnet", "Canada", "FGH135");
		insertLivestream(livestream9);

		// Insert Players
		PlayersModel player1 = new PlayersModel(1, "Liverpool", "Liverpool", "John Blonde", 175, 75, 33, 12345);
		insertPlayer(player1);

		PlayersModel player2 = new PlayersModel(2, "Liverpool", "Liverpool", "Jack Black", 185, 85, 34, 12345);
		insertPlayer(player2);

		PlayersModel player3 = new PlayersModel(3, "Liverpool", "Liverpool", "Jay Gray", 165, 65, 35, 12345);
		insertPlayer(player3);

		PlayersModel player4 = new PlayersModel(11, "Manchester United", "Manchester", "Billy Klub", 167, 67, 36, 67890);
		insertPlayer(player4);

		PlayersModel player5 = new PlayersModel(22, "Manchester United", "Manchester", "Bobby Pynn", 177, 77, 37, 67890);
		insertPlayer(player5);

		PlayersModel player6 = new PlayersModel(33, "Manchester United", "Manchester", "Barry Caid", 187, 87, 38, 67890);
		insertPlayer(player6);
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

	private void dropTVTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("tv")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE TV");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropTeamsTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("teams")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Teams");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropCitiesTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("cities")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Cities");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropPlayersTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("players")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Players");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropCoachesTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals("coaches")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Coaches");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropMatchesTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("matches")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Matches");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropLivestreamsTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("livestreams")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Livestreams");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropLocationsTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("locations")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Locations");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


	private void dropStadiumsTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("stadiums")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Stadiums");
					break;
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	private void dropOrganizersTableIfExists() {
		try {
			String query = "select table_name from user_tables";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				if (rs.getString(1).toLowerCase().equals("organizers")) {
					System.out.println(rs.getString(1).toLowerCase());
					ps.execute("DROP TABLE Organizers");
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
