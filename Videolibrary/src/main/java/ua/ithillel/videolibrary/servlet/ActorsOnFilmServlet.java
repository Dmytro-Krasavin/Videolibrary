package ua.ithillel.videolibrary.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.ithillel.videolibrary.databasetools.DBProcessor;
import ua.ithillel.videolibrary.entity.Actor;

@WebServlet("/actors_on_film")
public class ActorsOnFilmServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/actors_on_film.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBProcessor db = new DBProcessor();
		
		String filmTitle = request.getParameter("film");
		
		List<Actor> actors = null;
		try {
			actors = db.findActorsOnFilmTitle(filmTitle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();

		request.setAttribute("film", filmTitle);
		request.setAttribute("actorsDirectors", actors);
		request.getRequestDispatcher("/actors_on_film.jsp").forward(request, response);
		
	}
}
