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
import ua.ithillel.videolibrary.entity.Director;

@WebServlet("/actors_directors")
public class ActorsDirectorsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBProcessor db = new DBProcessor();
		
		List<Director> directorActorList = null;
		try {
			directorActorList = db.findDirectorsWhoWhereActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		
		request.setAttribute("directorActorList", directorActorList);
		request.getRequestDispatcher("/actors_directors.jsp").forward(request, response);
	}
	
}
