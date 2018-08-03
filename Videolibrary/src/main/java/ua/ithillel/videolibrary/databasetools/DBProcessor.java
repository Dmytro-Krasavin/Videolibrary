package ua.ithillel.videolibrary.databasetools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import ua.ithillel.videolibrary.entity.Actor;
import ua.ithillel.videolibrary.entity.Director;
import ua.ithillel.videolibrary.entity.Film;

public class DBProcessor {

	private final static String SQL_FIND_FILMS_NEWER = "SELECT * FROM film WHERE release_data > ?";
	private final static String SQL_FIND_FILMS_OLDER = "SELECT * FROM film WHERE release_data < ?";
	private final static String SQL_FIND_DIRECTORS_WHO_WERE_ACTORS = "SELECT * FROM director, actor WHERE (director.full_name = actor.full_name AND director.date_of_birth = actor.date_of_birth)";
	private final static String SQL_FIND_FILMS_BY_DIRECTOR_ID = "SELECT * FROM film WHERE id in (SELECT film_id FROM film_to_director WHERE director_id = ?)";
	private final static String SQL_FIND_ACTORS_ON_FILM_TITLE = "SELECT * FROM actor WHERE id in (SELECT actor_id FROM film_to_actor WHERE film_id in (SELECT id FROM film WHERE movie_title = ?))";
	private final static String SQL_SELECT_ACTORS = "SELECT * FROM actor";
	private final static String SQL_SELECT_FILM_TO_ACTOR = "SELECT * FROM film_to_actor";
	private final static String SQL_DELETE_FILMS_OLDER = "DELETE FROM film WHERE release_data < ?";
	
	private Connection connect;

	public DBProcessor() {
		this.connect = ConnectorDB.getConnection();
	}

	public List<Film> findCurrentAndLastYearFilms() throws SQLException {
		final int DIFFERENCE_IN_YEARS = 2;
		return findFilmsNewer(DIFFERENCE_IN_YEARS);
	}

	public List<Director> findDirectorsWhoWhereActors() throws SQLException {
		Statement st = getStatement();

		List<Director> result = new ArrayList<Director>();
		ResultSet rs = st.executeQuery(SQL_FIND_DIRECTORS_WHO_WERE_ACTORS);
		while (rs.next()) {
			result.add(createDirectorFromRS(rs));
		}
		
		rs.close();
		closeStatement(st);
		return result;
	}

	public List<Actor> findActorsOnFilmTitle(String movieTitle) throws SQLException {
		PreparedStatement st = getPreparedStatement(SQL_FIND_ACTORS_ON_FILM_TITLE);
		st.setString(1, movieTitle);

		List<Actor> result = new ArrayList<Actor>();
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			result.add(createActorFromRS(rs));
		}

		rs.close();
		closeStatement(st);
		return result;
	}

	public List<Actor> findActorByMinFilmsNumber (int givenNumber) throws SQLException {		
		Statement st = getStatement();

		List<Integer> actorIdList = new ArrayList<Integer>();
		ResultSet rs = st.executeQuery(SQL_SELECT_FILM_TO_ACTOR);
		while (rs.next()) {
			int actorId = rs.getInt(3);
			actorIdList.add(actorId);
		}
		
		Set<Integer> resultActorIdList = actorIdList
				.stream()
				.filter((id) -> Collections.frequency(actorIdList, id) >= givenNumber)
				.collect(Collectors.toSet());

		StringJoiner idString = new StringJoiner(", ", " WHERE id in (", ")");
		for (int id : resultActorIdList) {
			idString.add(String.valueOf(id));
		}
		
		List<Actor> result = new ArrayList<Actor>();
		rs = st.executeQuery(SQL_SELECT_ACTORS + idString);
		while (rs.next()) {
			result.add(createActorFromRS(rs));
		}
		
		rs.close();
		closeStatement(st);
		return result;
	}

	public void deleteFilmsOlder(int year) throws SQLException {
		java.util.Date currentDate = new java.util.Date();
		int resultYear = (currentDate.getYear() + 1900) - year;
		Date correctDate = Date.valueOf(resultYear + "-01-01");
		
		PreparedStatement st = getPreparedStatement(SQL_DELETE_FILMS_OLDER);
		st.setDate(1, correctDate);
		st.executeUpdate();
		
		closeStatement(st);
	}
	
	public List<Film> findFilmsNewer(int yearsNumber) throws SQLException {
		return findNewOrOldFilms(yearsNumber, SQL_FIND_FILMS_NEWER);
	}
	
	public List<Film> findFilmsOlder(int yearsNumber) throws SQLException {
		return findNewOrOldFilms(yearsNumber, SQL_FIND_FILMS_OLDER);
	}
	
	public void closeConnection() {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				System.err.println("Connection close error: " + e);
				e.printStackTrace();
			}
		} else {
			System.err.println("Connection was not created");
		}
	}
	
	private List<Film> findNewOrOldFilms(int year, String sql) throws SQLException{
		java.util.Date currentDate = new java.util.Date();
		int resultYear = (currentDate.getYear() + 1900) - year;
		String correctDate = resultYear + "-01-01";
		
		PreparedStatement st = getPreparedStatement(sql);
		st.setString(1, correctDate);

		List<Film> newFilms = new ArrayList<Film>();
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			newFilms.add(createFilmFromRS(rs));
		}

		rs.close();
		closeStatement(st);
		return newFilms;
	}

	private List<Film> findFilmsByCinemaWorkerId (int id) throws SQLException {
		PreparedStatement st = getPreparedStatement(SQL_FIND_FILMS_BY_DIRECTOR_ID);
		st.setInt(1, id);
		
		List<Film> result = new ArrayList<Film>();
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			result.add(createFilmFromRS(rs));
		}
		
		rs.close();
		closeStatement(st);
		return result;
	}
	
	private void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.err.println("Statement close error: " + e);
				e.printStackTrace();
			}
		} else {
			System.err.println("Statement was not created");
		}
	}

	private Statement getStatement() {
		Statement statement = null;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			System.err.println("Creating statement error: " + e);
			e.printStackTrace();
		}
		return statement;
	}

	private PreparedStatement getPreparedStatement(String query) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connect.prepareStatement(query);
		} catch (SQLException e) {
			System.err.println("Creating statement error: " + e);
			e.printStackTrace();
		}
		return preparedStatement;
	}

	private Actor createActorFromRS(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		String fullName = rs.getString(2);
		Date dayOfBirth = rs.getDate(3);
		List<Film> filmList = findFilmsByCinemaWorkerId(id);
		return new Actor(id, fullName, dayOfBirth, filmList);
	}
	
	private Director createDirectorFromRS(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		String fullName = rs.getString(2);
		Date dayOfBirth = rs.getDate(3);
		List<Film> filmList = findFilmsByCinemaWorkerId(id);
		return new Director(id, fullName, dayOfBirth, filmList);
	}
	
	private Film createFilmFromRS(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		String filmTitle = rs.getString(2);
		Date releaseData = rs.getDate(3);
		String country = rs.getString(4);
		return new Film(id, filmTitle, releaseData, country);
	}

}