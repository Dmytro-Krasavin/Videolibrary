<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/header.jspf" charEncoding="utf-8"/>
<html>
	<head>
		<title>MyWebApp</title>
	</head>
	<body>
		<center>
			<form name="deleteOldFilms" method="post" action="http://localhost:8080/delete_old_films">
				<table>
					<tr>
						<td><B>Delete films older than </td>
						<td><input type=textbox name="yearNumber" size=5 value=""></td>
						<td><B> years</td>
					</tr>
				</table>
				<input type=submit value="Delete">
			</form>	
			
			<c:if test="${not empty deletedFilms}">
				<p><B>These films older ${yearNumber} year have been successfully deleted:<p>
				<table border=1>
					<tr bgcolor=silver>
						<td>id</td>
						<td>Film title</td>
						<td>Release data</td>
						<td>Country</td>
					</tr>
					<c:forEach var="film" items="${deletedFilms}" varStatus="status">
						<tr>
							<td><c:out value="${film.id}"/></td>
							<td><c:out value="${film.filmTitle}"/></td>
							<td><c:out value="${film.releaseDate}"/></td>
							<td><c:out value="${film.country}"/></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			
		</center>		
	</body>
</html>
