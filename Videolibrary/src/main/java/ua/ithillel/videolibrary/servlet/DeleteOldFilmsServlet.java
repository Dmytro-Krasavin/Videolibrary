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
import ua.ithillel.videolibrary.entity.Film;

@WebServlet("/delete_old_films")
public class DeleteOldFilmsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/delete_old_films.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBProcessor db = new DBProcessor();
		
		int yearNumber = Integer.parseInt(request.getParameter("yearNumber"));
		
		List<Film> deletedFilms = null;
		try {
			deletedFilms = db.findFilmsOlder(yearNumber);
			db.deleteFilmsOlder(yearNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();

		request.setAttribute("yearNumber", yearNumber);
		request.setAttribute("deletedFilms", deletedFilms);
		request.getRequestDispatcher("/delete_old_films.jsp").forward(request, response);
	}
}
