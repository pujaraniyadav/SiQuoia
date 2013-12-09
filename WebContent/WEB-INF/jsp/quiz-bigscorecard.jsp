<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="proj.webapp.*" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="css/quiz.css" media="screen" rel="stylesheet" type="text/css"/>
    <title>SiQuoia :: BigScoreCard</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />
	
     <div class="Form">
      	<h1 class="FormHeading">Big Score Card</h1>
      	<div class="FieldDiv">
	 		<div class="QAndA">
	 			<table>
	 				<tr>
	 					<td width="50%"><u><b>Game</b></u></td>
	 					<td width="50%"><u><b>Score</b></u></td>
	 					<td width="50%"><u><b>MaxScore</b></u></td>
	 				</tr>
	 				<c:forEach var="game" items="${games}">
  						<tr>
  							<td>${game.gameName}</td>
  							<td>${game.gameScore}</td>
  							<td>${game.gameMaxScore}</td>
  						</tr>
					</c:forEach>
	 			</table>
	 		</div>

			<br/>
			<div class="QAndA">
				<h3>Total Score is ${total}</h3>
				<h3>Total purchase credits ${totalCredit}</h3>
			</div>

	 	 	<br/>
	 		<a class="SaveButton" href="home.q">Go Home</a>
	 	</div>
	 </div>
	 		
</body>
</html>

