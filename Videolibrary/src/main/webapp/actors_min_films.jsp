<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/header.jspf" charEncoding="utf-8"/>
<html>
	<head>
		<title>MyWebApp</title>
	</head>
	<body>
		<center>
			<form name="actorsMinFilms" method="post" action="http://localhost:8080/actors_min_films">
				<table>
					<tr>
						<td><B>Enter the minimum films number to search actors:</td>
						<td><input type=textbox name="filmsNumber" size=25 value=""></td>
					</tr>
				</table>
				<input type=submit value="Search">
			</form>	
			
			<c:if test="${not empty actorsByFilmsNumber}">
				<p><B>Actors who starred at least in ${filmsNumber} films:<p>
				<table border=1>
					<tr bgcolor="silver">
						<td>id</td>
						<td>Full name</td>
						<td>Date of birth</td>
					</tr>
					<c:forEach var="actor" items="${actorsByFilmsNumber}" varStatus="status">
						<tr>
							<td><c:out value="${actor.id}"/></td>
							<td><c:out value="${actor.fullName}"/></td>
							<td><c:out value="${actor.dateOfBirth}"/></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>

		</center>		
	</body>
</html>
