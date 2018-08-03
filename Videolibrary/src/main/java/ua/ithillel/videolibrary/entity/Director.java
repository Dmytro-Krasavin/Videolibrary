package ua.ithillel.videolibrary.entity;

import java.sql.Date;
import java.util.List;

public class Director extends AbstractCinemaWorker {
	
	public Director() {
	}

	public Director(int id, String fullName, Date dateOfBirth) {
		super(id, fullName, dateOfBirth);
	}
	
	public Director(int id, String fullName, Date dateOfBirth, List<Film> filmList) {
		super(id, fullName, dateOfBirth, filmList);
	}

	@Override
	public String toString() {
		return "Director " + super.toString();
	}
}