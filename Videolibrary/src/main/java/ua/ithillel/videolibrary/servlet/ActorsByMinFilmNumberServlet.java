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

@WebServlet("/actors_min_films")
public class ActorsByMinFilmNumberServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/actors_min_films.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBProcessor db = new DBProcessor();
		
		int filmsNumber = Integer.parseInt(request.getParameter("filmsNumber"));
		
		List<Actor> actorsByFilmsNumber = null;
		try {
			actorsByFilmsNumber = db.findActorByMinFilmsNumber(filmsNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();

		request.setAttribute("filmsNumber", filmsNumber);
		request.setAttribute("actorsByFilmsNumber", actorsByFilmsNumber);
		request.getRequestDispatcher("/actors_min_films.jsp").forward(request, response);
	}
}
