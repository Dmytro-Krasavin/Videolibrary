package ua.ithillel.videolibrary.entity;

import java.sql.Date;
import java.util.List;

public class Actor extends AbstractCinemaWorker {
	
	List<Film> filmList;

	public Actor() {
	}
	
	public Actor(int id, String fullName, Date dateOfBirth) {
		super(id, fullName, dateOfBirth);
	}
	
	public Actor(int id, String fullName, Date dateOfBirth, List<Film> filmList) {
		super(id, fullName, dateOfBirth, filmList);
	}

	@Override
	public String toString() {
		return "Actor " + super.toString();
	}
}