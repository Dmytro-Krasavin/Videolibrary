package ua.ithillel.videolibrary.entity;

import java.sql.Date;
import java.util.List;

public abstract class AbstractCinemaWorker extends Entity {

	private String fullName;
	private Date dateOfBirth;
	private List<Film> filmList;

	public AbstractCinemaWorker() {
	}

	public AbstractCinemaWorker(int id, String fullName, Date dateOfBirth) {
		super(id);
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
	}

	public AbstractCinemaWorker(int id, String fullName, Date dateOfBirth, List<Film> filmList) {
		this(id, fullName, dateOfBirth);
		this.filmList = filmList;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Film> getFilmList() {
		return filmList;
	}

	public void setFilmList(List<Film> filmList) {
		this.filmList = filmList;
	}

	@Override
	public String toString() {
		return "[id=" + getId() + ", fullName=" + fullName + ", dateOfBirth="
				+ dateOfBirth + "]";
	}
	
}