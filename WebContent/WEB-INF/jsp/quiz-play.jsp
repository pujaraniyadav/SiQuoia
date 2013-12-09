<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="proj.webapp.*" %>
<html>

<head>
    <meta http-equiv="content-type" content="text/html; charset=
    UTF-8"/>
    <link href="css/quiz.css" media="screen" rel="stylesheet" type="text/css"/>
    <title>SiQuoia :: Play</title>

</head>

<body class="Main">
	<jsp:include page="html/header.html" />

	<div class="Form" id="start_div">
	
   		<h1 class="FormHeading">${quizname}</h1>
		
		<form:form name="GameForm" method="post" action="quiz-play.q" commandName="config">
			<c:if test="${not empty error}">
				<div class="FieldDiv">
					<div class="Error">${error}</div>
				</div>
			</c:if>

			<div class="FieldDiv">
				<div class="QAndA">
					<div>${question}</div>
					<br/>
					<c:choose>
						<c:when test="${not firsttry}">
							<form:radiobuttons path="answer" items="${answers}" element="div" disabled="true" />
						</c:when>
						<c:otherwise>
							<form:radiobuttons path="answer" items="${answers}" element="div" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<br/>
			<div class="FieldDiv">
				<a class="SaveButton" href="javascript:" onclick="document.GameForm.action='quiz-play.q?op=next';document.GameForm.submit()">Next</a>
				<a class="SaveButton" href="javascript:" onclick="document.GameForm.action='quiz-play.q?op=skip';document.GameForm.submit()">Skip</a>
				<a class="SaveButton" href="javascript:" onclick="document.GameForm.action='quiz-play.q?op=cancel';document.GameForm.submit()">Cancel</a>			
				<c:if test="${mode !=  'Learner'}">
					<label id="timeleft" style="float:right;" />
				</c:if>
			</div>
			<br/>
		</form:form>
		
	</div>

<c:if test="${mode != 'Learner'}">
	<script>
	
	var timeleft = ${timeleft};
	var myVar=setInterval(function(){myTimer()} , 1000);
	
	myTimer();
	
	function myTimer()
	{
		if (timeleft > 0) timeleft = timeleft - 1;
		var str = Math.floor(timeleft / 60) + "m " + (timeleft % 60) + "s";
		document.getElementById("timeleft").innerHTML = str;
	}
	
	</script>
</c:if>
</body>
</html>

