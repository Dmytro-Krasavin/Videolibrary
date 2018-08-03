package ua.ithillel.videolibrary.entity;

import java.sql.Date;

public class Film extends Entity {

	private String filmTitle;
	private Date releaseDate;
	private String country;

	public Film() {
	}

	public Film(int id, String filmTitle, Date releaseDate, String country) {
		super(id);
		this.filmTitle = filmTitle;
		this.releaseDate = releaseDate;
		this.country = country;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Film [id=" + getId() + ", filmTitle=" + filmTitle + ", releaseDate="
				+ releaseDate + ", country=" + country + "]";
	}
}