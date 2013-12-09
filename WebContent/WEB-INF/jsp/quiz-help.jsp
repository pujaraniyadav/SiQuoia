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
    <title>${journal.title}</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />

     <div class="Form">
     	<div class="FieldDiv">
     		<h1 class="FormHeading">Help</h1>
			<div class="QAndA">
			<h4>
1. SiQuoia is basically a multiple choice type of quiz program with each set consisting of one question followed by four answers with one and only one of them being correct.
<br />2. Question sets are categorized into topics and subtopics, upto three levels.
<br />3. Only paid users/gamers can continue playing beyond the sample set. 
<br />4. As players progress through levels, achievements are tracked and points awarded. Points accumulated can be later exchanged for rewards such as quiz packets and game memorabilia.
<br />5. Gamers can submit questions (each with four relevant answers of which one and only one is correct) from within the game.
<br />6. Questions submitted will be verified, edited and categorized (Topic, subtopics) with in the system. 
<br />7. Games are made available in question packets that can be purchased with either cash or reward points.
<br />8. Game status for each gamer is stored when a gamer leaves the system and restored when a gamer logs in again.
<br />9. The system also maintains and monitors a leaderboard.
			</h4>
			</div>
		</div>
		
		<div class="FieldDiv">
			<a class="SaveButton" href="home.q">Home</a>
	 	</div>
	 </div>
		
</body>
</html>

