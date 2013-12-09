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
    <title>SiQuoia :: Home</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />
	
<div id="main" class="Form">

	<c:if test="${session.username != 'admin'}">
	
	<!--  Play menus -->

      		<h1 class="FormHeading">Player Menu</h1>
			<form:form name="PlayForm" method="post" action="submit-play.q" commandName="config">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">Category</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="category0" style="width:60%;" onchange="this.form.action='submit-play-cat.q?c=0';this.form.submit()">
						<form:options items="${categories0}" />
					</form:select>
				</div>
				<div class="FieldLabel">Sub-Category</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="category1" style="width:60%;" onchange="this.form.action='submit-play-cat.q?c=1';this.form.submit()">
						<form:options items="${categories1}" />
					</form:select>
				</div>
				<div class="FieldLabel">Topic</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="category2" style="width:60%;" onchange="this.form.action='submit-play-cat.q?c=2';this.form.submit()">
						<form:options items="${categories2}" />
					</form:select>
				</div>

				<div class="FieldLabel">Game</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="game" style="width:80%;">
						<form:options items="${games}" />
					</form:select>
				</div>

				<div class="FieldLabel">Play mode</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="mode" style="width:80%;">
						<form:options items="${modes}" />
					</form:select>
				</div>
								
				<br/>
				<div class="FieldDiv">
					<input type="submit" class="SaveButton" value="Play !" />
				</div>
			</form:form>
	
	</c:if>
	
	<c:if test="${session.username == 'admin'}">
		<h1 class="FormHeading">Admin Menu</h1>
	</c:if>
	
	<!--  Manage menus -->
		
			<form:form name="PlayForm" action="submit-play.q">									
				<br/>
				<div class="FieldDiv">
					<c:if test="${session.username != 'admin'}">
						<a href="bigscorecard.q">ScoreCard</a>  |
						<a href="buy.q">Buy Game</a>  |
						<a href="question.q">Submit Question</a>  |
						<a href="help.q">Help</a>  |
					</c:if>
					<c:if test="${session.username == 'admin'}">
						<a href="start-create-quiz.q">Create Quiz</a>|
						<a href="delete-quiz.q">Delete Quiz</a>|
					</c:if>
					<a href="logout.q">Logout</a>
				</div>
			</form:form>
			
</div>

</body>
</html>

